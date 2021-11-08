package dev.panda.combofly.utilities.hooks;

import dev.panda.ability.PandaAbilityAPI;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

/**
 * Created by Risas
 * Project: PandaFly
 * Date: 08-11-2021 - 13:37
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class PandaAbilityHook {

    @Getter @Setter
    private boolean pandaAbility;

    @Getter @Setter
    private PandaAbilityAPI pandaAbilityAPI;


    public void init() {
        setPandaAbility(true);
        setPandaAbilityAPI(new PandaAbilityAPI());
    }
}
