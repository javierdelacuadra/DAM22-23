package domain.utils;

import domain.utils.common.ConstantesUtils;

public class MessageGenerator {
    public String generateActivationMessage(String activationCode) {
        return ConstantesUtils.PARA_ACTIVAR_SU_CUENTA_HAGA_CLICK_EN_EL_SIGUIENTE_ENLACE + ConstantesUtils.ACTIVATION_URL + activationCode;
    }

    public String generatePasswordRecoveryMessage(String activationCode) {
        return ConstantesUtils.PARA_RECUPERAR_SU_PASSWORD_HAGA_CLICK_EN_EL_SIGUIENTE_ENLACE + ConstantesUtils.PASSWORD_RECOVERY_URL + activationCode;
    }
}