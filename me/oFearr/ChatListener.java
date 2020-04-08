package me.oFearr;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class ChatListener implements Listener {

    public Main plugin;
    public ChatListener(Main instance) {
        plugin = instance;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        String player = event.getPlayer().getName();
        String msg = event.getMessage();
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.chatLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " said: " + msg);
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw1 = new FileWriter(plugin.unfilteredLog, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("[" + time + "] " + player + " issued server command: " + msg);
            bw1.newLine();
            fw1.flush();
            bw1.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
