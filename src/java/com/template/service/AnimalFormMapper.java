package com.template.controller.helper;

import com.template.model.dto.AnimalDTO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class AnimalFormMapper {

    private final TextField txtNome;
    private final TextField txtEspecie;
    private final TextField txtRaca;
    private final Spinner<Integer> txtIdade;
    private final Spinner<Double> txtPeso;
    private final ComboBox<String> cbSexo;

    public AnimalFormMapper(TextField txtNome, TextField txtEspecie, TextField txtRaca,
                            Spinner<Integer> txtIdade, Spinner<Double> txtPeso,
                            ComboBox<String> cbSexo) {
        this.txtNome = txtNome;
        this.txtEspecie = txtEspecie;
        this.txtRaca = txtRaca;
        this.txtIdade = txtIdade;
        this.txtPeso = txtPeso;
        this.cbSexo = cbSexo;
    }

    public AnimalDTO extrairDTOdoFormulario(Integer idAtual) {
        AnimalDTO animal = new AnimalDTO();

        if (idAtual != null) {
            animal.setId(idAtual);
        }

        animal.setNome(txtNome.getText());
        animal.setEspecie(txtEspecie.getText());
        animal.setRaca(txtRaca.getText());
        animal.setIdade(txtIdade.getValue());
        animal.setPeso(txtPeso.getValue());

        String sexoSelecionado = cbSexo.getValue();
        if (sexoSelecionado != null && !sexoSelecionado.isEmpty()) {
            animal.setSexo(sexoSelecionado.charAt(0));
        }

        return animal;
    }

    public void preencherFormulario(AnimalDTO animal) {
        if (animal == null) return;

        txtNome.setText(animal.getNome());
        txtEspecie.setText(animal.getEspecie());
        txtRaca.setText(animal.getRaca());
        txtIdade.getValueFactory().setValue(animal.getIdade());
        txtPeso.getValueFactory().setValue(animal.getPeso());
        cbSexo.setValue(String.valueOf(animal.getSexo()));
    }
}