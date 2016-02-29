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

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Utils {

	private static Block getBlock(Location initLocation, int x, int y, int z) {
		return initLocation.clone().add(new Location(initLocation.getWorld(), x, y, z)).getBlock();
	}
	
	public static Block[] getTreeTrunk(Location initLocation) {
		Block[] arr = new Block[5];
		for (int i = 0; i < 5; i++) {
			arr[i] = getBlock(initLocation, 0, i, 0);
		}
		return arr;
	}
	
	public static List<Block> getTreeLeaves(Location loc) {
		List<Block> blocks = getTreeLeavesRow1(loc);
		blocks.addAll(getTreeLeavesRow2(loc));
		blocks.addAll(getTreeLeavesRow3(loc));
		blocks.addAll(getTreeLeavesRow4(loc));
		return blocks;
	}

	public static List<Block> getTreeLeavesRow1(Location loc) {
		List<Block> blocks = new ArrayList<Block>();
		blocks.add(getBlock(loc, 0, 2, 1));
		blocks.add(getBlock(loc, 0, 2, 2));
		blocks.add(getBlock(loc, 0, 2, -2));
		blocks.add(getBlock(loc, 0, 2, -1));
		
		blocks.add(getBlock(loc, 1, 2, 0));
		blocks.add(getBlock(loc, 2, 2, 0));
		blocks.add(getBlock(loc, -1, 2, 0));
		blocks.add(getBlock(loc, -2, 2, 0));
		
		blocks.add(getBlock(loc, 1, 2, 1));
		blocks.add(getBlock(loc, 2, 2, 1));
		blocks.add(getBlock(loc, 1, 2, 2));
		
		blocks.add(getBlock(loc, 1, 2, -1));
		blocks.add(getBlock(loc, 2, 2, -1));
		blocks.add(getBlock(loc, 1, 2, -2));
		
		blocks.add(getBlock(loc, -1, 2, 1));
		blocks.add(getBlock(loc, -2, 2, 1));
		blocks.add(getBlock(loc, -1, 2, 2));
		
		blocks.add(getBlock(loc, -1, 2, -1));
		blocks.add(getBlock(loc, -2, 2, -1));
		blocks.add(getBlock(loc, -1, 2, -2));
		return blocks;
	}
	
	public static List<Block> getTreeLeavesRow2(Location loc) {
		List<Block> blocks = new ArrayList<Block>();
		blocks.add(getBlock(loc, 0, 3, 1));
		blocks.add(getBlock(loc, 0, 3, 2));
		blocks.add(getBlock(loc, 0, 3, -2));
		blocks.add(getBlock(loc, 0, 3, -1));
		
		blocks.add(getBlock(loc, 1, 3, 0));
		blocks.add(getBlock(loc, 2, 3, 0));
		blocks.add(getBlock(loc, -1, 3, 0));
		blocks.add(getBlock(loc, -2, 3, 0));
		
		blocks.add(getBlock(loc, 1, 3, 1));
		blocks.add(getBlock(loc, 2, 3, 1));
		blocks.add(getBlock(loc, 1, 3, 2));
		
		blocks.add(getBlock(loc, 1, 3, -1));
		blocks.add(getBlock(loc, 2, 3, -1));
		blocks.add(getBlock(loc, 1, 3, -2));
		
		blocks.add(getBlock(loc, -1, 3, 1));
		blocks.add(getBlock(loc, -2, 3, 1));
		blocks.add(getBlock(loc, -1, 3, 2));
		
		blocks.add(getBlock(loc, -1, 3, -1));
		blocks.add(getBlock(loc, -2, 3, -1));
		blocks.add(getBlock(loc, -1, 3, -2));
		
		blocks.add(getBlock(loc, 2, 3, 2));
		blocks.add(getBlock(loc, 2, 3, -2));
		blocks.add(getBlock(loc, -2, 3, 2));
		blocks.add(getBlock(loc, -2, 3, -2));
		
		return blocks;
	}
	
	public static List<Block> getTreeLeavesRow3(Location loc) {
		List<Block> blocks = new ArrayList<Block>();
		blocks.add(getBlock(loc, 0, 4, 1));
		blocks.add(getBlock(loc, 0, 4, -1));

		blocks.add(getBlock(loc, 1, 4, 0));
		blocks.add(getBlock(loc, -1, 4, 0));
		
		blocks.add(getBlock(loc, 1, 4, 1));
		blocks.add(getBlock(loc, -1, 4, -1));
		
		blocks.add(getBlock(loc, -1, 4, 1));
		blocks.add(getBlock(loc, 1, 4, -1));
		
		return blocks;
	}
	
	private static List<Block> getTreeLeavesRow4(Location loc) {
		List<Block> blocks = new ArrayList<Block>();
		blocks.add(getBlock(loc, 0, 5, 1));
		blocks.add(getBlock(loc, 0, 5, -1));

		blocks.add(getBlock(loc, 1, 5, 0));
		blocks.add(getBlock(loc, -1, 5, 0));
		
		blocks.add(getBlock(loc, 0, 5, 0));
		return blocks;
	}


}
