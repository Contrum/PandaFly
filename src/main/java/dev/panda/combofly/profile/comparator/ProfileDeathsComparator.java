package dev.panda.combofly.profile.comparator;

import dev.panda.combofly.profile.Profile;

import java.util.Comparator;

public class ProfileDeathsComparator implements Comparator<Profile> {
    @Override
    public int compare(Profile o1, Profile o2) {
        return Integer.compare(o1.getKd().getDeaths(), o2.getKd().getDeaths());
    }
}
