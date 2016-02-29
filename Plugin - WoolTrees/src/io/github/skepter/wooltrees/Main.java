/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.skepter.wooltrees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	// Minimum amount of dyes required to make a tree
	private int MIN_DYES = 2;

	// Maximum amount of dyes to make a tree (-1 due to how the code works. In
	// this case, it's 5)
	private int MAX_DYES = 5 - 1;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onBonemeal(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getPlayer().getItemInHand().getType().equals(Material.INK_SACK)) {
				if (event.getClickedBlock().getType().equals(Material.SAPLING)) {

					// Get the color the player has
					MaterialData data = event.getPlayer().getItemInHand().getData();
					Dye dye = (Dye) data;
					if (!dye.getColor().equals(DyeColor.WHITE)) {
						event.setCancelled(true);

						// Remove an item
						if (event.getPlayer().getGameMode() == GameMode.SURVIVAL)
							if (event.getPlayer().getItemInHand().getAmount() == 1)
								event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
							else
								event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);

						// Create the tree instantly
						Random random = new Random();
						int r = random.nextInt(100);
						if (r < 8) {
							if (!event.getClickedBlock().hasMetadata("wooltreedye")) {
								spawnTree(event.getClickedBlock(), true, dye.getColor());
								return;
							}
						} else {
							// Put the dye color into the metadata and play the
							// particle effect.
							// If there is already a dye color, spawn in the
							// custom
							// tree
							if (event.getClickedBlock().hasMetadata("wooltreedye")) {
								@SuppressWarnings("unchecked")
								List<DyeColor> colorFromMetadata = (List<DyeColor>) event.getClickedBlock().getMetadata("wooltreedye").get(0).value();
								if (colorFromMetadata.size() == MAX_DYES) {
									// force spawn
									colorFromMetadata.add(dye.getColor());
									DyeColor[] array = colorFromMetadata.toArray(new DyeColor[colorFromMetadata.size()]);

									spawnTree(event.getClickedBlock(), false, array);
									return;
								} else if (colorFromMetadata.size() >= MIN_DYES) {
									// 40% chance to spawn straight away
									int r1 = random.nextInt(100);
									if (r1 < 40) {
										colorFromMetadata.add(dye.getColor());
										DyeColor[] array = colorFromMetadata.toArray(new DyeColor[colorFromMetadata.size()]);
										spawnTree(event.getClickedBlock(), false, array);
									} else {
										bonemealFail(event.getClickedBlock(), false, dye.getColor());
									}
									return;
								} else {
									bonemealFail(event.getClickedBlock(), false, dye.getColor());
								}
							} else {
								bonemealFail(event.getClickedBlock(), true, dye.getColor());
								return;
							}
						}
					}
				}
			}
		}
	}

	private Location getCenter(Location location) {
		return new Location(location.getWorld(), getCenter(location.getBlockX()), getCenter(location.getBlockY()), getCenter(location.getBlockZ()));
	}

	private double getCenter(final int location) {
		final double newLocation = location;
		return newLocation + 0.5D;
	}

	/** Adds the color to the list of colors if it isn't the firs time */
	private void bonemealFail(Block block, boolean firstTime, DyeColor color) {
		if (firstTime) {
			List<DyeColor> dyeColorList = new ArrayList<DyeColor>();
			dyeColorList.add(color);
			block.setMetadata("wooltreedye", new FixedMetadataValue(this, dyeColorList));
		} else {
			@SuppressWarnings("unchecked")
			List<DyeColor> colorFromMetadata = (List<DyeColor>) block.getMetadata("wooltreedye").get(0).value();
			colorFromMetadata.add(color);
			block.setMetadata("wooltreedye", new FixedMetadataValue(this, colorFromMetadata));
		}

		try {
			ParticleEffect.VILLAGER_HAPPY.display(0.5F, 0, 0.5F, 0, 20, getCenter(block.getLocation()), 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void spawnTree(Block initBlock, boolean singleColor, DyeColor... dyes) {
		for (Block b : Utils.getTreeTrunk(initBlock.getLocation())) {
			b.setType(Material.WOOL);
			Wool wool = new Wool();
			wool.setColor(DyeColor.BROWN);
			b.setData(wool.getData());
		}
		if (singleColor) {
			for (Block b : Utils.getTreeLeaves(initBlock.getLocation())) {
				b.setType(Material.WOOL);
				Wool wool = new Wool();
				wool.setColor(dyes[0]);
				b.setData(wool.getData());
			}
		} else {
			Random random = new Random();
			for (Block b : Utils.getTreeLeaves(initBlock.getLocation())) {
				b.setType(Material.WOOL);
				Wool wool = new Wool();
				wool.setColor(dyes[random.nextInt(dyes.length)]);
				b.setData(wool.getData());
			}
		}
	}

}
