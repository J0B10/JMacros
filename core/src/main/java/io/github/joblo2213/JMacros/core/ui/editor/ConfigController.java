package io.github.joblo2213.JMacros.core.ui.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class ConfigController {

    @FXML
    public VBox settingsList;
    @FXML
    private HBox header;
    @FXML
    public Label exitButton;

    private double dragXOffset, dragYOffset;


    @FXML
    public void windowDragStart(MouseEvent mouseEvent) {
        Window stage = header.getScene().getWindow();
        dragXOffset = stage.getX() - mouseEvent.getScreenX();
        dragYOffset = stage.getY() - mouseEvent.getScreenY();
    }

    @FXML
    public void windowDrag(MouseEvent mouseEvent) {
        Window stage = header.getScene().getWindow();
        stage.setX(dragXOffset + mouseEvent.getScreenX());
        stage.setY(dragYOffset + mouseEvent.getScreenY());
    }

    @FXML
    public void exitButtonMouseEnter(MouseEvent mouseEvent) {
        exitButton.setStyle("-fx-background-color: #e81123");
    }

    @FXML
    public void exitButtonMouseExit(MouseEvent mouseEvent) {
        exitButton.setStyle("-fx-background-color: #242629");
    }

    @FXML
    public void exit(MouseEvent mouseEvent) {
        Window stage = exitButton.getScene().getWindow();
        exitButton.setStyle("-fx-background-color: #242629");
        stage.hide();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        //TODO save
    }

    @FXML
    public void reset(ActionEvent actionEvent) {
        //TODO reset
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        header.getScene().getWindow().hide();
    }
}
