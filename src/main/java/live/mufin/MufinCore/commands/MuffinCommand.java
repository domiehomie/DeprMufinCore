package live.mufin.MufinCore.commands;

import live.mufin.MufinCore.MufinCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MuffinCommand extends BukkitCommand {
    private CommandExecutor ex;
    private TabCompleter tab;
    private MCMD cmd;
    public MuffinCommand(MCMD cmd, CommandExecutor ex) {
        super(cmd.name(), cmd.description(), cmd.usage(), Arrays.asList(cmd.aliases()));
        this.cmd = cmd;
        this.ex = ex;
    }

    public void addTabCompleter(TabCompleter tab) {
        this.tab = tab;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions = tab == null ? null : tab.onTabComplete(sender, this, alias, args);
        return completions == null ? Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()) : completions;
    }

    public MuffinCommand(String name, CommandExecutor ex) {
        super(name);
        this.ex = ex;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(cmd.permission().isBlank()) {
            return ex.onCommand(sender, this, commandLabel, args);
        }
        if(!sender.hasPermission(cmd.permission())) {
            sender.sendMessage("&cInvalid permission.");
            return true;
        }
        return ex.onCommand(sender, this, commandLabel, args);
    }
}

