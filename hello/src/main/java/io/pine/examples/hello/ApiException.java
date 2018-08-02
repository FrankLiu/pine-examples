package io.pine.examples.hello;

/**
 * @author Frank
 * @sinace 2018/8/2 0002.
 */
public class ApiException extends Exception {
    private static final long serialVersionUID = 3037589527638521971L;

    public ApiException(String message){
        super(message);
    }
}
