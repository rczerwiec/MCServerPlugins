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

public class acceptationGUI implements CommandExecutor {

    SMPvp plugin = SMPvp.getInstance();
    public static HashMap<Player, Inventory> inventories = new HashMap<>();

    public Inventory inv;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(NewArenas.playerIsInCustomArena(player)!=null) {
                player.sendMessage("§4§lNie możesz tego użyć podczas walki|customowa");
                return false;
            }
            else if(ArenaManager.playerIsInArena(player)) {
                player.sendMessage("§4§lNie możesz tego użyć podczas walki|zwykla");
                return false;
            }
            else if (arg.length == 0) {
                player.sendMessage("§4§lWprowadź nick gracza");
            }
            else if(player.getGameMode() != GameMode.SURVIVAL){
                player.sendMessage("§4§lNie możesz tego użyć w trybie tworzenia!");
            }
            else {
                Player target = Bukkit.getPlayerExact(arg[0]);
                if (target != null) {
                    createInv(player,target);
                    openInventory(player);
                } else {
                    player.sendMessage("§4§lNie ma takiego gracza!");
                }
            }

        }
        return false;
    }





    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public void createInv(Player p, Player target){
        inv = Bukkit.createInventory(null,36, ChatColor.DARK_BLUE+"Wyzwij gracza "+ target.getName());

        List<String> mykits = plugin.kitlist.getConfig().getStringList(p.getName()+".name");
        ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        for(int i=0; i<mykits.size(); i++){
                meta.setDisplayName(mykits.get(i));
                item.setItemMeta(meta);
                inv.setItem(i,item);
        }

        meta = item.getItemMeta();
        meta.setDisplayName(target.getName());
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Gracz którego wyzwałeś na pojedynek");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(35,item);

        inventories.put(p,inv);

    }

    //W momencie wybrania areny
    public static void acceptationInv(Player target,Player player,String arenaName) {
        Inventory inv = Bukkit.createInventory(null,9, ChatColor.GREEN+"Akceptuj wyzwanie "+ player.getName());

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(player.getName());
        List<String> lore = new ArrayList<String>();
        int kills = SMPvp.getInstance().data.getConfig().getInt("kda."+player.getUniqueId().toString()+".kills");
        int death = SMPvp.getInstance().data.getConfig().getInt("kda."+player.getUniqueId().toString()+".death");
        lore.add(ChatColor.GREEN + "Twój przeciwnik");
        lore.add(ChatColor.GREEN + "Zabójstwa: "+kills);
        lore.add(ChatColor.GREEN + "Smierci: "+death);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(0,item);
        lore.remove(2);
        lore.remove(1);

        meta.setDisplayName(arenaName);
        lore.set(0,ChatColor.GREEN + "Wybrana Arena");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(1,item);

        item.setType(Material.GREEN_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        meta.setDisplayName("AKCEPTUJ");
        lore.set(0,ChatColor.GREEN + "Akceptuj wyzwanie");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(3,item);

        item.setType(Material.RED_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        meta.setDisplayName("ODRZUC");
        lore.set(0,ChatColor.RED + "Odrzuć wyzwanie");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(5,item);

        inventories.put(target,inv);

    }
}