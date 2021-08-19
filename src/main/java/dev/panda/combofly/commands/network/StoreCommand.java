package dev.panda.combofly.commands.network;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class StoreCommand extends BaseCommand {

    @Command(name = "store", permission = "pandafly.store", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        sender.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("NETWORK.STORE")
                .replace("{store}", ComboFly.get().getMainConfig().getString("STORE"))));
    }
}
