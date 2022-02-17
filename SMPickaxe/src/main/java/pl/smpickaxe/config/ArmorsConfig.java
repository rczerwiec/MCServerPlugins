package pl.smpickaxe.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import pl.smpickaxe.Smpickaxe;
import pl.smpickaxe.ores.Bedrock;
import pl.smpickaxe.ores.CustomDiamond;
import pl.smpickaxe.ores.Meteorite;
import pl.smpickaxe.ores.Platinium;

import java.util.List;
import java.util.UUID;

public class ArmorsConfig {

    public static void setup() {
        Smpickaxe plugin = Smpickaxe.getInstance();
        for (int i = 0; i < plugin.getConfig().getKeys(false).size(); i++) {
            String name = plugin.getConfig().getString(i + ".name");
            List lore = plugin.getConfig().getStringList(i + ".lore");
            Integer health = plugin.getConfig().getInt(i + ".health");
            Integer armor = plugin.getConfig().getInt(i + ".armor");
            ItemStack item1 = new ItemStack(Material.valueOf(plugin.getConfig().getString(i + ".item1")));
            ItemStack out = new ItemStack(Material.valueOf(plugin.getConfig().getString(i + ".out")));
            ItemStack item2 = null;
            if (plugin.getConfig().getString(i + ".item2").equals("rubin")) {
                item2 = CustomDiamond.createCustomDiamond();
            } else if (plugin.getConfig().getString(i + ".item2").equals("meteor")) {
                item2 = Meteorite.createMeteorite();
            } else if (plugin.getConfig().getString(i + ".item2").equals("platyna")) {
                item2 = Platinium.createPlatinium();
            } else if (plugin.getConfig().getString(i + ".item2").equals("bedrock")) {
                item2 = Bedrock.createBedrock();
            }
            create(i, name, lore, health, armor, item1, item2, out, EquipmentSlot.valueOf(plugin.getConfig().getString(i + ".placeholder")));


        }
    }


    public static ItemStack create(int i, String name, List lore, int health, int armor, ItemStack item1, ItemStack item2, ItemStack out, EquipmentSlot enumerator) {
        NamespacedKey JebanyKlucz = new NamespacedKey(Smpickaxe.getInstance(), i + "chuj");


        ItemMeta meta = out.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);

        //Modifier max health
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.maxHealth", health, AttributeModifier.Operation.ADD_NUMBER, enumerator);
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, modifier);
        modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", armor, AttributeModifier.Operation.ADD_NUMBER, enumerator);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);


        out.setItemMeta(meta);
        if (Bukkit.getRecipe(JebanyKlucz) != null) {
            Bukkit.removeRecipe(JebanyKlucz);
        }


        RecipeChoice k1 = new RecipeChoice.ExactChoice(item2);

        ShapedRecipe rec = new ShapedRecipe(JebanyKlucz, out);

        rec.shape(" A ", "ABA", " A ");
        rec.setIngredient('A', k1);
        rec.setIngredient('B', item1.getType());

        boolean success = Bukkit.addRecipe(rec);
        if (success)
            Smpickaxe.getInstance().getLogger().fine("Dodany przepis na " + JebanyKlucz.getNamespace() + ":" + JebanyKlucz.getKey());
        else
            Smpickaxe.getInstance().getLogger().fine("NIEPOMYSLNE dodanie przepisu na: " + JebanyKlucz.getNamespace() + ":" + JebanyKlucz.getKey());

        return out;
    }
}

