package se.mickelus.mutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import se.mickelus.mutil.scheduling.ClientScheduler;
import se.mickelus.mutil.scheduling.ServerScheduler;

public class MUtilMod implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "mutil";

	@Override
	public void onInitialize() {
		ServerScheduler.INSTANCE.onServerTick();
	}

	@Override
	public void onInitializeClient() {
		ConfigHandler.setup();
		Perks.init(Minecraft.getInstance().getUser().getUuid());
		ClientScheduler.INSTANCE.onClientTick();
	}
}