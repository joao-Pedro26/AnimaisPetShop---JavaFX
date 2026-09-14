package com.template.validator;

import com.template.exception.BusinessException;
import com.template.model.dto.AnimalDTO;
import java.util.ArrayList;
import java.util.List;

public class AnimalValidator implements IAnimalValidator {

    @Override
    public boolean validarAnimal(AnimalDTO animal) {
        List<Validator<String>> animalValidadores = new ArrayList<>();

        animalValidadores.add(new CampoObrigatorioValidator("Nome", animal.getNome()));
        animalValidadores.add(new CampoObrigatorioValidator("Espécie", animal.getEspecie()));
        animalValidadores.add(new EspecieValidator("Espécie", animal.getEspecie()));
        animalValidadores.add(new CampoObrigatorioValidator("Raça", animal.getRaca()));
        animalValidadores.add(new CampoObrigatorioValidator("Idade", String.valueOf(animal.getIdade())));
        animalValidadores.add(new CampoObrigatorioValidator("Peso", String.valueOf(animal.getPeso())));
        animalValidadores.add(new CampoObrigatorioValidator("Sexo", String.valueOf(animal.getSexo())));

        for (Validator<String> animalValidator : animalValidadores) {
            if (!animalValidator.validar(animalValidator.getValor())) {
                throw new BusinessException(animalValidator.getMensagemErro());
            }
        }
        return true;
    }
}