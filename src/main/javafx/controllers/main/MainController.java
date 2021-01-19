package main.javafx.controllers.main;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import main.application.DependencyProvider;
import models.fxml.ValueRow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    public ComboBox combobox;
    private List<ValueRow> _originalRows;

    public void pushToSources(ActionEvent actionEvent) {
        DependencyProvider.applicationState.pushToSources();
    }

    public void pullFromSources(ActionEvent actionEvent) {
        DependencyProvider.applicationState.pullFromSources();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.getItems().add("");
        var values = DependencyProvider
                .applicationState
                .valueRows
                .stream()
                .map(ValueRow::getVariable)
                .distinct()
                .collect(Collectors.toList());
        combobox.getItems().addAll(values);
        combobox.setOnAction(e ->
                DependencyProvider.applicationState.variableFilterState
                        .setFilter(combobox.getValue().toString()));
    }
}
