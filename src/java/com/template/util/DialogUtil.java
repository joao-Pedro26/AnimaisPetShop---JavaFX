package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import java.util.Optional;

public class DialogUtil {

    public static boolean mostrarConfirmacao(String titulo, String mensagem, boolean isExclusao) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);

        Label labelMsg = new Label(mensagem);
        labelMsg.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 14px; -fx-font-weight: bold;");
        labelMsg.setWrapText(true);
        alert.getDialogPane().setContent(labelMsg);

        ButtonType btnConfirmar = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #333333; -fx-border-width: 2px;");
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);

        Button confirmButton = (Button) dialogPane.lookupButton(btnConfirmar);
        if (isExclusao) {
            confirmButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #E74C3C; -fx-border-color: #E74C3C; -fx-border-radius: 5; -fx-cursor: hand;");
        } else {
            confirmButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
        }

        Button cancelButton = (Button) dialogPane.lookupButton(btnCancelar);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #A0A0A0; -fx-border-color: #666666; -fx-border-radius: 5; -fx-cursor: hand;");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnConfirmar;
    }

    public static void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}