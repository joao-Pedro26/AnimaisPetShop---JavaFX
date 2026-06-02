package com.template;

import com.template.model.dao.AnimalDAO;
import com.template.model.dto.AnimalDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class AnimalController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEspecie;
    @FXML private TextField txtRaca;
    @FXML private TextField txtIdade;
    @FXML private TextField txtPeso;
    @FXML private ComboBox<String> cbSexo;

    @FXML private TableView<AnimalDTO> tabelaAnimais;
    @FXML private TableColumn<AnimalDTO, Integer> colId;
    @FXML private TableColumn<AnimalDTO, String> colNome;
    @FXML private TableColumn<AnimalDTO, String> colEspecie;
    @FXML private TableColumn<AnimalDTO, String> colRaca;
    @FXML private TableColumn<AnimalDTO, Integer> colIdade;
    @FXML private TableColumn<AnimalDTO, Double> colPeso;
    @FXML private TableColumn<AnimalDTO, String> colSexo;

    private AnimalDAO animalDAO = new AnimalDAO();
    private ObservableList<AnimalDTO> obsAnimais;
    private AnimalDTO animalSelecionado;

    @FXML
    public void initialize() {
        // Configura as opções do ComboBox de Sexo
        cbSexo.setItems(FXCollections.observableArrayList("M", "F"));

        // Liga as colunas da tabela aos atributos da classe AnimalDTO
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        carregarTabela();

        // Escuta os cliques na tabela para preencher o formulário para edição
        tabelaAnimais.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTabela(newValue)
        );
    }

    @FXML
    public void salvar() {
        if (txtNome.getText().isEmpty() || cbSexo.getValue() == null) {
            System.out.println("Preencha os campos obrigatórios!");
            return;
        }

        AnimalDTO animal = new AnimalDTO();
        animal.setNome(txtNome.getText());
        animal.setEspecie(txtEspecie.getText());
        animal.setRaca(txtRaca.getText());
        animal.setIdade(Integer.parseInt(txtIdade.getText()));
        animal.setPeso(Double.parseDouble(txtPeso.getText()));
        animal.setSexo(cbSexo.getValue().charAt(0));

        // Se tiver um animal selecionado (com ID), é uma atualização. Se não, é cadastro novo.
        if (animalSelecionado != null) {
            animal.setId(animalSelecionado.getId());
            animalDAO.updateAnimal(animal);
        } else {
            animalDAO.cadastrarAnimal(animal);
        }

        limpar();
        carregarTabela();
    }

    @FXML
    public void excluir() {
        if (animalSelecionado != null) {
            animalDAO.deletarAnimal(animalSelecionado);
            limpar();
            carregarTabela();
        } else {
            System.out.println("Selecione um animal na tabela para excluir!");
        }
    }

    @FXML
    public void limpar() {
        animalSelecionado = null;
        txtNome.clear();
        txtEspecie.clear();
        txtRaca.clear();
        txtIdade.clear();
        txtPeso.clear();
        cbSexo.setValue(null);
    }

    private void carregarTabela() {
        obsAnimais = FXCollections.observableArrayList(animalDAO.selecionarAnimal());
        tabelaAnimais.setItems(obsAnimais);
    }

    private void selecionarItemTabela(AnimalDTO animal) {
        if (animal != null) {
            animalSelecionado = animal;
            txtNome.setText(animal.getNome());
            txtEspecie.setText(animal.getEspecie());
            txtRaca.setText(animal.getRaca());
            txtIdade.setText(String.valueOf(animal.getIdade()));
            txtPeso.setText(String.valueOf(animal.getPeso()));
            cbSexo.setValue(String.valueOf(animal.getSexo()));
        }
    }
}