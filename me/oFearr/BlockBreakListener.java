package me.oFearr;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class BlockBreakListener implements Listener {

    public Main plugin;
    public BlockBreakListener(Main instance) {
        plugin = instance;
    }
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event){
        String player = event.getPlayer().getName();
        Material block = event.getBlock().getType();
        Location Coords = event.getBlock().getLocation();
        int ID = event.getBlock().getTypeId();
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.blockBreakLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " removed the block: " + block.toString() + ", with the ID of: " + ID + ", at " + Coords.toString());
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw1 = new FileWriter(plugin.unfilteredLog, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("[" + time + "] " + player + " removed the block: " + block.toString() + ", with the ID of: " + ID + ", at " + Coords.toString());
            bw1.newLine();
            fw1.flush();
            bw1.close();
            FileWriter fw2 = new FileWriter(plugin.blockLog, true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write("[" + time + "] " + player + " removed the block: " + block.toString() + ", with the ID of: " + ID + ", at " + Coords.toString());
            bw2.newLine();
            fw2.flush();
            bw2.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
