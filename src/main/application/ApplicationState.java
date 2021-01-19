package main.application;

import main.application.state.variableFilterState.VariableFilterState;
import models.configuration.xml.nodes.SourceNode;
import models.configuration.xml.nodes.VariableEnumerationNode;
import models.configuration.xml.nodes.VariableEnumerationValueNode;
import models.fxml.*;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ApplicationState {
    public List<EnvironmentRow> environmentRows;
    public List<VariableRow> variableRows;
    public List<SourceRow> sourceRows;
    public List<ValueRow> valueRows;
    public VariableFilterState variableFilterState = new VariableFilterState();

    public CompletableFuture<Boolean> pushToSources() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                for (var sourceRow : sourceRows) {
                    var sourceAlias = sourceRow.getAlias();
                    Logger.log("processing push for " + sourceAlias);
                    var jsonObjectBuilderTree = new JsonObjectBuilderTree(Json.createObjectBuilder());
                    var valueRowsForSource = valueRows
                            .stream()
                            .filter(valueRow -> valueRow.getSource().equals(sourceAlias))
                            .collect(Collectors.toList());
                    Logger.log("valueRows for processing amount " + valueRowsForSource.size());
                    valueRowsForSource.forEach(valueRow -> {
                        var target = jsonObjectBuilderTree;
                        var value = valueRow.getValue();
                        var keys = valueRow.getVariable().split("\\.");
                        var key = keys[0];
                        Logger.log("value = " + value + ", key = " + key);
                        for (var i = 0; i < keys.length - 1; ++i) {
                            key = keys[i];
                            Logger.log(key);
                            if (!target.contains(key)) {
                                var nestedObject = new JsonObjectBuilderTree(Json.createObjectBuilder());
                                target.add(key, nestedObject);
                                target = nestedObject;
                            } else {
                                target = target.get(key);
                            }
                        }

                        key = keys[keys.length - 1];
                        switch (valueRow.getValueType()) {
                            case String:
                                target.jsonObjectBuilder.add(key, value);
                                break;
                            case Number:
                                target.jsonObjectBuilder.add(key, Integer.parseInt(value));
                                break;
                            case Null:
                                target.jsonObjectBuilder.addNull(key);
                                break;
                            case Boolean:
                                target.jsonObjectBuilder.add(key, Boolean.parseBoolean(value));
                                break;
                        }
                        Logger.log("processed value");
                    });

                    var absolutePath = sourceRow.getName();
                    try {
                        var jsonFactoryProperties = new HashMap<String, Object>();
                        jsonFactoryProperties.put(JsonGenerator.PRETTY_PRINTING, true);
                        var jsonWriterFactory = Json.createWriterFactory(jsonFactoryProperties);
                        var stringWriter = new StringWriter();
                        var jsonWriter = jsonWriterFactory.createWriter(stringWriter);
                        jsonWriter.write(jsonObjectBuilderTree.build());
                        jsonWriter.close();
                        Logger.log(stringWriter.toString());

                        var printWriter = new PrintWriter(new FileOutputStream(absolutePath));
                        printWriter.write(stringWriter.toString());
                        printWriter.close();
                    } catch (FileNotFoundException e) {
                        Logger.log("Fail to open for write " + absolutePath);
                        return false;
                    }
                }
            } catch (Exception ex) {
                Logger.log(ex.getMessage());
                return false;
            }
            return true;
        });
    }

    public CompletableFuture<Boolean> pullFromSources() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                var xmlFile = new File(DependencyProvider.constants.absolutePathForConfiguration);
                Logger.log("prepare xml", 0);
                var documentBuilderFactory = DocumentBuilderFactory.newInstance();
                var documentBuilder = documentBuilderFactory.newDocumentBuilder();
                var document = documentBuilder.parse(xmlFile);

                Logger.log("normalize xml", 0);
                document.getDocumentElement().normalize();

                Logger.log("parse sources", 0);
                var sourceNodes = new ArrayList<SourceNode>();
                var sourceNodeList = document.getElementsByTagName("Source");
                for (var i = 0; i < sourceNodeList.getLength(); ++i) {
                    var sourceNodeElement = (org.w3c.dom.Element) sourceNodeList.item(i);
                    sourceNodes.add(new SourceNode()
                            .setAbsolutePath(sourceNodeElement.getAttribute("absolutePath"))
                            .setAlias(sourceNodeElement.getAttribute("alias")));
                }
                DependencyProvider.applicationState.sourceRows =
                        sourceNodes
                                .stream()
                                .map(SourceRow::new)
                                .collect(Collectors.toList());

                Logger.log("process variable enumeration nodes", 0);
                var variableEnumerationNodes = new ArrayList<VariableEnumerationNode>();
                var variableEnumerationNodeList = document.getElementsByTagName("VariableEnumeration");
                for (var i = 0; i < variableEnumerationNodeList.getLength(); ++i) {
                    var variableEnumerationNodeElement = (Element) variableEnumerationNodeList.item(i);
                    var variableEnumerationValueNodes = new ArrayList<VariableEnumerationValueNode>();
                    var variableEnumerationValueNodeList = variableEnumerationNodeElement.getElementsByTagName("Value");
                    for (var j = 0; j < variableEnumerationValueNodeList.getLength(); ++j) {
                        var variableEnumerationValueNodeElement = (Element) variableEnumerationValueNodeList.item(j);
                        variableEnumerationValueNodes.add(new VariableEnumerationValueNode()
                                .setAliase(variableEnumerationValueNodeElement.getAttribute("alias"))
                                .setValue(variableEnumerationValueNodeElement.getAttribute("value"))
                        );
                    }
                    variableEnumerationNodes.add(new VariableEnumerationNode()
                            .setVariableName(variableEnumerationNodeElement.getAttribute("variableName"))
                            .setVariableEnumerationValueNodes(variableEnumerationValueNodes)
                    );
                }

                Logger.log("process sources");
                valueRows = new ArrayList<>();
                variableRows = new ArrayList<>();
                for (var sourceNode : sourceNodes) {
                    var key = "";
                    var currKey = "";
                    var value = "";
                    var objectKey = "";
                    var jsonParser = Json.createParser(new FileReader(sourceNode.absolutePath));
                    while (jsonParser.hasNext()) {
                        var event = jsonParser.next();
                        switch (event) {
                            case KEY_NAME:
                                currKey = jsonParser.getString();
                                key = String.join(".", List.of(objectKey, currKey));
                                break;
                            case VALUE_NUMBER:
                                value = jsonParser.getString();
                                valueRows.add(new ValueRow(key, sourceNode.alias, value, ValueType.Number));
                                break;
                            case VALUE_STRING:
                                value = jsonParser.getString();
                                valueRows.add(new ValueRow(key, sourceNode.alias, value, ValueType.String));
                                break;
                            case VALUE_TRUE:
                                value = Boolean.toString(true);
                                valueRows.add(new ValueRow(key, sourceNode.alias, value, ValueType.Boolean));
                                break;
                            case VALUE_FALSE:
                                value = Boolean.toString(false);
                                valueRows.add(new ValueRow(key, sourceNode.alias, value, ValueType.Boolean));
                                break;
                            case VALUE_NULL:
                                value = "N/A";
                                valueRows.add(new ValueRow(key, sourceNode.alias, value, ValueType.Null));
                                break;
                            case START_OBJECT:
                                objectKey = !objectKey.isEmpty()
                                        ? objectKey + "." + currKey
                                        : currKey;
                                break;
                            case END_OBJECT:
                                var keyItems= objectKey.split("\\.");
                                var newKeyItems = Arrays.copyOf(keyItems, keyItems.length - 1);
                                Logger.log("Success");
                                objectKey = String.join(".", newKeyItems);
                                break;
                        }
                    }
                    Logger.log("Finish processing for: " + sourceNode.absolutePath);
                }
                Logger.log("sleep", 0);
                Thread.sleep(1000);
                return true;
            } catch (InterruptedException e) {
                Logger.fatalError("Reading from " + DependencyProvider.constants.absolutePathForConfiguration + " has been interrupted");
                return false;
            } catch (ParserConfigurationException | SAXException e) {
                Logger.fatalError("Fail to parse " + DependencyProvider.constants.absolutePathForConfiguration);
                return false;
            } catch (IOException e) {
                Logger.fatalError("Fail to open " + DependencyProvider.constants.absolutePathForConfiguration);
                return false;
            } catch (NullPointerException e) {
                Logger.fatalError("Null pointer exception while reading from " + DependencyProvider.constants.absolutePathForConfiguration);
                return false;
            } catch (Exception e) {
                Logger.fatalError(e.getMessage());
                return false;
            }
        });
    }
}
