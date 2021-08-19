package dev.panda.combofly;

import dev.panda.combofly.commands.essentials.KBCommand;
import dev.panda.combofly.commands.PandaFlyCommand;
import dev.panda.combofly.commands.essentials.*;
import dev.panda.combofly.kit.listener.KitListener;
import dev.panda.combofly.kit.commands.KitCommand;
import dev.panda.combofly.kit.KitManager;
import dev.panda.combofly.kit.MenuKitManager;
import dev.panda.combofly.profile.commands.EnderChestCommand;
import dev.panda.combofly.profile.commands.KillStreakCommand;
import dev.panda.combofly.profile.commands.leaderboard.LeaderboardCommand;
import dev.panda.combofly.profile.commands.StatsCommand;
import dev.panda.combofly.profile.commands.balance.BalanceCommand;
import dev.panda.combofly.profile.commands.balance.PayCommand;
import dev.panda.combofly.commands.essentials.MessageCommand;
import dev.panda.combofly.commands.essentials.ReplyCommand;
import dev.panda.combofly.commands.network.*;
import dev.panda.combofly.commands.spawn.SetSpawnCommand;
import dev.panda.combofly.commands.spawn.SpawnCommand;
import dev.panda.combofly.commands.staff.*;
import dev.panda.combofly.koth.KoTH;
import dev.panda.combofly.koth.KoTHListener;
import dev.panda.combofly.koth.commands.HostCommand;
import dev.panda.combofly.koth.commands.KothCommand;
import dev.panda.combofly.listeners.*;
import dev.panda.combofly.managers.*;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.profile.ProfileListener;
import dev.panda.combofly.providers.ScoreboardProvider;
import dev.panda.combofly.providers.TablistProvider_v1_7;
import dev.panda.combofly.providers.TablistProvider_v1_8;
import dev.panda.combofly.utilities.Animation;
import dev.panda.combofly.utilities.Description;
import dev.panda.combofly.utilities.balance.Balance;
import dev.panda.combofly.utilities.balance.impl.Normal;
import dev.panda.combofly.utilities.balance.impl.Vault;
import dev.panda.combofly.utilities.placeholder.PlaceholderAPI;
import dev.panda.combofly.utilities.scoreboard.Assemble;
import dev.panda.combofly.utilities.scoreboard.AssembleStyle;
import dev.panda.combofly.utilities.tablist.v1_7.Tablist_v1_7;
import dev.panda.combofly.utilities.tablist.v1_8.Tablist_v1_8;
import dev.risas.panda.chat.CC;
import dev.risas.panda.command.CommandManager;
import dev.risas.panda.files.FileConfig;
import dev.risas.panda.license.License;
import dev.risas.panda.menu.ButtonListener;
import dev.risas.panda.rank.RankManager;
import dev.risas.panda.utils.Server;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import dev.panda.combofly.kit.commands.KitManagerCommand;
import dev.panda.combofly.commands.warp.WarpCommand;
import dev.panda.combofly.commands.warp.WarpManagerCommand;

import java.util.stream.Collectors;

@Getter @Setter
public class ComboFly extends JavaPlugin {

	private RankManager rankManager;
	private CommandManager commandManager;
	private KBManager kbManager;
	private KitManager kitManager;
	private InventoryManager inventoryManager;
	private WarpManager warpManager;
	private SpeedManager speedManager;
	private KillStreakManager killstreakManager;
	private EnderpearlManager enderpearlManager;
	private CombatManager combatManager;
	private FreezeManager freezeManager;
	private StaffManager staffManager;
	private VanishManager vanishManager;
	private ClaimManager claimManager;
	private SpawnManager spawnManager;
	private MenuKitManager menuKitManager;
	private FileConfig mainConfig, claimConfig, kitConfig, messageConfig, profileConfig, scoreboardConfig, spawnConfig,
			staffitemsConfig, tablistConfig, warpConfig, kothsConfig, arenasConfig;
	private Chat chat;
	private Economy econ;
	private Balance balanceType;
	private boolean pandaAbility = false;

