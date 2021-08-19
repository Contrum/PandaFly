package dev.panda.combofly.managers;

import com.sk89q.worldedit.bukkit.selections.Selection;
import dev.panda.combofly.ComboFly;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Set;

public class ClaimManager {

    public void createClaimPvP(String claim, Selection sel) {
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".WORLD", sel.getMaximumPoint().getWorld().getName());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".PVP", true);
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerA.x", sel.getMaximumPoint().getX());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerA.y", sel.getMaximumPoint().getY());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerA.z", sel.getMaximumPoint().getZ());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerB.x", sel.getMinimumPoint().getX());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerB.y", sel.getMinimumPoint().getY());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerB.z", sel.getMinimumPoint().getZ());
        ComboFly.get().getClaimConfig().save();
        ComboFly.get().getClaimConfig().reload();
    }

    public void createClaimNoPvP(String claim, Selection sel) {
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".WORLD", sel.getMaximumPoint().getWorld().getName());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".PVP", false);
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerA.x", sel.getMaximumPoint().getX());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerA.y", sel.getMaximumPoint().getY());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerA.z", sel.getMaximumPoint().getZ());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerB.x", sel.getMinimumPoint().getX());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerB.y", sel.getMinimumPoint().getY());
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim + ".cornerB.z", sel.getMinimumPoint().getZ());
        ComboFly.get().getClaimConfig().save();
        ComboFly.get().getClaimConfig().reload();
    }

    public void deleteClaim(String claim) {
        ComboFly.get().getClaimConfig().getConfiguration().set("CLAIMS." + claim, null);
        ComboFly.get().getClaimConfig().save();
        ComboFly.get().getClaimConfig().reload();
    }

    public Location getLocation(String claim, String corner) {
        World world = Bukkit.getWorld(ComboFly.get().getClaimConfig().getString("CLAIMS." + claim + ".WORLD"));
        double x = ComboFly.get().getClaimConfig().getDouble("CLAIMS." + claim + "." + corner + ".x");
        double y = ComboFly.get().getClaimConfig().getDouble("CLAIMS." + claim + "." + corner + ".y");
        double z = ComboFly.get().getClaimConfig().getDouble("CLAIMS." + claim + "." + corner + ".z");
        return new Location(world, x, y, z);
    }

    public boolean isPvPClaim(String claim) {
        return ComboFly.get().getClaimConfig().getBoolean("CLAIMS." + claim + ".PVP");
    }

    public World getClaimWorld(String claim) {
        return Bukkit.getWorld(ComboFly.get().getClaimConfig().getString("CLAIMS." + claim + ".WORLD"));
    }

    public Set<String> getClaimList() {
        return ComboFly.get().getClaimConfig().getConfiguration().getConfigurationSection("CLAIMS").getKeys(false);
    }
}
