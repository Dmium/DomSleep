package com.dmium.plugins.domsleep;

import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.broadcastMessage;

public class DomSleep extends JavaPlugin {
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new SleepListener(), this);
        broadcastMessage("Domsleep has started");
    }
}
