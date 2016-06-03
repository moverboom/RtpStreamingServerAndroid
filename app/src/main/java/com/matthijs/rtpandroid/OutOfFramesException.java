package com.matthijs.rtpandroid;

/**
 * Created by Matthijs Overboom on 1-6-16.
 */
public class OutOfFramesException extends Exception {
    public OutOfFramesException(String message) {
        super(message);
    }
}
