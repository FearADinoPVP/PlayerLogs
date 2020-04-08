package me.oFearr;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class DeathListener implements Listener {

    public Main plugin;
    public DeathListener(Main instance) {
        plugin = instance;
    }
    @EventHandler
    public void onPlayerChat(PlayerDeathEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        String player = event.getEntity().getName();
       String DeathTo = event.getEntity().getLastDamageCause().getCause().name();
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.deathLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " has died, reason:(" + DeathTo + ")");
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw1 = new FileWriter(plugin.unfilteredLog, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("[" + time + "] " + player + " has died, reason: (" + DeathTo + ")");
            bw1.newLine();
            fw1.flush();
            bw1.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

