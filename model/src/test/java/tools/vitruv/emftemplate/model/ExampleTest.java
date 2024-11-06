package tools.vitruv.emftemplate.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExampleTest {
    
    @Test
    public void test() {
        Example example = new Example();
        Link link = example.generate();

        assertTrue(link.getComponents().stream().allMatch(item ->
            item.getSupportedProtocols().contains(link.getProtocol())));
    }

}
