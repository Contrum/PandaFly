package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
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
            player.sendMessage(ChatUtil.translate("&7&m-----------------------------"));
            player.sendMessage(ChatUtil.translate("&4&lKnockback Info"));
            player.sendMessage(ChatUtil.translate(""));
            player.sendMessage(ChatUtil.translate("&cDirection X&7: &f" + ComboFly.get().getKbManager().getKbX()));
            player.sendMessage(ChatUtil.translate("&cDirection Y&7: &f" + ComboFly.get().getKbManager().getKbY()));
            player.sendMessage(ChatUtil.translate("&cDirection Z&7: &f" + ComboFly.get().getKbManager().getKbZ()));
            player.sendMessage(ChatUtil.translate("&cMaxHeight&7: &f" + ComboFly.get().getKbManager().getHeightKB()));
            player.sendMessage(ChatUtil.translate(""));
            player.sendMessage(ChatUtil.translate("&7&m-----------------------------"));
        }
        else if (args[0].equalsIgnoreCase("set")){
            if (args.length < 4) {
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " set <x> <y> <z>"));
                return;
            }
            double getX = Double.parseDouble(args[1]);
            double getY = Float.parseFloat(args[2]);
            double getZ = Double.parseDouble(args[3]);
            if (getX <= 0) {
                player.sendMessage(ChatUtil.translate("&cDirection X must be positive."));
                return;
            }
            if (getY <= 0) {
                player.sendMessage(ChatUtil.translate("&cDirection Y must be positive."));
                return;
            }
            if (getZ <= 0) {
                player.sendMessage(ChatUtil.translate("&cDirection Z must be positive."));
                return;
            }
            ComboFly.get().getKbManager().setKnockBack(getX, getY, getZ);
            player.sendMessage(ChatUtil.translate("&7[&c&lKnockBack&7] &fSuccessfully saved &cDirections&f."));
        }
        else if (args[0].equalsIgnoreCase("setmaxheight")) {

            if (args.length < 2){
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " setmaxheight <maxheight>"));
                return;
            }

            double heightKB = Double.parseDouble(args[1]);
            if (heightKB <= 0) {
                player.sendMessage(ChatUtil.translate("&cmax height must be positive."));
                return;
            }
            ComboFly.get().getKbManager().setHeightKB(heightKB);
            player.sendMessage(ChatUtil.translate("&7[&c&lKnockBack&7] &fSuccessfully saved &cMax Height&f."));
        }
        else this.getUsage(player, label);
    }

    private void getUsage(Player player, String label){
            player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <info|set|setmaxheight>"));
    }
}
