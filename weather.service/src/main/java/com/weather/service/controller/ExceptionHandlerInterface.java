package com.weather.service.controller;

import com.mongodb.MongoException;
import com.weather.service.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;


public interface ExceptionHandlerInterface {
    Logger logger = LogManager.getLogger(ExceptionHandlerInterface.class);

    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason="Entity not found. Check params")
    @ExceptionHandler(NotFoundException.class)
    default void handleNotFoundException() {
        logger.atError().log("Entity not found. Returning 404 status for request");
    }
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE,
            reason="Service temporary is unavailable.")
    @ExceptionHandler({SQLException.class, MongoException.class})
    default void handleMongoTimeoutException(HttpServletRequest request) {
        logger.atError().log("Request to " + request.getRequestURI());
        logger.atError().log("Data source is unreached");
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
            reason="Internal server error")
    @ExceptionHandler(RuntimeException.class)
    default void handleAbstractException(HttpServletRequest request, Exception exception) {
        logger.atError().log("Request to " + request.getRequestURI());
        logger.atError().log("Exception on run time was handled. More details below");
        logger.atError().log(exception.getMessage());
    }
}
