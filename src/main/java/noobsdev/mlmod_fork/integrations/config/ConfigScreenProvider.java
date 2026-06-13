package noobsdev.mlmod_fork.integrations.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class ConfigScreenProvider {

    public static Screen createConfigScreen(Screen parentScreen) {
        ModConfig config = ModConfig.INSTANCE;

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.translatable("text.mlmod_fork.config.config_title"));

        ConfigCategory creative = builder.getOrCreateCategory(Text.translatable("text.mlmod_fork.config.main_category"));
        ConfigCategory chatUtils = builder.getOrCreateCategory(Text.translatable("text.mlmod_fork.config.main_category"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        SubCategoryBuilder textReplaceSub = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.config.text_replace.title"))
                .setExpanded(true);

        textReplaceSub.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.config.text_replace.title"), config.isTextReplaceEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.isTextReplaceEnabled = newValue)
                .build());

        textReplaceSub.add(entryBuilder.startStrField(Text.translatable("text.mlmod_fork.config.source_text"), config.sourceText)
                .setDefaultValue("%player%")
                .setSaveConsumer(newValue -> config.sourceText = newValue)
                .build());

        textReplaceSub.add(entryBuilder.startStrField(Text.translatable("text.mlmod_fork.config.target_text"), config.targetText)
                .setDefaultValue("%selected%")
                .setSaveConsumer(newValue -> config.targetText = newValue)
                .build());

        creative.addEntry(textReplaceSub.build());

        SubCategoryBuilder ignoredPlayers = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.ignored_players.title"))
                .setExpanded(true);

        ignoredPlayers.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_players.title"), config.isIgnorePlayersEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.isIgnorePlayersEnabled = newValue)
                .build());

        ignoredPlayers.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_players.debug"), config.ignorePlayersDebug)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.ignorePlayersDebug = newValue)
                .build());

        ignoredPlayers.add(entryBuilder.startStrList(
                        Text.translatable("text.mlmod_fork.ignored_players_list"),
                        config.ignoredPlayers
                )
                .setDefaultValue(new ArrayList<>())
                .setSaveConsumer(newValue -> config.ignoredPlayers = newValue) // Записываем измененный список обратно в конфиг
                .build());

        chatUtils.addEntry(ignoredPlayers.build());

        SubCategoryBuilder playerInteraction = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.player_interaction.title"))
                .setExpanded(true);

        playerInteraction.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.player_interaction.title"), config.isPlayerInteractionEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isPlayerInteractionEnabled = newValue)
                .build());

        playerInteraction.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.player_interaction.ignoring"), config.playerInteractionIgnoring)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.playerInteractionIgnoring = newValue)
                .build());

        playerInteraction.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.player_interaction.add_friend"), config.playerInteractionAddFriend)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.playerInteractionAddFriend = newValue)
                .build());

        playerInteraction.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.player_interaction.send_dm"), config.playerInteractionSendDM)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.playerInteractionSendDM = newValue)
                .build());
        chatUtils.addEntry(playerInteraction.build());

        builder.setSavingRunnable(config::save);
        return builder.build();
    }
}
