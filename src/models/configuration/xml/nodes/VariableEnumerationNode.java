package models.configuration.xml.nodes;

import java.util.List;

public class VariableEnumerationNode {
    public String variableName;
    public List<VariableEnumerationValueNode> variableEnumerationValueNodes;

    public String getVariableName() {
        return variableName;
    }

    public VariableEnumerationNode setVariableName(String variableName) {
        this.variableName = variableName;
        return this;
    }

    public List<VariableEnumerationValueNode> getVariableEnumerationValueNodes() {
        return variableEnumerationValueNodes;
    }

    public VariableEnumerationNode setVariableEnumerationValueNodes(List<VariableEnumerationValueNode> variableEnumerationValueNodes) {
        this.variableEnumerationValueNodes = variableEnumerationValueNodes;
        return this;
    }
}
