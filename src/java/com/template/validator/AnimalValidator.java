package com.template.validator;

import com.template.exception.BusinessException;
import com.template.model.dto.AnimalDTO;
import java.util.ArrayList;
import java.util.List;

import static com.template.util.DialogUtil.mostrarErro;

public class AnimalValidator {

    public static boolean validarAnimal(AnimalDTO animal) {
        List<Validator<String>> animalValidadores = new ArrayList<>();

        animalValidadores.add(new CampoObrigatorioValidator("Nome", animal.getNome()));
        animalValidadores.add(new CampoObrigatorioValidator("especie", animal.getEspecie()));
        animalValidadores.add(new EspecieValidator("Espécie", animal.getEspecie()));
        animalValidadores.add(new CampoObrigatorioValidator("raca", animal.getRaca()));
        animalValidadores.add(new CampoObrigatorioValidator("Idade", String.valueOf(animal.getIdade())));
        animalValidadores.add(new CampoObrigatorioValidator("Peso", String.valueOf(animal.getPeso())));
        animalValidadores.add(new CampoObrigatorioValidator("Peso", String.valueOf(animal.getSexo())));

        for (Validator<String> animalValidator : animalValidadores) {
            if(!animalValidator.validar(animalValidator.getValor())){
               throw  new BusinessException(animalValidator.getMensagemErro());
            }
        }
        return true;
    }

}