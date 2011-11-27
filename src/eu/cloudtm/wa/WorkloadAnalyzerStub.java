package eu.cloudtm.wa;

import eu.cloudtm.wa.parser.StatsMeasurement;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 5:30 PM
 *
 * This is just  a stub to simulate the behaviour of the
 * {@link #doProcess(ResourceType, String, SpaceHierarchy, java.util.List, long, long, int)}
 */
public class WorkloadAnalyzerStub {
    private static final Random r = new Random(System.currentTimeMillis());

    public static List<StatsMeasurement> doProcess(ResourceType type,String misType,SpaceHierarchy spaceGrouping,
                                     List<SpaceSpec> spaceSpecs,long dateFrom,long dateTo,int timeGrouping) {
        double min, max, avg;
        double val1 = r.nextDouble()*100.0;
        double val2 = r.nextDouble()*100.0;

        if(val1 < val2) {
            min = val1;
            max = val2;
        } else {
            min = val2;
            max = val1;
        }

        avg = min + ((max - min) / 2);

        String msg = new StringBuilder("[GENERATE_DATA] ResourceType:").append(type)
                .append("\n\tmisType:").append(misType)
                .append("\n\tSpaceHierarchy:").append(spaceGrouping)
                .append("\n\tSpaceSpecs:").append(spaceSpecs)
                .append("\n\tmin value=").append(min)
                .append("\n\tmax value=").append(max)
                .append("\n\tavg value=").append(avg).toString();

        System.out.println(msg);

        StatsMeasurement sm = new StatsMeasurement();
        sm.setResourceType(type);
        sm.setMisType(misType);
        sm.setGroup_ID("1");
        sm.setProvider_ID("2");
        sm.setTimestamp(System.currentTimeMillis());
        sm.setMinValue(min);
        sm.setAverageValue(avg);
        sm.setMaxValue(max);
        return Collections.singletonList(sm);
    }
}
