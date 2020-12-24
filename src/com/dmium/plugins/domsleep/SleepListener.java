package com.dmium.plugins.domsleep;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.world.TimeSkipEvent;

import java.util.*;

import static org.bukkit.Bukkit.broadcastMessage;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.event.EventPriority.HIGHEST;
import static org.bukkit.event.world.TimeSkipEvent.SkipReason.NIGHT_SKIP;

public class SleepListener implements Listener {
    private Set<UUID> sleepyPlayers = new HashSet<>();

    @EventHandler(priority = HIGHEST)
    public void onSleep(PlayerBedEnterEvent event) {
        if(!event.isCancelled()) {
            sleepyPlayers.add(event.getPlayer().getUniqueId());
            broadcastMessage("[DomSleep]: Player " + event.getPlayer().getDisplayName() + " has gone to bed!");
            broadcastMessage("[DomSleep]: (" + sleepyPlayers.size() + "/" + getOverworldPlayers() + ") players are sleeping");
        }
    }

    @EventHandler
    public void onNightSkip(TimeSkipEvent event) {
        if (event.getSkipReason().equals(NIGHT_SKIP)) {
            sleepyPlayers.clear();
            broadcastMessage("[DomSleep]: Everyone is asleep!");
        }
    }

    @EventHandler(priority = HIGHEST)
    public void onGetUp(PlayerBedLeaveEvent event) {
        if (sleepyPlayers.contains(event.getPlayer().getUniqueId())) {
            sleepyPlayers.remove(event.getPlayer().getUniqueId());
            broadcastMessage("[DomSleep]: Player " + event.getPlayer().getDisplayName() + " has got out of bed!");
            broadcastMessage("[DomSleep]: (" + sleepyPlayers.size() + "/" + getOnlinePlayers().size() + ") players are sleeping");
        }
    }

    private long getOverworldPlayers() {
        return getOnlinePlayers().stream().filter(player -> player.getWorld().getEnvironment().equals(World.Environment.NORMAL)).count();
    }
}
