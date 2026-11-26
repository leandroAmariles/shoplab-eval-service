package com.shoplab.eval.domain.exception;

import com.shoplab.eval.shared.exception.BusinessException;
import com.shoplab.eval.shared.exception.ResponseCodeEnum;

/** Two runs are asked to be compared but they are not of the same kind: their metrics are not the same. */
public class EvalRunsNotComparableException extends BusinessException {

    public EvalRunsNotComparableException() {
        super(ResponseCodeEnum.EVAL_RUNS_NOT_COMPARABLE);
    }
}
