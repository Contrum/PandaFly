package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.combofly.koth.cuboid.Cuboid;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class InformationCommand extends BaseCommand {

    @Command(name = "koth.information", permission = "pandafly.koth.info", aliases = {"info"})
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /koth information (name)"));
            return;
        }

        String name = args[0];
        KoTH koth = KoTH.getFromName(name);

        if (koth != null) {
            Cuboid cuboid = koth.getCuboid();
            Location center = cuboid.getCenter();
            Location upper = cuboid.getUpperCorner();
            Location lower = cuboid.getLowerCorner();
            String[] message = new String[]{CC.CHAT_BAR,
                    "&4&l" + koth.getName() + " &4Information",
                    "",
                    " &cDate&7: &f" + koth.getDate(),
                    " &cCreator&7: &f" + koth.getCreator(),
                    " &cCoords&7: &f" + center.getX() + ", " + center.getY() + ", " + center.getZ(),
                    " &cCorners&7:",
                    "    &cUpper&7: " + upper.getX() + ", " + upper.getY() + ", " + upper.getZ(),
                    "    &cLower&7: " + lower.getX() + ", " + lower.getY() + ", " + lower.getZ(),
                    CC.CHAT_BAR};

            for (String s : message) {
                player.sendMessage(CC.translate(s));
            }
        } else {
            player.sendMessage(CC.translate("&cPlease insert a valid koth name!"));
        }
    }
}
