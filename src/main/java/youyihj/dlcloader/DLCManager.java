package youyihj.dlcloader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyihj
 */
public class DLCManager {
    private final Map<String, DLC> dlcs = new HashMap<>();

    public void addDLC(DLC dlc) {
        dlcs.put(dlc.getName(), dlc);
    }

    public DLC getDLC(String name) {
        return dlcs.get(name);
    }

    public void checkAllDLC() {
        dlcs.values().forEach(DLC::checkLoaded);
    }
}
