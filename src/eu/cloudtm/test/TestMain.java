package eu.cloudtm.test;

import eu.cloudtm.wa.ResourceType;
import eu.cloudtm.wa.SpaceHierarchy;
import eu.cloudtm.wa.SpaceSpec;
import eu.cloudtm.wa.WorkLoadClient;
import eu.cloudtm.wa.rules.*;
import eu.cloudtm.wa.time.Frequency;
import eu.cloudtm.wa.time.TimeType;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: pruivo
 * Date: 11/25/11
 * Time: 12:05 PM
 */
public class TestMain {

    public static void main(String[] args) {
        Notification n = new Notification();
        WorkLoadClient wlc = new WorkLoadClient();

        SpaceSpec a = new SpaceSpec(SpaceHierarchy.PROVIDER,"Px9");
        SpaceSpec b = new SpaceSpec(SpaceHierarchy.GROUP,"G01");
        ArrayList<SpaceSpec> list = new ArrayList<SpaceSpec>();
        list.add(a);
        list.add(b);

        Metric avgFreeMemory = new Metric(ResourceType.MEMORY, "free", SpaceHierarchy.MACHINE, list, Metric.Value.AVG);
        Metric maxFreeMemory = new Metric(ResourceType.MEMORY, "free", SpaceHierarchy.MACHINE, list, Metric.Value.MAX);
        Metric minFreeMemory = new Metric(ResourceType.MEMORY, "free", SpaceHierarchy.MACHINE, list, Metric.Value.MIN);

        Metric avgUsedCPU = new Metric(ResourceType.CPU, "used", SpaceHierarchy.MACHINE, list, Metric.Value.AVG);
        Metric maxUsedCPU = new Metric(ResourceType.CPU, "used", SpaceHierarchy.MACHINE, list, Metric.Value.MAX);
        Metric minUsedCPU = new Metric(ResourceType.CPU, "used", SpaceHierarchy.MACHINE, list, Metric.Value.MIN);

        Rule avgCPU_GE_50 = new SimpleRule(avgUsedCPU, SimpleRule.Operator.GREATER_OR_EQUAL, 50.0);
        Rule maxMemory_G_70 = new SimpleRule(maxFreeMemory, SimpleRule.Operator.GREATER, 70.0);
        Rule minMemory_LE_minCPU = new SimpleRule(minFreeMemory, SimpleRule.Operator.LESS_OR_EQUAL, minUsedCPU);
        Rule minCPU_L_50 = new SimpleRule(minUsedCPU, SimpleRule.Operator.LESS, 50.0);
        Rule avgMemory_E_maxCPU = new SimpleRule(avgFreeMemory, SimpleRule.Operator.EQUAL, maxUsedCPU);
        Rule avgCPU_NE_100 = new SimpleRule(avgUsedCPU, SimpleRule.Operator.NOT_EQUAL, 100);

        TimeType tenSec = new Frequency(10000);
        TimeType twentySec = new Frequency(20000);
        TimeType oneHundredSec = new Frequency(100000);

        try {
            wlc.register(avgCPU_GE_50, tenSec, "cpu_ge_50", n);
            wlc.register(new CompositeRule(minCPU_L_50, maxMemory_G_70, CompositeRule.Operator.AND), twentySec,
                    "low_cpu_and_high_memory",n);
                wlc.register(minMemory_LE_minCPU, tenSec, "memory_less_cpu",n);
            wlc.register(new NotRule(avgMemory_E_maxCPU), oneHundredSec, "memory_diff_cpu", n);
            wlc.register(new CompositeRule(new NotRule(avgCPU_NE_100), minMemory_LE_minCPU, CompositeRule.Operator.OR),
                    twentySec, "cpu_diff_100_or_memory_less_cpu", n);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        System.console().readLine();
        wlc.cancelRegistrations();
        System.exit(0);
    }

    public static class Notification {
        public void cpu_ge_50() {
            System.out.println("[Notification] average CPU is greater or equal to 50%");
        }

        public void low_cpu_and_high_memory() {
            System.out.println("[Notification] min cpu is less the 50% and max memory is greater than 70%");
        }

        public void memory_less_cpu() {
            System.out.println("[Notification] min memory is less than min cpu");
        }

        public void memory_diff_cpu() {
            System.out.println("[Notification] avg memory is different than max cpu");
        }

        public void cpu_diff_100_or_memory_less_cpu() {
            System.out.println("[Notification] avg cpu is different than 100% or min memory is less than min cpu");
        }
    }

}
