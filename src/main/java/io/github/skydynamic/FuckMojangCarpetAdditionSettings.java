package io.github.skydynamic;

import carpet.api.settings.Rule;

import static io.github.skydynamic.FmcaRuleCategory.FMCA_FEATURE;

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
}
