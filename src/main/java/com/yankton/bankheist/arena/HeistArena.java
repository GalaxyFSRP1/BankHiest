package com.yankton.bankheist.arena;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.World;

public class HeistArena {
    private final Location startLoc;
    private final Location doorLoc;
    private final Location exfilLoc;
    private final World world;
    private ArenaState state = ArenaState.WAITING;

    public enum ArenaState {
        WAITING, ACTIVE, VAULT, EXFIL, COMPLETE, FAILED
    }

    public HeistArena(FileConfiguration config) {
        String worldName = config.getString("arena.world", "world");
        this.world = org.bukkit.Bukkit.getWorld(worldName);
        this.startLoc = new Location(world, config.getDouble("arena.start.x"), config.getDouble("arena.start.y"), config.getDouble("arena.start.z"));
        this.doorLoc = new Location(world, config.getDouble("arena.door-keycard.x"), config.getDouble("arena.door-keycard.y"), config.getDouble("arena.door-keycard.z"));
        this.exfilLoc = new Location(world, config.getDouble("arena.exfil.x"), config.getDouble("arena.exfil.y"), config.getDouble("arena.exfil.z"));
    }

    // Getters
    public Location getStartLoc() { return startLoc; }
    public Location getDoorLoc() { return doorLoc; }
    public Location getExfilLoc() { return exfilLoc; }
    public ArenaState getState() { return state; }
    public void setState(ArenaState state) { this.state = state; }
    public World getWorld() { return world; }

    public void spawnKeycardGuard() {
        // Spawn guard at door
        // To be called by game
    }
}

