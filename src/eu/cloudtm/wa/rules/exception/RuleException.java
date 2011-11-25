package eu.cloudtm.wa.rules.exception;

/**
 * User: pruivo
 * Date: 11/25/11
 * Time: 4:36 PM
 */
public class RuleException extends Exception {
    public RuleException() {
    }

    public RuleException(String s) {
        super(s);
    }

    public RuleException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RuleException(Throwable throwable) {
        super(throwable);
    }
}
