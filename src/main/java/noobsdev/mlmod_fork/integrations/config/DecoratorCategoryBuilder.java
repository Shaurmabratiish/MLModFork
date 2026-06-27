package noobsdev.mlmod_fork.integrations.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.StringFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.text.Text;

import java.util.regex.Pattern;

public class DecoratorCategoryBuilder {

    private static final Pattern HEX_PATTERN = Pattern.compile("^#?([A-Fa-f0-9]{6})$");
    public DecoratorCategoryBuilder() {}

    public StringFieldBuilder build(ConfigEntryBuilder entry, Text translatable, String defaultValue) {
        return entry.startStrField(translatable, defaultValue)
                .setErrorSupplier(value -> {
                    if (HEX_PATTERN.matcher(value).matches()) {
                        return java.util.Optional.empty();
                    } else {
                        return java.util.Optional.of(Text.translatable("text.mlmod_fork.decorators.invalid_hex"));
                    }
                });
    }

    public static SubCategoryBuilder decorators(ConfigEntryBuilder builder) {

        ModConfig config = ModConfig.INSTANCE;
        DecoratorCategoryBuilder decorators = new DecoratorCategoryBuilder();

        SubCategoryBuilder varDecorators = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators"))
                .setExpanded(false);

        varDecorators.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.enabled"), config.isDecoratorsEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.isDecoratorsEnabled = newValue)
                .build());

        varDecorators.add(builder.startFloatField(Text.translatable("text.mlmod_fork.decorators.size"), config.DecoratorsSize)
                .setDefaultValue(1.0f)
                .setSaveConsumer(newValue -> config.DecoratorsSize = newValue)
                .build());

        SubCategoryBuilder textDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.text"))
                .setExpanded(false);

        textDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isTextDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isTextDecoratorEnabled = newValue)
                .build());

        textDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.textDecoratorColor)
                .setSaveConsumer(newValue -> config.textDecoratorColor = newValue)
                .build()
        );

        textDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.textDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.textDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(textDecorator.build());

        SubCategoryBuilder numDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.num"))
                .setExpanded(false);

        numDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isNumDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isNumDecoratorEnabled = newValue)
                .build());

        numDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.numDecoratorColor)
                .setSaveConsumer(newValue -> config.numDecoratorColor = newValue)
                .build()
        );

        numDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.numDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.numDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(numDecorator.build());

        SubCategoryBuilder locationDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.location"))
                .setExpanded(false);

        locationDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isLocationDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isLocationDecoratorEnabled = newValue)
                .build());

        locationDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.locationDecoratorColor)
                .setSaveConsumer(newValue -> config.locationDecoratorColor = newValue)
                .build()
        );

        locationDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.locationDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.locationDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(locationDecorator.build());

        SubCategoryBuilder potionDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.potion"))
                .setExpanded(false);

        potionDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isPotionDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isPotionDecoratorEnabled = newValue)
                .build());

        potionDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.potionDecoratorColor)
                .setSaveConsumer(newValue -> config.potionDecoratorColor = newValue)
                .build()
        );

        potionDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.potionDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.potionDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(potionDecorator.build());

        SubCategoryBuilder varDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.dynamic_var"))
                .setExpanded(false);

        varDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isVarDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isVarDecoratorEnabled = newValue)
                .build());

        varDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.varDecoratorColor)
                .setSaveConsumer(newValue -> config.varDecoratorColor = newValue)
                .build()
        );

        varDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.varDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.varDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(varDecorator.build());

        SubCategoryBuilder gameValueDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.game_value"))
                .setExpanded(false);

        gameValueDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isGameValueDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isGameValueDecoratorEnabled = newValue)
                .build());

        gameValueDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.gameValueDecoratorColor)
                .setSaveConsumer(newValue -> config.gameValueDecoratorColor = newValue)
                .build()
        );

        gameValueDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.gameValueDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.gameValueDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(gameValueDecorator.build());

        SubCategoryBuilder particleDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.particle"))
                .setExpanded(false);

        particleDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isParticleDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isParticleDecoratorEnabled = newValue)
                .build());

        particleDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.particleDecoratorColor)
                .setSaveConsumer(newValue -> config.particleDecoratorColor = newValue)
                .build()
        );

        particleDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.particleDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.particleDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(particleDecorator.build());

        SubCategoryBuilder textComponent = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.text_component"))
                .setExpanded(false);

        textComponent.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isTextComponentDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isTextComponentDecoratorEnabled = newValue)
                .build());

        textComponent.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.textComponentDecoratorColor)
                .setSaveConsumer(newValue -> config.textComponentDecoratorColor = newValue)
                .build()
        );

        textComponent.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.textComponentDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.textComponentDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(textComponent.build());

        SubCategoryBuilder vectorDecorator = builder
                .startSubCategory(Text.translatable("text.mlmod_fork.decorators.vector"))
                .setExpanded(false);

        vectorDecorator.add(builder.startBooleanToggle(Text.translatable("text.mlmod_fork.decorators.any.enabled"), config.isVectorDecoratorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.isVectorDecoratorEnabled = newValue)
                .build());

        vectorDecorator.add(decorators.build(builder, Text.translatable("text.mlmod_fork.decorators.any.color"), config.vectorDecoratorColor)
                .setSaveConsumer(newValue -> config.vectorDecoratorColor = newValue)
                .build()
        );

        vectorDecorator.add(builder.startIntField(Text.translatable("text.mlmod_fork.decorators.any.char_limit"), config.vectorDecoratorCharLimit)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.vectorDecoratorCharLimit = newValue)
                .build());

        varDecorators.add(vectorDecorator.build());
        return varDecorators;
    }

}
