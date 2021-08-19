package dev.panda.combofly.utilities.placeholder;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.Player;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Optional;
import java.util.UUID;

public class PlaceholderAPI extends PlaceholderExpansion {

    private final ComboFly plugin;

    public PlaceholderAPI(ComboFly plugin) {
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
    public String onPlaceholderRequest(Player player, String identifier){
        if (player == null) return "";

        switch (identifier) {
            case "kills": return String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getKills());
            case "deaths": return String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getDeaths());
            case "balance": return String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getBalance());
            default: return null;
        }
    }
}
