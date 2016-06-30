package fr.cab13140.failcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class Main extends JavaPlugin implements Listener {
    boolean enabled;
    ConsoleCommandSender cs = this.getServer().getConsoleSender();
    boolean wecompat;
    boolean  failmsg;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Read Config
        enabled = this.getConfig().getBoolean("plugin-enable");
        wecompat = this.getConfig().getBoolean("we-compat");
        failmsg = this.getConfig().getBoolean("fail-msg");

        if (enabled){
            this.getLogger().info("Enabled in config. Starting");

        }else{
            this.getLogger().info("Disabled in config.");
        }

        this.getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
        if (cmd.getName().equalsIgnoreCase("failcommand")||cmd.getName().equalsIgnoreCase("flc")){
            if (args.length == 1) {
                if (Objects.equals(args[0], "reload")) {
                    this.reloadConfig();
                    cs.sendMessage("Reloading FailCommand Config...");
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e){
        String msg = e.getMessage();
        boolean cancel = false;
        if (wecompat){
            cancel = msg.startsWith("(\\?\\?|\\?/|/\\?|::|:/|/:)");
            if (!cancel)
                cancel = msg.startsWith("(\\?|:)");
        }else{
            cancel = msg.startsWith("(\\?|:)");
        }
        e.setCancelled(cancel);
        if (failmsg)
            e.getPlayer().sendMessage(ChatColor.BLUE + "Looks like you failed your command !");
    }
}
