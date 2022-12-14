package org.cornocrystal.cornocore.patches;

import lombok.RequiredArgsConstructor;
import org.cornocrystal.cornocore.Main;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

@RequiredArgsConstructor
public class BowBomb implements Listener {
    private final Main plugin;

    @EventHandler
    public void onArrow(ProjectileLaunchEvent evt) {
        if (plugin.getConfig().getBoolean("PreventBowBombExploit")) {
            if (evt.getEntity() instanceof Arrow || evt.getEntity() instanceof SpectralArrow) {
                if (evt.getEntity().getVelocity().lengthSquared() > plugin.getConfig().getInt("MaxBowSquaredVelocity")) {
                    evt.setCancelled(true);
                }
            }
        }
    }
}