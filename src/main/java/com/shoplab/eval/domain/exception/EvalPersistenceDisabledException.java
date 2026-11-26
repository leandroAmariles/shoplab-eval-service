package com.shoplab.eval.domain.exception;

import com.shoplab.eval.shared.exception.BusinessException;
import com.shoplab.eval.shared.exception.ResponseCodeEnum;

/** Saving evaluation runs is off (no token configured) or the request does not carry the right token. */
public class EvalPersistenceDisabledException extends BusinessException {

    public EvalPersistenceDisabledException() {
        super(ResponseCodeEnum.EVAL_PERSISTENCE_DISABLED);
    }
}
