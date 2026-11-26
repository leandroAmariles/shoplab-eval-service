package com.shoplab.eval.domain.model;

import com.shoplab.eval.domain.model.enums.RunKind;
import java.time.Instant;

/** The few fields that tell the two runs of a comparison apart. */
public record RunHeader(String runId, RunKind kind, Instant startedAt, String origin) {
}
