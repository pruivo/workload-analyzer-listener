package eu.cloudtm.wa;

import java.util.Collections;
import java.util.List;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 5:30 PM
 */
public class WorkloadAnalyzerStub {
    public static List<StatsMeasurement> doProcess(ResourceType type,String misType,SpaceHierarchy spaceGrouping,
                                     List<SpaceSpec> spaceSpecs,long dateFrom,long dateTo,int timeGrouping) {
        return Collections.emptyList();
    }
}
