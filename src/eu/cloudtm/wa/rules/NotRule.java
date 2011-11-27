package eu.cloudtm.wa.rules;

import eu.cloudtm.wa.rules.exception.RuleException;

import java.util.Map;
import java.util.Set;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 5:15 PM
 *
 * this class represents the *logical not* rule
 *  < NOT, rule >
 *
 * See {@link Rule} for more info
 */
public class NotRule implements Rule {

    private Rule rule;

    public NotRule(Rule rule) {
        this.rule = rule;
    }

    public boolean evaluate(Map<Metric, Object> values) throws RuleException {
        return !rule.evaluate(values);
    }

    public Set<Metric> getMetricsNeeded() {
        return rule.getMetricsNeeded();
    }
}
