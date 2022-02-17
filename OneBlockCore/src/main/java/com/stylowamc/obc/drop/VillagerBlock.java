package com.stylowamc.obc.drop;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Iterator;
import java.util.List;

public class VillagerBlock implements Listener {
    @EventHandler
    public void onVillagerInteract(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager)) return;

        Villager villager = (Villager) e.getRightClicked();

        List<MerchantRecipe> recipes = Lists.newArrayList(villager.getRecipes());

        Iterator<MerchantRecipe> recipeIterator;
        for (recipeIterator = recipes.iterator(); recipeIterator.hasNext(); ) {
            MerchantRecipe recipe = recipeIterator.next();

            if (recipe.getResult().getType().equals(Material.ENCHANTED_BOOK)) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) recipe.getResult().getItemMeta();

                assert meta != null;
                if (meta.hasStoredEnchant(Enchantment.MENDING)) {
                    recipeIterator.remove();
                }
            }
        }

        villager.setRecipes(recipes);

        if (villager.getRecipes().size() == 0) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void FishingReplacement(PlayerFishEvent e){
        if(e.getCaught() != null) {
            Item item = (Item) e.getCaught();

            ItemStack itemStack = item.getItemStack();

            if (itemStack.getType().equals(Material.ENCHANTED_BOOK)) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                assert meta != null;
                if (meta.hasStoredEnchant(Enchantment.MENDING)) {
                    item.remove();
                }
            }
        }
    }

}
