package com.shoplab.eval.application.port.in;

/** What the request of the comparison says, as it came: the use case checks it. {@code drop} is optional. */
public record CompareEvalRunsCommand(String base, String target, String metric, String drop) {
}
