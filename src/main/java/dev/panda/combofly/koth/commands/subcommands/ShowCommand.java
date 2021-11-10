package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ShowCommand extends BaseCommand {

    @Command(name = "koth.show")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(ChatUtil.translate("&cUsage: /koth show (name)"));
            return;
        }

        String name = args[0];
        KoTH koth = KoTH.getFromName(name);

        if (koth != null) {
            Location center = koth.getCuboid().getCenter();
            String[] message = new String[]{ChatUtil.NORMAL_LINE,
                    "&4&l" + koth.getName() + " &4Show",
                    "",
                    " &cTime Left&7: &f" + koth.getTimeLeft(),
                    " &cCoords&7: &f" + center.getX() + ", " + center.getY() + ", " + center.getZ(),
                    " &cCapper&7: &f" + koth.getCapper(),
                    ChatUtil.NORMAL_LINE};

            for (String s : message) {
                player.sendMessage(ChatUtil.translate(s));
            }
        } else {
            player.sendMessage(ChatUtil.translate("&cPlease insert a valid koth name!"));
        }
    }
}
