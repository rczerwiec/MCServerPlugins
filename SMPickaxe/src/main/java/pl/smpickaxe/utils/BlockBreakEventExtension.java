package pl.smpickaxe.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakEventExtension extends BlockBreakEvent {
    public BlockBreakEventExtension(Block block, Player p) {
        super(block, p);
    }
}
