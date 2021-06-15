package live.mufin.MufinCore.commands;

import live.mufin.MufinCore.MufinCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class HelpCommand implements CommandExecutor, TabCompleter {

  private MufinCore core;
  public HelpCommand(MufinCore core) {
    this.core = core;
  }

  private void sendHelpCommand(CommandSender sender) {
    core.sendFormattedMessage(sender, "&8=============================");
    core.commands.forEach(cmd -> {
      core.sendFormattedMessage(sender, "&" + core.color + "/" + cmd.commandName() + "&7 - " + cmd.description());
    });
    core.sendFormattedMessage(sender, "&8=============================");
  }

  private void sendCommandHelpCommand(CommandSender p, String command) {
    MCM cmd = core.getMCMFromName(command);
    Formatter f = new Formatter();
    core.sendFormattedMessage(p, "&8=============================");
    core.sendFormattedMessage(p, f.format("Command: &{0}{1}", core.color, cmd.commandName()).toString());
    core.sendFormattedMessage(p, f.format("Description: &{0}{1}", core.color, cmd.description()).toString());
    StringBuilder aliases = new StringBuilder();
    for (String alias : cmd.commandAliases()) {
      if(aliases.isEmpty()) aliases.append(alias);
      else aliases.append(", ").append(alias);
    }
    if (!aliases.isEmpty()) core.sendFormattedMessage(p, f.format("Aliases: &{0}{1}", core.color, aliases.toString()).toString());
    if (!cmd.permission().isEmpty()) core.sendFormattedMessage(p, f.format("Permission: &{0}{1}", core.color, cmd.permission()).toString());
    core.sendFormattedMessage(p, f.format("Usage: &{0}{1}", core.color, cmd.usage()).toString());
    core.sendFormattedMessage(p, "&8=============================");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!label.equalsIgnoreCase("help")) return true;
    if(args.length == 0) {
      this.sendHelpCommand(sender);
    } else {
      try {
        this.sendCommandHelpCommand(sender, args[0]);
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
      commands.add(cmd.commandName());
    });

    List<String> results = new ArrayList<String>();
    if(args.length == 2) {
      for(String result : commands) {
        if(result.toUpperCase().startsWith(args[1].toUpperCase()))
          results.add(result);
      }
      return results;
    }
    return null;
  }
}
