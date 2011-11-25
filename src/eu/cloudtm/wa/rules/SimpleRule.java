package eu.cloudtm.wa.rules;

import eu.cloudtm.wa.StatsMeasurement;
import eu.cloudtm.wa.rules.exception.RuleException;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pruivo
 * Date: 11/25/11
 * Time: 12:14 PM
 *
 * This represents a simple rule. a simple rule is composed by
 *
 *       < metric, operator, valueExpected >
 *
 */
public class SimpleRule<T> implements Rule{

    public static enum Operator {
        LESS,
        LESS_OR_EQUAL,
        GREATER,
        GREATER_OR_EQUAL,
        EQUAL,
        NOT_EQUAL
    }

    private T valueExpected;
    private Metric metric;
    private Operator operator;

    public SimpleRule(Metric metric, Operator operator, T valueExpected) {
        this.metric = metric;
        this.operator = operator;
        this.valueExpected = valueExpected;
    }


    @SuppressWarnings({"SuspiciousMethodCalls"})
    public boolean evaluate(Map<Metric,StatsMeasurement> values) throws RuleException {
        Object value1 = values.get(metric);
        Object value2;

        if(valueExpected instanceof Metric) {
            value2 = values.get(valueExpected);
        } else {
            value2 = valueExpected;
        }


        if(value1 == null || value2 == null) {
            throw new RuleException("one of the values received is null");
        }

        if(isANumber(value2.getClass()) && isANumber(value1.getClass())) {
            compareNumber((Number)value2, (Number) value1);
        }
        return compareRawType(value2, value1);
    }

    public Set<Metric> getMetricsNeeded() {
        Set<Metric> metrics = new HashSet<Metric>(1);
        metrics.add(metric);
        return metrics;
    }

    private boolean isANumber(Class<?> clazz) {
        return clazz == Integer.class ||
                clazz == Double.class ||
                clazz == Long.class ||
                clazz == Float.class;
    }

    private boolean compareRawType(Object expected, Object value) throws RuleException {
        if(operator == Operator.EQUAL) {
            return expected.equals(value);
        } else if(operator == Operator.NOT_EQUAL) {
            return !expected.equals(value);
        }
        throw new RuleException("Type mismatch. expected is " + expected.getClass() + " and the type received is" +
                value.getClass());
    }

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
}
