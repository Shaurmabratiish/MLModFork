package noobsdev.mlmod_fork.integrations.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import noobsdev.mlmod_fork.integrations.config.ConfigScreenProvider;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreenProvider::createConfigScreen;
    }

}