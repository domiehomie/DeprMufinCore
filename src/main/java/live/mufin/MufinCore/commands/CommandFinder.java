package live.mufin.MufinCore.commands;

import live.mufin.MufinCore.MufinCore;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CommandFinder {

    private static MufinCore core;
    private final Set<Class<?>> commands;
    public CommandFinder(MufinCore coree, String packageName) {
        Reflections reflections = new Reflections(packageName);
        commands = reflections.getTypesAnnotatedWith(MCMD.class);
        core = coree;
        testCommands();

    }

    private void testCommands() {
        for(Class<?> cmd : commands) {
            for (Class<?> interf : cmd.getInterfaces()) {
                if(interf == CommandExecutor.class) {
                    MCMD command = cmd.getAnnotation(MCMD.class);
                    try {
                        registerCmd(command, (CommandExecutor) cmd.getDeclaredConstructor().newInstance());
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                if(interf == TabCompleter.class) {
                    MCMD command = cmd.getAnnotation(MCMD.class);
                    for (MuffinCommand commandClass : core.commandClasses) {
                        if(commandClass.getName().equalsIgnoreCase(command.name())) {
                            try {
                                commandClass.addTabCompleter((TabCompleter) cmd.getDeclaredConstructor().newInstance());
                            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private void registerCmd(MCMD cmd, CommandExecutor ex) {
        core.commands.add(cmd);
        if(Bukkit.getCommandMap().getKnownCommands().containsKey(cmd.name())) return;
        MuffinCommand muffinCommand = new MuffinCommand(cmd, ex);
        core.commandClasses.add(muffinCommand);
        Bukkit.getCommandMap().register(cmd.name(), core.name.toLowerCase(), muffinCommand);
    }

    public static void registerCommand(String name, CommandExecutor executor) {
        MuffinCommand muffinCommand = new MuffinCommand(name, executor);
        core.commandClasses.add(muffinCommand);
        Bukkit.getCommandMap().register(name, core.name.toLowerCase(), muffinCommand);
    }

}
