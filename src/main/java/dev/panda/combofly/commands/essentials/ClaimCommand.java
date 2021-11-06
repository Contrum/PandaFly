package dev.panda.combofly.commands.essentials;

import com.sk89q.worldedit.bukkit.selections.Selection;
import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.Utils;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class ClaimCommand extends BaseCommand {

    @Command(name = "claim", permission = "pandafly.claim")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        if (args.length < 1) {
            this.getUsage(player, label);
            return;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!player.hasPermission("pandafly.claim.create")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            if (args.length < 3) {
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " create <nopvp|pvp> <name>"));
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 2; i < args.length; ++i) {
                sb.append(args[i]).append(" ");
            }

            String claim = sb.toString().trim();

            if (ComboFly.get().getClaimConfig().getConfiguration().getConfigurationSection("CLAIMS." + claim) != null) {
                player.sendMessage(ChatUtil.translate("&cThis claim is already created."));
                return;
            }

            Selection sel = Utils.getWorldEdit().getSelection(player);

            if (sel == null) {
                player.sendMessage(ChatUtil.translate("&cYou must make a WorldEdit selection."));
                return;
            }

            if (args[1].equalsIgnoreCase("nopvp")) {
                ComboFly.get().getClaimManager().createClaimNoPvP(claim, sel);
                player.sendMessage(ChatUtil.translate("&fYou have created &c" + claim + " &fClaim."));
            }
            else if (args[1].equalsIgnoreCase("pvp")) {
                ComboFly.get().getClaimManager().createClaimPvP(claim, sel);
                player.sendMessage(ChatUtil.translate("&fYou have created &c" + claim + " &fClaim."));
            }
            else {
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " create <nopvp|pvp> <name>"));
            }
        }
        else if (args[0].equalsIgnoreCase("delete")) {
            if (!player.hasPermission("pandafly.claim.delete")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            if (args.length < 2) {
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " delete <name>"));
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 1; i < args.length; ++i) {
                sb.append(args[i]).append(" ");
            }

            String claim = sb.toString().trim();

            if (ComboFly.get().getClaimConfig().getConfiguration().getConfigurationSection("CLAIMS." + claim) == null) {
                player.sendMessage(ChatUtil.translate("&cThis claim is already deleted."));
                return;
            }

            ComboFly.get().getClaimManager().deleteClaim(claim);
            player.sendMessage(ChatUtil.translate("&fYou have deleted &c" + claim + " &fClaim."));
        }
        else if (args[0].equalsIgnoreCase("list")) {
            if (!player.hasPermission("pandafly.claim.list")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            player.sendMessage(ChatUtil.translate("&cClaims Availables: " + ComboFly.get().getClaimManager().getClaimList()));
        }
        else {
            this.getUsage(player, label);
        }
    }

    private void getUsage(Player player, String label) {
        player.sendMessage(ChatUtil.translate("&7&m------------------------------"));
        player.sendMessage(ChatUtil.translate("&4Claim Commands"));
        player.sendMessage("");
        player.sendMessage(ChatUtil.translate("&c/" + label + " create <nopvp|pvp> <name> &7- &fCreate a claim."));
        player.sendMessage(ChatUtil.translate("&c/" + label + " delete <name> &7- &fDelete a claim."));
        player.sendMessage(ChatUtil.translate("&c/" + label + " list &7- &fList of all claims."));
        player.sendMessage(ChatUtil.translate("&7&m------------------------------"));
    }
}
