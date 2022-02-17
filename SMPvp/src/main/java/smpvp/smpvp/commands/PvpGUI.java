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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.arenas.ArenaManager;
import smpvp.smpvp.arenas.NewArenas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PvpGUI implements CommandExecutor {
    SMPvp plugin = SMPvp.getInstance();
    public static HashMap<Player, Inventory> inventories = new HashMap<>();

    public Inventory inv;

    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] arg){

        Player player = (Player) sender;
        if(NewArenas.playerIsInCustomArena(player)!=null) {
            player.sendMessage("Nie możesz tego użyć podczas walki|customowa");
            return false;
        }
        else if(ArenaManager.playerIsInArena(player)) {
            player.sendMessage("Nie możesz tego użyć podczas walki|zwykla");
            return false;
        }
        else if(label.equalsIgnoreCase("pvp")){
            if(!(sender instanceof Player)){
                sender.sendMessage("Musisz być graczem!");
                return true;
            }
            //Otwórz gui z kitami
            createInv(player);
            openInventory(player);


        }
        return false;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public void createInv(Player p){
        inv = Bukkit.createInventory(null,27, ChatColor.BLUE+"SMPVP");

        List<String> mykits = plugin.kitlist.getConfig().getStringList(p.getName()+".name");
        ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        //Tryb Tworzenia
        item.setType(Material.DIAMOND_BLOCK);
        meta.setDisplayName(ChatColor.RED + "TRYB TWORZENIA");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Kliknij żeby przenieść się do areny tworzenia!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(11,item);

        //Twórz Kit
        item.setType(Material.IRON_AXE);
        meta.setDisplayName(ChatColor.RED + "UTWÓRZ KIT");
        lore.set(0,ChatColor.GRAY + "Kliknij, żeby utworzyć własny kit!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(12,item);

        //Edytuj Kit
        item.setType(Material.CRAFTING_TABLE);
        meta.setDisplayName(ChatColor.RED + "EDYTUJ KIT");
        lore.set(0,ChatColor.GRAY + "Kliknij, żeby edytować jeden ze swoich kitów");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(13,item);

        //Anuluj
        item.setType(Material.RED_STAINED_GLASS_PANE);
        meta.setDisplayName(ChatColor.RED + "ANULUJ");
        lore.set(0,ChatColor.GRAY + "Anuluj tworzenie kitu");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(26,item);

        //Wyjście
        item.setType(Material.SKELETON_SKULL);
        meta.setDisplayName(ChatColor.RED + "WYJDZ Z TRYBU TWORZENIA");
        lore.set(0,ChatColor.GRAY + "Lub wpisz /kill");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(15,item);

        //Wyzwyij na pojedynek
        item.setType(Material.DIAMOND_SWORD);
        meta.setDisplayName(ChatColor.RED + "POJEDYNEK");
        lore.set(0,ChatColor.GRAY + "Wpisz /pojedynek <nick>");
        lore.add(ChatColor.GRAY + "żeby walczyć z innym graczem!");
        lore.add(ChatColor.GRAY + "TYLKO POZA TRYBEM TWORZENIA");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(14,item);






        inventories.put(p,inv);

    }
}
