package com.yankton.bankheist;

import com.yankton.bankheist.commands.BankHeistCommand;
import com.yankton.bankheist.game.HeistGame;
import com.yankton.bankheist.listeners.HeistListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BankHeistPlugin extends JavaPlugin {

    private static BankHeistPlugin instance;
    private HeistGame heistGame;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // Register command
        getCommand("bankheist").setExecutor(new BankHeistCommand());

        // Register listener
        getServer().getPluginManager().registerEvents(new HeistListener(this), this);

        // Init game manager
        heistGame = new HeistGame(this);

        getLogger().info("BankHeist enabled!");
    }

    @Override
    public void onDisable() {
        if (heistGame != null) {
            heistGame.stopGame();
        }
        getLogger().info("BankHeist disabled!");
    }

    public static BankHeistPlugin getInstance() {
        return instance;
    }

    public HeistGame getHeistGame() {
        return heistGame;
    }
}

