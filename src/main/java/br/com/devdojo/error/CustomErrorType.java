package br.com.devdojo.error;

/**
 * Classe básica usada para a descrição de erros nos retornos
 * dos métodos do endpoint. Geralmente acompanha um
 * status code.
 */
public class CustomErrorType {
    private String errorMessage;

    public CustomErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
