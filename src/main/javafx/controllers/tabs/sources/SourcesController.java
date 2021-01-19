package main.javafx.controllers.tabs.sources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.application.DependencyProvider;
import models.fxml.SourceRow;

import java.net.URL;
import java.util.ResourceBundle;

public class SourcesController implements Initializable {
    public TableView tableView;
    public TableColumn name;
    public TableColumn alias;

    private ObservableList<SourceRow> variableRowsObservableList = FXCollections.observableArrayList(
            DependencyProvider.applicationState.sourceRows
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<SourceRow, String>("name"));
        alias.setCellValueFactory(new PropertyValueFactory<SourceRow, String>("alias"));
        tableView.setItems(variableRowsObservableList);
    }
}
