package com.template.service;

import com.template.exception.BusinessException;
import com.template.model.dao.AnimalDAO;
import com.template.model.dto.AnimalDTO;
import com.template.validator.IAnimalValidator;

import java.util.List;

public class AnimalService implements IAnimalService {

    private final AnimalDAO animalDAO;
    private final IAnimalValidator animalValidator;

    public AnimalService(IAnimalValidator animalValidator) {
        this.animalValidator = animalValidator;
        this.animalDAO = new AnimalDAO();
    }

    @Override
    public void salvar(AnimalDTO animal) {
        animalValidator.validarAnimal(animal);

        if (animal.getId() != null && animal.getId() > 0) {
            animalDAO.updateAnimal(animal);
        } else {
            animalDAO.cadastrarAnimal(animal);
        }
    }

    @Override
    public void excluir(AnimalDTO animal) {
        if (animal == null) {
            throw new BusinessException("Nenhum animal selecionado para exclusão.");
        }
        animalDAO.deletarAnimal(animal);
    }

    @Override
    public List<AnimalDTO> listarTodos() {
        return animalDAO.selecionarAnimal();
    }
}