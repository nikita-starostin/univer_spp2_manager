package main.javafx.controllers.tabs.migrations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.fxml.MigrationRow;

import java.net.URL;
import java.util.ResourceBundle;

public class MigrationsController implements Initializable {
    public TableView migrationsTable;
    public TableColumn dbname;
    public TableColumn lastMigration;
    public TableColumn actualMigration;

    private static final ObservableList<MigrationRow> migrationRowsObservableList = FXCollections.observableArrayList(
            new MigrationRow("test", "qwe", "ewq"),
            new MigrationRow("cart", "qwe", "ewq"),
            new MigrationRow("central", "ewq", "ewq")
    );

    public Label migrationsDirectory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbname.setCellValueFactory(new PropertyValueFactory<MigrationRow, String>("dbname"));
        lastMigration.setCellValueFactory(new PropertyValueFactory<MigrationRow, String>("lastMigration"));
        actualMigration.setCellValueFactory(new PropertyValueFactory<MigrationRow, String>("actualMigration"));
        migrationsTable.setItems(migrationRowsObservableList);
    }

    public void refresh(ActionEvent actionEvent) {
    }

    public void configureMigrationDirectory(ActionEvent actionEvent) {
    }

    public void addMigration(ActionEvent actionEvent) {
    }

    public void removeMigration(ActionEvent actionEvent) {
    }
}
