package eu.cloudtm.wa.rules;

import eu.cloudtm.wa.parser.StatsMeasurement;
import eu.cloudtm.wa.rules.exception.RuleException;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * @author pruivo
 * Date: 11/25/11
 * Time: 12:14 PM
 *
 * This represents a simple rule. a simple rule is composed by
 *
 *       < metric, operator, valueExpected >
 *
 * Note: the valueExpected, can be a metric
 */
public class SimpleRule implements Rule{

    public static enum Operator {
        LESS,
        LESS_OR_EQUAL,
        GREATER,
        GREATER_OR_EQUAL,
        EQUAL,
        NOT_EQUAL
    }

    private Object valueExpected;
    private Metric metric;
    private Operator operator;

    /**
     * it constructs a rule
     *
     * @param metric the metric
     * @param operator the operator
     * @param valueExpected the value expected
     */
    public SimpleRule(Metric metric, Operator operator, Object valueExpected) {
        this.metric = metric;
        this.operator = operator;
        this.valueExpected = valueExpected;
    }

    /**
     * see {@link Rule}
     *
     * @param values the metrics values present in this rule
     * @return true or false
     * @throws RuleException
     */
    @SuppressWarnings({"SuspiciousMethodCalls"})
    public boolean evaluate(Map<Metric, Object> values) throws RuleException {
        Object value1 = getValueFromMetric(values, metric);
        Object value2;

        if(valueExpected instanceof Metric) {
            value2 = getValueFromMetric(values, (Metric) valueExpected);
        } else {
            value2 = valueExpected;
        }

        if(value1 == null || value2 == null) {
            throw new RuleException("one of the values received is null");
        }

        //System.out.println("compare " + value1 + ":" + isANumber(value1) + " with " + value2 + ":" + isANumber(value2));

        //both values are a number (Integer, Double, etc...)
        if(isANumber(value2) && isANumber(value1)) {
            return compareNumber((Number)value2, (Number) value1);
        }

        //it is a discrete type or something else. it is compared by the method equals()
        return compareRawType(value2, value1);
    }

    /**
     * see {@link Rule}
     *
     * @return a set of {@link Metric}
     */
    public Set<Metric> getMetricsNeeded() {
        Set<Metric> metrics = new HashSet<Metric>(1);
        metrics.add(metric);
        if(valueExpected instanceof Metric) {
            metrics.add((Metric) valueExpected);
        }
        return metrics;
    }

    /**
     * it verifies if the @param is a number
     *
     * @param obj the object to be verify
     * @return true if it is an instance of {@link Number}, false otherwise
     */
    private boolean isANumber(Object obj) {
        return obj instanceof Number;
    }

    /**
     * it compares two objects via {@link #equals(Object)}
     *
     * @param expected the value expected
     * @param value the value obtained
     * @return true if they are equal, false otherwise
     * @throws RuleException when the values cannot be compared, or the {@link #operator} is different from
     *         Equal or NOT_EQUAL
     */
    private boolean compareRawType(Object expected, Object value) throws RuleException {
        if(operator == Operator.EQUAL) {
            return expected.equals(value);
        } else if(operator == Operator.NOT_EQUAL) {
            return !expected.equals(value);
        }
        throw new RuleException("Type mismatch. expected is " + expected.getClass() + " and the type received is" +
                value.getClass());
    }

    /**
     * it compares two number based on the {@link #operator}
     *
     * @param expected the value expected
     * @param value the value obtained
     * @return true or false
     */
    private boolean compareNumber(Number expected, Number value) {
        if(operator == Operator.LESS) {
            return value.floatValue() < expected.floatValue();
        } else if(operator == Operator.LESS_OR_EQUAL) {
            return value.floatValue() <= expected.floatValue();
        } else if(operator == Operator.GREATER) {
            return value.floatValue() > expected.floatValue();
        } else if(operator == Operator.GREATER_OR_EQUAL) {
            return value.floatValue() >= expected.floatValue();
        } else if(operator == Operator.EQUAL) {
            return value.floatValue() == expected.floatValue();
        }

        return value.floatValue() != expected.floatValue();
    }

    /**
     * it obtains the value of a {@link Metric}s.
     *
     * If the metric value is an instance of {@link StatsMeasurement}, then the value can be the maximum, the minimum
     * or the average. By default,it returns the average value
     *
     * @param map the values of the metrics
     * @param metric the metric wanted
     * @return the metric value
     */
    private static Object getValueFromMetric(Map<Metric, Object> map, Metric metric) {
        Object val = map.get(metric);
        if(val instanceof StatsMeasurement) {
            Metric.Value v = metric.getValue();
            if(v == Metric.Value.MIN) {
                return ((StatsMeasurement) val).getMinValue();
            } else if(v == Metric.Value.MAX) {
                return ((StatsMeasurement) val).getMaxValue();
            } else {
                return ((StatsMeasurement) val).getAverageValue();
            }
        }
        return val;
    }
}
