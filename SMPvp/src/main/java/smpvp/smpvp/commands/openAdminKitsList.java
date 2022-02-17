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

import java.util.HashMap;
import java.util.List;

public class openAdminKitsList implements CommandExecutor {

    SMPvp plugin = SMPvp.getInstance();
    public static HashMap<Player,Inventory> inventories = new HashMap<>();

    public Inventory inv;

    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] arg){

        Player player = (Player) sender;
        if(NewArenas.playerIsInCustomArena(player)!=null) {
            player.sendMessage("§4§lNie możesz tego użyć podczas walki");
            return false;
        }
        else if(ArenaManager.playerIsInArena(player)) {
            player.sendMessage("§4§lNie możesz tego użyć podczas walki");
            return false;
        }
        else if(player.getGameMode() != GameMode.CREATIVE){
            player.sendMessage("§4§lMusisz być w trybie tworzenia!");
        }
        else if(!player.isOp()){
            player.sendMessage("§4§lTa komenda zarezerwowana jest dla Administracji!");
        }
        else if(label.equalsIgnoreCase("adminkitlist")){
            if(!(sender instanceof Player)){
                sender.sendMessage("§4§lMusisz być graczem!");
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
        inv = Bukkit.createInventory(null,27, ChatColor.DARK_BLUE+"Kity "+ p.getName());

        List<String> mykits = plugin.adminKitList.getConfig().getStringList("name");
        ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        for(int i=0; i<mykits.size(); i++){
            meta.setDisplayName(mykits.get(i));
            item.setItemMeta(meta);
            inv.setItem(i,item);
        }

        inventories.put(p,inv);

    }


}
