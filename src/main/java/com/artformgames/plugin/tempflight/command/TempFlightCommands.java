package com.artformgames.plugin.tempflight.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;


@Command(name = "tempflight", aliases = {"tempfly"})
@Description("TempFlight commands")
@Permission("tempflight.admin")
public class TempFlightCommands {

    void start(@Arg Player player, @Arg String duration, @OptionalArg Boolean back) {

    }

    void stop(@Arg Player player) {

    }

}
