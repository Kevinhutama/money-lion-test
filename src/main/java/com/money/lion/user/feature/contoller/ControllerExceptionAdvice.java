package com.money.lion.user.feature.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public void handleRuntimeException(HttpServletRequest req, IllegalArgumentException ex) {
        log.error("handleRuntimeException", ex.getMessage());
    }
}
