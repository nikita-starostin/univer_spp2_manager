package models.fxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VariableRow {
    private StringProperty name;
    private StringProperty valueAliases;

    public VariableRow(String name, String valueAliases) {
        this.name = new SimpleStringProperty(name);
        this.valueAliases = new SimpleStringProperty(valueAliases);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getValueAliases() {
        return valueAliases.get();
    }

    public void setValueAliases(String valueAliases) {
        this.valueAliases.set(valueAliases);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty valueAliasesProperty() { return valueAliases; }
}
