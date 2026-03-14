package com.yankton.bankheist.commands;

import com.yankton.bankheist.BankHeistPlugin;
import com.yankton.bankheist.game.HeistGame;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BankHeistCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player leader = (Player) sender;
        if (!leader.hasPermission("bankheist.start")) {
            leader.sendMessage("§cNo permission!");
            return true;
        }

        if (args.length != 1 || !args[0].equalsIgnoreCase("start")) {
            leader.sendMessage("§cUsage: /bankheist start");
            return true;
        }

        BankHeistPlugin plugin = BankHeistPlugin.getInstance();
        HeistGame game = plugin.getHeistGame();
        if (game.isActive()) {
            leader.sendMessage("§cA heist is already active!");
            return true;
        }

        // Gather team: leader + nearby players within 10 blocks (max 4 total)
        List<Player> team = new ArrayList<>();
        team.add(leader);
        for (Player p : leader.getWorld().getPlayers()) {
            if (p != leader && p.getLocation().distance(leader.getLocation()) <= 10.0) {
                team.add(p);
                if (team.size() == 4) break;
            }
        }

        // Teleport to start, start game
        FileConfiguration config = plugin.getConfig();
        String worldName = config.getString("arena.world", "world");
        double startX = config.getDouble("arena.start.x");
        double startY = config.getDouble("arena.start.y");
        double startZ = config.getDouble("arena.start.z");

        for (Player p : team) {
            Location loc = new Location(leader.getWorld().getServer().getWorld(worldName), startX, startY, startZ);
            p.teleport(loc);
            p.sendMessage("§aBank Heist started! Team size: " + team.size());
        }

        game.startHeist(team, leader);
        return true;
    }
}

