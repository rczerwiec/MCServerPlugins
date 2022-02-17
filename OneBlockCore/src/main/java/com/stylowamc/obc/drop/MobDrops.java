package com.stylowamc.obc.drop;

import com.stylowamc.obc.items.DarkPiece;
import com.stylowamc.obc.items.LightPiece;
import com.stylowamc.obc.items.StarBook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Random;

public class MobDrops implements Listener {
    @EventHandler
    public void SetMobDrop(EntityDeathEvent e){
        //Drop z każdego MOBA
        if(e.getEntity() instanceof Monster && !(e.getEntity() instanceof Enderman)){
            Random r = new Random();
            float chance = r.nextFloat();
            if (chance <= 0.002f){ //1% szansy na drop ciemnego odłamku
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), DarkPiece.create());
            }
        }
        if(e.getEntity() instanceof Monster && !(e.getEntity() instanceof Enderman)){
            Random r = new Random();
            float chance = r.nextFloat();
            if (chance <= 0.002f){ //1% szansy na drop ciemnego odłamku
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), LightPiece.create());
            }
        }
        if(e.getEntity() instanceof Skeleton){
            Random r = new Random();
            float chance = r.nextFloat();
            if (chance <= 0.01f){ //5% szansy na drop czaszki szkieleta z szkieleta
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.SKELETON_SKULL));
            }
        }
        if(e.getEntity() instanceof WitherSkeleton){
            List<ItemStack> drops = e.getDrops();
            for (ItemStack x : drops){
                if (x.getType() == Material.WITHER_SKELETON_SKULL){
                    x.setAmount(0);
                }
            }
        }
        if(e.getEntity() instanceof Wither){
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), StarBook.create());
            List<ItemStack> drops = e.getDrops();
            for (ItemStack x : drops){
                if (x.getType() == Material.NETHER_STAR){
                    x.setAmount(0);
                }
            }
        }
    }
}