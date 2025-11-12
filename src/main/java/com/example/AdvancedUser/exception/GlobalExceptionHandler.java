package com.example.AdvancedUser.exception;

import com.example.AdvancedUser.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> badRequestHandler(MethodArgumentNotValidException exception){

        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> fieldError = exception.getFieldErrors();
        for(FieldError error : fieldError ){
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) //this is used because there is a constraints on the table
    public Map<String, String> duplicateEntryExceptionHandler (SQLIntegrityConstraintViolationException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.forEach(e -> {
            errorMap.put(e.getLocalizedMessage(), e.getMessage());
            errorMap.put((e.getLocalizedMessage()), "JavaStudent with thr email provider already exist");
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(StudentNotFoundException.class)
    public String handleNotFoundException (StudentNotFoundException exception){

        return exception.getMessage();
    }
}

