package com.artformgames.plugin.tempflight.listener;

import com.artformgames.core.ArtCore;
import com.artformgames.plugin.tempflight.conf.PluginMessages;
import com.artformgames.plugin.tempflight.user.FlightAccount;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class TempFlyListener implements Listener {

    @EventHandler
    public void onModeChange(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() != GameMode.CREATIVE) return;

        FlightAccount data = ArtCore.getHandler(event.getPlayer(), FlightAccount.class);

        if (data.isTempFlying()) {
            PluginMessages.MODE_CHANGE.send(event.getPlayer());
            data.reset();
        }

    }

}
