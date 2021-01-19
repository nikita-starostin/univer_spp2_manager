package main.javafx.controllers.tabs.environments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.application.DependencyProvider;
import main.javafx.cellFactories.TextFieldCellFactory;
import models.fxml.EnvironmentRow;
import models.fxml.VariableRow;

import java.net.URL;
import java.util.ResourceBundle;

public class EnvironmentsController implements Initializable {
    public TableView tableView;
    public TableColumn name;

    private ObservableList<EnvironmentRow> environmentRowsObservableList = FXCollections.observableArrayList(
            DependencyProvider.applicationState.environmentRows
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<VariableRow, String>("name"));
        name.setCellFactory(new TextFieldCellFactory());
        tableView.setItems(environmentRowsObservableList);
    }
}
