package com.jt.file.ud.demo;

/**
 * Created by Jason Tao on 7/15/2020
 */
public class DuplicateFileException extends Exception {

    public DuplicateFileException(String errorMessage) {
        super(errorMessage);
    }
}
