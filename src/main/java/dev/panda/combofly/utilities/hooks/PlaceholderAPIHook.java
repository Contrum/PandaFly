package dev.panda.combofly.utilities.hooks;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: PandaFly
 * Date: 08-11-2021 - 13:36
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final ComboFly plugin;

    public PlaceholderAPIHook(ComboFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return "Aleesk, TulioTriste, Risas";
    }

    @Override
    public String getIdentifier(){
        return "PandaFly";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

        switch (identifier) {
            case "kills": return String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getKills());
            case "deaths": return String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getDeaths());
            case "balance": return String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getBalance());
            default: return null;
        }
    }
}
