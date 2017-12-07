package main.controller;

import core.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Manuel Wimmer
 * Date: 29.11.17
 * Desc:
 */

class AddInstanceController extends VBox {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfHost;

    @FXML
    private TextField tfUser;

    @FXML
    private PasswordField tfPassword = new PasswordField();

    @FXML
    private TextField tfPort;

    @FXML
    private TextField tfBaseDir;

    @FXML
    private Button btnSubmitInstance;

    @FXML
    private Button btnCancel;

    AddInstanceController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WPInstance.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void handleSubmitInstance() throws Exception {
        if (!tfName.getText().isEmpty() && !tfHost.getText().isEmpty() && !tfUser.getText().isEmpty() && !tfPassword.getText().isEmpty() && !tfPort.getText().isEmpty() && !tfBaseDir.getText().isEmpty()) {
            DBHandler.addWpInstanceEntry(tfName.getText(), tfHost.getText(), tfUser.getText(), tfPassword.getText(), Integer.parseInt(tfPort.getText()), tfBaseDir.getText());
            handleCancel();
            MainLayoutController.refreshInstances();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) btnSubmitInstance.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {

    }
}
