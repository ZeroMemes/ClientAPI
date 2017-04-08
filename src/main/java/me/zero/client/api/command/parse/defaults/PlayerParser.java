package me.zero.client.api.command.parse.defaults;

import me.zero.client.api.command.parse.ArgumentParser;
import net.minecraft.client.network.NetworkPlayerInfo;

/**
 * Gets the NetworkPlayerInfo from a player name
 *
 * @since
 *
 * Created by Brady on 2/13/2017.
 */
public class PlayerParser implements ArgumentParser<NetworkPlayerInfo> {

    @Override
    public NetworkPlayerInfo apply(String t) {
        return mc.player.connection.getPlayerInfoMap().stream()
                .filter(info -> info.getGameProfile().getName().equalsIgnoreCase(t))
                .findFirst().orElse(null);
    }
}
