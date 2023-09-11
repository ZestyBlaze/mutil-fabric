package se.mickelus.mutil;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class ConfigHandler {
    public static Client client;
    static ForgeConfigSpec clientSpec;

    public static void setup() {
        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            setupClient();
            ForgeConfigRegistry.INSTANCE.register(MUtilMod.MOD_ID, ModConfig.Type.CLIENT, clientSpec);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void setupClient() {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        client = specPair.getLeft();
    }

    @Environment(EnvType.CLIENT)
    public static class Client {
        public ForgeConfigSpec.BooleanValue queryPerks;

        Client(ForgeConfigSpec.Builder builder) {
            queryPerks = builder
                    .comment("Controls if perks data should be queried on startup")
                    .define("query_perks", true);
        }
    }
}
