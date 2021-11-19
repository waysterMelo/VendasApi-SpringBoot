package main.rest.controller;


import main.exception.PedidoNaoEncontradoException;
import main.exception.RegraDeNegocioException;
import main.rest.ErrorsHandle.ApiErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppControllerAdvice {


        @ExceptionHandler(RegraDeNegocioException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ApiErrorHandler handleRegraDeNegocioException(RegraDeNegocioException exception){
           String msg =  exception.getMessage();
            return new ApiErrorHandler(msg);
         }


         @ExceptionHandler(PedidoNaoEncontradoException.class)
         @ResponseStatus(HttpStatus.NOT_FOUND)
         public ApiErrorHandler handlerPedidoNotFound(PedidoNaoEncontradoException ex){
            return new ApiErrorHandler(ex.getMessage());
         }


         @ExceptionHandler(MethodArgumentNotValidException.class)
         @ResponseStatus(HttpStatus.BAD_REQUEST)
         public ApiErrorHandler handleMethodNotValidException(MethodArgumentNotValidException ex){
            List<String> errors = ex.getBindingResult().getAllErrors().stream().map( erro -> erro.getDefaultMessage()).collect(Collectors.toList());
            return new ApiErrorHandler(errors);
         }


}
