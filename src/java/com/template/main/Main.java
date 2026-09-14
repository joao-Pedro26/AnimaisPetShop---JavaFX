package com.template.main;

import com.template.controller.AnimalController;
import com.template.service.AnimalService;
import com.template.service.IAnimalService;
import com.template.validator.AnimalValidator;
import com.template.validator.IAnimalValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../main.fxml"));

        loader.setControllerFactory(classeDoController -> {
            if (classeDoController == AnimalController.class) {
                IAnimalValidator validator = new AnimalValidator();
                IAnimalService service = new AnimalService(validator);

                return new AnimalController(service);
            }

            try {
                return classeDoController.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Erro ao criar o controller: " + classeDoController.getName(), e);
            }
        });

        Parent root = loader.load();

        primaryStage.setTitle("Pet Shop - CRUD JavaFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}