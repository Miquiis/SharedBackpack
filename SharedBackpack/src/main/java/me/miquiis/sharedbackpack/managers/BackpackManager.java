package me.miquiis.sharedbackpack.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.miquiis.sharedbackpack.SharedBackpack;
import me.miquiis.sharedbackpack.data.BackpackObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class BackpackManager {

    private final File backpackFolder;
    private final Inventory backpackInventory;
    private final ConfigManager configManager;
    private final Gson gson;

    public BackpackManager(SharedBackpack plugin)
    {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.backpackFolder = new File(plugin.getDataFolder() + "/backpack");
        this.configManager = plugin.getConfigManager();
        this.backpackInventory = Bukkit.createInventory(null, configManager.getInt("backpack.size"), configManager.getString("backpack.title"));

        loadItems();
    }

    public void save()
    {
        if (!backpackFolder.exists()) {
            backpackFolder.mkdir();
        }

        final BackpackObject backpackObject = new BackpackObject(backpackInventory);

        final String json = gson.toJson(backpackObject);

        File backpackFile = new File(backpackFolder + "/backpack.json");
        if (backpackFile.exists()) backpackFile.delete();

        try {
            Files.write(backpackFile.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadItems()
    {
        if (!backpackFolder.exists()) {
            backpackFolder.mkdir();
            return;
        }

        File backpackFile = new File(backpackFolder + "/backpack.json");
        if (!backpackFile.exists()) return;

        try {
            JsonReader jsonReader = new JsonReader(new FileReader(backpackFile));
            BackpackObject backpackObject = gson.fromJson(jsonReader, BackpackObject.class);
            backpackInventory.setContents(backpackObject.toInventory().getContents());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    public Inventory getBackpackInventory() {
        return backpackInventory;
    }
}
