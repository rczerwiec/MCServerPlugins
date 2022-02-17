package smpvp.smpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.Statics;
import smpvp.smpvp.arenas.ArenaManager;
import smpvp.smpvp.arenas.NewArenas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class openAdminKitGui implements CommandExecutor {
    SMPvp plugin = SMPvp.getInstance();
    public static HashMap<Player, Inventory> inventories = new HashMap<>();

    public Inventory inv;

    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] arg){
        Player player = (Player) sender;
        if(NewArenas.playerIsInCustomArena(player)!=null) {
            player.sendMessage("§4§lNie możesz tego użyć podczas walki");
            return false;
        }
        else if(ArenaManager.playerIsInArena(player)) {
            player.sendMessage("§4§lNie możesz tego użyć podczas walki|zwykla");
            return false;
        }
        else if(player.getGameMode() != GameMode.CREATIVE){
            player.sendMessage("§4§lMusisz być w trybie tworzenia!");
            return false;
        }
        else if(!player.isOp()){
            player.sendMessage("§4§lTa komenda zarezerwowana jest dla Administracji!");
        }
        else if(label.equalsIgnoreCase("adminkit")){
            if(!(sender instanceof Player)){
                sender.sendMessage("§4§lMusisz być graczem!");
                return true;
            }
            //Otwórz gui
            createInv(player);
            //player.openInventory(inv);
            openInventory(player);


        }
        return false;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public void createInv(Player p){

        inv = Bukkit.createInventory(null,45, ChatColor.DARK_BLUE+"Kit Generator | "+p.getName());

        ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        //Głowa
        meta.setDisplayName(ChatColor.DARK_BLUE + "HELM");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Przeciągnij hełm pod ten slot");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(0,item);

        //Klata
        meta.setDisplayName(ChatColor.DARK_BLUE + "ZBROJA");
        lore.set(0,ChatColor.GRAY + "Przeciągnij zbroje pod ten slot");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(1,item);

        //Spodnie
        meta.setDisplayName(ChatColor.DARK_BLUE + "SPODNIE");
        lore.set(0,ChatColor.GRAY + "Przeciągnij spodnie pod ten slot");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(2,item);

        //Spodnie
        meta.setDisplayName(ChatColor.DARK_BLUE + "BUTY");
        lore.set(0,ChatColor.GRAY + "Przeciągnij buty pod ten slot");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(3,item);


        //Reszta
        item.setType(Material.GREEN_STAINED_GLASS_PANE);
        meta.setDisplayName(ChatColor.GREEN + "RESZTA:");
        lore.set(0,ChatColor.GRAY + "Przeciagaj przedmioty na prawo od tego slotu ->");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(18,item);

        //Zapisz kit
        item.setType(Material.GREEN_GLAZED_TERRACOTTA);
        meta.setDisplayName(ChatColor.GREEN + "ZAPISZ");
        lore.set(0,ChatColor.GRAY + "Zapisz swój kit");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(Statics.SAVE_BUTTON,item);

        //Anuluj
        item.setType(Material.RED_GLAZED_TERRACOTTA);
        meta.setDisplayName(ChatColor.RED + "ANULUJ");
        lore.set(0,ChatColor.GRAY + "Anuluj tworzenie kitu");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(Statics.CANCEL_BUTTON,item);

        for (int i=4; i<9; i++){
            item.setType(Material.BLACK_STAINED_GLASS_PANE);
            meta.setDisplayName(ChatColor.BLACK + "X");
            lore.set(0,ChatColor.GRAY + "X");
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(i,item);
        }
        for (int i=13; i<18; i++){
            item.setType(Material.BLACK_STAINED_GLASS_PANE);
            meta.setDisplayName(ChatColor.BLACK + "X");
            lore.set(0,ChatColor.GRAY + "X");
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(i,item);
        }
        for (int i=36; i<43; i++){
            item.setType(Material.BLACK_STAINED_GLASS_PANE);
            meta.setDisplayName(ChatColor.BLACK + "X");
            lore.set(0,ChatColor.GRAY + "X");
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(i,item);
        }

        inventories.put(p,inv);
    }
}
