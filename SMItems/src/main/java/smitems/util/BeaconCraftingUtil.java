package smitems.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import smitems.SMItems;

public class BeaconCraftingUtil {
    public static final NamespacedKey BEACON_KEY = new NamespacedKey(SMItems.getInstance(), "beaconkey");

    private BeaconCraftingUtil() {
    }

    public static ItemStack createBeacon() {
        ItemStack netherstar = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = netherstar.getItemMeta();
        meta.setDisplayName("Gwiazda Netheru");
        netherstar.setItemMeta(meta);

        return netherstar;
    }

    public static void registerBeaconRecipe() {
        ItemStack mending = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) mending.getItemMeta();
        meta.addStoredEnchant(Enchantment.MENDING, 1, true);
        mending.setItemMeta(meta);


        RecipeChoice.ExactChoice mendingBook = new RecipeChoice.ExactChoice(mending);

        ShapedRecipe rec = new ShapedRecipe(BEACON_KEY, createBeacon());
        rec.shape("AAA", "AAA", "AAA");
        rec.setIngredient('A', mendingBook);


        boolean success = Bukkit.addRecipe(rec);
        if (success)
            SMItems.getInstance().getLogger().fine("Dodany przepis na " + BEACON_KEY.getNamespace() + ":" + BEACON_KEY.getKey());
        else
            SMItems.getInstance().getLogger().fine("NIEPOMYSLNE dodanie przepisu na: " + BEACON_KEY.getNamespace() + ":" + BEACON_KEY.getKey());

    }

    public static boolean unregisterBeaconRecipe() {
        boolean success = Bukkit.removeRecipe(BEACON_KEY);

        if (success)
            SMItems.getInstance().getLogger().fine("Usunieto przepis na:" + BEACON_KEY.getNamespace() + ":" + BEACON_KEY.getKey());

        else
            SMItems.getInstance().getLogger().fine("Nie udalo sie usunac przepisu na: " + BEACON_KEY.getNamespace() + ":" + BEACON_KEY.getKey());

        return success;
    }
}
