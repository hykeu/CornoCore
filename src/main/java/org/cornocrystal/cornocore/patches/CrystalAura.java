package org.cornocrystal.cornocore.patches;

import org.cornocrystal.cornocore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class CrystalAura implements Listener {
    private final Main plugin;
    private final HashSet<Player> players = new HashSet<>();

    public CrystalAura(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent evt) {
        if (evt.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)) {
            if (evt.getDamager() instanceof Player) {
                Player p = (Player) evt.getDamager();

                if (players.contains(p)) {
                    evt.setCancelled(true);
                } else {
                    players.add(p);
                    Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> players.remove(p), (long) plugin.getConfig().getInt("CrystalDelay"));

                }
            }
        }
    }
}