package com.lazrproductions.lazrslib.common.network.exception;

public class InvalidLazrNetworkParameterException extends RuntimeException {
    public InvalidLazrNetworkParameterException(){
        super();
    }
    public InvalidLazrNetworkParameterException(Throwable cause) {
        super(cause);
    }
    public InvalidLazrNetworkParameterException(String message) {
        super(message);
    }
    public InvalidLazrNetworkParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
