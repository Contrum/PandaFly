package dev.panda.combofly.managers;

import dev.panda.combofly.ComboFly;

public class KBManager {

    public boolean hasKnockBackEnabled() {
        return ComboFly.get().getMainConfig().getBoolean("KNOCKBACK.ENABLE");
    }
    public boolean hasMaxTicksEnabled() {
        return ComboFly.get().getMainConfig().getBoolean("HIT-TICKS.ENABLE");
    }

    public void setKnockBack(double X, double Y, double Z) {
        ComboFly.get().getMainConfig().getConfiguration().set("KNOCKBACK.X", X);
        ComboFly.get().getMainConfig().getConfiguration().set("KNOCKBACK.Y", Y);
        ComboFly.get().getMainConfig().getConfiguration().set("KNOCKBACK.Z", Z);
        ComboFly.get().getMainConfig().save();
        ComboFly.get().getMainConfig().reload();
    }
    public double getKbX() { return ComboFly.get().getMainConfig().getDouble("KNOCKBACK.X"); }
    public double getKbY() { return ComboFly.get().getMainConfig().getDouble("KNOCKBACK.Y"); }
    public double getKbZ() { return ComboFly.get().getMainConfig().getDouble("KNOCKBACK.Z"); }

    public void setHeightKB(double HeightKB){
        ComboFly.get().getMainConfig().getConfiguration().set("KNOCKBACK.MAXHEIGHT", HeightKB);
        ComboFly.get().getMainConfig().save();
        ComboFly.get().getMainConfig().reload();
    }
    public double getHeightKB(){
        return ComboFly.get().getMainConfig().getDouble("KNOCKBACK.MAXHEIGHT");
    }
}

