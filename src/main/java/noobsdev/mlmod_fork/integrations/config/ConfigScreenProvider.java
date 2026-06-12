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

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("text.mlmod_fork.config.main_category"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        SubCategoryBuilder textReplaceSub = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.config.text_replace_title"))
                .setExpanded(true);

        textReplaceSub.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.config.text_replace_title"), config.isTextReplaceEnabled)
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

        general.addEntry(textReplaceSub.build());

        SubCategoryBuilder ignoredPlayers = entryBuilder
                .startSubCategory(Text.translatable("text.mlmod_fork.ignored_players_title"))
                .setExpanded(true);

        ignoredPlayers.add(entryBuilder.startBooleanToggle(Text.translatable("text.mlmod_fork.ignored_players_title"), config.ignoredPlayers_)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.ignoredPlayers_ = newValue)
                .build());

        ignoredPlayers.add(entryBuilder.startStrList(
                        Text.translatable("text.mlmod_fork.ignored_players_list"),
                        config.ignoredPlayers
                )
                .setDefaultValue(new ArrayList<>())
                .setSaveConsumer(newValue -> config.ignoredPlayers = newValue) // Записываем измененный список обратно в конфиг
                .build());

        general.addEntry(ignoredPlayers.build());
        builder.setSavingRunnable(config::save);
        return builder.build();
    }
}
