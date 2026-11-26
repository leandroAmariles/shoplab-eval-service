package com.shoplab.eval.application.port.out;

import com.shoplab.eval.domain.model.AssistantSettingsSnapshot;
import reactor.core.publisher.Mono;

/** Where the module learns what the assistant uses now. */
public interface AssistantSettingsPort {

    Mono<AssistantSettingsSnapshot> current();
}
