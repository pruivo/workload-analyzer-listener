package eu.cloudtm.wa.time;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 5:39 PM
 */
public class Frequency implements TimeType {

    private long frequency; //in milliseconds
    private long nextTrigger;
    private boolean active;

    public Frequency(long frequency) {
        this.frequency = frequency;
        this.active = true;
        updateNextTrigger();
    }

    public void start() {
        active = true;
        updateNextTrigger();
    }

    public void cancel() {
        active = false;
    }

    public boolean hasNext() {
        return active;
    }

    public void waitNext() {
        long waitingTime = nextTrigger - System.currentTimeMillis();
        if(waitingTime <= 0) {
            updateNextTrigger();
            return;
        }
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
            active = false;
        }
        updateNextTrigger();
    }

    private void updateNextTrigger() {
        this.nextTrigger = System.currentTimeMillis() + frequency;
    }
}
