package com.template.controller.helper;

import com.template.model.dto.AnimalDTO;
import javafx.scene.control.*;

public class AnimalFormCleaner {

    private final TextField txtNome;
    private final TextField txtEspecie;
    private final TextField txtRaca;
    private final Spinner<Integer> txtIdade;
    private final Spinner<Double> txtPeso;
    private final ComboBox<String> cbSexo;
    private final Button btnExcluir;
    private final TableView<AnimalDTO> tabelaAnimais;

    public AnimalFormCleaner(TextField txtNome, TextField txtEspecie, TextField txtRaca,
                             Spinner<Integer> txtIdade, Spinner<Double> txtPeso,
                             ComboBox<String> cbSexo, Button btnExcluir,
                             TableView<AnimalDTO> tabelaAnimais) {
        this.txtNome = txtNome;
        this.txtEspecie = txtEspecie;
        this.txtRaca = txtRaca;
        this.txtIdade = txtIdade;
        this.txtPeso = txtPeso;
        this.cbSexo = cbSexo;
        this.btnExcluir = btnExcluir;
        this.tabelaAnimais = tabelaAnimais;
    }

    public void limparCampos() {
        txtNome.clear();
        txtEspecie.clear();
        txtRaca.clear();

        txtIdade.getValueFactory().setValue(0);
        txtPeso.getValueFactory().setValue(0.0);
        cbSexo.setValue(null);

        btnExcluir.setDisable(true);
        tabelaAnimais.getSelectionModel().clearSelection();
    }
}