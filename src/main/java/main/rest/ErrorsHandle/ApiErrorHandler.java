package main.rest.ErrorsHandle;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrorHandler {

    @Getter
    private List<String> errors;


    public ApiErrorHandler(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrorHandler(String mensagemErro){
        this.errors = Arrays.asList(mensagemErro);
    }
}
