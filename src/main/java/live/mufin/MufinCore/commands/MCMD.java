package live.mufin.MufinCore.commands;

import org.bukkit.command.TabCompleter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MCMD {
    String name();
    String[] aliases() default {};
    String usage();
    String description();
    String permission();
}
