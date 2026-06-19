package noobsdev.mlmod_fork.util;

public enum AdvertTemplates {

    ADVERTISING_WORLD("  | [Приглашение в мир]\\n " +
            "| {prefix} {name} {suffix} приглашает тебя посетить игру:\\n " +
            "|   {world_name}\\n " +
            "| \\n |            [Присоединиться]\\n " +
            "| \\n");

    public String text;

    AdvertTemplates(String text) {
        this.text = text;
    }
    
}
