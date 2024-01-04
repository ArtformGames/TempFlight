package com.artformgames.plugin.tempflight.conf;

import cc.carm.lib.configuration.core.Configuration;
import cc.carm.lib.mineconfiguration.bukkit.builder.message.CraftMessageListBuilder;
import cc.carm.lib.mineconfiguration.bukkit.builder.message.CraftMessageValueBuilder;
import cc.carm.lib.mineconfiguration.bukkit.value.ConfiguredMessage;
import cc.carm.lib.mineconfiguration.bukkit.value.ConfiguredMessageList;
import de.themoep.minedown.MineDown;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public interface PluginMessages extends Configuration {
    static @NotNull CraftMessageListBuilder<BaseComponent[]> list() {
        return ConfiguredMessageList.create(getParser())
                .whenSend((sender, message) -> message.forEach(m -> sender.spigot().sendMessage(m)));
    }

    static @NotNull CraftMessageValueBuilder<BaseComponent[]> value() {
        return ConfiguredMessage.create(getParser())
                .whenSend((sender, message) -> sender.spigot().sendMessage(message));
    }

    private static @NotNull BiFunction<CommandSender, String, BaseComponent[]> getParser() {
        return (sender, message) -> {
            if (sender == null) return MineDown.parse(message);
            if (sender instanceof Player player) {
                return MineDown.parse(PlaceholderAPI.setPlaceholders(player, message));
            } else {
                return MineDown.parse(message);
            }
        };
    }

    ConfiguredMessageList<BaseComponent[]> COOLING = list()
            .defaults("&fYou need to wait for &e%(time) &fseconds before you can use the timed flight again.")
            .params("time")
            .build();

    ConfiguredMessageList<BaseComponent[]> FLYING = list()
            .defaults("&fYou have enabled time-limited flying, and the remaining flight time is &e%(time) &fseconds.")
            .params("time")
            .build();

    ConfiguredMessageList<BaseComponent[]> ENABLED = list()
            .defaults("&fYou &a&l&Enabled &f for timed flights, and from now on you can fly &e%(time) &fseconds.")
            .params("time")
            .build();

    ConfiguredMessageList<BaseComponent[]> DISABLED = list()
            .defaults("&fYour timed flight has ended and your flight status has been automatically turned off.")
            .build();

    ConfiguredMessageList<BaseComponent[]> MODE_CHANGE = list()
            .defaults("&fYou've switched to &eCreative Mode&f, so there's no longer a time limit for flying.")
            .build();

    ConfiguredMessageList<BaseComponent[]> WILL_BACK = list()
            .defaults("&7Note: Teleport back to your current location after the flight is over!")
            .build();


}
