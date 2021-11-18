package main.rest.controller;


import main.exception.PedidoNaoEncontradoException;
import main.exception.RegraDeNegocioException;
import main.rest.ErrorsHandle.ApiErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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


}
