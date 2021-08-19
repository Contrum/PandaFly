package dev.panda.combofly.utilities;

import dev.panda.combofly.ComboFly;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Animation {

    public static String title, footer;

    public static void init() {
        List<String> titles = ComboFly.get().getScoreboardConfig().getStringList("TITLE");
        List<String> footers = ComboFly.get().getScoreboardConfig().getStringList("FOOTER");

        title = titles.get(0);
        footer = footers.get(0);

        if (ComboFly.get().getScoreboardConfig().getBoolean("TITLE-ANIMATION-ENABLE")) {
            AtomicInteger atomicInteger = new AtomicInteger();

            ComboFly.get().getServer().getScheduler().runTaskTimerAsynchronously(ComboFly.get(), () -> {
                if (atomicInteger.get() == titles.size()) atomicInteger.set(0);

                title = titles.get(atomicInteger.getAndIncrement());

            }, 0L, (long) (ComboFly.get().getScoreboardConfig().getDouble("TITLE-TASK") * 20L));
        }

        if (ComboFly.get().getScoreboardConfig().getBoolean("FOOTER-ANIMATION-ENABLE")) {
            AtomicInteger atomicInteger = new AtomicInteger();

            ComboFly.get().getServer().getScheduler().runTaskTimerAsynchronously(ComboFly.get(), () -> {
                if (atomicInteger.get() == footers.size()) atomicInteger.set(0);

                footer = footers.get(atomicInteger.getAndIncrement());

            }, 0L, (long) (ComboFly.get().getScoreboardConfig().getDouble("FOOTER-TASK") * 20L));
        }
    }

    public static String getScoreboardTitle() {
        return title;
    }

    public static String getScoreboardFooter() {
        return footer;
    }

}
