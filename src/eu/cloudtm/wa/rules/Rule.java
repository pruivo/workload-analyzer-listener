package eu.cloudtm.wa.rules;

import eu.cloudtm.wa.parser.StatsMeasurement;
import eu.cloudtm.wa.rules.exception.RuleException;

import java.util.Map;
import java.util.Set;

/**
 * @author pruivo
 * Date: 11/25/11
 * Time: 12:12 PM
 *
 * This interface represents a rule. A rule is composed by a set of conditions and each condition can compare one or
 * more {@link Metric}s.
 *
 * This interface has a method do evaluate the rule ({@link #evaluate(java.util.Map)}) and a method to know what are
 * the {@link Metric}s present on this rule ({@link #getMetricsNeeded()})
 */
public interface Rule {

    /**
     * it evaluates the rule based replacing the metrics by their values
     *
     * @param values the metrics values present  in this rule
     * @return true or false, accordingly to the rule result is true or false
     * @throws RuleException when one of the metric value is not known or when the type of the metric value is not
     *         comparable
     */
    boolean evaluate(Map<Metric, Object> values) throws RuleException;

    /**
     * obtains a set of all metrics present in this rule. This method is particularly useful to know the values to be
     * passed to the {@link #evaluate(java.util.Map)}
     *
     * @return a set of {@link Metric}s
     */
    Set<Metric> getMetricsNeeded();
}
