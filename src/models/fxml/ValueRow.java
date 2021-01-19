package models.fxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ValueRow {
    private StringProperty variable;
    private StringProperty source;
    private StringProperty value;
    private ValueType valueType;

    public ValueRow(String variable, String source, String value, ValueType valueType) {
        this.variable = new SimpleStringProperty(variable);
        this.source = new SimpleStringProperty(source);
        this.value = new SimpleStringProperty(value);
        this.valueType = valueType;
    }

    public String getVariable() {
        return variable.get();
    }

    public void setVariable(String variable) {
        this.variable.set(variable);
    }

    public String getSource() {
        return source.get();
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public StringProperty variableProperty() {
        return this.variable;
    }

    public StringProperty valueProperty() {
        return this.value;
    }

    public StringProperty sourceProperty() {
        return this.source;
    }

    public ValueType getValueType() {
        return valueType;
    }
}
