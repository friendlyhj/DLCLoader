package youyihj.dlcloader;

import com.google.gson.annotations.Expose;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author youyihj
 */
public class DLC {
    private final List<String> mods = new ArrayList<>();
    private boolean isLoaded = true;

    @Expose(deserialize = false, serialize = false)
    private final String name;

    public DLC(String name) {
        this.name = name;
    }

    public void addMod(String mod) {
        this.mods.add(mod);
    }

    public void addMods(String... mods) {
        this.mods.addAll(Arrays.asList(mods));
    }

    public String getName() {
        return name;
    }

    public void checkLoaded() {
        for (String mod : this.mods) {
            if (!Loader.isModLoaded(mod)) {
                isLoaded = false;
            }
        }
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }
}
