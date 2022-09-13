package com.stal111.forbidden_arcanus.client;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.source.CosmeticsSource;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author stal111
 * @since 2022-07-31
 */
public class FACosmeticsSource extends CosmeticsSource {

    private static final String URL = "https://raw.githubusercontent.com/stal111/Mandala-Supporters/main/supporters.json";

    private static final Map<UUID, PatreonLevel> PATREON_LEVEL_MAP = new HashMap<>();

    public FACosmeticsSource(String name) {
        super(name);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            InputStream stream = connection.getInputStream();

            if (connection.getResponseCode() != 200) {
                stream.close();

                if (connection.getErrorStream() != null) {
                    connection.getErrorStream().close();
                }
            } else {
                JsonObject jsonObject = GsonHelper.parse(new InputStreamReader(stream));

                for (String key : jsonObject.keySet()) {
                    PatreonLevel level = PatreonLevel.valueOf(key.toUpperCase(Locale.ROOT));

                    jsonObject.getAsJsonArray(key).forEach(jsonElement -> {
                        PATREON_LEVEL_MAP.put(UUID.fromString(jsonElement.getAsString().replaceFirst(
                                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                        )), level);
                    });
                }
            }
        } catch (IOException e) {
            // Either player is offline or hasn't bought any cosmetics.
        }
    }

    @Override
    public List<CosmeticKey> loadCosmeticsFor(UUID uuid) {
        if (!PATREON_LEVEL_MAP.containsKey(uuid)) {
            return List.of();
        }

        return PATREON_LEVEL_MAP.get(uuid).getCosmetics().stream().map(s -> new CosmeticKey(this, s)).toList();
    }

    @Override
    public void loadTextures(CosmeticKey key) {
        this.registerMainTexture(key, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/cosmetic/" + key.name() + ".png"));
    }

    private enum PatreonLevel implements StringRepresentable {
        SILVER_BLACKSMITH("silver_blacksmith"),
        GOLDEN_FIGHTER("golden_fighter", PatreonLevel.DRACO_AURUM_HEAD),
        ARCANE_SORCERER("arcane_sorcerer", PatreonLevel.DRACO_AURUM_HEAD, PatreonLevel.DRACO_AURUM_WINGS),
        NEBULA_CLUB("nebula_club", PatreonLevel.DRACO_AURUM_HEAD, PatreonLevel.DRACO_AURUM_WINGS);

        private static final String DRACO_AURUM_HEAD = "draco_aurum_head";
        private static final String DRACO_AURUM_WINGS = "draco_aurum_wings";

        private final String name;
        private final List<String> cosmetics;

        PatreonLevel(String name, String... cosmetics) {
            this.name = name;
            this.cosmetics = List.of(cosmetics);
        }

        @Nonnull
        @Override
        public String getSerializedName() {
            return this.name;
        }

        public List<String> getCosmetics() {
            return this.cosmetics;
        }
    }
}
