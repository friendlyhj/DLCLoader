package youyihj.dlcloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Mod(
        modid = DLCLoader.MOD_ID,
        name = DLCLoader.MOD_NAME,
        version = DLCLoader.VERSION,
        dependencies = DLCLoader.DEPENDENCIES
)
public class DLCLoader {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "dlcloader";
    public static final String MOD_NAME = "DLCLoader";
    public static final String DEPENDENCIES = "required-after:crafttweaker";
    public static final String VERSION = "1.0";
    private static final String JSON_NAME = "config/dlcloader/dlc.json";

    public DLCManager dlcManager;

    @Mod.Instance(MOD_ID)
    public static DLCLoader INSTANCE;

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DLCManager.class, DLCManagerDeserializer.INSTANCE);
        Gson gson = gsonBuilder.create();
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction(DLCPreprocessor.NAME, DLCPreprocessor::new);

        try {
            Reader reader = new InputStreamReader(new FileInputStream(JSON_NAME));
            dlcManager = gson.fromJson(reader, DLCManager.class);
            dlcManager.checkAllDLC();
        } catch (IOException e) {
            LOGGER.catching(e);
        }
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        File exampleJSON = new File("config/dlcloader/example/dlc.json");
        File exampleZS = new File("config/dlcloader/example/dlcUsage.zs");
        String jsonContent =
                "[\n" +
                        "    {\n" +
                        "        \"__comment0\": \"put your json to config/dlc_loader/dlc.json\",\n" +
                        "        \"__comment1\": \"this is only a comment. remove it in your configuration\",\n" +
                        "        \"name\": \"vanilla\",\n" +
                        "        \"mods\": [\n" +
                        "            \"minecraft\",\n" +
                        "            \"forge\"\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"name\": \"tech\",\n" +
                        "        \"mods\": [\n" +
                        "            \"thermalexpansion\",\n" +
                        "            \"mekanism\"\n" +
                        "        ]\n" +
                        "    }\n" +
                        "]";
        String zsContent =
                "#dlcloaded vanilla !tech\n" +
                "\n" +
                "print(\"DLC Vanilla is loaded but DLC tech is not loaded!\");\n" +
                "print(\"minecraft and forge is loaded\");\n" +
                "print(\"but thermal expansion or mekanium is not loaded\");";
        try {
            FileUtils.writeStringToFile(exampleJSON, jsonContent, StandardCharsets.UTF_8);
            FileUtils.writeStringToFile(exampleZS, zsContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.catching(e);
        }
    }
}
