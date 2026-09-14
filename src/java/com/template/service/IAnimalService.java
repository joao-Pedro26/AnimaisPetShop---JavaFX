package com.template.service;

import com.template.model.dto.AnimalDTO;
import java.util.List;

public interface IAnimalService {
    void salvar(AnimalDTO animal);
    void excluir(AnimalDTO animal);
    List<AnimalDTO> listarTodos();
}