package me.oFearr;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ItemDropListener implements Listener {

    public Main plugin;
    public ItemDropListener(Main instance) {
        plugin = instance;
    }
    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event){
        String player = event.getPlayer().getName(); //1
        String itemName = event.getItemDrop().getItemStack().getItemMeta().getDisplayName();
        Map<Enchantment, Integer> enchants = event.getItemDrop().getItemStack().getEnchantments();
        Material item = event.getItemDrop().getItemStack().getType(); //2
        int stackSize = event.getItemDrop().getItemStack().getAmount(); //4
        int ID = event.getItemDrop().getItemStack().getTypeId(); //3
        Location Coords = event.getItemDrop().getLocation(); //6
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.itemDropLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " dropped the item(s): " + item.toString() + ", with the ID of: " + ID + ", stack size of: " + stackSize + ", custom name: " + itemName + ", enchants: " + enchants.toString() + ", at:" + Coords.toString());
            bw.newLine();
            fw.flush();
            bw.close();
            FileWriter fw2 = new FileWriter(plugin.itemLog, true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write("[" + time + "] " + player + " dropped the item(s): " + item.toString() + ", with the ID of: " + ID + ", stack size of: " + stackSize + ", custom name: " + itemName + ", enchants: " + enchants.toString() + ", at:" + Coords.toString());
            bw2.newLine();
            fw2.flush();
            bw2.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
