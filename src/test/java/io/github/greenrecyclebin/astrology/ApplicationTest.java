package io.github.greenrecyclebin.astrology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

class ApplicationTest {
    @Test
    void checkControllerAdviceClassExistence() {
        Assertions.assertTrue(Files.exists(Paths.get("build/classes/java/main/io/github/greenrecyclebin/astrology/ControllerAdvice.class")));
    }
}
