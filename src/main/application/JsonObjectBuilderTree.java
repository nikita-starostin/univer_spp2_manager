package main.application;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.LinkedHashMap;
import java.util.Map;

// JsonObjectBuilder.add(string, JsonObjectBuilder) calls build, but after build we can not add new props
public class JsonObjectBuilderTree {
    public JsonObjectBuilder jsonObjectBuilder;
    public LinkedHashMap<String, JsonObjectBuilderTree> jsonObjectsToAdd = new LinkedHashMap<>();

    public JsonObjectBuilderTree(JsonObjectBuilder jsonObjectBuilder) {
        this.jsonObjectBuilder = jsonObjectBuilder;
    }

    public void add(String key, JsonObjectBuilderTree jsonObjectBuilderTree) {
        jsonObjectsToAdd.put(key, jsonObjectBuilderTree);
    }

    public boolean contains(String key) {
        return jsonObjectsToAdd.containsKey(key);
    }

    public JsonObjectBuilderTree get(String key) {
        return jsonObjectsToAdd.get(key);
    }

    public JsonObject build() throws Exception {
        if (this.jsonObjectBuilder == null) {
            throw new Exception("JsonObjectBuilder is null");
        }

        var iterator = jsonObjectsToAdd.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonObjectBuilderTree> pair = iterator.next();
            jsonObjectBuilder.add(pair.getKey(), pair.getValue().build());
            iterator.remove(); // avoids concurrent exception https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        }

        return jsonObjectBuilder.build();
    }
}
