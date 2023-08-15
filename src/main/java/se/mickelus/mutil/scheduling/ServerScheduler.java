package se.mickelus.mutil.scheduling;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ServerScheduler extends AbstractScheduler {
    public static final ServerScheduler INSTANCE = new ServerScheduler();

    public void onServerTick() {
        ServerTickEvents.END_SERVER_TICK.register(server -> this.tick());
    }
}
