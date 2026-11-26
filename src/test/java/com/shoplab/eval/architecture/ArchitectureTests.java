package com.shoplab.eval.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

/** Hexagonal boundaries and service independence rules of {@code eval-service} (ADR-0015, ADR-0031, ADR-0035). */
class ArchitectureTests {

    private static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.shoplab.eval");

    @Test
    void theDomainDoesNotKnowFrameworksMessagingPersistenceOrReactor() {
        noClasses().that().resideInAPackage("com.shoplab.eval.domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "org.springframework.ai..",
                        "io.opentelemetry..",
                        "org.apache.kafka..",
                        "org.springframework.kafka..",
                        "jakarta.persistence..",
                        "org.springframework.data.jpa..",
                        "reactor..",
                        "..infrastructure..",
                        "..application..")
                .check(CLASSES);
    }

    @Test
    void theApplicationLayerDoesNotUseAdapters() {
        noClasses().that().resideInAPackage("com.shoplab.eval.application..")
                .should().dependOnClassesThat().resideInAPackage("com.shoplab.eval.infrastructure..")
                .check(CLASSES);
    }

    @Test
    void nothingComesFromTheMonolithOrChatCode() {
        noClasses().should().dependOnClassesThat()
                .resideInAnyPackage("com.shoplab.helpdesk..", "com.shoplab.chat..")
                .check(CLASSES);
    }
}
