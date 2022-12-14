package org.cornocrystal.cornocore.patches;

import lombok.RequiredArgsConstructor;
import org.cornocrystal.cornocore.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class GodMode implements Listener {
    private final Main plugin;

    @EventHandler
    public void onMove(PlayerMoveEvent evt) {
        if (plugin.getConfig().getBoolean("ExperimentalGodModePatch")) {
            if (evt.getPlayer().isInsideVehicle()) {
                if (!evt.getPlayer().getVehicle().isValid()) {
                    evt.getPlayer().getVehicle().eject();
                    plugin.getLogger().warning("Attempted to prevent godmode from " + evt.getPlayer().getName() + ": dismounted from entity.");
                }
            }

            if (!evt.getPlayer().isValid() && !evt.getPlayer().isDead()) {
                evt.getPlayer().kickPlayer(ChatColor.GOLD + "You have lost connection to the server");
                plugin.getLogger().warning("Attempted to prevent godmode from " + evt.getPlayer().getName() + ": kicked player");
            }
        }
    }
}
