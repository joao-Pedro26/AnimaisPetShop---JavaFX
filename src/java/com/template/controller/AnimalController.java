package com.template.controller;

import com.template.controller.helper.AnimalFormCleaner;
import com.template.controller.helper.AnimalFormMapper;
import com.template.exception.BusinessException;
import com.template.model.dto.AnimalDTO;
import com.template.service.AnimalService;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AnimalController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEspecie;
    @FXML private TextField txtRaca;
    @FXML private Spinner<Integer> txtIdade;
    @FXML private Spinner<Double> txtPeso;
    @FXML private ComboBox<String> cbSexo;
    @FXML private Button btnExcluir;
    @FXML private Label lblMensagem;

    @FXML private TableView<AnimalDTO> tabelaAnimais;
    @FXML private TableColumn<AnimalDTO, Integer> colId;
    @FXML private TableColumn<AnimalDTO, String> colNome;
    @FXML private TableColumn<AnimalDTO, String> colEspecie;
    @FXML private TableColumn<AnimalDTO, String> colRaca;
    @FXML private TableColumn<AnimalDTO, Integer> colIdade;
    @FXML private TableColumn<AnimalDTO, Double> colPeso;
    @FXML private TableColumn<AnimalDTO, String> colSexo;

    private AnimalService animalService;
    private AnimalFormCleaner formCleaner;
    private AnimalFormMapper formMapper;

    private AnimalDTO animalSelecionado;

    @FXML
    public void initialize() {
        this.animalService = new AnimalService();

        this.formCleaner = new AnimalFormCleaner(
                txtNome, txtEspecie, txtRaca, txtIdade, txtPeso, cbSexo, btnExcluir, tabelaAnimais
        );

        this.formMapper = new AnimalFormMapper(
                txtNome, txtEspecie, txtRaca, txtIdade, txtPeso, cbSexo
        );

        configurarComponentesIniciais();
        configurarColunasTabela();
        carregarTabela();
    }

    @FXML
    public void salvar() {
        try {
            Integer idSelecionado = (animalSelecionado != null) ? animalSelecionado.getId() : null;

            AnimalDTO animal = formMapper.extrairDTOdoFormulario(idSelecionado);

            animalService.salvar(animal);

            mostrarMensagem(idSelecionado != null ? "Animal atualizado com sucesso!" : "Animal cadastrado com sucesso!", "green");
            limpar();
            carregarTabela();

        } catch (BusinessException e) {
            mostrarMensagem(e.getMessage(), "red");
        }
    }

    @FXML
    public void excluir() {
        if (animalSelecionado == null) return;

        boolean confirmou = DialogUtil.mostrarConfirmacao(
                "Confirmar Exclusão",
                "Tem certeza que deseja excluir o animal '" + animalSelecionado.getNome() + "' permanentemente?",
                true
        );

        if (confirmou) {
            try {
                animalService.excluir(animalSelecionado);
                mostrarMensagem("Animal excluído com sucesso!", "green");
                limpar();
                carregarTabela();
            } catch (BusinessException e) {
                mostrarMensagem(e.getMessage(), "red");
            }
        }
    }

    @FXML
    public void limpar() {
        animalSelecionado = null;
        formCleaner.limparCampos();
    }

    private void aoSelecionarAnimalNaTabela(AnimalDTO animal) {
        if (animal == null) return;

        this.animalSelecionado = animal;
        formMapper.preencherFormulario(animal);

        btnExcluir.setDisable(false);
        lblMensagem.setText("");
    }

    private void carregarTabela() {
        ObservableList<AnimalDTO> obsAnimais = FXCollections.observableArrayList(animalService.listarTodos());
        tabelaAnimais.setItems(obsAnimais);
    }

    private void configurarComponentesIniciais() {
        cbSexo.setItems(FXCollections.observableArrayList("M", "F"));
        txtIdade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        txtPeso.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 500.0, 0.0, 0.5));
        btnExcluir.setDisable(true);
    }

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        tabelaAnimais.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> aoSelecionarAnimalNaTabela(newValue)
        );
    }

    private void mostrarMensagem(String msg, String cor) {
        lblMensagem.setText(msg);
        lblMensagem.setStyle(String.format("-fx-text-fill: %s; -fx-font-weight: bold;", cor));
    }
}