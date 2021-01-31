package youyihj.dlcloader;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.preprocessor.PreprocessorActionBase;
import crafttweaker.runtime.ScriptFile;

/**
 * @author youyihj
 */
public class DLCPreprocessor extends PreprocessorActionBase {
    private boolean isLoaded = true;
    public static final String NAME = "dlcloaded";

    public DLCPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        if (DLCLoader.INSTANCE.dlcManager == null)
            return;
        preprocessorLine = preprocessorLine.substring(NAME.length() + 1).trim();
        String[] dlcs = preprocessorLine.split(" ");
        for (String dlcName : dlcs) {
            dlcName = dlcName.trim();
            boolean shouldDLCLoaded = true;
            if (dlcName.startsWith("!")) {
                dlcName = dlcName.substring(1);
                shouldDLCLoaded = false;
            }
            DLC dlc = DLCLoader.INSTANCE.dlcManager.getDLC(dlcName);
            if (dlc == null) {
                CraftTweakerAPI.logError("DLC " + dlcName + " is not defined! check your dlc.json!");
            } else {
                if (dlc.isLoaded() != shouldDLCLoaded) {
                    isLoaded = false;
                    return;
                }
            }
        }
    }

    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        if (!isLoaded) {
            scriptFile.setParsingBlocked(true);
            scriptFile.setCompileBlocked(true);
            scriptFile.setExecutionBlocked(true);
        }
    }

    @Override
    public String getPreprocessorName() {
        return NAME;
    }
}
