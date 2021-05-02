package me.miquiis.sharedbackpack;

import me.miquiis.sharedbackpack.commands.CommandManager;
import me.miquiis.sharedbackpack.managers.BackpackManager;
import me.miquiis.sharedbackpack.managers.ConfigManager;
import me.miquiis.sharedbackpack.tasks.SaveTask;
import org.bukkit.plugin.java.JavaPlugin;

public class SharedBackpack extends JavaPlugin {

    private static SharedBackpack instance;

    private CommandManager commandManager;
    private ConfigManager configManager;
    private BackpackManager backpackManager;

    private SaveTask saveTask;

    @Override
    public void onEnable()
    {
        instance = this;

        configManager = new ConfigManager(this);
        backpackManager = new BackpackManager(this);
        commandManager = new CommandManager(this);

        saveTask = new SaveTask(this);

        getServer().getScheduler().runTaskTimerAsynchronously(this, saveTask, getConfigManager().getInt("auto-save") * 20, getConfigManager().getInt("auto-save") * 20);
    }

    @Override
    public void onDisable()
    {
        getBackpackManager().save();

        instance = null;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }

    public static SharedBackpack getInstance() {
        return instance;
    }
}
