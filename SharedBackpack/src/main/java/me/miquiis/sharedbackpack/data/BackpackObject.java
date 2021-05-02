package me.miquiis.sharedbackpack.data;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BackpackObject {

    private String inventoryString;

    public BackpackObject(String inventoryString)
    {
        this.inventoryString = inventoryString;
    }

    public BackpackObject(Inventory inventory)
    {
        this.inventoryString = toBase64(inventory);
    }

    public Inventory toInventory()
    {
        return fromBase64(inventoryString);
    }

    private Inventory fromBase64(String data)
    {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());
            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String toBase64(Inventory inventory)
    {
        try {
            ByteArrayOutputStream finalOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream temporaryOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(temporaryOutputStream);
            int failedItems = 0;

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                try {
                    dataOutput.writeObject(inventory.getItem(i));
                } catch (Exception ignored) {
                    failedItems++;
                    temporaryOutputStream.reset();
                } finally {
                    if (temporaryOutputStream.size() == 0) {
                        dataOutput.writeObject(null);
                    }
                    finalOutputStream.write(temporaryOutputStream.toByteArray());
                    temporaryOutputStream.reset();
                }
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(finalOutputStream.toByteArray());

        } catch (Exception e) {
            throw new IllegalStateException("Cannot into itemstacksz!", e);
        }
    }
}
