package com.template.validator;

import com.template.model.dto.AnimalDTO;

public interface IAnimalValidator {
    boolean validarAnimal(AnimalDTO animal);
}