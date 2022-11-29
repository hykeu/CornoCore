package org.cornocrystal.cornocore;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.cornocrystal.cornocore.misc.Commands;
import org.cornocrystal.cornocore.patches.*;
import org.cornocrystal.cornocore.prevention.Bedrock;
import org.cornocrystal.cornocore.prevention.Redstone;

import java.util.logging.Logger;

public final class Main extends JavaPlugin implements Listener {
    public PluginManager pluginManager;

    @Override
    public void onEnable() {
        Logger log = getLogger();
        pluginManager = getServer().getPluginManager();
        log.info("Registering events");
        register(
                new Elytra(this), new Burrow(this),
                new Commands(this), new Redstone(this),
				new BowBomb(this), new Bedrock(this),
                new CrashExploits(this), new InvalidNames(this),
                new GodMode(this),
				new InstantMine(this),
                new CrystalAura(this)
        );
        ProtocolLib.protocolLibWrapper(this);
        log.info("Registering events finished");
        log.info("Registering commands");
        getCommand("aef").setExecutor(new Commands(this));
        log.info("Registering commands finished");
        saveDefaultConfig();
        log.info("[ENABLED] CornoCore - Made by moomoo, hykeu and cherosin");
    }

    private void register(Listener... list) {
        pluginManager.registerEvents(this, this);
        for (Listener listener : list) {
            pluginManager.registerEvents(listener, this);
        }
    }
}
