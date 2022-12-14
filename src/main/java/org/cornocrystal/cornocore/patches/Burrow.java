package org.cornocrystal.cornocore.patches;

import org.cornocrystal.cornocore.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Burrow implements Listener {
    private final Main plugin;

    public Burrow(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onMove(PlayerMoveEvent evt) {
        if (plugin.getConfig().getBoolean("PreventBurrow") && evt.getPlayer().getGameMode() != GameMode.SPECTATOR) {
            Location l = evt.getPlayer().getLocation();
            int x = l.getBlockX();
            int y = l.getBlockY();
            double yy = l.getY();
            int z = l.getBlockZ();
            Material b = evt.getPlayer().getLocation().getWorld().getBlockAt(x, y, z).getType();
            if (!b.equals(Material.AIR) && (b.isOccluding() || b.equals(Material.ANVIL)) && !b.equals(Material.SOUL_SAND) && !isGravityBlock(b)) {
                evt.getPlayer().damage(plugin.getConfig().getInt("BurrowDamageWhenMoving"));
                if (plugin.getConfig().getBoolean("TeleportBurrow")) {
                    evt.getPlayer().teleport(new Location(l.getWorld(), x, y + 1, z));
                }
            }
            switch (b) {
                case ENDER_CHEST:
                case SOUL_SAND: {
                    if (yy - y < 0.875) {
                        evt.getPlayer().damage(plugin.getConfig().getInt("BurrowDamageWhenMoving"));
                        if (plugin.getConfig().getBoolean("TeleportBurrow")) {
                            evt.getPlayer().teleport(new Location(l.getWorld(), x, y + 1, z));
                        }
                    }
                }
                case ENCHANTMENT_TABLE: {
                    if (yy - y < 0.75) {
                        evt.getPlayer().damage(plugin.getConfig().getInt("BurrowDamageWhenMoving"));
                        if (plugin.getConfig().getBoolean("TeleportBurrow")) {
                            evt.getPlayer().teleport(new Location(l.getWorld(), x, y + 1, z));
                        }
                    }
                }
            }
        }
    }

    private Boolean isGravityBlock(Material b) {
        switch (b) {
            case SAND:
            case GRAVEL:
                return true;
            default:
                return false;
        }
    }
}
