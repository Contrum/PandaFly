package dev.panda.combofly;

import dev.panda.chat.ChatUtil;
import dev.panda.combofly.balance.Balance;
import dev.panda.combofly.commands.PandaFlyCommand;
import dev.panda.combofly.commands.essentials.*;
import dev.panda.combofly.commands.network.*;
import dev.panda.combofly.commands.spawn.SetSpawnCommand;
import dev.panda.combofly.commands.spawn.SpawnCommand;
import dev.panda.combofly.commands.staff.*;
import dev.panda.combofly.commands.warp.WarpCommand;
import dev.panda.combofly.commands.warp.WarpManagerCommand;
import dev.panda.combofly.kit.KitManager;
import dev.panda.combofly.kit.MenuKitManager;
import dev.panda.combofly.kit.commands.KitCommand;
import dev.panda.combofly.kit.commands.KitManagerCommand;
import dev.panda.combofly.kit.listener.KitListener;
import dev.panda.combofly.koth.KoTH;
import dev.panda.combofly.koth.KoTHListener;
import dev.panda.combofly.koth.commands.HostCommand;
import dev.panda.combofly.koth.commands.KothCommand;
import dev.panda.combofly.listeners.*;
import dev.panda.combofly.managers.*;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.profile.ProfileListener;
import dev.panda.combofly.profile.commands.EnderChestCommand;
import dev.panda.combofly.profile.commands.KillStreakCommand;
import dev.panda.combofly.profile.commands.StatsCommand;
import dev.panda.combofly.profile.commands.balance.BalanceCommand;
import dev.panda.combofly.profile.commands.balance.PayCommand;
import dev.panda.combofly.profile.commands.leaderboard.LeaderboardCommand;
import dev.panda.combofly.providers.ScoreboardProvider;
import dev.panda.combofly.utilities.Animation;
import dev.panda.combofly.utilities.Description;
import dev.panda.combofly.balance.impl.Normal;
import dev.panda.combofly.balance.impl.Vault;
import dev.panda.combofly.utilities.hooks.PandaAbilityHook;
import dev.panda.combofly.utilities.hooks.PlaceholderAPIHook;
import dev.panda.combofly.utilities.menu.ButtonListener;
import dev.panda.combofly.utilities.scoreboard.Assemble;
import dev.panda.combofly.utilities.scoreboard.AssembleStyle;
import dev.panda.command.CommandManager;
import dev.panda.file.FileConfig;
import dev.panda.rank.RankManager;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
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

	public void onEnable() {
		loadConfigs();
		loadManagers();
		loadEvents();
		loadCommands();

		getRankManager().loadRank();

		setupScoreboard();
		setupEconomy();
		onlineDonors();

		if (Bukkit.getPluginManager().getPlugin("PandaAbility") != null) {
			PandaAbilityHook.init();
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PlaceholderAPIHook(this).register();
		}

		ChatUtil.log("&aLoading plugin...");
		ChatUtil.log(ChatUtil.NORMAL_LINE);
		ChatUtil.log(" ");
		ChatUtil.log("     &4\u2764 &c&l" + Description.getName() + " &4\u2764");
		ChatUtil.log("");
		ChatUtil.log(" &7\u27A5 &cAuthor&7: &f" + Description.getAuthors());
		ChatUtil.log(" &7\u27A5 &cVersion&7: &f" + Description.getVersion());
		ChatUtil.log(" &7\u27A5 &cRank System&7: &f" + this.getRankManager().getRankSystem());
		ChatUtil.log(ChatUtil.NORMAL_LINE);
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
		new ButtonListener();
	}

	private void loadManagers() {
		this.rankManager = new RankManager(this);
		this.commandManager = new CommandManager(this, new ArrayList<>());
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
			Bukkit.getConsoleSender().sendMessage(ChatUtil.translate("&cBalance type not found."));
			Bukkit.getConsoleSender().sendMessage(ChatUtil.translate("&cCheck in config.yml!"));
		}
	}

	private void onlineDonors() {
		if (ComboFly.get().getMainConfig().getBoolean("ONLINE-DONATORS-ANNOUNCE.TOGGLE")) {
			ComboFly.get().getServer().getScheduler().runTaskTimerAsynchronously(ComboFly.get(), () -> {
				String players = Bukkit.getOnlinePlayers().stream().filter(online -> online.hasPermission("pandafly.donator") && !online.isOp() && !online.hasPermission("*")).map(HumanEntity::getName).collect(Collectors.joining(", "));
				if (players.isEmpty() && mainConfig.getBoolean("ONLINE-DONATORS-ANNOUNCE.NO-PLAYERS")) return;
				if (players.isEmpty()) {
					players = ChatUtil.translate(ComboFly.get().getMainConfig().getString("ONLINE-DONATORS-ANNOUNCE.MESSAGE-NO-DONATORS"));
				}
				for (String lines : ComboFly.get().getMainConfig().getStringList("ONLINE-DONATORS-ANNOUNCE.MESSAGE")) {
					Bukkit.broadcastMessage(ChatUtil.translate(lines
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
