package com.template.validator;

public class AnimalValidator {

    public static boolean isValidoParaSalvar(String nome, String sexo) {
        boolean nomeValido = nome != null && !nome.trim().isEmpty();

        boolean sexoValido = sexo != null && !sexo.trim().isEmpty();

        return nomeValido && sexoValido;
    }
}