package com.yankton.bankheist.game;

import com.yankton.bankheist.BankHeistPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class KeycardMechanic {
    private Zombie guard;
    private final Location doorLoc;

    public KeycardMechanic(Location doorLoc) {
        this.doorLoc = doorLoc;
    }

    public void spawn() {
        guard = (Zombie) doorLoc.getWorld().spawnEntity(doorLoc.clone().add(0,1,0), EntityType.ZOMBIE);
        guard.setCustomName("§cBank Guard");
        guard.setCustomNameVisible(true);
        guard.setMaxHealth(50);
        guard.setHealth(50);

        // Drop keycard item
        ItemStack keycard = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = keycard.getItemMeta();
        meta.setDisplayName("§eKeycard");
        meta.setCustomModelData(1); // Optional
        keycard.setItemMeta(meta);
        doorLoc.getWorld().dropItemNaturally(doorLoc, keycard);
    }

    public boolean isGuardHit(org.bukkit.entity.Entity hitEntity) {
        return hitEntity == guard;
    }

    public void onKeycardSuccess() {
        guard.remove();
        // "Open door" - particles or command blocks assumed in map, or kill door blocks
        // For now, broadcast
        org.bukkit.Bukkit.broadcastMessage("§aDoor unlocked! Proceed to vault.");
    }

    public Location getDoorLoc() { return doorLoc; }
}

