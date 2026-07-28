package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.AnimalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalDAO {

    private static final Logger logger = Logger.getLogger(AnimalDAO.class.getName());

    public ArrayList<AnimalDTO> selecionarAnimal() {
        ArrayList<AnimalDTO> listaAnimal = new ArrayList<>();
        String sql = "SELECT * FROM animais";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AnimalDTO animal = new AnimalDTO();
                animal.setId(rs.getInt("id"));
                animal.setNome(rs.getString("nome"));
                animal.setEspecie(rs.getString("especie"));
                animal.setRaca(rs.getString("raca"));
                animal.setIdade(rs.getInt("idade"));
                animal.setPeso(rs.getDouble("peso"));
                animal.setSexo(rs.getString("sexo").charAt(0));

                listaAnimal.add(animal);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar animais", e);
        }
        return listaAnimal;
    }

    public boolean cadastrarAnimal(AnimalDTO animal) {
        String sql = "INSERT INTO animais (nome, especie, raca, idade, peso, sexo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, animal.getNome());
            ps.setString(2, animal.getEspecie());
            ps.setString(3, animal.getRaca());
            ps.setInt(4, animal.getIdade());
            ps.setDouble(5, animal.getPeso());
            ps.setString(6, String.valueOf(animal.getSexo()));

            ps.execute();
            return true; // Sucesso!

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar animal", e);
            return false; // Falha!
        }
    }

    public boolean deletarAnimal(AnimalDTO animal) {
        String sql = "DELETE FROM animais WHERE id = ?";

        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, animal.getId());

            // executeUpdate retorna o número de linhas afetadas. Se for maior que 0, deletou.
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar animal", e);
            return false;
        }
    }

    public boolean updateAnimal(AnimalDTO animal) {
        String sql = "UPDATE animais SET nome = ?, especie = ?, raca = ?, idade = ?, peso = ?, sexo = ? WHERE id = ?";

        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, animal.getNome());
            ps.setString(2, animal.getEspecie());
            ps.setString(3, animal.getRaca());
            ps.setInt(4, animal.getIdade());
            ps.setDouble(5, animal.getPeso());
            ps.setString(6, String.valueOf(animal.getSexo()));
            ps.setInt(7, animal.getId());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar animal", e);
            return false;
        }
    }
}