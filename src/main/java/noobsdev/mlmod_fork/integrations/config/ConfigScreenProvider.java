package noobsdev.mlmod_fork.integrations.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ConfigScreenProvider {


    public static Screen createConfigScreen(Screen parentScreen) {
        ModConfig config = ModConfig.INSTANCE;

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.translatable("text.mlmod_fork.config.config_title"));

        ConfigCategory creative = builder.getOrCreateCategory(Text.translatable("text.mlmod_fork.config.creative_category"));
        ConfigCategory chatUtils = builder.getOrCreateCategory(Text.translatable("text.mlmod_fork.config.chat_category"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        SubCategoryBuilder textReplaceSub = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.config.text_replace.title"))
                .setExpanded(false);

        textReplaceSub.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.config.text_replace_setting"), config.isTextReplaceEnabled)
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

        textReplaceSub.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.config.text_replace.reverse"), config.reverseMode)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.reverseMode = newValue)
                .build());

        creative.addEntry(textReplaceSub.build());

        SubCategoryBuilder flyBoost = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.config.fly_boost.title"))
                .setExpanded(false);

        flyBoost.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.config.fly_boost"), config.isFlyBoostEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.isFlyBoostEnabled = newValue)
                .build());

        flyBoost.add(entryBuilder.startIntSlider(Text.translatable("text.mlmod_fork.config.fly_boost.speed"), config.flyBoost, 10, 150)
                .setDefaultValue(10)
                .setSaveConsumer(newValue -> config.flyBoost = newValue)
                .build());

        creative.addEntry(flyBoost.build());

        SubCategoryBuilder ignoredPlayers = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.ignored_players.title"))
                .setExpanded(false);

        ignoredPlayers.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_players_setting"), config.isIgnorePlayersEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.isIgnorePlayersEnabled = newValue)
                .build());

        ignoredPlayers.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_players.debug"), config.ignorePlayersDebug)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.ignorePlayersDebug = newValue)
                .build());

        ignoredPlayers.add(entryBuilder.startStrList(
                        Text.translatable("text.mlmod_fork.ignored_players_list"),
                        config.ignoringPlayers
                )
                .setDefaultValue(new ArrayList<>())
                .setSaveConsumer(newValue -> config.ignoringPlayers = newValue)
                .build());

        chatUtils.addEntry(ignoredPlayers.build());

        SubCategoryBuilder playerInteraction = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.player_interaction.title"))
                .setExpanded(false);

        playerInteraction.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.player_interaction_setting"), config.isPlayerInteractionEnabled)
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

        playerInteraction.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.player_interaction.report"), config.isPlayerInteractionReport)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isPlayerInteractionReport = newValue)
                .build());

        SubCategoryBuilder clanIgnoring = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.ignored_clans.title"))
                .setExpanded(false);

        clanIgnoring.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_clans_setting"), config.isClanIgnoreEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.isClanIgnoreEnabled = newValue)
                .build());

        clanIgnoring.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_clans.debug"), config.ignoreClansDebug)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.ignoreClansDebug = newValue)
                .build());

        clanIgnoring.add(entryBuilder.startStrList(
                        Text.translatable("text.mlmod_fork.ignored_clans_list"),
                        config.clansIgnoring
                )
                .setDefaultValue(new ArrayList<>())
                .setSaveConsumer(newValue -> config.clansIgnoring = newValue)
                .build());

        SubCategoryBuilder worldIgnoring = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.ignored_worlds.title"))
                .setExpanded(false);

        worldIgnoring.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_worlds_setting"), config.isWorldIgnoreEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.isWorldIgnoreEnabled = newValue)
                .build());

        worldIgnoring.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_worlds.debug"), config.ignoreWorldsDebug)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.ignoreWorldsDebug = newValue)
                .build());

        worldIgnoring.add(entryBuilder.startStrList(
                        Text.translatable("text.mlmod_fork.ignored_worlds_list"),
                        config.worldsIgnoring
                )
                .setDefaultValue(new ArrayList<>())
                .setSaveConsumer(newValue -> config.worldsIgnoring = newValue)
                .build());

        SubCategoryBuilder decorators = DecoratorCategoryBuilder.decorators(entryBuilder);
        creative.addEntry(decorators.build());

        chatUtils.addEntry(worldIgnoring.build());
        chatUtils.addEntry(clanIgnoring.build());
        chatUtils.addEntry(playerInteraction.build());

        builder.setSavingRunnable(config::save);
        return builder.build();
    }
    // todo: переделать в методы все

}