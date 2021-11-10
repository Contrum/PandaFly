package dev.panda.lib.utilities;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

@UtilityClass
public class LocationUtil {

    public Location getHighestLocation(Location origin) {
        return getHighestLocation(origin, null);
    }

    public Location getHighestLocation(Location origin, Location def) {
        Preconditions.checkNotNull(origin, "The location cannot be null");
        Location cloned = origin.clone();
        World world = cloned.getWorld();

        int x = cloned.getBlockX();
        int y = world.getMaxHeight();
        int z = cloned.getBlockZ();

        while (y > origin.getBlockY()) {
            Block block = world.getBlockAt(x, --y, z);
            if (!block.isEmpty()) {
                Location next = block.getLocation();
                next.setPitch(origin.getPitch());
                next.setYaw(origin.getYaw());
                return next;
            }
        }
        return def;
    }

    public String serialize(Location location) {
        if (location == null) return "null";

        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() +
                ":" + location.getYaw() + ":" + location.getPitch();
    }

    public Location deserialize(String source) {
        if (source == null) return null;

        String[] split = source.split(":");
        World world = Bukkit.getServer().getWorld(split[0]);

        if (world == null) return null;

        return new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }
}
