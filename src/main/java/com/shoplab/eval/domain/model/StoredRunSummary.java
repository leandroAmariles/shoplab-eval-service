package com.shoplab.eval.domain.model;

import com.shoplab.eval.domain.model.enums.RunKind;
import java.time.Instant;

/** A run that is already in the history. */
public record StoredRunSummary(String runId, RunKind kind, Instant startedAt, Instant recordedAt) {
}
