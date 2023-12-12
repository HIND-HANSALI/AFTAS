package com.app.aftas.handlers.response;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Getter
public class ResponseMessage {
    private int status;
    private String message;
    private Object data;

    public ResponseMessage(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // ok
    public static ResponseEntity ok(Object data, String message) {
        return new ResponseEntity(new ResponseMessage(HttpStatus.OK.value(), message, data), HttpStatus.OK);
    }

    // created
    public static ResponseEntity created(Object data, String message) {
        return new ResponseEntity(new ResponseMessage(HttpStatus.CREATED.value(), message, data), HttpStatus.CREATED);
    }

    // bad request
    public static ResponseEntity badRequest(String message) {
        return new ResponseEntity(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), message), HttpStatus.BAD_REQUEST);
    }

    // not found
    public static ResponseEntity notFound(String message) {
        return new ResponseEntity(new ResponseMessage(HttpStatus.NOT_FOUND.value(), message), HttpStatus.NOT_FOUND);
    }
}
