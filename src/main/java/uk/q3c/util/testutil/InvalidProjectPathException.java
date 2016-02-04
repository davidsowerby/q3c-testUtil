package uk.q3c.util.testutil;

/**
 * Created by David Sowerby on 04 Feb 2016
 */
public class InvalidProjectPathException extends RuntimeException {
    public InvalidProjectPathException(String rootPathName) {
        super(rootPathName);
    }
}
