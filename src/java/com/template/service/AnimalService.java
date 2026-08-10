package com.template.service;

import com.template.exception.BusinessException;
import com.template.model.dao.AnimalDAO;
import com.template.model.dto.AnimalDTO;
import com.template.validator.AnimalValidator;

import java.util.List;

public class AnimalService {

    private final AnimalDAO animalDAO;

    public AnimalService() {
        this.animalDAO = new AnimalDAO();
    }

    public void salvar(AnimalDTO animal) {
        String sexoStr = animal.getSexo() != null ? String.valueOf(animal.getSexo()) : "";
        if (!AnimalValidator.isValidoParaSalvar(animal.getNome(), sexoStr)) {
            throw new BusinessException("Preencha os campos obrigatórios (Nome e Sexo)!");
        }

        if (animal.getId() != null && animal.getId() > 0) {
            animalDAO.updateAnimal(animal);
        } else {
            animalDAO.cadastrarAnimal(animal);
        }
    }

    public void excluir(AnimalDTO animal) {
        if (animal == null) {
            throw new BusinessException("Nenhum animal selecionado para exclusão.");
        }
        animalDAO.deletarAnimal(animal);
    }

    public List<AnimalDTO> listarTodos() {
        return animalDAO.selecionarAnimal();
    }
}