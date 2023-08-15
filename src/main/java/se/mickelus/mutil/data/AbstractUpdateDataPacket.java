package se.mickelus.mutil.data;

import com.google.gson.JsonElement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.AbstractPacket;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public abstract class AbstractUpdateDataPacket extends AbstractPacket {
    protected String directory;
    protected Map<ResourceLocation, String> data;

    public AbstractUpdateDataPacket() {}

    public AbstractUpdateDataPacket(String directory, Map<ResourceLocation, JsonElement> data) {
        this.directory = directory;
        this.data = data.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().toString()
                ));
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(directory);
        buffer.writeInt(data.size());
        data.forEach((resourceLocation, data) -> {
            buffer.writeResourceLocation(resourceLocation);
            buffer.writeUtf(data);
        });
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        directory = buffer.readUtf();
        int count = buffer.readInt();
        data = new HashMap<>();
        for (int i = 0; i < count; i++) {
            data.put(buffer.readResourceLocation(), buffer.readUtf());
        }
    }

    @Override
    public abstract void handle(Player player);
}
