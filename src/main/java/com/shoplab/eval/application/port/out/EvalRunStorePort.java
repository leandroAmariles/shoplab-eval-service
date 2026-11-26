package com.shoplab.eval.application.port.out;

import com.shoplab.eval.domain.model.EvalRun;
import com.shoplab.eval.domain.model.SavedRun;
import com.shoplab.eval.domain.model.StoredRunSummary;
import java.util.Optional;
import reactor.core.publisher.Mono;

/** The history of evaluation runs. A run is saved whole in one transaction. */
public interface EvalRunStorePort {

    Mono<SavedRun> save(EvalRun run);

    Mono<Optional<StoredRunSummary>> find(String runId);
}
