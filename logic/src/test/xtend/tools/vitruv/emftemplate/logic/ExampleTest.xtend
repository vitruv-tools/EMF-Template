package tools.vitruv.emftemplate.logic

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tools.vitruv.emftemplate.model.impl.ModelFactoryImpl;

class ExampleTest {

    @Test
    def testConnect() {
        // given
        val example = new Example()

        val protocol = ModelFactoryImpl.eINSTANCE.createProtocol()
        
        val compA = ModelFactoryImpl.eINSTANCE.createComponent()
        compA.getSupportedProtocols().add(protocol)

        val compB = ModelFactoryImpl.eINSTANCE.createComponent()
        compB.getSupportedProtocols().add(protocol)

        // when
        val link = example.connect(compA, compB)

        // then
        assertFalse(link.isEmpty())

        link.ifPresent[
            assertEquals(protocol, it.getProtocol())
            assertTrue(it.getComponents().contains(compA))
            assertTrue(it.getComponents().contains(compB))
        ]
    }

}
