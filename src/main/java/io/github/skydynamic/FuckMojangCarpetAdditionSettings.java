package io.github.skydynamic;

import carpet.api.settings.Rule;

import static io.github.skydynamic.FmcaRuleCategory.FMCA_CHUNK_LOADER;
import static io.github.skydynamic.FmcaRuleCategory.FMCA_FEATURE;
import static io.github.skydynamic.FmcaRuleCategory.FMCA_LC;

public class FuckMojangCarpetAdditionSettings {
    public static final String FMCA = "fmca";

    @Rule(
        options = {"bone_block", "all", "OFF"},
        categories = {FMCA_FEATURE, FMCA}
    )
    public static String endGatewayDoNotAddLoadTicket = "OFF";

    @Rule(
        categories = {FMCA_FEATURE, FMCA}
    )
    public static boolean fixExtendedPistonDeleteFrontBlock = false;

    @Rule(
        categories = {FMCA_FEATURE, FMCA}
    )
    public static boolean scheduledRandomTickCactus = false;

    @Rule(
        options = {"bone_block", "wither_skeleton_skull", "note_block", "OFF"},
        categories = {FMCA_FEATURE, FMCA_CHUNK_LOADER, FMCA}
    )
    public static String noteBlockChunkLoader = "OFF";

    @Rule(
        options = {"bone_block", "bedrock", "all", "OFF"},
        categories = {FMCA_FEATURE, FMCA_CHUNK_LOADER, FMCA}
    )
    public static String pistonBlockChunkLoader = "OFF";

    @Rule(
        categories = {FMCA_FEATURE, FMCA}
    )
    public static boolean softDeepslate = false;

    @Rule(
        categories = {FMCA_FEATURE, FMCA}
    )
    public static boolean softObsidian = false;

    @Rule(
        categories = {FMCA_FEATURE, FMCA}
    )
    public static boolean enderPearlChunkLoader = false;
}
