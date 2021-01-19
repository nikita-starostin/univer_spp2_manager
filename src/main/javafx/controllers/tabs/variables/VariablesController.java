package main.javafx.controllers.tabs.variables;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.application.DependencyProvider;
import main.javafx.cellFactories.TextFieldCellFactory;
import models.fxml.VariableRow;

import java.net.URL;
import java.util.ResourceBundle;

public class VariablesController implements Initializable {
    public TableView tableView;
    public TableColumn name;
    public TableColumn environments;
    public TableColumn valueAliases;

    private ObservableList<VariableRow> variableRowsObservableList = FXCollections.observableArrayList(
            DependencyProvider.applicationState.variableRows
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<VariableRow, String>("name"));
        name.setCellFactory(new TextFieldCellFactory());
        valueAliases.setCellValueFactory(new PropertyValueFactory<VariableRow, String>("valueAliases"));
        environments.setCellValueFactory(new PropertyValueFactory<VariableRow, String>("environments"));
        environments.setCellFactory(new TextFieldCellFactory());
        tableView.setItems(variableRowsObservableList);
    }
}
