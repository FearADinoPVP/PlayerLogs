package me.oFearr;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class GamemodeListener implements Listener {
    public Main plugin;

    public GamemodeListener(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerGameModeChangeEvent event) {
        String player = event.getPlayer().getName();
       GameMode gm = event.getNewGameMode();
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.unfilteredLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + "'s gamemode has been updated to " + gm.toString());
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw1 = new FileWriter(plugin.gamemodeLog, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("[" + time + "] " + player + "'s gamemode has been updated to " + gm.toString());
            bw1.newLine();
            fw1.flush();
            bw1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}