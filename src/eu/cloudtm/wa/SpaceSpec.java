package eu.cloudtm.wa;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 12:33 PM
 */
public class SpaceSpec {
    private SpaceHierarchy level;
    private String value;

    public SpaceSpec(SpaceHierarchy level, String value) {
        this.level = level;
        this.value = value;
    }

    public SpaceHierarchy getLevel() {
        return level;
    }

    public void setLevel(SpaceHierarchy level) {
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
