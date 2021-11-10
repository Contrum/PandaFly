package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import dev.panda.lib.file.FileConfig;
import org.bukkit.command.CommandSender;

public class ListCommand extends BaseCommand {

    @Command(name = "koth.list", permission = "pandafly.koth.list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs commandArgs) {
        CommandSender sender = commandArgs.getSender();
        FileConfig config = ComboFly.get().getKothsConfig();

        sender.sendMessage(ChatUtil.NORMAL_LINE);
        sender.sendMessage(ChatUtil.translate("&4&lKoTH List"));
        sender.sendMessage("");
        config.getConfiguration().getConfigurationSection("KOTHS").getKeys(false).forEach(koth -> {
            boolean enabled = config.getBoolean("KOTHS." + koth + ".enabled");
            sender.sendMessage(ChatUtil.translate(" &7- " + (enabled ? "&a" : "&c") + koth));
        });
        sender.sendMessage(ChatUtil.NORMAL_LINE);
    }
}
