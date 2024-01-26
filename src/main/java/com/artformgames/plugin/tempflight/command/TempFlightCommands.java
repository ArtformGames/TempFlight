package com.artformgames.plugin.tempflight.command;

import com.artformgames.core.ArtCore;
import com.artformgames.core.utils.TimeStringUtils;
import com.artformgames.plugin.tempflight.Main;
import com.artformgames.plugin.tempflight.conf.PluginMessages;
import com.artformgames.plugin.tempflight.manager.FlightManager;
import com.artformgames.plugin.tempflight.user.FlightAccount;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;


@Command(name = "tempflight", aliases = {"tempfly"})
@Description("TempFlight commands")
@Permission("tempflight.admin")
public class TempFlightCommands {

    @Execute(name = "start")
    @Description("Start a temporary flight for a player")
    void start(@Context CommandSender sender, @Arg Player target, @Arg String duration, @OptionalArg Boolean back) {
        long time = TimeStringUtils.toMilliSecPlus(duration);
        if (time < 0) {
            PluginMessages.COMMANDS.WRONG_TIME_FORMAT.send(sender);
            return;
        }

        FlightAccount data = ArtCore.getHandler(target, FlightAccount.class);
        if (data.isTempFlying()) {
            PluginMessages.COMMANDS.ALREADY_ENABLED.send(sender, target.getName());
            PluginMessages.FLYING.send(target, data.getRemainMillis() / 1000);
            return;
        }

        FlightManager flightManager = Main.getFlightManager();
        long cooldown = flightManager.getCooldownMillis(target);
        if (cooldown > 0) {
            PluginMessages.COMMANDS.COOLING.send(sender, target.getName(), cooldown / 1000);
            PluginMessages.COOLING.send(target, cooldown / 1000);
            return;
        }

        if (flightManager.startFly(target, Duration.ofMillis(time), Boolean.TRUE.equals(back))) {
            PluginMessages.COMMANDS.ENABLED.send(sender, target.getName(), (time / 1000));
            PluginMessages.ENABLED.send(target, time / 1000);
            if (Boolean.TRUE.equals(back)) PluginMessages.WILL_BACK.send(target);
        } else {
            PluginMessages.COMMANDS.ALREADY_ENABLED.send(sender, target.getName());
        }

    }

    @Execute(name = "stop", aliases = "shut")
    @Description("Stop temporary flight for a player.")
    void stop(@Context CommandSender sender, @Arg Player target) {
        FlightManager flightManager = Main.getFlightManager();
        FlightAccount data = ArtCore.getHandler(target, FlightAccount.class);
        if (!data.isTempFlying() || !flightManager.endFly(target)) {
            PluginMessages.COMMANDS.ALREADY_DISABLED.send(sender, target.getName());
            return;
        }
        PluginMessages.COMMANDS.DISABLED.send(sender, target.getName());
        PluginMessages.DISABLED.send(target);
    }

}
