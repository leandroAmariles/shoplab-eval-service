package com.shoplab.eval.application.port.in;

import com.shoplab.eval.domain.model.EvalRun;
import com.shoplab.eval.domain.model.SavedRun;
import reactor.core.publisher.Mono;

/** Saves a whole evaluation run, or nothing. */
public interface SaveEvalRunServicePort {

    Mono<SavedRun> save(String token, EvalRun run);
}
