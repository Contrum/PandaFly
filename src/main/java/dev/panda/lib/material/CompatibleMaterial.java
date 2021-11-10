package dev.panda.lib.material;

import dev.panda.lib.utilities.Server;
import org.bukkit.Material;

/**
 * Created by Risas
 * Project: PandaLib
 * Date: 08-11-2021 - 16:20
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public enum CompatibleMaterial {

    HUMAN_SKULL("SKULL_ITEM", "LEGACY_SKULL_ITEM"),
    WOOL("WOOL", "LEGACY_WOOL"),
    SNOW_BALL("SNOW_BALL", "LEGACY_SNOW_BALL"),
    CARPET("CARPET", "LEGACY_CARPET");

    private final String material8;
    private final String material13;

    CompatibleMaterial(String material8, String material13) {
        this.material8 = material8;
        this.material13 = material13;
    }

    CompatibleMaterial(String material8) {
        this(material8, null);
    }

    public Material getMaterial() {
        if (Server.SERVER_VERSION_INT <= 12) {
            return material8 == null ? Material.valueOf("SKULL_ITEM") : Material.valueOf(material8);
        }
        else {
            return material13 == null ? Material.valueOf("LEGACY_SKULL_ITEM") : Material.valueOf(material13);
        }
    }
}
