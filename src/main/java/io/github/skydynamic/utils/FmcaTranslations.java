package io.github.skydynamic.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

/**
 * Reference to <a href="https://github.com/gnembon/carpet-extra/blob/master/src/main/java/carpetextra/utils/CarpetExtraTranslations.java">CarpetExtra</a>
 */
public class FmcaTranslations {
    public static Map<String, String> getTranslationFromResourcePath(String lang) {
        InputStream langFile =
            FmcaTranslations.class
                            .getClassLoader()
                            .getResourceAsStream("assets/fmca/lang/%s.json".formatted(lang));
        if (langFile == null) {
            return Collections.emptyMap();
        }
        String jsonData;
        try {
            jsonData = IOUtils.toString(langFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        Gson gson = new GsonBuilder().setLenient().create();
        return gson.fromJson(jsonData, new TypeToken<Map<String, String>>() {}.getType());
    }
}
