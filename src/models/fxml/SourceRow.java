package models.fxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.configuration.xml.nodes.SourceNode;

public class SourceRow {
    private StringProperty name;
    private StringProperty alias;

    public SourceRow(SourceNode sourceNode) {
       this.name = new SimpleStringProperty(sourceNode.absolutePath);
       this.alias = new SimpleStringProperty(sourceNode.alias);
    }

    public SourceRow(String name, String alias) {
        this.name = new SimpleStringProperty(name);
        this.alias = new SimpleStringProperty(alias);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAlias() {
        return this.alias.get();
    }

    public void setAlias(String alias) {
        this.alias.set(alias);
    }

    public StringProperty aliasProperty() {
        return this.alias;
    }
}
