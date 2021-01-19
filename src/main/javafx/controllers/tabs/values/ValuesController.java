package main.javafx.controllers.tabs.values;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.application.DependencyProvider;
import main.javafx.cellFactories.TextFieldCellFactory;
import models.fxml.ValueRow;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ValuesController implements Initializable {
    public TableView tableView;
    public TableColumn variable;
    public TableColumn value;
    public TableColumn source;

    private ObservableList<ValueRow> valueRowsObservableList = FXCollections.observableArrayList(
            DependencyProvider.applicationState.valueRows
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DependencyProvider.applicationState.variableFilterState.addHandler(f -> {
            var items = DependencyProvider.applicationState.valueRows;
            if (!f.equals("")) {
                items = items.stream()
                        .filter(i -> i.getVariable().equals(f))
                        .collect(Collectors.toList());
            }
            valueRowsObservableList.setAll(items);
        });
        source.setCellValueFactory(new PropertyValueFactory<ValueRow, String>("source"));
        variable.setCellValueFactory(new PropertyValueFactory<ValueRow, String>("variable"));
        value.setCellValueFactory(new PropertyValueFactory<ValueRow, String>("value"));
        value.setCellFactory(new TextFieldCellFactory());

        var compareTableColumn = new TableColumn("");
        compareTableColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        compareTableColumn.setCellFactory(new ValueActionsCellFactory());
        tableView.getColumns().add(compareTableColumn);

        tableView.setItems(valueRowsObservableList);
    }
}
