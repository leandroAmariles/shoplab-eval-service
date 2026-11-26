package com.shoplab.eval;

import com.shoplab.eval.support.PostgresTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@PostgresTest
class EvalServiceApplicationTests {

    @Test
    void contextLoads() {
        // Verifies the service context starts cleanly with no database or external server.
    }
}
