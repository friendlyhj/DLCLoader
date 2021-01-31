package youyihj.dlcloader;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author youyihj
 */
public enum DLCManagerDeserializer implements JsonDeserializer<DLCManager> {
    INSTANCE;

    @Override
    public DLCManager deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DLCManager dlcManager = new DLCManager();
        json.getAsJsonArray().forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            DLC dlc = new DLC(jsonObject.get("name").getAsString());
            jsonObject.get("mods").getAsJsonArray().forEach(element -> {
                dlc.addMod(element.getAsString());
            });
            dlcManager.addDLC(dlc);
        });
        return dlcManager;
    }
}
