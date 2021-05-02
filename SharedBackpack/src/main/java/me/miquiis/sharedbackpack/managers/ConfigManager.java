package me.miquiis.sharedbackpack.managers;

import me.miquiis.sharedbackpack.SharedBackpack;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final SharedBackpack plugin;
    private final FileConfiguration config;

    public ConfigManager(SharedBackpack plugin)
    {
        this.plugin = plugin;
        this.config = new YamlConfiguration();
        load();
    }

    public <T> T getConfig(String path) {
        return get(path, config);
    }

    public String getString(String path)
    {
        return get(path, config);
    }

    public Integer getInt(String path)
    {
        return config.getInt(path);
    }

    public <T> T get(String path, FileConfiguration config) {
        T result = (T) config.get(path, ChatColor.DARK_RED + "Ocorreu um erro ao carregar a mensagem: " + ChatColor.YELLOW + path);

        if (result instanceof String) {
            return (T) result.toString().replace('&', '\u00A7');
        }
        return result;
    }

    private void load()
    {
        try
        {
            File configFile = new File(plugin.getDataFolder(), "config.yml");
            if (!configFile.exists())
            {
                plugin.saveResource("config.yml", false);
            }
            config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

}
