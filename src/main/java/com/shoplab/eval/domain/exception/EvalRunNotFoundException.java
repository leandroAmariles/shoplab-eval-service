package com.shoplab.eval.domain.exception;

import com.shoplab.eval.shared.exception.BusinessException;
import com.shoplab.eval.shared.exception.ResponseCodeEnum;

/** There is no saved run with that identifier. */
public class EvalRunNotFoundException extends BusinessException {

    public EvalRunNotFoundException() {
        super(ResponseCodeEnum.EVAL_RUN_NOT_FOUND);
    }
}
