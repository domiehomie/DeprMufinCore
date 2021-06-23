# MufinCore
 Core library to make spigot developing easier!

## How to initialize core

To initialize the core, write this in your main plugin class.

```java
public static MufinCore core;

    @Override
    public void onEnable() {
        core = new MufinCore(this, "plugin-name", ChatColor.PLUGIN-COLOR, "plugin-prefix");
    }
```

**IMPORTANT:** Make sure to add a command to your `plugin.yml` with the plugin name as the command name.

## How to use core

### Formatted messages

```java
core.sendFormattedMessage(target, "message");
```

### Commands

Commands are handled with the MCM interface. An example MCM implemented command class:

```java
public class ExampleCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "example";
    }

    @Override
    public String[] commandAliases() {
        return new String[]{"ex", "exmpl"};
    }

    @Override
    public String usage() {
        return "/example";
    }

    @Override
    public String description() {
        return "An example command!";
    }

    @Override
    public String permission() {
        return "example.example";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
```

To then register this command, add one of the following in your onEnable.

```java
core.registerCommand(new ExampleCommand());
core.registerCommands(new MCM[]{new ToDoCommand()});
```

This then adds it to the /help menu.

### Databases

#### MySQL

For a MySQL database, type this in your onEnable.

```java
core.registerMySQL("host", "port", "database", "user", "password", useSSL);
core.mySQL.connect();
```

You can then store the connection, to refer to later.

```
Connection conn = core.mySQL.getConnection();
```

#### MongoDB

*Coming soon.*
