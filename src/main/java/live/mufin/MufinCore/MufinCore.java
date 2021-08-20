package live.mufin.MufinCore;

import live.mufin.MufinCore.commands.CommandFinder;
import live.mufin.MufinCore.commands.HelpCommand;
import live.mufin.MufinCore.commands.MCMD;
import live.mufin.MufinCore.commands.MuffinCommand;
import live.mufin.MufinCore.databases.MongoDB;
import live.mufin.MufinCore.databases.MySQL;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MufinCore {

  public String mufinCoreVersion = "1.6.3";

  public List<MCMD> commands = new ArrayList<MCMD>();
  public char color;
  public String name;
  public String prefix;
  public JavaPlugin plugin;
  public HelpCommand helpCommand = new HelpCommand(this);
  public MongoDB mongoDB = null;
  public MySQL mySQL = null;
  public CommandFinder cmdFinder;
  public List<MuffinCommand> commandClasses = new ArrayList<>();

  public MCMD getMCMDFromName(String name) {
    for(MCMD cmd : commands) {
      if(cmd.name().equalsIgnoreCase(name)) {
        return cmd;
      }
    }
    throw new IllegalArgumentException("Invalid command name.");
  }

  public ConfigFile initializeConfig(String fileName) {
    return new ConfigFile(this, fileName);
  }

  public void sendDividerMessage(CommandSender sender) {
    this.sendFormattedMessage(sender, "&8=============================");
  }

  public void sendFormattedMessage(CommandSender sender, String message) {
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&" + color + prefix + "&8]&7 " + message));
  }

  public MufinCore(JavaPlugin plugin, String name, ChatColor color, String prefix) {
    this.color = color.getChar();
    this.name = name;
    this.prefix = prefix;
    this.plugin = plugin;
    cmdFinder = new CommandFinder(this, plugin.getClass().getPackageName());


    HelpCommand helpcmd = new HelpCommand(this);
    if(plugin.getCommand(name.toLowerCase()) != null) {
      plugin.getCommand(name.toLowerCase()).setExecutor(helpcmd);
      plugin.getCommand(name.toLowerCase()).setTabCompleter(helpcmd);
    }
  }

  /**
   * MongoDB Database
   * @param username Username for authentication
   * @param database Database you want to save data in
   * @param password Password for authentication
   * @param host Hostname of the database
   * @param port Port of the database
   */
  public void registerMongoDB(String username, String database, String password, String host, int port) {
    this.mongoDB = new MongoDB(this, username, database, password, host, port);
    mongoDB.connect();
  }

  /**
   * MySQL Database
   * @param host Where the database is hosted
   * @param port Port of the database (usually 3306)
   * @param database Name of the database you want to connect to
   * @param user Username for authentication
   * @param password Password for authentication
   */
  public void registerMySQL(String host, String port, String database, String user, String password, boolean useSSL) {
    this.mySQL = new MySQL(this, host, port, database, user, password, useSSL);
  }
}
