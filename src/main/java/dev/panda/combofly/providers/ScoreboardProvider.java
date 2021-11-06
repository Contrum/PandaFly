package dev.panda.combofly.providers;

import dev.panda.ability.AbilityAPI;
import dev.panda.combofly.ComboFly;
import dev.panda.combofly.koth.KoTH;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.utilities.Animation;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.Utils;
import dev.panda.combofly.utilities.scoreboard.AssembleAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProvider implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate(Animation.getScoreboardTitle());
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        String kills = String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getKills());
        String deaths = String.valueOf(Profile.getProfiles().get(player.getUniqueId()).getKd().getDeaths());
        String balance = String.valueOf(ComboFly.get().getBalanceType().getBalance(player));

        for (String key : ComboFly.get().getScoreboardConfig().getStringList("LINES")) {
            if (key.contains("{staffmode}")) {
                if (ComboFly.get().getStaffManager().isStaffMode(player)) {
                    ComboFly.get().getScoreboardConfig().getStringList("VARIABLES.STAFFMODE").forEach(staffLines ->
                            lines.add(staffLines
                                .replace("{vanish}", ComboFly.get().getVanishManager().isVanish(player) ? "&a\u2714" : "&c\u2716")
                                .replace("{online}", String.valueOf(Utils.getPlayersAmount()))
                                .replace("{tps}", String.valueOf(Math.round(Bukkit.getServer().spigot().getTPS()[0] * 100.0) / 100.0))));
                }
                continue;
            }

            if (key.contains("{pandaAbility}")) {
                if (ComboFly.get().isPandaAbility()) {
                    AbilityAPI abilityAPI = new AbilityAPI();
                    if (abilityAPI.getGlobalCooldown().hasGlobalCooldown(player)) {
                        lines.add(ComboFly.get().getScoreboardConfig().getString("PANDAABILITY.GLOBAL-COOLDOWN")
                                .replace("%name%", abilityAPI.getGlobalCooldown().getGlobalCooldownName())
                                .replace("%cooldown%", abilityAPI.getGlobalCooldown().getGlobalCooldown(player)));
                    }

                    abilityAPI.getActiveAbility(player).forEach(abilityHandler -> {
                        String ability = abilityHandler.getName();
                        String cooldown = abilityHandler.getCooldown(player);

                        lines.add(ComboFly.get().getScoreboardConfig().getString("PANDAABILITY.COOLDOWNS")
                                .replace("%name%", ability).replace("%cooldown%", cooldown));
                    });
                }
                continue;
            }

            if (key.contains("{enderpearl}")) {
                if (ComboFly.get().getEnderpearlManager().hasCooldown(player)) {
                    lines.add(key
                        .replace("{enderpearl}", ComboFly.get().getEnderpearlManager().getCooldown(player)));
                }
                continue;
            }
            if (key.contains("{combat}")) {
                if (ComboFly.get().getCombatManager().hasCooldown(player)) {
                    lines.add(key
                            .replace("{combat}", ComboFly.get().getCombatManager().getCooldown(player)));
                }
                continue;
            }
            if (key.contains("{spawn}")) {
                if (ComboFly.get().getSpawnManager().hasCooldown(player)) {
                    lines.add(key
                            .replace("{spawn}", String.valueOf(ComboFly.get().getSpawnManager().getCooldown(player))));
                }
                continue;
            }
            if (key.contains("{koth}")) {
                if (KoTH.getActiveKoth() != null) {
                    KoTH koth = KoTH.getActiveKoth();
                    ComboFly.get().getScoreboardConfig().getStringList("KOTH").forEach(s ->
                            lines.add(s
                                    .replace("{koth}", koth.getName())
                                    .replace("{time}", koth.getTimeLeft())
                                    .replace("{capper}", koth.getCapper())));
                }
                continue;
            }
            lines.add(key
                    .replace("{kills}", kills)
                    .replace("{deaths}", deaths)
                    .replace("{balance}", balance));
        }
        if (ComboFly.get().getScoreboardConfig().getBoolean("FOOTER-ENABLE")) {
            lines.add(lines.size() - 1, "");
            lines.add(lines.size() - 1, Animation.getScoreboardFooter());
        }
        return ChatUtil.translate(lines);
    }
}
