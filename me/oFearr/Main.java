package me.oFearr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.events.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class Main extends JavaPlugin implements Listener {
    public final Logger logger = Logger.getLogger("Minecraft");
    File chatLog;
    File cmdLog;
    File unfilteredLog;
    File joinLog;
    File leaveLog;
    File blockBreakLog;
    File blockPlaceLog;
    File spawnerLog;
    File blockLog;
    File deathLog;
    File itemPickupLog;
    File itemDropLog;
    File itemLog;
    File gamemodeLog;

    @Override
    public void onEnable() {
        System.out.println(ChatColor.AQUA + "Starting up...");
        loadConfig();
        if (this.getConfig().getString("chat-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        }
        if (this.getConfig().getString("command-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        }
        if (this.getConfig().getString("join-leave-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
        }
        if (this.getConfig().getString("block-break-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        }
        if (this.getConfig().getString("block-place-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        }
        if (this.getConfig().getString("death-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        }
        if (this.getConfig().getString("item-drop-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new ItemDropListener(this), this);
        }
        if (this.getConfig().getString("item-pickup-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new ItemPickupListener(this), this);
        }
        if (this.getConfig().getString("gamemode-logs") == "true") {
            this.getServer().getPluginManager().registerEvents(new GamemodeListener(this), this);
        }
        getServer().getPluginManager().registerEvents(this, this);

        //this.getServer().getPluginManager().registerEvents(new SpawnerListener(this), this);
        String pluginFolder = getDataFolder().getAbsolutePath();
        new File(pluginFolder).mkdirs();
        chatLog = new File(pluginFolder + File.separator + "ChatLog.txt");
        cmdLog = new File(pluginFolder + File.separator + "CommandLog.txt");
        unfilteredLog = new File(pluginFolder + File.separator + "GeneralLog.txt");
        joinLog = new File(pluginFolder + File.separator + "LoginLog.txt");
        leaveLog = new File(pluginFolder + File.separator + "LogoutLog.txt");
        blockBreakLog = new File(pluginFolder + File.separator + "BlockBreakLog.txt");
        blockPlaceLog = new File(pluginFolder + File.separator + "BlockPlaceLog.txt");
       // spawnerLog = new File(pluginFolder + File.separator + "SpawnerBreakLog.txt");
        blockLog = new File(pluginFolder + File.separator + "MasterBlockLog.txt");
        deathLog = new File(pluginFolder + File.separator + "DeathLog.txt");
        itemPickupLog = new File(pluginFolder + File.separator + "ItemPickupLog.txt");
        itemDropLog = new File(pluginFolder + File.separator + "ItemDropLog.txt");
        itemLog = new File(pluginFolder + File.separator + "GeneralItemLog.txt");
        gamemodeLog = new File(pluginFolder + File.separator + "GamemodeLog.txt");
        try {
            if(!chatLog.exists()){
                chatLog.createNewFile();
            }
            if(!cmdLog.exists()){
                cmdLog.createNewFile();
            }
            if(!unfilteredLog.exists()){
                unfilteredLog.createNewFile();
            }
            if(!joinLog.exists()){
                joinLog.createNewFile();
            }
            if(!leaveLog.exists()){
                leaveLog.createNewFile();
            }
            if(!blockBreakLog.exists()){
                blockBreakLog.createNewFile();
            }
            if(!blockPlaceLog.exists()){
                blockPlaceLog.createNewFile();
            }
        /*    if(!spawnerLog.exists()){
              spawnerLog.createNewFile();
            } */
            if(!blockLog.exists()){
                blockLog.createNewFile();
            }
            if(!deathLog.exists()){
                blockLog.createNewFile();
            }
            if(!itemDropLog.exists()){
                itemPickupLog.createNewFile();
            }
            if(!itemPickupLog.exists()){
                itemPickupLog.createNewFile();
            }
            if(!itemLog.exists()){
                itemLog.createNewFile();
            }
            if(!gamemodeLog.exists()){
                gamemodeLog.createNewFile();
            }
        } catch (Exception e) {

        }

    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.AQUA + "Shutting down...");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equals("pl-admin")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(!(player.hasPermission("playerlogs.admin"))) {
                    player.sendMessage(this.getConfig().getString("prefix") + " " + this.getConfig().getString("insufficient-permissions-message"));
                    return false;
                }
                Inventory gui = Bukkit.createInventory(player, 27, ChatColor.RED + "PlayerLogs Admin GUI");

                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack CommandLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack JoinLeaveLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack BBreakLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack BPlaceLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack IDropLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack IPickupLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack GamemodeLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack DeathLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemStack Surrounding = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
                ItemStack Info = new ItemStack(Material.PAPER, 1);
                ItemStack Reboot = new ItemStack(Material.REDSTONE_TORCH_ON, 1);

                if (this.getConfig().getString("chat-logs") == "false") {
                    ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("command-logs") == "'true'") {
                    ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("command-logs") == "false") {
                    CommandLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("command-logs") == "true") {
                    CommandLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("join-leave-logs") == "false") {
                    JoinLeaveLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("join-leave-logs") == "true") {
                    JoinLeaveLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("block-break-logs") == "false") {
                    BBreakLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("block-break-logs") == "true") {
                    BBreakLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("join-leave-logs") == "false") {
                    JoinLeaveLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("join-leave-logs") == "true") {
                    JoinLeaveLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("block-place-logs") == "false") {
                    BPlaceLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("block-place-logs") == "true") {
                    BPlaceLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("death-logs") == "false") {
                    DeathLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("death-logs") == "true") {
                    DeathLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("item-drop-logs") == "false") {
                    IDropLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("item-drops-logs") == "true") {
                    IDropLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("item-pickup-logs") == "false") {
                    IPickupLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("item-pickup-logs") == "true") {
                    IPickupLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }
                if (this.getConfig().getString("gamemode-logs") == "false") {
                    GamemodeLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                }else if(this.getConfig().getString("gamemode-logs") == "true") {
                    GamemodeLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                }

                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                if (this.getConfig().getString("chat-logs") == "false") {
                    cl_meta.setDisplayName(ChatColor.AQUA + "Chat Logs (Click to Enable)");
                    cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    cl_meta.setLore(cl_lore);
                } else if (this.getConfig().getString("chat-logs") == "true") {
                    cl_meta.setDisplayName(ChatColor.AQUA + "Chat Logs (Click to Disable)");
                    cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    cl_meta.setLore(cl_lore);
                }
                ItemMeta cmd_meta = CommandLogs.getItemMeta();
                ArrayList<String> cmd_lore = new ArrayList<>();
                if (this.getConfig().getString("command-logs") == "false") {
                    cmd_meta.setDisplayName(ChatColor.AQUA + "Command Logs (Click to Enable)");
                    cmd_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    cmd_meta.setLore(cmd_lore);
                } else if (this.getConfig().getString("command-logs") == "true") {
                    cmd_meta.setDisplayName(ChatColor.AQUA + "Command Logs (Click to Disable)");
                    cmd_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    cmd_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    cmd_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    cmd_meta.setLore(cmd_lore);
                }
                ItemMeta jl_meta = JoinLeaveLogs.getItemMeta();
                ArrayList<String> jl_lore = new ArrayList<>();
                if (this.getConfig().getString("join-leave-logs") == "false") {
                    jl_meta.setDisplayName(ChatColor.AQUA + "Join-Leave Logs (Click to Enable)");
                    jl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    jl_meta.setLore(jl_lore);
                } else if (this.getConfig().getString("join-leave-logs") == "true") {
                    jl_meta.setDisplayName(ChatColor.AQUA + "Join-Leave Logs (Click to Disable)");
                    jl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    jl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    jl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    jl_meta.setLore(jl_lore);
                }
                ItemMeta bbl_meta = BBreakLogs.getItemMeta();
                ArrayList<String> bbl_lore = new ArrayList<>();
                if (this.getConfig().getString("block-break-logs") == "false") {
                    bbl_meta.setDisplayName(ChatColor.AQUA + "Block Break Logs (Click to Enable)");
                    bbl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    bbl_meta.setLore(bbl_lore);
                } else if (this.getConfig().getString("block-break-logs") == "true") {
                    bbl_meta.setDisplayName(ChatColor.AQUA + "Block Break Logs (Click to Disable)");
                    bbl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    bbl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    bbl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    bbl_meta.setLore(bbl_lore);
                }
                ItemMeta bpl_meta = BPlaceLogs.getItemMeta();
                ArrayList<String> bpl_lore = new ArrayList<>();
                if (this.getConfig().getString("block-place-logs") == "false") {
                    bpl_meta.setDisplayName(ChatColor.AQUA + "Block Place Logs (Click to Enable)");
                    bpl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    bpl_meta.setLore(bpl_lore);
                } else if (this.getConfig().getString("block-place-logs") == "true") {
                    bpl_meta.setDisplayName(ChatColor.AQUA + "Block Place Logs (Click to Disable)");
                    bpl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    bpl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    bpl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    bpl_meta.setLore(bpl_lore);
                }
                ItemMeta dl_meta = DeathLogs.getItemMeta();
                ArrayList<String> dl_lore = new ArrayList<>();
                if (this.getConfig().getString("death-logs") == "false") {
                    dl_meta.setDisplayName(ChatColor.AQUA + "Death Logs (Click to Enable)");
                    dl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    dl_meta.setLore(dl_lore);
                } else if (this.getConfig().getString("death-logs") == "true") {
                    dl_meta.setDisplayName(ChatColor.AQUA + "Death Logs (Click to Disable)");
                    dl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    dl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    dl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    dl_meta.setLore(dl_lore);
                }
                ItemMeta idl_meta = IDropLogs.getItemMeta();
                ArrayList<String> idl_lore = new ArrayList<>();
                if (this.getConfig().getString("item-drop-logs") == "false") {
                    idl_meta.setDisplayName(ChatColor.AQUA + "Item Drop Logs (Click to Enable)");
                    idl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    idl_meta.setLore(idl_lore);
                } else if (this.getConfig().getString("item-drop-logs") == "true") {
                    idl_meta.setDisplayName(ChatColor.AQUA + "Item Drop Logs (Click to Disable)");
                    idl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    idl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    idl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    idl_meta.setLore(idl_lore);
                }
                ItemMeta ipl_meta = IPickupLogs.getItemMeta();
                ArrayList<String> ipl_lore = new ArrayList<>();
                if (this.getConfig().getString("item-pickup-logs") == "false") {
                    ipl_meta.setDisplayName(ChatColor.AQUA + "Item Pickup Logs (Click to Enable)");
                    ipl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    ipl_meta.setLore(ipl_lore);
                } else if (this.getConfig().getString("item-pickup-logs") == "true") {
                    ipl_meta.setDisplayName(ChatColor.AQUA + "Item Pickup Logs (Click to Disable)");
                    ipl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    ipl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    ipl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    ipl_meta.setLore(ipl_lore);
                }
                ItemMeta gl_meta = GamemodeLogs.getItemMeta();
                ArrayList<String> gl_lore = new ArrayList<>();
                if (this.getConfig().getString("gamemode-logs") == "false") {
                    gl_meta.setDisplayName(ChatColor.AQUA + "Gamemode Logs (Click to Enable)");
                    gl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                    gl_meta.setLore(gl_lore);
                } else if (this.getConfig().getString("gamemode-logs") == "true") {
                    gl_meta.setDisplayName(ChatColor.AQUA + "Gamemode Logs (Click to Disable)");
                    gl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    gl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    gl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    gl_meta.setLore(gl_lore);
                }
                ItemMeta surrounding_meta = Surrounding.getItemMeta();
                surrounding_meta.setDisplayName(ChatColor.RED + "Currently Editing");

                ItemMeta info_meta = Info.getItemMeta();
                ArrayList<String> info_lore = new ArrayList<>();
                info_meta.setDisplayName(ChatColor.RED + "Please note:");
                info_lore.add(ChatColor.GREEN + "You will need to reload the config once changes have been made.");
                info_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                info_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                info_meta.setLore(info_lore);

                ItemMeta reboot_meta = Reboot.getItemMeta();
                ArrayList<String> reboot_lore = new ArrayList<>();
                reboot_meta.setDisplayName(ChatColor.RED + "Click to Reload Server");
                reboot_lore.add(ChatColor.GREEN + "You may add a custom cmd via config.");
                reboot_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                reboot_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                ChatLogs.setItemMeta(cl_meta);
                CommandLogs.setItemMeta(cmd_meta);
                JoinLeaveLogs.setItemMeta(jl_meta);
                BBreakLogs.setItemMeta(bbl_meta);
                BPlaceLogs.setItemMeta(bpl_meta);
                IDropLogs.setItemMeta(idl_meta);
                IPickupLogs.setItemMeta(ipl_meta);
                GamemodeLogs.setItemMeta(gl_meta);
                DeathLogs.setItemMeta(dl_meta);
                Surrounding.setItemMeta(surrounding_meta);
                Info.setItemMeta(info_meta);
                Reboot.setItemMeta(reboot_meta);


                    ItemStack[] menu_items = {ChatLogs, CommandLogs, JoinLeaveLogs, BBreakLogs, BPlaceLogs, IDropLogs, IPickupLogs, GamemodeLogs, Surrounding, Info};

                    gui.setItem(0, Surrounding);
                    gui.setItem(1, Surrounding);
                    gui.setItem(2, Surrounding);
                    gui.setItem(3, Surrounding);
                    gui.setItem(4, Reboot);
                    gui.setItem(5, Surrounding);
                    gui.setItem(6, Surrounding);
                    gui.setItem(7, Surrounding);
                    gui.setItem(8, Surrounding);
                    gui.setItem(9, ChatLogs);
                    gui.setItem(10, CommandLogs);
                    gui.setItem(11, JoinLeaveLogs);
                    gui.setItem(12, BBreakLogs);
                    gui.setItem(13, DeathLogs);
                    gui.setItem(14, BPlaceLogs);
                    gui.setItem(15, IDropLogs);
                    gui.setItem(16, IPickupLogs);
                    gui.setItem(17, GamemodeLogs);
                    gui.setItem(18, Surrounding);
                    gui.setItem(19, Surrounding);
                    gui.setItem(20, Surrounding);
                    gui.setItem(21, Surrounding);
                    gui.setItem(22, Info);
                    gui.setItem(23, Surrounding);
                    gui.setItem(24, Surrounding);
                    gui.setItem(25, Surrounding);
                    gui.setItem(26, Surrounding);
                    player.openInventory(gui);
            }

        }

        return true;
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e){

        if(e.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.RED + "PlayerLogs Admin GUI")){
            Player player = (Player) e.getWhoClicked();
            Inventory open = e.getClickedInventory();
            ItemStack item = e.getCurrentItem();


            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Chat Logs (Click to Enable)")){
                this.getConfig().set("chat-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                    cl_meta.setDisplayName(ChatColor.AQUA + "Chat Logs (Click to Disable)");
                    cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                    cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(9, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Chat Logs (Click to Disable)")){
                this.getConfig().set("chat-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Chat Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(9, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Command Logs (Click to Enable)")){
                this.getConfig().set("command-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Command Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(10, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Command Logs (Click to Disable)")){
                this.getConfig().set("command-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Command Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(10, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Join-Leave Logs (Click to Enable)")){
                this.getConfig().set("join-leave-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Join-Leave Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(11, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Join-Leave Logs (Click to Disable)")){
                this.getConfig().set("join-leave-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Join-Leave Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(11, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Block Break Logs (Click to Enable)")){
                this.getConfig().set("block-break-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Block Break Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(12, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Block Break Logs (Click to Disable)")){
                this.getConfig().set("block-break-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Block Break Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(12, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Block Place Logs (Click to Enable)")){
                this.getConfig().set("block-place-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Block Place Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(14, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Block Place Logs (Click to Disable)")){
                this.getConfig().set("block-place-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Block Place Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(14, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Item Drop Logs (Click to Enable)")){
                this.getConfig().set("item-drop-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Item Drop Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(15, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Item Drop Logs (Click to Disable)")){
                this.getConfig().set("item-drop-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Item Drop Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(15, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Item Pickup Logs (Click to Enable)")){
                this.getConfig().set("item-pickup-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Item Pickup Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(16, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Item Pickup Logs (Click to Disable)")){
                this.getConfig().set("item-pickup-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Item Pickup Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(16, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Gamemode Logs (Click to Enable)")){
                this.getConfig().set("gamemode-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Gamemode Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(17, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Gamemode Logs (Click to Disable)")){
                this.getConfig().set("gamemode-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Gamemode Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(17, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Death Logs (Click to Enable)")){
                this.getConfig().set("death-logs", true);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Death Logs (Click to Disable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.GREEN + "Enabled");
                cl_meta.addEnchant(Enchantment.DURABILITY, 1, true);
                cl_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(13, ChatLogs);
                player.updateInventory();
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Death Logs (Click to Disable)")){
                this.getConfig().set("death-logs", false);
                saveConfig();
                ItemStack ChatLogs = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta cl_meta = ChatLogs.getItemMeta();
                ArrayList<String> cl_lore = new ArrayList<>();
                cl_meta.setDisplayName(ChatColor.AQUA + "Death Logs (Click to Enable)");
                cl_lore.add(ChatColor.WHITE + "Current State: " + ChatColor.RED + "Disabled");
                cl_meta.setLore(cl_lore);
                cl_meta.setLore(cl_lore);
                ChatLogs.setItemMeta(cl_meta);
                open.setItem(13, ChatLogs);
                player.updateInventory();

            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click to Reload Server")){
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    String command = this.getConfig().getString("reboot-command");
                    Bukkit.dispatchCommand(console, command);

                player.sendMessage(this.getConfig().getString("prefix") + ChatColor.GREEN + "Sucessfully reloaded the config file!");
            }
            e.setCancelled(true);
        }

    }
}
