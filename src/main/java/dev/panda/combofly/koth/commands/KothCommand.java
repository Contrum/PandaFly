package dev.panda.combofly.koth.commands;

import dev.panda.combofly.koth.commands.subcommands.*;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class KothCommand extends BaseCommand {

    public KothCommand() {
        super();
        new CreateCommand();
        new WandCommand();
        new ListCommand();
        new StartCommand();
        new DeleteCommand();
        new ShowCommand();
        new InformationCommand();
        new TeleportCommand();
        new StopCommand();
    }

    @Command(name = "koth", permission = "pandafly.koth")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String label = commandArgs.getLabel();

        String[] messages = new String[]{ChatUtil.NORMAL_LINE,
            "&4&lKoTH &4Commands",
            "",
            " &7- &c" + label + " show (name)",
            " &7- &c" + label + " information (name)",
            " &7- &c" + label + " create (name)",
            " &7- &c" + label + " delete (name)",
            " &7- &c" + label + " wand",
            " &7- &c" + label + " list",
            " &7- &c" + label + " start (name)",
            " &7- &c" + label + " stop (name)",
            ChatUtil.NORMAL_LINE};

        for (String message : messages) {
            player.sendMessage(ChatUtil.translate(message));
        }
    }
}
