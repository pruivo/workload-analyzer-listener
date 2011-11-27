package eu.cloudtm.wa.time;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 5:38 PM
 *
 * This interface represents a time type used to verify the rules. This can be implemented to create behaviour like:
 *  -- every X seconds,
 *  -- every X seconds, for a year
 *  -- etc...
 */
public interface TimeType {

    /**
     * it starts this time
     */
    public void start();

    /**
     * it cancels this time
     */
    public void cancel();

    /**
     * it verifies if it has more rule verification to do (see {@link eu.cloudtm.wa.WorkLoadClient.WorkLoadMonitorThread})
     *
     * @return true if it is more verifications to do, false otherwise
     */
    public boolean hasNext();

    /**
     * it blocks the thread execution, until the instant where the next verification must be done
     */
    public void waitNext();
}
