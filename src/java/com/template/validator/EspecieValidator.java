package com.template.validator;

public class EspecieValidator implements Validator<String> {

    private String campo;
    private String valor;
    private String mensagemErro;

    public EspecieValidator(String campo, String valor) {
        this.campo = campo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return true;
        }

        if (valor.trim().length() < 3) {
            this.mensagemErro = "A " + campo + " deve ter pelo menos 3 caracteres.";
            return false;
        }

        if (valor.matches(".*\\d.*")) {
            this.mensagemErro = "A " + campo + " não pode conter números.";
            return false;
        }

        return true;
    }

    @Override
    public String getMensagemErro() {
        return this.mensagemErro;
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}