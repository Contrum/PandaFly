package dev.panda.combofly.utilities;

import dev.panda.combofly.ComboFly;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

import java.util.*;

public class CC {

	public static String LINE = CC.translate("&7&m-----------------------------");
	private static final Map<String, ChatColor> MAP;

	public static final String BLUE;
	public static final String AQUA;
	public static final String YELLOW;
	public static final String RED;
	public static final String GRAY;
	public static final String GOLD;
	public static final String GREEN;
	public static final String WHITE;
	public static final String BLACK;
	public static final String BOLD;
	public static final String ITALIC;
	public static final String UNDER_LINE;
	public static final String STRIKE_THROUGH;
	public static final String RESET;
	public static final String MAGIC;
	public static final String DARK_BLUE;
	public static final String DARK_AQUA;
	public static final String DARK_GRAY;
	public static final String DARK_GREEN;
	public static final String DARK_PURPLE;
	public static final String DARK_RED;
	public static final String PINK;
	public static final String MENU_BAR;
	public static final String CHAT_BAR;
	public static final String SB_BAR;
	public static final String TAB_BAR;

	static {
		MAP = new HashMap<>();
		MAP.put("pink", ChatColor.LIGHT_PURPLE);
		MAP.put("orange", ChatColor.GOLD);
		MAP.put("purple", ChatColor.DARK_PURPLE);

		for (ChatColor chatColor : ChatColor.values()) {
			MAP.put(chatColor.name().toLowerCase().replace("_", ""), chatColor);
		}

		BLUE = ChatColor.BLUE.toString();
		AQUA = ChatColor.AQUA.toString();
		YELLOW = ChatColor.YELLOW.toString();
		RED = ChatColor.RED.toString();
		GRAY = ChatColor.GRAY.toString();
		GOLD = ChatColor.GOLD.toString();
		GREEN = ChatColor.GREEN.toString();
		WHITE = ChatColor.WHITE.toString();
		BLACK = ChatColor.BLACK.toString();
		BOLD = ChatColor.BOLD.toString();
		ITALIC = ChatColor.ITALIC.toString();
		UNDER_LINE = ChatColor.UNDERLINE.toString();
		STRIKE_THROUGH = ChatColor.STRIKETHROUGH.toString();
		RESET = ChatColor.RESET.toString();
		MAGIC = ChatColor.MAGIC.toString();
		DARK_BLUE = ChatColor.DARK_BLUE.toString();
		DARK_AQUA = ChatColor.DARK_AQUA.toString();
		DARK_GRAY = ChatColor.DARK_GRAY.toString();
		DARK_GREEN = ChatColor.DARK_GREEN.toString();
		DARK_PURPLE = ChatColor.DARK_PURPLE.toString();
		DARK_RED = ChatColor.DARK_RED.toString();
		PINK = ChatColor.LIGHT_PURPLE.toString();
		MENU_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------";
		CHAT_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";
		SB_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "----------------------";
		TAB_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "-----------------";
	}

	public static Set<String> getColorNames() {
		return MAP.keySet();
	}

	public static ChatColor getColorFromName(String name) {
		if (MAP.containsKey(name.trim().toLowerCase())) {
			return MAP.get(name.trim().toLowerCase());
		}

		ChatColor color;

		try {
			color = ChatColor.valueOf(name.toUpperCase().replace(" ", "_"));
		} catch (Exception e) {
			return null;
		}

		return color;
	}

	public static String translate(String in) {
		return ChatColor.translateAlternateColorCodes('&', in);
	}

	public static List<String> translate(List<String> lines) {
		List<String> toReturn = new ArrayList<>();

		for (String line : lines) {
			toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
		}

		return toReturn;
	}

	public static String[] translate(String[] lines) {
		List<String> toReturn = new ArrayList<>();

		for (String line : lines) {
			if (line != null) {
				toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
			}
		}

		return toReturn.toArray(new String[toReturn.size()]);
	}

	public static String strip(String message) {
		return ChatColor.stripColor(message);
	}

	public static String getNoConsole() {
		return CC.translate(ComboFly.get().getMainConfig().getString("NO-PERMISSION.CONSOLE"));
	}

	public static String getNoPermission() {
		return CC.translate(ComboFly.get().getMainConfig().getString("NO-PERMISSION.PLAYER"));
	}

	public static String getPlayerNotFound(String arg) {
		return CC.translate(ComboFly.get().getMainConfig().getString("PLAYER-NOTFOUND.MESSAGE").replace("{argument}", arg));
	}

	public static String getNoNumber(){
		return CC.translate(ComboFly.get().getMessageConfig().getString("AMOUNT.NO-NUMBER"));
	}

	public static String getNoPositive(){
		return CC.translate(ComboFly.get().getMessageConfig().getString("AMOUNT.NO-POSITIVE"));
	}

	public static String getKitNotFound(String arg) {
		return CC.translate(ComboFly.get().getMessageConfig().getString("KIT.NOT-FOUND").replace("{kit}", arg));
	}

	public static String getWarpNotFound(String arg){
		return CC.translate(ComboFly.get().getMessageConfig().getString("WARP.NOT-FOUND").replace("{warp}", arg));
	}

	public static String capitalize(String string) {
		return WordUtils.capitalize(string.toLowerCase());
	}
}
