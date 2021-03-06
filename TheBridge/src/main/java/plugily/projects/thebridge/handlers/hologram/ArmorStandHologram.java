/*
 * TheBridge - Defend your base and try to wipe out the others
 * Copyright (C)  2021  Plugily Projects - maintained by Tigerpanzer_02, 2Wild4You and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package plugily.projects.thebridge.handlers.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tigerpanzer_02, 2Wild4You
 * <p>
 * Created at 31.10.2020
 */
public class ArmorStandHologram {

  private Item entityItem;
  private ItemStack item;
  private List<String> lines = new ArrayList<>();
  private Location location;

  private final List<ArmorStand> armorStands = new ArrayList<>();

  public ArmorStandHologram() {
  }

  public ArmorStandHologram(Location location) {
    this.location = location;
  }

  public ArmorStandHologram(Location location, @NotNull String... lines) {
    this.location = location;
    this.lines = Arrays.asList(lines);

    append();
  }

  public ArmorStandHologram(Location location, @NotNull List<String> lines) {
    this.location = location;
    this.lines = lines;

    append();
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public ItemStack getItem() {
    return item;
  }

  public Item getEntityItem() {
    return entityItem;
  }

  @NotNull
  public List<String> getLines() {
    return lines;
  }

  @NotNull
  public List<ArmorStand> getArmorStands() {
    return armorStands;
  }

  public ArmorStandHologram appendLines(@NotNull String... lines) {
    this.lines = Arrays.asList(lines);
    append();
    return this;
  }

  public ArmorStandHologram appendLines(@NotNull List<String> lines) {
    this.lines = lines;
    append();
    return this;
  }

  public ArmorStandHologram appendLine(@NotNull String line) {
    this.lines.add(line);
    append();
    return this;
  }

  public ArmorStandHologram appendItem(@NotNull ItemStack item) {
    this.item = item;
    append();
    return this;
  }

  public void delete() {
    for(ArmorStand armor : armorStands) {
      armor.setCustomNameVisible(false);
      armor.remove();
      HologramManager.getArmorStands().remove(armor);
    }
    if(entityItem != null) {
      entityItem.remove();
    }
    armorStands.clear();
  }

  public boolean isDeleted() {
    if(entityItem != null) {
      return false;
    }
    return armorStands.isEmpty();
  }

  private void append() {
    delete();
    double distanceAbove = -0.27,
      y = location.getY();

    for(int i = 0; i <= lines.size() - 1; i++) {
      y += distanceAbove;
      ArmorStand eas = getEntityArmorStand(location, y);
      eas.setCustomName(lines.get(i));
      armorStands.add(eas);
      HologramManager.getArmorStands().add(eas);
    }

    if(item != null && item.getType() != org.bukkit.Material.AIR) {
      Location l = location.clone();
      entityItem = location.getWorld().dropItem(l, item);
      if(Bukkit.getServer().getVersion().contains("Paper"))
        entityItem.setCanMobPickup(false);
      entityItem.setCustomNameVisible(false);
      entityItem.setGravity(true);
      entityItem.setInvulnerable(true);
      entityItem.teleport(l);
    }
  }

  /**
   * @param y the y axis of the hologram
   * @return {@link ArmorStand}
   */
  private ArmorStand getEntityArmorStand(Location loc, double y) {
    loc.setY(y);
    location.getWorld().getNearbyEntities(location, 0.2, 0.2, 0.2).forEach(entity -> {
      if(entity instanceof ArmorStand && !armorStands.contains(entity) && !HologramManager.getArmorStands().contains(entity)) {
        entity.remove();
        entity.setCustomNameVisible(false);
        HologramManager.getArmorStands().remove(entity);
      }
    });
    ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
    stand.setVisible(false);
    stand.setGravity(false);
    stand.setCustomNameVisible(true);
    return stand;
  }

}