	public void onEnable() {
		loadConfigs();

		License license = new License(ComboFly.get().getMainConfig().getString("LICENSE"), Server.getIP() + ":" + getServer().getPort(), this);
		license.check();

		if (license.isValid()) {
			license.correct();

			loadManagers();
			loadEvents();
			loadCommands();

			getRankManager().loadRank();

			setupScoreboard();
			setupTablist();
			setupEconomy();
			onlineDonors();

			if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				new PlaceholderAPI(this).register();
			}

			pandaAbility = getServer().getPluginManager().getPlugin("PandaAbility") != null;

			Bukkit.getConsoleSender().sendMessage(CC.translate("&aLoading plugin..."));
			Bukkit.getConsoleSender().sendMessage(CC.translate(CC.LINE));
			Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
			Bukkit.getConsoleSender().sendMessage(CC.translate("     &4\u2764 &c&l" + Description.getName() + " &4\u2764"));
			Bukkit.getConsoleSender().sendMessage(CC.translate(""));
			Bukkit.getConsoleSender().sendMessage(CC.translate(" &7\u27A5 &cAuthor&7: &f" + Description.getAuthors()).replace("[", "").replace("]", ""));
			Bukkit.getConsoleSender().sendMessage(CC.translate(" &7\u27A5 &cVersion&7: &f" + Description.getVersion()));
			Bukkit.getConsoleSender().sendMessage(CC.translate(" &7\u27A5 &cRank System&7: &f" + getRankManager().getRankSystem()));
			Bukkit.getConsoleSender().sendMessage(CC.translate(CC.LINE));
		}
		else license.wrong();
	}

	public void onDisable() {
		Profile.getProfiles().values().forEach(profile -> profile.save(null));
		KoTH.getKoths().forEach(KoTH::save);
		econ = null;
	}

	private void loadCommands() {
		new PandaFlyCommand();
		new HelpCommand();
		new KBCommand();
		new KitCommand();
		new KitManagerCommand();
		new WarpCommand();
		new WarpManagerCommand();
		new SetSpawnCommand();
		new SpawnCommand();
		new GamemodeCommand();
		new FlyCommand();
		new SpeedCommand();
		new RenameCommand();
		new PingCommand();
		new MessageCommand();
		new ReplyCommand();
		new KillStreakCommand();
		new BalanceCommand();
		new PayCommand();
		new DiscordCommand();
		new StoreCommand();
		new TeamSpeakCommand();
		new TwitterCommand();
		new WebsiteCommand();
		new MoreCommand();
		new BuildCommand();
		new EnderChestCommand();
		new SkullCommand();
		new TeleportCommand();
		new TeleportHereCommand();
		new TeleportAllCommand();
		new TeleportPositionCommand();
		new TopCommand();
		new StatsCommand();
		new EnchantCommand();
		new ClaimCommand();
		new InvseeCommand();
		new KothCommand();
		new HostCommand();
		new LeaderboardCommand();
		if (ComboFly.get().getStaffManager().isStaffEnable()) {
			new FreezeCommand();
			new StaffModeCommand();
			new VanishCommand();
			new StaffListCommand();
		}
	}

	private void loadEvents() {
		new PlayerListener();
		new EnderpearlListener();
		new KitListener();
		new ChatListener();
		new KillStreakListener();
		new KnockBackListener();
		new ShopListener();
		new CombatListener();
		new FreezeListener();
		new StaffListener();
		new ClaimListener();
		new ProfileListener();
		new KoTHListener();
		new ButtonListener(this);
	}

	private void loadManagers() {
		this.rankManager = new RankManager(this);
		this.commandManager = new CommandManager(this);
		this.kbManager = new KBManager();
		this.kitManager = new KitManager();
		this.inventoryManager = new InventoryManager();
		this.warpManager = new WarpManager();
		this.speedManager = new SpeedManager();
		this.killstreakManager = new KillStreakManager();
		this.enderpearlManager = new EnderpearlManager();
		this.combatManager = new CombatManager();
		this.freezeManager = new FreezeManager();
		this.staffManager = new StaffManager();
		this.vanishManager = new VanishManager();
		this.claimManager = new ClaimManager();
		this.spawnManager = new SpawnManager();
		this.menuKitManager = new MenuKitManager();
		Animation.init();
		Profile.init();
		KoTH.init();
	}

	private void loadConfigs() {
		this.mainConfig = new FileConfig(this, "config.yml");
		this.claimConfig = new FileConfig(this, "claims.yml");
		this.kitConfig = new FileConfig(this, "kit.yml");
		this.messageConfig = new FileConfig(this, "messages.yml");
		this.profileConfig = new FileConfig(this, "profile.yml");
		this.scoreboardConfig = new FileConfig(this, "scoreboard.yml");
		this.spawnConfig = new FileConfig(this, "spawn.yml");
		this.staffitemsConfig = new FileConfig(this, "staff-items.yml");
		this.tablistConfig = new FileConfig(this, "tablist.yml");
		this.warpConfig = new FileConfig(this, "warp.yml");
		this.kothsConfig = new FileConfig(this, "koths.yml");
	}

	private void setupScoreboard() {
		Assemble assemble = new Assemble(this, new ScoreboardProvider());
		assemble.setTicks(1);
		assemble.setAssembleStyle(AssembleStyle.KOHI);
	}

	private void setupTablist() {
		if (getTablistConfig().getBoolean("ENABLE")) {
			if (Bukkit.getVersion().contains("1.7")) new Tablist_v1_7(new TablistProvider_v1_7(), this);
			else if (Bukkit.getVersion().contains("1.8")) new Tablist_v1_8(new TablistProvider_v1_8(), this);
			else Bukkit.getConsoleSender().sendMessage(CC.translate("&cNo tablist version found."));
		}
	}

	private void setupEconomy() {
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp != null) econ = rsp.getProvider();
		if (ComboFly.get().getMainConfig().getString("BALANCE-TYPE").equalsIgnoreCase("normal")) {
			balanceType = new Normal();
		}
		else if (ComboFly.get().getMainConfig().getString("BALANCE-TYPE").equalsIgnoreCase("vault")) {
			balanceType = new Vault();
		}
		else {
			Bukkit.getConsoleSender().sendMessage(CC.translate("&cBalance type not found."));
			Bukkit.getConsoleSender().sendMessage(CC.translate("&cCheck in config.yml!"));
		}
	}

	private void onlineDonors() {
		if (ComboFly.get().getMainConfig().getBoolean("ONLINE-DONATORS-ANNOUNCE.TOGGLE")) {
			ComboFly.get().getServer().getScheduler().runTaskTimerAsynchronously(ComboFly.get(), () -> {
				String players = Bukkit.getOnlinePlayers().stream().filter(online -> online.hasPermission("pandafly.donator") && !online.isOp() && !online.hasPermission("*")).map(HumanEntity::getName).collect(Collectors.joining(", "));
				if (players.isEmpty() && mainConfig.getBoolean("ONLINE-DONATORS-ANNOUNCE.NO-PLAYERS")) return;
				if (players.isEmpty()) {
					players = CC.translate(ComboFly.get().getMainConfig().getString("ONLINE-DONATORS-ANNOUNCE.MESSAGE-NO-DONATORS"));
				}
				for (String lines : ComboFly.get().getMainConfig().getStringList("ONLINE-DONATORS-ANNOUNCE.MESSAGE")) {
					Bukkit.broadcastMessage(CC.translate(lines
							.replace("{donators}", players)
							.replace("{store}", ComboFly.get().getMainConfig().getString("STORE"))));
				}
			}, 2000L, 2000L);
		}
	}

	public static ComboFly get() {
		return getPlugin(ComboFly.class);
	}
}