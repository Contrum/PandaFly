package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class KBCommand extends BaseCommand {

    @Command(name = "knockback", permission = "pandafly.knockback", aliases = {"kb"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            this.getUsage(player, label);
            return;
        }
        if (args[0].equalsIgnoreCase("info")){
            player.sendMessage(CC.translate("&7&m-----------------------------"));
            player.sendMessage(CC.translate("&4&lKnockback Info"));
            player.sendMessage(CC.translate(""));
            player.sendMessage(CC.translate("&cDirection X&7: &f" + ComboFly.get().getKbManager().getKbX()));
            player.sendMessage(CC.translate("&cDirection Y&7: &f" + ComboFly.get().getKbManager().getKbY()));
            player.sendMessage(CC.translate("&cDirection Z&7: &f" + ComboFly.get().getKbManager().getKbZ()));
            player.sendMessage(CC.translate("&cMaxHeight&7: &f" + ComboFly.get().getKbManager().getHeightKB()));
            player.sendMessage(CC.translate(""));
            player.sendMessage(CC.translate("&7&m-----------------------------"));
        }
        else if (args[0].equalsIgnoreCase("set")){
            if (args.length < 4) {
                player.sendMessage(CC.translate("&cUsage: /" + label + " set <x> <y> <z>"));
                return;
            }
            double getX = Double.parseDouble(args[1]);
            double getY = Float.parseFloat(args[2]);
            double getZ = Double.parseDouble(args[3]);
            if (getX <= 0) {
                player.sendMessage(CC.translate("&cDirection X must be positive."));
                return;
            }
            if (getY <= 0) {
                player.sendMessage(CC.translate("&cDirection Y must be positive."));
                return;
            }
            if (getZ <= 0) {
                player.sendMessage(CC.translate("&cDirection Z must be positive."));
                return;
            }
            ComboFly.get().getKbManager().setKnockBack(getX, getY, getZ);
            player.sendMessage(CC.translate("&7[&c&lKnockBack&7] &fSuccessfully saved &cDirections&f."));
        }
        else if (args[0].equalsIgnoreCase("setmaxheight")) {

            if (args.length < 2){
                player.sendMessage(CC.translate("&cUsage: /" + label + " setmaxheight <maxheight>"));
                return;
            }

            double heightKB = Double.parseDouble(args[1]);
            if (heightKB <= 0) {
                player.sendMessage(CC.translate("&cmax height must be positive."));
                return;
            }
            ComboFly.get().getKbManager().setHeightKB(heightKB);
            player.sendMessage(CC.translate("&7[&c&lKnockBack&7] &fSuccessfully saved &cMax Height&f."));
        }
        else this.getUsage(player, label);
    }

    private void getUsage(Player player, String label){
            player.sendMessage(CC.translate("&cUsage: /" + label + " <info|set|setmaxheight>"));
    }
}
