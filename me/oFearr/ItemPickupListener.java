package me.oFearr;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ItemPickupListener implements Listener {

    public Main plugin;
    public ItemPickupListener(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerItemPickup(PlayerPickupItemEvent event){
        String player = event.getPlayer().getName(); //1
        String itemName = event.getItem().getItemStack().getItemMeta().getDisplayName();
        Map<Enchantment, Integer> enchants = event.getItem().getItemStack().getEnchantments();
        Material item = event.getItem().getItemStack().getType(); //2
        int stackSize = event.getItem().getItemStack().getAmount(); //4
        int ID = event.getItem().getItemStack().getTypeId(); //3
        Location Coords = event.getItem().getLocation(); //6
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.itemPickupLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " picked up the item(s): " + item.toString() + ", with the ID of: " + ID + ", stack size of: " + stackSize + ", custom name: " + itemName + ", enchants: " + enchants.toString() + ", at:" + Coords.toString());
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw2 = new FileWriter(plugin.itemLog, true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write("[" + time + "] " + player + " picked up the item(s): " + item.toString() + ", with the ID of: " + ID + ", stack size of: " + stackSize + ", custom name: " + itemName + ", enchants: " + enchants.toString() + ", at:" + Coords.toString());
            bw2.newLine();
            fw2.flush();
            bw2.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
