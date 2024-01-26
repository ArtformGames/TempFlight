package com.artformgames.plugin.tempflight.manager;

import com.artformgames.core.ArtCore;
import com.artformgames.plugin.tempflight.Main;
import com.artformgames.plugin.tempflight.conf.PluginConfig;
import com.artformgames.plugin.tempflight.conf.PluginMessages;
import com.artformgames.plugin.tempflight.user.FlightAccount;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FlightManager {

    /**
     * Used to record the last time the user enabled an ad-hoc flight
     * The UUID is the user's UUID, and the time is the timestamp when the user theoretically ended the flight
     */
    protected final @NotNull Map<UUID, Long> cooldownTime = new HashMap<>();
    public static @NotNull BukkitRunnable repeatRunnable = new BukkitRunnable() {
        @Override
        public void run() {
            FlightManager flightManager = Main.getFlightManager();
            Bukkit.getOnlinePlayers().forEach(flightManager::checkFlight);
            Main.getFlightManager().purgeCooldownData();
        }
    };


    public FlightManager(Main main) {
        ArtCore.getUserManager().registerHandler(main, FlightAccount.class, FlightAccount::new, FlightAccount::new);
        repeatRunnable.runTaskTimerAsynchronously(Main.getInstance(), 100L, 20L);
    }

    public void shutdown() {
        if (!repeatRunnable.isCancelled()) repeatRunnable.cancel();
    }

    protected static FlightAccount getAccount(@NotNull Player player) {
        return ArtCore.getHandler(player, FlightAccount.class);
    }

    public boolean startFly(@NotNull Player player, @NotNull Duration duration, boolean teleportBack) {
        FlightAccount account = getAccount(player);
        if (account.isTempFlying()) return false;

        long end = account.start(duration, teleportBack ? player.getLocation() : null);
        long cooldown = PluginConfig.COOLDOWN.entrySet().stream()
                .filter(e -> player.hasPermission(e.getValue()))
                .mapToLong(Map.Entry::getKey).min().orElse(-1);
        if (cooldown > 0) {
            cooldownTime.put(player.getUniqueId(), end + cooldown);
        }

        player.setAllowFlight(true);
        player.setFlying(true);
        return true;
    }

    public boolean endFly(@NotNull Player player) {
        FlightAccount account = getAccount(player);
        if (!account.isTempFlying()) return false;

        Optional.ofNullable(account.getStartLocation()).ifPresent(player::teleport);
        account.reset();

        long cooldown = PluginConfig.COOLDOWN.entrySet().stream()
                .filter(e -> player.hasPermission(e.getValue()))
                .mapToLong(Map.Entry::getKey).min().orElse(-1);
        if (cooldown > 0) {
            cooldownTime.put(player.getUniqueId(), System.currentTimeMillis() + cooldown);
        }

        if (player.getAllowFlight()) {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setFallDistance(-500L); //防止摔死
            return true;
        }

        return false;
    }

    public void checkFlight(@NotNull Player player) {
        FlightAccount account = getAccount(player);
        if (!account.isTempFlying()) return;

        if (account.getRemainMillis() <= 0) {
            PluginMessages.DISABLED.send(player);
            Main.getInstance().getScheduler().run(() -> endFly(player));
        }
    }

    public boolean isCoolingDown(@NotNull Player player) {
        return getCooldownMillis(player) > 0;
    }

    public long getCooldownMillis(@NotNull Player player) {
        Long time = cooldownTime.get(player.getUniqueId());
        if (time == null) return 0;

        return time - System.currentTimeMillis();
    }

    public void purgeCooldownData() {
        long v = System.currentTimeMillis();
        cooldownTime.entrySet().removeIf(entry -> v > entry.getValue());
    }

}
