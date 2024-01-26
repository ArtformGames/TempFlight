package com.artformgames.plugin.tempflight.conf;

import cc.carm.lib.mineconfiguration.bukkit.value.ConfiguredMessageList;
import com.artformgames.core.conf.Messages;
import net.md_5.bungee.api.chat.BaseComponent;

public interface PluginMessages extends Messages {

    interface COMMANDS extends Messages {

        ConfiguredMessageList<BaseComponent[]> WRONG_TIME_FORMAT = Messages.list()
                .defaults("&c&lWrong format! &fPlease use &e&o1h,2m,3s &f.")
                .build();

        ConfiguredMessageList<BaseComponent[]> ALREADY_ENABLED = Messages.list()
                .defaults("&fThe player &6%(player) &fis already enabled flying.")
                .params("player")
                .build();

        ConfiguredMessageList<BaseComponent[]> ALREADY_DISABLED = Messages.list()
                .defaults("&fThe player &6%(player) &fis not enabled flying.")
                .params("player")
                .build();

        ConfiguredMessageList<BaseComponent[]> COOLING = Messages.list()
                .defaults(
                        "&fThe player &6%(player) &fneed to wait for &e%(time) &fseconds before he can use the timed flight again."
                ).params("player", "time").build();

        ConfiguredMessageList<BaseComponent[]> ENABLED = Messages.list()
                .defaults("&fSuccessfully enabled temp flight for &6%(player) &fwith &e%(time) &fseconds.")
                .params("player", "time")
                .build();
        ConfiguredMessageList<BaseComponent[]> DISABLED = Messages.list()
                .defaults("&fSuccessfully disabled temp flight for &6%(player) &f.")
                .params("player")
                .build();

    }

    ConfiguredMessageList<BaseComponent[]> COOLING = Messages.list()
            .defaults("&fYou need to wait for &e%(time) &fseconds before you can use the timed flight again.")
            .params("time")
            .build();

    ConfiguredMessageList<BaseComponent[]> FLYING = Messages.list()
            .defaults("&fYou have enabled time-limited flying, and the remaining flight time is &e%(time) &fseconds.")
            .params("time")
            .build();

    ConfiguredMessageList<BaseComponent[]> ENABLED = Messages.list()
            .defaults("&fYou are &a&lEnabled &f for timed flights, and from now on you can fly &e%(time) &fseconds.")
            .params("time")
            .build();

    ConfiguredMessageList<BaseComponent[]> DISABLED = Messages.list()
            .defaults("&fYour timed flight has ended and your flight status has been automatically turned off.")
            .build();

    ConfiguredMessageList<BaseComponent[]> MODE_CHANGE = Messages.list()
            .defaults("&fYou've switched to &eCreative Mode&f, so there's no longer a time limit for flying.")
            .build();

    ConfiguredMessageList<BaseComponent[]> WILL_BACK = Messages.list()
            .defaults("&7Note: Will teleport back to your start location after the flight is over!")
            .build();


}
