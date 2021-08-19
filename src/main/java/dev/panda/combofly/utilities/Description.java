package dev.panda.combofly.utilities;

import java.util.List;

import dev.panda.combofly.ComboFly;

public class Description {
	
	public static String getName() {
		return ComboFly.get().getDescription().getName();
	}

	public static String getVersion() {
		return ComboFly.get().getDescription().getVersion();
	}

	public static List<String> getAuthors() {
		return ComboFly.get().getDescription().getAuthors();
	}
}
