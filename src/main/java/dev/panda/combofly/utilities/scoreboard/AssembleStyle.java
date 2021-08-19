package dev.panda.combofly.utilities.scoreboard;

import lombok.Getter;

@Getter
public enum AssembleStyle {

    KOHI(true, 15);

    private boolean descending;
    private int startNumber;

    AssembleStyle(boolean descending, int startNumber) {
        this.descending = descending;
        this.startNumber = startNumber;
    }
}
