package smpvp.smpvp.kits;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Kit {
    PlayerInventory inv;
    ItemStack boots;
    ItemStack chestplate;
    ItemStack helmet;
    ItemStack leggins;
    ItemStack hand;
    List<ItemStack> items;

    public Kit(PlayerInventory inventory, ItemStack boots, ItemStack helmet, ItemStack chestplate, ItemStack leggins, ItemStack hand, List<ItemStack> items) {
        this.inv = inventory;
        this.boots = boots;
        this.chestplate = chestplate;
        this.helmet = helmet;
        this.leggins = leggins;
        this.hand = hand;
        this.items = items;
        this.giveItems();
    }
    public Kit(PlayerInventory inventory, ItemStack boots, ItemStack helmet, ItemStack chestplate, ItemStack leggins, List<ItemStack> items) {
        this.inv = inventory;
        this.boots = boots;
        this.chestplate = chestplate;
        this.helmet = helmet;
        this.leggins = leggins;
        this.items = items;
        this.giveItems();
    }


    public void giveItems() {
        this.inv.setBoots(this.boots);
        this.inv.setChestplate(this.chestplate);
        this.inv.setHelmet(this.helmet);
        this.inv.setLeggings(this.leggins);
        //Bukkit.broadcastMessage("HEHEEHE");
        if(hand != null){
            this.inv.addItem(new ItemStack[]{this.hand});
        }
        for(ItemStack i: items){
            this.inv.addItem(i);
            //Bukkit.broadcastMessage(i.toString());
        }
    }
}