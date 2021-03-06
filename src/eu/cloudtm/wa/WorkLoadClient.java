package eu.cloudtm.wa;

import eu.cloudtm.wa.parser.StatsMeasurement;
import eu.cloudtm.wa.rules.Metric;
import eu.cloudtm.wa.rules.Rule;
import eu.cloudtm.wa.rules.exception.RuleException;
import eu.cloudtm.wa.time.TimeType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 5:28 PM
 *
 *
 */
public class WorkLoadClient {

    private List<WorkLoadMonitorThread> registeredComponents;

    public WorkLoadClient() {
        registeredComponents = new LinkedList<WorkLoadMonitorThread>();
    }

    public void register(Rule rule, TimeType time, String methodName, Object instanceToInvoke)
            throws NoSuchMethodException {
        WorkLoadMonitorThread wt = new WorkLoadMonitorThread(rule, time, methodName, instanceToInvoke);
        registeredComponents.add(wt);
        wt.start();
    }
    
    public void cancelRegistrations() {
        for(WorkLoadMonitorThread wt : registeredComponents) {
            wt.interrupt();
        }
        registeredComponents.clear();
    }

    private void trigger(final Method method, final Object instance) {
        new Thread() {
            @Override
            public void run() {
                try {
                    method.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class WorkLoadMonitorThread extends Thread {
        private Rule rule;
        private TimeType time;
        private Method method;
        private Object instanceToInvoke;
        private Set<Metric> metrics;
        private Map<Metric, Object> results;
        
        public WorkLoadMonitorThread(Rule rule, TimeType timeType, String methodName, Object instanceToInvoke) 
                throws NoSuchMethodException {
            Class<?> classToInvoke = instanceToInvoke.getClass();
            this.method = classToInvoke.getMethod(methodName);
            this.time = timeType;
            this.rule = rule;
            this.instanceToInvoke = instanceToInvoke;
            this.metrics = rule.getMetricsNeeded();
            this.results = new HashMap<Metric, Object>();
        }

        @Override
        public void run() {
            time.start();
            
            while(time.hasNext()) {
                time.waitNext();
                results.clear();
                for(Metric m : metrics) {
                    StatsMeasurement sm = WorkloadAnalyzerStub.doProcess(m.getType(), m.getMisType(),
                            m.getSpaceGrouping(), m.getSpaceSpec(), 0, Long.MAX_VALUE, 0).get(0);
                    results.put(m, sm);
                }
                try {
                    if(rule.evaluate(results)) {
                        trigger(method, instanceToInvoke);
                    }
                } catch (RuleException e) {
                    //just ignore...
                }
            }
        }

        @Override
        public void interrupt() {
            time.cancel();
            super.interrupt();
        }
    }
}
