package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ShowCommand extends BaseCommand {

    @Command(name = "koth.show")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /koth show (name)"));
            return;
        }

        String name = args[0];
        KoTH koth = KoTH.getFromName(name);

        if (koth != null) {
            Location center = koth.getCuboid().getCenter();
            String[] message = new String[]{CC.CHAT_BAR,
                    "&4&l" + koth.getName() + " &4Show",
                    "",
                    " &cTime Left&7: &f" + koth.getTimeLeft(),
                    " &cCoords&7: &f" + center.getX() + ", " + center.getY() + ", " + center.getZ(),
                    " &cCapper&7: &f" + koth.getCapper(),
                    CC.CHAT_BAR};

            for (String s : message) {
                player.sendMessage(CC.translate(s));
            }
        } else {
            player.sendMessage(CC.translate("&cPlease insert a valid koth name!"));
        }
    }
}
