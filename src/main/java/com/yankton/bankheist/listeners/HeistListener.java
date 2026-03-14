package com.yankton.bankheist.listeners;

import com.yankton.bankheist.BankHeistPlugin;
import com.yankton.bankheist.game.HeistGame;
import com.yankton.bankheist.game.HeistPlayer;
import com.yankton.bankheist.game.KeycardMechanic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDeathEvent;

public class HeistListener implements Listener {
    private final BankHeistPlugin plugin;

    public HeistListener(BankHeistPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        HeistGame game = plugin.getHeistGame();
        if (!game.isActive()) return;
        HeistPlayer hp = game.getPlayer(event.getEntity().getUniqueId());
        if (hp != null) {
            hp.onDeath();
            checkTeamWipe(game);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        HeistGame game = plugin.getHeistGame();
        if (!game.isActive()) return;
        if (event.getEntity().getKiller() instanceof Player killer) {
            if (event.getEntity().getType() == EntityType.ZOMBIE || event.getEntity().getCustomName() != null && event.getEntity().getCustomName().contains("Guard")) {
                game.addKill(killer.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        HeistGame game = plugin.getHeistGame();
        if (!game.isActive() || !(event.getEntity().getShooter() instanceof Player)) return;
        Projectile proj = event.getEntity();
        if (proj.getItem().getType() == org.bukkit.Material.TRIPWIRE_HOOK) { // Keycard
            // Assume first door guard
            // Better: track active keycard mechanic in game
            // Stub: assume success if hits named guard
            if (event.getHitEntity() != null && event.getHitEntity().getCustomName().contains("Bank Guard")) {
                // Trigger success
                org.bukkit.Bukkit.broadcastMessage("§aKeycard used! Door opening...");
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // Optional: juggernaut damage mods
    }

    private void checkTeamWipe(HeistGame game) {
        boolean allDead = true;
        for (HeistPlayer hp : game.getPlayers().values()) {
            if (hp.isAlive()) {
                allDead = false;
                break;
            }
        }
        if (allDead) {
            org.bukkit.Bukkit.broadcastMessage("§cAll players dead. Heist failed!");
            game.stopGame();
        }
    }
}

