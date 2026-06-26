package noobsdev.mlmod_fork.util;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import noobsdev.mlmod_fork.client.Mlmod_forkClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMessageParser {
    public static final Pattern DONATE_CHAT = Pattern.compile("(Донат-чат|Donate-chat) » (?:([^\\s:]+) )?([^\\s:]+)(?: ([^\\s:]+))?: (.*)");
    public static final Pattern CREATIVE_CHAT = Pattern.compile("(Креатив-чат|Creative-chat) (?:») (?:(.*?)\\s)?([^\\s:]+): (.*)");
    public static final Pattern SYSTEM = Pattern.compile("(Система|System|Друзья|Friends) » (?:([^\\s:]+) )?([^\\s:]+)(?: ([^\\s:]+))?(?: (.*))?");
    public static final Pattern LOCAL_CHAT = Pattern.compile("(?:\\[(\\S+)] )?(?:(\\S+) )?(\\S+)(?: (\\S+))? » (.*)");
    public static final Pattern WORLD_INVITE = Pattern.compile("\\| (?:([^\\s:]+) )?([^\\s:]+)(?: ([^\\s:]+))?");
    public ChatMessageParser(){}

    public String getNameByText(String text) {
        text = text.strip();
        if (!text.contains("»")) return null;
        Matcher matcher;
        if(text.contains("Почта") || text.contains("Вопросы") || text.contains("Mail") || text.contains("Questions")) return null;

        matcher = DONATE_CHAT.matcher(text);
        if (matcher.matches()) {
            return matcher.group(3);
        }

        matcher = CREATIVE_CHAT.matcher(text);
        if (matcher.matches()) {
            return matcher.group(3);
        }

        matcher = SYSTEM.matcher(text);
        if (matcher.matches()) {
            return matcher.group(3);
        }

        matcher = LOCAL_CHAT.matcher(text);
        if (matcher.matches()) {
            return matcher.group(3);
        }

        matcher = WORLD_INVITE.matcher(text);
        if(matcher.matches()) {
            return matcher.group(2);
        }

        return null;
    }

    public String getClanIDByText(String text) {

        text = text.strip();
        if (!text.contains("»")) return null;
        Matcher matcher;

        matcher = LOCAL_CHAT.matcher(text);
        if (matcher.matches()) {
            Mlmod_forkClient.LOGGER.info(
                    "g1={}, g2={}, g3={}, g4={}",
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4)
            );
            return matcher.group(1);
        }

        return null;
    }

    public String getWorldID(Text message) {
        ClickEvent event = findClickEvent(message);

        if(event != null && event.getAction() == ClickEvent.Action.RUN_COMMAND && event.getValue().startsWith("/ad ")) {
            return event.getValue().split(" ")[1];
        }

        return null;

    }

    private ClickEvent findClickEvent(Text text) {
        ClickEvent clickEvent = text.getStyle().getClickEvent();

        if (clickEvent != null) {
            return clickEvent;
        }

        for (Text sibling : text.getSiblings()) {
            ClickEvent event = findClickEvent(sibling);
            if (event != null) {
                return event;
            }
        }

        return null;
    }


}