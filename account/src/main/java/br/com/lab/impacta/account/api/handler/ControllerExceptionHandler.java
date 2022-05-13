package br.com.lab.impacta.account.api.handler;

import br.com.lab.impacta.account.application.dto.response.ErrorMessageResponse;
import br.com.lab.impacta.account.domain.exception.AccountNotFoundException;
import br.com.lab.impacta.account.domain.exception.AccountWithoutBalanceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice // intercepta as exceções
public class ControllerExceptionHandler {

    private final String MESSAGE_ERROR_INTERNAL = "Erro interno!";
    private final String DESCRIPTION_ERROR_INTERNAL = "Não foi possível processar sua requisicao, tente novamente!";

    @Value("${lab.account.exceptions.account-without-balance-message}")
    private String messageExceptionAccountWithoutBalanceException;

    @Value("${lab.account.exceptions.account-without-balance-description}")
    private String descriptionExceptionAccountWithoutBalanceException;

    @ExceptionHandler(AccountNotFoundException.class) // intercept this exception
    public ResponseEntity<ErrorMessageResponse> accountNotFoundException() {
        return getErrorResponse(MESSAGE_ERROR_INTERNAL, DESCRIPTION_ERROR_INTERNAL, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountWithoutBalanceException.class) // intercept this exception
    public ResponseEntity<ErrorMessageResponse> withoutBalanceException(AccountWithoutBalanceException exception) {
        return getErrorResponse(messageExceptionAccountWithoutBalanceException,
                descriptionExceptionAccountWithoutBalanceException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageResponse> errorGeneral(RuntimeException exception) {
        return getErrorResponse(exception.getMessage(), "", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessageResponse> getErrorResponse(String message, String description, HttpStatus httpStatus) {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(
                new Date(), message, description
        );
        return new ResponseEntity<>(errorMessageResponse, httpStatus);
    }
}
