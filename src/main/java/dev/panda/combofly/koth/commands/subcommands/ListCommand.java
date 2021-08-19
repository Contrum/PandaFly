package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import dev.risas.panda.files.FileConfig;
import org.bukkit.command.CommandSender;

public class ListCommand extends BaseCommand {

    @Command(name = "koth.list", permission = "pandafly.koth.list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs commandArgs) {
        CommandSender sender = commandArgs.getSender();
        FileConfig config = ComboFly.get().getKothsConfig();

        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&4&lKoTH List"));
        sender.sendMessage("");
        config.getConfiguration().getConfigurationSection("KOTHS").getKeys(false).forEach(s -> {
            boolean enabled = config.getBoolean("KOTHS." + s + ".enabled");
            sender.sendMessage(CC.translate(" &7- " + (enabled ? CC.GREEN : CC.RED) + s));
        });
        sender.sendMessage(CC.CHAT_BAR);
    }
}
