package me.miquiis.sharedbackpack.tasks;

import me.miquiis.sharedbackpack.SharedBackpack;
import me.miquiis.sharedbackpack.managers.BackpackManager;

import java.util.concurrent.CompletableFuture;

public class SaveTask implements Runnable {

    private SharedBackpack plugin;
    private BackpackManager backpackManager;

    public SaveTask(SharedBackpack plugin)
    {
        this.plugin = plugin;
        this.backpackManager = plugin.getBackpackManager();
    }

    @Override
    public void run() {
        backpackManager.save();
    }
}
