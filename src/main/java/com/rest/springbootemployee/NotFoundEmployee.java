package com.rest.springbootemployee;

public class NotFoundEmployee extends RuntimeException{
    public NotFoundEmployee() {
        super("Not found employee.");
    }
}
