package noobsdev.mlmod_fork.client.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public abstract class MLModCommand {
    public MLModCommand() {}

    public abstract void run();

}
