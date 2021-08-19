package dev.panda.combofly.profile.entry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class KDEntry {

    private int kills, deaths;

    public void incrementKills() {
        this.kills += 1;
    }

    public void decrementKills(int amount) {
        this.kills -= amount;
    }

    public void incrementDeaths() {
        this.deaths += 1;
    }

    public void decrementDeaths(int amount) {
        this.deaths -= amount;
    }
}
