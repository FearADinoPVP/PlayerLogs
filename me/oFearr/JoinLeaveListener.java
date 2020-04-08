package me.oFearr;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class JoinLeaveListener implements Listener {
    public Main plugin;
    public JoinLeaveListener(Main instance) {
        plugin = instance;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        String player = event.getPlayer().getName();
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.joinLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " joined the server.");
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw1 = new FileWriter(plugin.unfilteredLog, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("[" + time + "] " + player + " joined the server.");
            bw1.newLine();
            fw1.flush();
            bw1.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        String player = event.getPlayer().getName();
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.leaveLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " left the server.");
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw1 = new FileWriter(plugin.unfilteredLog, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("[" + time + "] " + player + " left the server.");
            bw1.newLine();
            fw1.flush();
            bw1.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
