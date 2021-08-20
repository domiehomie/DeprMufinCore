package live.mufin.MufinCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A simple way to create config files
 * @deprecated New config system in the works
 */
public class ConfigFile {

    private MufinCore core;
    private FileConfiguration playerDataConfig = null;
    private File configFile = null;
    private String fileName;


    /**
     * @deprecated New config system in the works
     * @param core Your instance of MufinCore
     * @param fileName the name of your file Without `.yml`
     */
    public ConfigFile(MufinCore core, String fileName) {
        this.core = core;
        this.fileName = fileName + ".yml";
        this.saveDefaultConfig();
    }

    /**
     * Reloads the file
     */
    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(core.plugin.getDataFolder(), fileName);

        this.playerDataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = core.plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.playerDataConfig.setDefaults(defaultConfig);
        }
    }

    /**
     * @return the file
     */
    public FileConfiguration getConfig() {
        if (playerDataConfig == null)
            this.reloadConfig();

        return this.playerDataConfig;
    }

    /**
     * Saves the config.
     */
    public void saveConfig() {
        if (this.playerDataConfig == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            core.plugin.getLogger().warning("Error saving config.");
        }
    }

    /**
     * Saves default config values
     */
    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(core.plugin.getDataFolder(), fileName);

        if (!this.configFile.exists()) {
            core.plugin.saveResource(fileName, false);
        }
    }
}
