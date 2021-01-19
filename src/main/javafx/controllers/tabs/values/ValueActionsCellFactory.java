package main.javafx.controllers.tabs.values;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import main.application.Logger;
import models.fxml.ValueRow;

public class ValueActionsCellFactory implements Callback<TableColumn<ValueRow, String>, TableCell<ValueRow, String>> {
    @Override
    public TableCell<ValueRow, String> call(TableColumn<ValueRow, String> configurationRowStringTableColumn) {
        final TableCell<ValueRow, String> cell = new TableCell<ValueRow, String>() {
            final Button btn = new Button("Compare");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btn.setOnAction(event -> {
                        ValueRow configurationRow = getTableView().getItems().get(getIndex());
                        Logger.log(configurationRow.getVariable() + "   " + configurationRow.getValue());
                    });
                    setGraphic(btn);
                    setText(null);
                }
            }
        };

        return cell;
    }
}
