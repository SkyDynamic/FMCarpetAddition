package io.github.skydynamic;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import io.github.skydynamic.utils.FmcaTranslations;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FuckMojangCarpetAddition implements CarpetExtension, ModInitializer {
	@Getter
    public static final Logger LOGGER = LoggerFactory.getLogger(FuckMojangCarpetAddition.class);
	@Getter
	public static final String MOD_NAME = "FuckMojangCarpetAddition";
	@Getter
	public static final String MOD_ID = "fmca";

	@Override
	public String version() {
		return FuckMojangCarpetAddition.getMOD_NAME();
	}

	public static void loadExtension() {
		CarpetServer.manageExtension(new FuckMojangCarpetAddition());
	}

	@Override
	public void onInitialize() {
		FuckMojangCarpetAddition.loadExtension();
	}

	@Override
	public void onGameStarted()
	{
		CarpetServer.settingsManager.parseSettingsClass(FuckMojangCarpetAdditionSettings.class);
	}

	@Override
	public Map<String, String> canHasTranslations(String lang)
	{
		return FmcaTranslations.getTranslationFromResourcePath(lang);
	}
}