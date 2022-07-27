package com.rest.springbootemployee.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundCompany extends RuntimeException{
    public NotFoundCompany() {
        super("Not Found Company");
    }
}
