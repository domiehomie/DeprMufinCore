
package live.mufin.MufinCore.commands;

import live.mufin.MufinCore.MufinCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class HelpCommand implements CommandExecutor, TabCompleter {

  private final MufinCore core;
  public HelpCommand(MufinCore core) {
    this.core = core;
  }

  private void sendPluginInfo(CommandSender sender) {
    core.sendFormattedMessage(sender, "&8=============================");
    core.sendFormattedMessage(sender, "&" + core.color + core.name + " &7v&" + core.color + core.plugin.getDescription().getVersion() + "&7.");
    core.sendFormattedMessage(sender, "MufinCore version v&" + core.color + core.mufinCoreVersion + "&7.");
    core.sendFormattedMessage(sender, "Plugin made by: &" + core.color + core.plugin.getDescription().getAuthors());
    core.sendFormattedMessage(sender, "&8=============================");
  }

  private void sendHelpCommand(CommandSender sender) {
    core.sendFormattedMessage(sender, "&8=============================");
    core.commands.forEach(cmd -> {
      if(sender.hasPermission(cmd.permission()))
        core.sendFormattedMessage(sender, "&" + core.color + "/" + cmd.name() + "&7 - " + cmd.description());
    });
    core.sendFormattedMessage(sender, "&8=============================");
  }

  private void sendCommandHelpCommand(CommandSender p, String command) {
    MCMD cmd = core.getMCMDFromName(command);
    if(p.hasPermission(cmd.permission())) {
      Formatter f = new Formatter();
      core.sendFormattedMessage(p, "&8=============================");
      core.sendFormattedMessage(p, "Command: &" + core.color + cmd.name());
      core.sendFormattedMessage(p, "Description: &" + core.color + cmd.description());
      StringBuilder aliases = new StringBuilder();
      if (cmd.aliases() != null) {
        for (String alias : cmd.aliases()) {
          if (aliases.isEmpty()) aliases.append(alias);
          else aliases.append(", ").append(alias);
        }
      }
      if (!aliases.isEmpty()) core.sendFormattedMessage(p, "Aliases: &" + core.color + aliases);
      if (!cmd.permission().isEmpty()) core.sendFormattedMessage(p, "Permission: &" + core.color + cmd.permission());
      core.sendFormattedMessage(p, "Usage: &" + core.color + cmd.usage());
      core.sendFormattedMessage(p, "&8=============================");
    } else {
      core.sendFormattedMessage(p, "&cInvalid permission.");
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!label.equalsIgnoreCase(core.name)) return true;
    if(args.length == 0) {
      this.sendPluginInfo(sender);
    }else if(args.length == 1) {
      this.sendHelpCommand(sender);
    } else {
      try {
        this.sendCommandHelpCommand(sender, args[1]);
      }catch(IllegalArgumentException e) {
        core.sendFormattedMessage(sender, "&cUnknown command.");
      }
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    List<String> commands = new ArrayList<String>();
    core.commands.forEach(cmd -> {
      commands.add(cmd.name());
    });

    List<String> results = new ArrayList<String>();
    if(args.length == 1) {
      if("help".toUpperCase().startsWith(args[0].toUpperCase()))
        results.add("help");
      return results;
    } else if(args.length == 2) {
      for(String result : commands) {
        if(result.toUpperCase().startsWith(args[1].toUpperCase()))
          results.add(result);
      }
      return results;
    }
    return null;
  }
}
