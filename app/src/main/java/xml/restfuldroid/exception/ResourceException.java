package xml.restfuldroid.exception;

/**
 * Created by zenbook on 16/08/15.
 */
public class ResourceException extends Throwable {

    public ResourceException() {
    }

    public ResourceException(String detailMessage) {
        super(detailMessage);
    }

    public ResourceException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }
}
