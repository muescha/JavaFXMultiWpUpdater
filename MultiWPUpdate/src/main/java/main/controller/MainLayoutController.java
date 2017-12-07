package main.controller;

import core.DBHandler;
import core.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.model.WpInstance;
import main.upload.PluginUpload;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Manuel Wimmer
 * Date: 29.11.17
 * Desc:
 */

public class MainLayoutController extends BorderPane {

    private static ObservableList<WpInstance> wpInstance = FXCollections.observableArrayList();

    @FXML
    private TableView<WpInstance> tvWpInstances;

    @FXML
    private TableColumn<WpInstance, String> tcWpName;

    @FXML
    private TableColumn<WpInstance, String> tcWpHost;

    @FXML
    private Button btnAddWpInstance;

    @FXML
    private Button btnRemoveWpInstance;

    @FXML
    private Button btnPluginUpload;


    public MainLayoutController() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void handleAddEntry() {
        VBox root1 = new AddInstanceController();
        Stage stage = new Stage();
        stage.setTitle("Add new Instance");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void handleDeleteSelectedEntry() throws IOException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (tvWpInstances.getSelectionModel().getSelectedItem() != null) {
            deleteWpEntry(tvWpInstances.getSelectionModel().getSelectedItem().getId());
            refreshInstances();
        }
    }

    @FXML
    private void handleButtonPluginUpload() throws Exception {
        ObservableList<WpInstance> uploadList = tvWpInstances.getSelectionModel().getSelectedItems();
        Stage stage = (Stage) btnPluginUpload.getScene().getWindow();
        File directory = Tools.selectDirectory(stage);

        if (directory != null) {
            PluginUpload plugin = new PluginUpload();
            plugin.upload(uploadList, directory);
        }
    }

    private void openEditWindow(int id) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException {
        VBox root1 = new EditInstanceController(id);
        Stage stage = new Stage();
        stage.setTitle("Edit Instance");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    private void deleteWpEntry(int id) throws IOException, SQLException {
        if (tvWpInstances.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Warning");
            alert.setContentText("Do you really want to remove this Instance?");

            ButtonType yes = new ButtonType("Yes, delete it!");
            ButtonType no = new ButtonType("No");

            alert.getButtonTypes().setAll(yes, no);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes){
                ObservableList<WpInstance> wpList = tvWpInstances.getSelectionModel().getSelectedItems();
                DBHandler.removeWpInstance(wpList);
            } else {
                Alert removeInfo = new Alert(Alert.AlertType.WARNING);
                removeInfo.setTitle("Remove Warning");
                removeInfo.setHeaderText("Please select a entry from the list before removing.");

                removeInfo.showAndWait();
            }
        }
    }

    @FXML
    private void handleMenuItemClose() {
        Stage stage = (Stage) btnAddWpInstance.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() throws IOException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException {
        tvWpInstances.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        refreshInstances();
        tcWpName.setCellValueFactory(cellData -> cellData.getValue().instanceNameProperty());
        tcWpHost.setCellValueFactory(cellData -> cellData.getValue().instanceHostProperty());
        tvWpInstances.setItems(wpInstance);

        Image iconAdd = new Image(getClass().getResourceAsStream("/icons/plus.png"));
        btnAddWpInstance.setGraphic(new ImageView(iconAdd));
        btnAddWpInstance.setTooltip(new Tooltip("Add new WP Instance"));

        Image iconRemove = new Image(getClass().getResourceAsStream("/icons/minus.png"));
        btnRemoveWpInstance.setGraphic(new ImageView(iconRemove));
        btnRemoveWpInstance.setTooltip(new Tooltip("Delete selected WP Instance"));

        Image iconSync = new Image(getClass().getResourceAsStream("/icons/sync.png"));
        btnPluginUpload.setGraphic(new ImageView(iconSync));
        btnPluginUpload.setTooltip(new Tooltip("Upload Plugin to selected Instances"));

        tvWpInstances.setRowFactory(tv -> {
            TableRow<WpInstance> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    WpInstance rowData = row.getItem();
                    try {
                        openEditWindow(rowData.getId());
                    } catch (SQLException | NoSuchPaddingException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        //handle delete key from keyboard
        tvWpInstances.setOnKeyPressed(keyEvent -> {
            if (tvWpInstances.getSelectionModel().getSelectedItem() != null) {
                int id = tvWpInstances.getSelectionModel().getSelectedItem().getId();
                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    try {
                        deleteWpEntry(id);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        refreshInstances();
    }

    static void refreshInstances() throws IOException, SQLException, NoSuchPaddingException, NoSuchAlgorithmException {
        wpInstance.removeAll();
        wpInstance.clear();
        wpInstance.addAll(DBHandler.loadAllWpInstances());
    }
}
