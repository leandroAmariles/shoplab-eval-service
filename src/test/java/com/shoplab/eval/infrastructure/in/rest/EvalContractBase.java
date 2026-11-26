package com.shoplab.eval.infrastructure.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import com.shoplab.eval.application.port.in.CompareEvalRunsServicePort;
import com.shoplab.eval.application.port.in.FindEvalRunServicePort;
import com.shoplab.eval.application.port.in.GetEvalRunServicePort;
import com.shoplab.eval.application.port.in.ListEvalRunsServicePort;
import com.shoplab.eval.application.port.in.ReadAssistantSettingsServicePort;
import com.shoplab.eval.application.port.in.SaveEvalRunServicePort;
import com.shoplab.eval.application.usecase.TraceUrlBuilder;
import com.shoplab.eval.domain.exception.EvalRunNotFoundException;
import com.shoplab.eval.domain.model.AssistantSettingsSnapshot;
import com.shoplab.eval.domain.model.MetricStat;
import com.shoplab.eval.domain.model.OriginCount;
import com.shoplab.eval.domain.model.PromptVersion;
import com.shoplab.eval.domain.model.RetrievalConfig;
import com.shoplab.eval.domain.model.RunListItem;
import com.shoplab.eval.domain.model.RunListing;
import com.shoplab.eval.domain.model.SavedRun;
import com.shoplab.eval.domain.model.StoredRunSummary;
import com.shoplab.eval.domain.model.enums.PromptSource;
import com.shoplab.eval.domain.model.enums.RunKind;
import com.shoplab.eval.shared.infrastructure.in.rest.handler.GlobalExceptionHandler;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/** Base class for Spring Cloud Contract producer verification of {@code eval-service} HTTP endpoints. */
public abstract class EvalContractBase {

    private static final String TOKEN = "contract-token";

    @BeforeEach
    void setup() {
        SaveEvalRunServicePort savePort = mock(SaveEvalRunServicePort.class);
        FindEvalRunServicePort findPort = mock(FindEvalRunServicePort.class);
        ReadAssistantSettingsServicePort settingsPort = mock(ReadAssistantSettingsServicePort.class);
        ListEvalRunsServicePort listPort = mock(ListEvalRunsServicePort.class);
        GetEvalRunServicePort getPort = mock(GetEvalRunServicePort.class);
        CompareEvalRunsServicePort comparePort = mock(CompareEvalRunsServicePort.class);
        TraceUrlBuilder traceBuilder = mock(TraceUrlBuilder.class);

        lenient().when(savePort.save(eq(TOKEN), any()))
                .thenReturn(Mono.just(new SavedRun("run-contract-1", RunKind.QUALITY, 1, 1)));
        lenient().when(findPort.find(TOKEN, "run-contract-1"))
                .thenReturn(Mono.just(new StoredRunSummary(
                        "run-contract-1",
                        RunKind.QUALITY,
                        Instant.parse("2026-11-21T14:02:11Z"),
                        Instant.parse("2026-11-21T14:11:20Z"))));
        lenient().when(findPort.find(TOKEN, "run-missing"))
                .thenReturn(Mono.error(new EvalRunNotFoundException()));

        lenient().when(settingsPort.read(TOKEN))
                .thenReturn(Mono.just(new AssistantSettingsSnapshot(
                        new RetrievalConfig(4, 0.6, 8000, null, null),
                        List.of(
                                new PromptVersion("chat-system", 3, PromptSource.LANGFUSE, false),
                                new PromptVersion("chat-small-talk", null, PromptSource.DEFAULT, false)))));

        RunListItem item = new RunListItem(
                "run-contract-1",
                RunKind.QUALITY,
                Instant.parse("2026-11-21T14:02:11Z"),
                Instant.parse("2026-11-21T14:11:03Z"),
                "dev",
                null,
                "chat",
                List.of(new PromptVersion("chat-system", 3, PromptSource.LANGFUSE, false)),
                96,
                Map.of("faithfulness", new MetricStat(0.85, 96)));
        lenient().when(listPort.list(any()))
                .thenReturn(Mono.just(new RunListing(
                        RunKind.QUALITY, List.of(item), 1, 1, List.of(new OriginCount("dev", 1)))));

        EvalRunController runController = new EvalRunController(savePort, findPort);
        EvalSettingsController settingsController = new EvalSettingsController(settingsPort);
        EvalHistoryController historyController =
                new EvalHistoryController(listPort, getPort, comparePort, traceBuilder);

        WebTestClient client = WebTestClient
                .bindToController(runController, settingsController, historyController)
                .controllerAdvice(new GlobalExceptionHandler())
                .build();
        RestAssuredWebTestClient.webTestClient(client);
    }
}
