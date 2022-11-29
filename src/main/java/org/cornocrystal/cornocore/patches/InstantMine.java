package org.cornocrystal.cornocore.patches;

import org.cornocrystal.cornocore.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class InstantMine implements Listener {
	private final Main plugin;
    private HashMap<Player, Long> map = new HashMap<>();
    private HashMap<Player, Block> map2 = new HashMap<>();

	public InstantMine(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
		if (plugin.getConfig().getBoolean("InstantMine")) {
			if(e.getBlock().getType() == Material.OBSIDIAN){
				if(map2.containsKey(e.getPlayer())){
					if(map2.get(e.getPlayer()).getX() == e.getBlock().getX() &&
							map2.get(e.getPlayer()).getY() == e.getBlock().getY() &&
							map2.get(e.getPlayer()).getZ() == e.getBlock().getZ())
					{
						if(map.containsKey(e.getPlayer())){
							if(System.currentTimeMillis()-map.get(e.getPlayer())<5000){
								e.setCancelled(true);
							}else{
								map.remove(e.getPlayer());
								map.put(e.getPlayer(), System.currentTimeMillis());
							}
						}else{
							map.put(e.getPlayer(), System.currentTimeMillis());
						}
					}else{
						map2.remove(e.getPlayer());
						map2.put(e.getPlayer(), e.getBlock());
						map.remove(e.getPlayer());
						map.put(e.getPlayer(), System.currentTimeMillis());
					}
				}else{
					map2.put(e.getPlayer(), e.getBlock());
				}
			}
		}
	}
}