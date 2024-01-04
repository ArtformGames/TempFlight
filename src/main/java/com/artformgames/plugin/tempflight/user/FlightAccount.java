package com.artformgames.plugin.tempflight.user;

import com.artformgames.core.user.User;
import com.artformgames.core.user.handler.AbstractUserHandler;
import com.artformgames.core.user.handler.UserHandler;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

public class FlightAccount extends AbstractUserHandler implements UserHandler {

    protected @Nullable Long endMillis;
    protected @Nullable Location startLocation;

    public FlightAccount(User user) {
        super(user);
    }

    public long start(Duration duration, @Nullable Location startLocation) {
        this.endMillis = System.currentTimeMillis() + duration.toMillis();
        this.startLocation = startLocation;
        return this.endMillis;
    }

    public void reset() {
        this.endMillis = null;
        this.startLocation = null;
    }

    public @Nullable Location getStartLocation() {
        return startLocation;
    }

    public boolean isTempFlying() {
        return this.endMillis != null;
    }

    public long getRemainMillis() {
        if (this.endMillis == null) return 0;
        else return (this.endMillis) - System.currentTimeMillis();
    }


}
