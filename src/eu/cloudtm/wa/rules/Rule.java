package eu.cloudtm.wa.rules;

import eu.cloudtm.wa.StatsMeasurement;
import eu.cloudtm.wa.rules.exception.RuleException;

import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: pruivo
 * Date: 11/25/11
 * Time: 12:12 PM
 *
 * interface of a rule
 */
public interface Rule {

    //this evaluate the rule based on the values of each metric
    boolean evaluate(Map<Metric,StatsMeasurement> values) throws RuleException;

    //this returns the
    Set<Metric> getMetricsNeeded();
}
