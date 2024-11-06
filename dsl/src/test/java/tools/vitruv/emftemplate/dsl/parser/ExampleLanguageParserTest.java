package tools.vitruv.emftemplate.dsl.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.xtext.diagnostics.Severity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tools.vitruv.emftemplate.dsl.ExampleLanguageStandaloneSetup;

public class ExampleLanguageParserTest {

    @BeforeAll
    public static void setupAll() {
        ExampleLanguageStandaloneSetup.doSetup();
    }

    @Test
    public void testParserValid() {
        // given
        var classLoader = this.getClass().getClassLoader();
        var file = new File(classLoader.getResource("valid.example").getFile());

        var parser = new ExampleLanguageParser();

        // when
        var result = parser.parse(file.getAbsolutePath());

        // then
        assertTrue(result.isOk());

        result.optionalValue().ifPresent(system -> {
            // there is a protocol called "BLE"
            var protocol = system.getProtocols().stream().filter(it -> it.getName().equals("BLE")).findAny();
            assertFalse(protocol.isEmpty());

            // there are two components
            assertEquals(2, system.getComponents().size());
            
            // there is a link
            var link = system.getLinks().stream().findAny();
            assertFalse(link.isEmpty());

            // the link connects all components
            link.ifPresent(l -> {
                assertTrue(l.getComponents().containsAll(system.getComponents()));
                // the link uses the protocol "BLE"
                protocol.ifPresent(p -> {
                    assertTrue(l.getProtocol().equals(p));
                });
            });
        });
    }

    @Test
    public void testParserInvalid() {
        // given
        var classLoader = this.getClass().getClassLoader();
        var file = new File(classLoader.getResource("invalid.example").getFile());

        var parser = new ExampleLanguageParser();

        // when
        var result = parser.parse(file.getAbsolutePath());

        // then
        assertFalse(result.isOk());

        result.optionalError().ifPresent(issues -> {
            assertTrue(issues.stream().anyMatch(issue ->
                issue.getSeverity().equals(Severity.ERROR) &&
                issue.getCode().equals("invalidName") &&
                issue.getLineNumber() == 2));
            assertTrue(issues.stream().anyMatch(issue ->
                issue.getSeverity().equals(Severity.WARNING) &&
                issue.getCode().equals("invalidName") &&
                issue.getLineNumber() == 1));
        });
    }

}
