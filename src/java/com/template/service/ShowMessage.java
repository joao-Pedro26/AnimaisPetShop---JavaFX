package com.template.service;

import javafx.scene.control.Label;

public class ShowMessage {

    private Label lblMensagem;

    public ShowMessage(Label lblMensagem) {
        this.lblMensagem = lblMensagem;
    }

    public void mostrarMensagem(String msg, String cor) {
        lblMensagem.setText(msg);
        lblMensagem.setStyle(String.format("-fx-text-fill: %s; -fx-font-weight: bold;", cor));
    }

}
