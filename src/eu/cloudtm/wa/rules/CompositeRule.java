package eu.cloudtm.wa.rules;

import eu.cloudtm.wa.rules.exception.RuleException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author pruivo
 * Date: 11/25/11
 * Time: 5:05 PM
 *
 * this class represents a composition of rules with a logical operator (AND or OR)
 *
 *     < rule, operator, rule >
 *
 * See {@link Rule} for more info
 */
public class CompositeRule implements Rule {

    public static enum Operator {
        AND,
        OR
    }

    private Rule rule1;
    private Rule rule2;
    private Operator operator;

    public CompositeRule(Rule rule1, Rule rule2, Operator operator) {
        this.rule1 = rule1;
        this.rule2 = rule2;
        this.operator = operator;
    }

    public boolean evaluate(Map<Metric, Object> values) throws RuleException {
        boolean b1 = rule1.evaluate(values);
        boolean b2 = rule2.evaluate(values);
        if(operator == CompositeRule.Operator.AND) {
            return b1 && b2;
        } else {
            return b1 || b2;
        }
    }

    public Set<Metric> getMetricsNeeded() {
        Set<Metric> metrics = new HashSet<Metric>();
        metrics.addAll(rule1.getMetricsNeeded());
        metrics.addAll(rule2.getMetricsNeeded());
        return metrics;
    }
}
