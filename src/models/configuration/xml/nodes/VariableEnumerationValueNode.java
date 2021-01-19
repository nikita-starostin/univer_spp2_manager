package models.configuration.xml.nodes;

public class VariableEnumerationValueNode {
    public String value;
    public String aliase;

    public String getValue() {
        return value;
    }

    public VariableEnumerationValueNode setValue(String value) {
        this.value = value;
        return this;
    }

    public String getAliase() {
        return aliase;
    }

    public VariableEnumerationValueNode setAliase(String aliase) {
        this.aliase = aliase;
        return this;
    }
}
