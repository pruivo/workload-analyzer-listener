package eu.cloudtm.wa.time;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 5:38 PM
 */
public interface TimeType {

    public void start();

    public void cancel();

    public boolean hasNext();

    public void waitNext();
}
