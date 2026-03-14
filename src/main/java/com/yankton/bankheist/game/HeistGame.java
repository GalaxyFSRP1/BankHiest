package com.yankton.bankheist.game;

import com.yankton.bankheist.BankHeistPlugin;
import com.yankton.bankheist.arena.HeistArena;
import com.yankton.bankheist.listeners.HeistListener;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class HeistGame {
    private final BankHeistPlugin plugin;
    private HeistArena arena;
    private final Map<UUID, HeistPlayer> players = new HashMap<>();
    private boolean active = false;
    private Player leader;
    private int totalMoney = 0;

    public HeistGame(BankHeistPlugin plugin) {
        this.plugin = plugin;
        this.arena = new HeistArena(plugin.getConfig());
    }

    public void startHeist(List<Player> team, Player leader) {
        this.leader = leader;
        players.clear();
        for (Player p : team) {
            players.put(p.getUniqueId(), new HeistPlayer(p));
        }
        active = true;
        arena.setState(HeistArena.ArenaState.ACTIVE);
        // Spawn keycard and guard
        // Start guard waves via BukkitRunnable
        plugin.getLogger().info("Heist started with " + team.size() + " players.");
    }

    public void stopGame() {
        active = false;
        players.clear();
        arena.setState(HeistArena.ArenaState.WAITING);
    }

    public boolean isActive() { return active; }

    public HeistPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void addKill(UUID uuid) {
        HeistPlayer hp = players.get(uuid);
        if (hp != null) {
            hp.incrementKills();
            checkExfil();
        }
    }

    private void checkExfil() {
        boolean allReady = true;
        for (HeistPlayer hp : players.values()) {
            if (hp.getKills() < plugin.getConfig().getInt("game.guards-per-player", 15) || !hp.isAlive()) {
                allReady = false;
                break;
            }
        }
        if (allReady) {
            arena.setState(HeistArena.ArenaState.EXFIL);
            // Notify, start payout timer
        }
    }

    public void payout() {
        if (!active) return;
        int teamSize = players.size();
        int basePayout = plugin.getConfig().getInt("game.payout-base", 50000);
        int perPlayer = basePayout / teamSize;
        for (HeistPlayer hp : players.values()) {
            if (hp.isAlive()) {
                int amount = perPlayer;
                if (teamSize == 3 && hp.getPlayer() == leader) amount++;
                // Give money items or vault
                hp.getPlayer().getInventory().addItem(MoneyUtil.createMoneyStack(amount));
            }
        }
        stopGame();
    }

    // More: money collection, guard spawns, etc.
    public HeistArena getArena() { return arena; }
    public Map<UUID, HeistPlayer> getPlayers() { return players; }
}

