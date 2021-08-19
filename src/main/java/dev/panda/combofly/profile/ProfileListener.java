package dev.panda.combofly.profile;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {

    public ProfileListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ComboFly.get());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event) {
        if (Profile.get(event.getUniqueId()) == null) {
            Profile profile = new Profile(event.getUniqueId(), event.getName());
            profile.load();
            Profile.getProfiles().put(event.getUniqueId(), profile);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onProfileQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ComboFly.get().getServer().getScheduler().runTaskAsynchronously(ComboFly.get(), () -> Profile.get(player.getUniqueId()).save(player));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();

        event.getDrops().clear();

        if (player != null) {
            int playerKills = Profile.getProfiles().get(player.getUniqueId()).getKd().getKills();

            Profile.getProfiles().get(player.getUniqueId()).getKd().incrementDeaths();

            if (killer == null) {
                event.setDeathMessage(CC.translate(ComboFly.get().getMessageConfig().getString("KILL-MESSAGE.NO-KILLER")
                        .replace("{player}", player.getName())
                        .replace("{kills}", String.valueOf(playerKills))));
                return;
            }

            int killerBalance = (int) ComboFly.get().getBalanceType().getBalance(killer);

            Profile.getProfiles().get(killer.getUniqueId()).getKd().incrementKills();

            if (ComboFly.get().getMainConfig().getBoolean("KILL-MONEY.ENABLE")) {
                ComboFly.get().getBalanceType().giveBalance(killer, killerBalance + ComboFly.get().getMainConfig().getInt("KILL-MONEY.MONEY"));
                killer.sendMessage(CC.translate(ComboFly.get().getMainConfig().getString("KILL-MONEY.KILL-MESSAGE")
                        .replace("{money}", String.valueOf(ComboFly.get().getMainConfig().getInt("KILL-MONEY.MONEY")))
                        .replace("{target}", player.getName())));
            }

            if (ComboFly.get().getMainConfig().getBoolean("KILL-REWARD.ENABLE")) {
                for (String cmd : ComboFly.get().getMainConfig().getStringList("KILL-REWARD.REWARD")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", killer.getName()));
                }
            }

            player.getWorld().strikeLightningEffect(player.getLocation());
            if (killer.getItemInHand().getType().equals(Material.AIR) || killer.getItemInHand() == null) {
                event.setDeathMessage(CC.translate(ComboFly.get().getMessageConfig().getString("KILL-MESSAGE.KILLER-NO-ITEM")
                        .replace("{player}", player.getName())
                        .replace("{kills}", String.valueOf(playerKills))
                        .replace("{killer}", killer.getName())
                        .replace("{killerKills}", String.valueOf( Profile.getProfiles().get(killer.getUniqueId()).getKd().getKills()))));
            }
            else {
                Material itemType = killer.getItemInHand().getType();
                String itemDisplayname = killer.getItemInHand().getItemMeta().getDisplayName();
                event.setDeathMessage(CC.translate(ComboFly.get().getMessageConfig().getString("KILL-MESSAGE.KILLER-WITH-ITEM")
                        .replace("{player}", player.getName())
                        .replace("{kills}", String.valueOf(playerKills))
                        .replace("{killer}", killer.getName())
                        .replace("{killerKills}", String.valueOf( Profile.getProfiles().get(killer.getUniqueId()).getKd().getKills()))
                        .replace("{item}", (killer.getItemInHand().getItemMeta().getDisplayName() != null ? itemDisplayname : this.toReadable(itemType)))));
            }
        }
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        if (!ComboFly.get().getStaffManager().isStaffMode(player) && ComboFly.get().getMainConfig().getBoolean("DEFAULT-KIT-ON-JOIN")) {
            for (String kit : ComboFly.get().getKitConfig().getConfiguration().getConfigurationSection("KITS").getKeys(false)) {
                if (!ComboFly.get().getKitManager().getDefaultKit().equals(kit)) return;
                ComboFly.get().getKitManager().equipKit(ComboFly.get().getKitManager().getDefaultKit(), player);
            }
        }
        else if (ComboFly.get().getMainConfig().getBoolean("JOIN-MESSAGE.STATUS")) {
            ComboFly.get().getMainConfig().getStringList("JOIN-MESSAGE.MESSAGE").forEach(key -> player.sendMessage(CC.translate(key
                    .replace("{player}", player.getName())
                    .replace("{discord}", ComboFly.get().getMainConfig().getString("DISCORD"))
                    .replace("{store}", ComboFly.get().getMainConfig().getString("STORE"))
                    .replace("{teamspeak}", ComboFly.get().getMainConfig().getString("TEAMSPEAK"))
                    .replace("{twitter}", ComboFly.get().getMainConfig().getString("TWITTER"))
                    .replace("{website}", ComboFly.get().getMainConfig().getString("WEBSITE")))));
        }
        else if (!(ComboFly.get().getSpawnManager().getSpawnLocation() == null)) {
            player.teleport(ComboFly.get().getSpawnManager().getSpawnLocation());
        }
    }

    public String toReadable(Enum<?> enu) {
        return WordUtils.capitalize(enu.name().replace("_", " ").toLowerCase());
    }
}
