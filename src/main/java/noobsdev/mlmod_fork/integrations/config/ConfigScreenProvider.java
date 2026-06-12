package noobsdev.mlmod_fork.integrations.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenProvider {

    public static Screen createConfigScreen(Screen parentScreen) {
        ModConfig config = ModConfig.INSTANCE;

        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parentScreen).setTitle(Text.translatable("text.mlmod_fork.config.config_title"));

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

        builder.setSavingRunnable(config::save);
        return builder.build();
    }

}
