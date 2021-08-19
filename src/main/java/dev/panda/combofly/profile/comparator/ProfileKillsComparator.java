package dev.panda.combofly.profile.comparator;

import dev.panda.combofly.profile.Profile;

import java.util.Comparator;

public class ProfileKillsComparator implements Comparator<Profile> {
    @Override
    public int compare(Profile o1, Profile o2) {
        return Integer.compare(o1.getKd().getKills(), o2.getKd().getKills());
    }
}
