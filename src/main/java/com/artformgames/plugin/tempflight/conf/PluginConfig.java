package com.artformgames.plugin.tempflight.conf;

import cc.carm.lib.configuration.core.Configuration;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.impl.ConfigValueMap;
import cc.carm.lib.configuration.core.value.type.ConfiguredMap;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;

public interface PluginConfig extends Configuration {

    ConfiguredValue<Boolean> DEBUG = ConfiguredValue.of(Boolean.class, false);

    @HeaderComment({
            "Statistics Settings",
            "This option is used to help developers count plug-in versions and usage, and it will never affect performance and user experience.",
            "Of course, you can also choose to turn it off here for this plugin,",
            "or turn it off for all plugins in the configuration file under \"plugins/bStats\"."
    })
    ConfiguredValue<Boolean> METRICS = ConfiguredValue.of(Boolean.class, true);

    @HeaderComment({
            "Check update settings",
            "This option is used by the plug-in to determine whether to check for updates.",
            "If you do not want the plug-in to check for updates and prompt you, you can choose to close.",
            "Checking for updates is an asynchronous operation that will never affect performance and user experience."
    })
    ConfiguredValue<Boolean> CHECK_UPDATE = ConfiguredValue.of(Boolean.class, true);

    @HeaderComment({
            "The cooldown time for the player to use the flight command of each permissions",
            "format -> millisecond: permission"
    })
    ConfiguredMap<Long, String> COOLDOWN = ConfigValueMap.builderOf(Long.class, String.class)
            .asLinkedMap().fromString().parseKey(Long::parseUnsignedLong).parseValue(Object::toString)
            .defaults(m -> {
                m.put(60000L, "tempflight.use");
                m.put(30000L, "group.vip");
            }).build();


}
