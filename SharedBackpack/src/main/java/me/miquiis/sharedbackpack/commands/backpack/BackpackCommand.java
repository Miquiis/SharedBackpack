package me.miquiis.sharedbackpack.commands.backpack;

import me.miquiis.sharedbackpack.SharedBackpack;
import me.miquiis.sharedbackpack.commands.mCommand;
import me.miquiis.sharedbackpack.commands.mSubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BackpackCommand extends mCommand {

    @Override
    public String getName() {
        return "backpack";
    }

    @Override
    public String getDescription() {
        return "Opens a shared backpack.";
    }

    @Override
    public String getSyntax() {
        return "/backpack";
    }

    @Override
    public String getPermission() {
        return "backpack.open";
    }

    @Override
    public String getPermissionMessage() {
        return "§cYou don't have enough permissions to execute this command. §e[backpack.open]";
    }

    @Override
    public TextComponent getHelp() {
        return null;
    }

    @Override
    public ArrayList<mSubCommand> getSubcommand() {
        return new ArrayList<mSubCommand>(){
            {

            }
        };
    }

    @Override
    public void perform(CommandSender sender, ArrayList<String> args) {
        if (!sender.hasPermission(getPermission())) {
            sender.sendMessage(getPermissionMessage());
            return;
        }

        if (!(sender instanceof Player))
        {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return;
        }

        Player player = (Player)sender;
        player.openInventory(SharedBackpack.getInstance().getBackpackManager().getBackpackInventory());

        sender.spigot().sendMessage(getHelp());
        return;
    }
}
