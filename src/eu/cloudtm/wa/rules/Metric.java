package eu.cloudtm.wa.rules;

import eu.cloudtm.wa.ResourceType;
import eu.cloudtm.wa.SpaceHierarchy;
import eu.cloudtm.wa.SpaceSpec;

import java.util.List;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 12:29 PM
 */
public class Metric {
    ResourceType type;
    String misType;
    SpaceHierarchy spaceGrouping;
    List<SpaceSpec> spaceSpec;

    public Metric(ResourceType type, String misType, SpaceHierarchy spaceGrouping, List<SpaceSpec> spaceSpec) {
        this.type = type;
        this.misType = misType;
        this.spaceGrouping = spaceGrouping;
        this.spaceSpec = spaceSpec;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getMisType() {
        return misType;
    }

    public void setMisType(String misType) {
        this.misType = misType;
    }

    public SpaceHierarchy getSpaceGrouping() {
        return spaceGrouping;
    }

    public void setSpaceGrouping(SpaceHierarchy spaceGrouping) {
        this.spaceGrouping = spaceGrouping;
    }

    public List<SpaceSpec> getSpaceSpec() {
        return spaceSpec;
    }

    public void addSpaceSpec(SpaceSpec ss) {
        spaceSpec.add(ss);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metric metric = (Metric) o;

        if (misType != null ? !misType.equals(metric.misType) : metric.misType != null) return false;
        if (spaceGrouping != metric.spaceGrouping) return false;
        if (spaceSpec != null ? !spaceSpec.equals(metric.spaceSpec) : metric.spaceSpec != null) return false;
        if (type != metric.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (misType != null ? misType.hashCode() : 0);
        result = 31 * result + (spaceGrouping != null ? spaceGrouping.hashCode() : 0);
        result = 31 * result + (spaceSpec != null ? spaceSpec.hashCode() : 0);
        return result;
    }
}
