package com.yankton.bankheist.game;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class HeistPlayer {
    private final Player player;
    private int kills = 0;
    private boolean alive = true;
    private boolean hasJuggernaut = false;

    public HeistPlayer(Player player) {
        this.player = player;
    }

    public void incrementKills() {
        kills++;
        player.sendMessage("§eKills: " + kills + "/15");
    }

    public void giveJuggernautArmor() {
        if (hasJuggernaut) return;
        // Full netherite armor
        player.getInventory().setHelmet(new ItemStack(org.bukkit.Material.NETHERITE_HELMET));
        player.getInventory().setChestplate(new ItemStack(org.bukkit.Material.NETHERITE_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(org.bukkit.Material.NETHERITE_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(org.bukkit.Material.NETHERITE_BOOTS));
        // Effects
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 1));
        hasJuggernaut = true;
        player.sendMessage("§aJuggernaut armor equipped!");
    }

    public void onDeath() {
        alive = false;
    }

    // Getters
    public Player getPlayer() { return player; }
    public int getKills() { return kills; }
    public boolean isAlive() { return alive; }
}

