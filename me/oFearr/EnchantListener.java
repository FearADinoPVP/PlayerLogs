package me.oFearr;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class EnchantListener implements Listener {

    public Main plugin;
    public EnchantListener(Main instance) {
        plugin = instance;
    }
    @EventHandler
    public void onPlayerBreakBlock(EnchantItemEvent event){
        String player = event.getEnchanter().getName();
        Map<Enchantment, Integer> enchant = event.getEnchantsToAdd();
        ItemStack item = event.getItem();
        String itemname = item.getItemMeta().getDisplayName();
        int cost = event.getExpLevelCost();
        Date time = Calendar.getInstance().getTime();
        try {
            FileWriter fw = new FileWriter(plugin.itemLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + time + "] " + player + " enchanted a: " + item.toString() + ", with: " + enchant.toString() + ", cost: " + cost + ", custom name: " + itemname);
            bw.newLine();
            fw.flush();
            bw.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

