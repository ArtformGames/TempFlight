package com.artformgames.plugin.tempflight.command;

import com.artformgames.core.ArtCore;
import com.artformgames.plugin.tempflight.Main;
import com.artformgames.plugin.tempflight.conf.PluginMessages;
import com.artformgames.plugin.tempflight.manager.FlightManager;
import com.artformgames.plugin.tempflight.user.FlightAccount;
import com.artformgames.plugin.tempflight.utils.TimeUtils;
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
import java.util.Optional;


@Command(name = "tempflight", aliases = {"tempfly"})
@Description("TempFlight commands")
@Permission("tempflight.admin")
public class TempFlightCommands {

    @Execute(name = "start")
    void start(@Context CommandSender sender,
               @Arg Player target, @Arg String duration, @OptionalArg Boolean back) {
        back = Optional.ofNullable(back).orElse(false);
        FlightAccount data = ArtCore.getHandler(target, FlightAccount.class);
        if (data.isTempFlying()) {
            PluginMessages.COMMANDS.ALREADY_FLYING.send(sender, target.getName());
            PluginMessages.FLYING.send(target, data.getRemainMillis() / 1000);
            return;
        }

        FlightManager manager = Main.getFlightManager();
        long cooldown = manager.getCooldownMillis(target);
        if (cooldown > 0) {
            PluginMessages.COMMANDS.COOLING.send(sender, target.getName(), cooldown / 1000);
            PluginMessages.COOLING.send(target, cooldown / 1000);
            return;
        }

        Duration time = TimeUtils.parseDuration(duration);
        if (time.isZero() || time.isNegative()) {
            PluginMessages.COMMANDS.TIME_USAGE.send(sender);
            return;
        }

        if (manager.startFly(target, time, back)) {
            PluginMessages.COMMANDS.FLY_ENABLED.send(sender, target.getName(), time.toSeconds());
            PluginMessages.ENABLED.send(target, duration);
            if (back) PluginMessages.WILL_BACK.send(target);
        } else {
            PluginMessages.COMMANDS.ALREADY_FLYING.send(sender, target.getName());
        }
    }

    @Execute(name = "stop")
    void stop(@Context CommandSender sender, @Arg Player player) {
        FlightManager manager = Main.getFlightManager();
        if (manager.endFly(player)) {
            PluginMessages.COMMANDS.FLY_DISABLED.send(sender, player.getName());
            PluginMessages.DISABLED.send(player);
        } else {
            PluginMessages.COMMANDS.NOT_FLYING.send(sender, player.getName());
        }
    }

}
