package it.polimi.ingsw.network.messages;

import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter implements JsonSerializer, JsonDeserializer<Object> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    /**
     * Used to deserialize an interface/abstract class, since gson can't do that alone without a predefined constructor
     * @param jsonElement element to be deserialized
     * @param type of the object
     * @param jsonDeserializationContext Context for serialization that is passed to a custom serializer during invocation of its JsonSerializer.serialize(Object, Type, JsonSerializationContext) method.
     * @return the deserialized object corresponding to the jsonElement
     */
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }

    /**
     * Used to serialize an interface/abstract class, since gson can't do that alone without a predefined constructor
     * @param jsonElement element to be serialized
     * @param type of the object
     * @param jsonSerializationContext Context for serialization that is passed to a custom serializer during invocation of its JsonSerializer.serialize(Object, Type, JsonSerializationContext) method.
     * @return a jsonObject representing the jsonElement
     */
    public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }

    /**
     * Helper method to get the className of the object to be deserialized
     * @param className string representing the name of the class
     * @return the Class corresponding to that name
     */
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
