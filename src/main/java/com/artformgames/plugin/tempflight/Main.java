package com.artformgames.plugin.tempflight;

import cc.carm.lib.easyplugin.EasyPlugin;
import cc.carm.lib.easyplugin.i18n.EasyPluginMessageProvider;
import cc.carm.lib.mineconfiguration.bukkit.MineConfiguration;
import com.artformgames.core.ArtCore;
import com.artformgames.core.utils.GHUpdateChecker;
import com.artformgames.plugin.tempflight.command.TempFlightCommands;
import com.artformgames.plugin.tempflight.conf.PluginConfig;
import com.artformgames.plugin.tempflight.conf.PluginMessages;
import com.artformgames.plugin.tempflight.listener.TempFlyListener;
import com.artformgames.plugin.tempflight.manager.FlightManager;
import dev.rollczi.litecommands.LiteCommands;
import org.bstats.bukkit.Metrics;

public class Main extends EasyPlugin {

    private static Main instance;

    public Main() {
        super(EasyPluginMessageProvider.EN_US);
        Main.instance = this;
    }

    protected MineConfiguration configuration;
    protected LiteCommands<?> commands;
    protected FlightManager flightManager;


    @Override
    protected void load() {
        log("Loading plugin configurations...");
        configuration = new MineConfiguration(this);
        configuration.initializeConfig(PluginConfig.class);
        configuration.initializeMessage(PluginMessages.class);
    }

    @Override
    protected boolean initialize() {

        log("Initialize flight manager...");
        this.flightManager = new FlightManager(this);

        log("Register listeners...");
        registerListener(new TempFlyListener());

        log("Register commands...");
        this.commands = ArtCore.createCommand().commands(new TempFlightCommands()).build();

        if (PluginConfig.METRICS.getNotNull()) {
            log("Initializing bStats...");
            new Metrics(this, 20647);
        }

        if (PluginConfig.CHECK_UPDATE.getNotNull()) {
            log("Start to check the plugin versions...");
            getScheduler().runAsync(GHUpdateChecker.runner(this));
        } else {
            log("Version checker is disabled, skipped.");
        }

        return true;
    }

    @Override
    protected void shutdown() {

        log("Shutting down...");
        getFlightManager().shutdown();

    }

    @Override
    public boolean isDebugging() {
        return PluginConfig.DEBUG.getNotNull();
    }

    public static void info(String... messages) {
        getInstance().log(messages);
    }

    public static void severe(String... messages) {
        getInstance().error(messages);
    }

    public static void debugging(String... messages) {
        getInstance().debug(messages);
    }

    public static Main getInstance() {
        return instance;
    }

    public static FlightManager getFlightManager() {
        return getInstance().flightManager;
    }

}
