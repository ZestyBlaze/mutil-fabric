package se.mickelus.mutil.scheduling;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ClientScheduler extends AbstractScheduler {
    public static final ClientScheduler INSTANCE = new ClientScheduler();

    public void onClientTick() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> this.tick());
    }
}
