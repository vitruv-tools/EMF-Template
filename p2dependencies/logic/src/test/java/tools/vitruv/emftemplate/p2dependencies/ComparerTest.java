package tools.vitruv.emftemplate.p2dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.Test;

import tools.vitruv.emftemplate.p2dependencies.utils.ModelGenerator;

public class ComparerTest {

    @Test
    public void testCompareEqualSystems() {
        var generator = new ModelGenerator();

        var systemA = generator.generateTestModelA();
        var systemB = generator.generateTestModelA();

        var resourceSetA = wrapInResourceSet(systemA, "systemA");
        var resourceSetB = wrapInResourceSet(systemB, "systemB");

        var diffs = Comparer.getDiffs(resourceSetA, resourceSetB);

        assertEquals(0, diffs.size());
    }

    @Test
    public void testCompareDifferentSystems() {
        var generator = new ModelGenerator();

        var systemA = generator.generateTestModelA();
        var systemB = generator.generateTestModelB();

        var resourceSetA = wrapInResourceSet(systemA, "systemA");
        var resourceSetB = wrapInResourceSet(systemB, "systemB");

        var diffs = Comparer.getDiffs(resourceSetA, resourceSetB);

        assertNotEquals(0, diffs.size());
    }
    
    private static ResourceSet wrapInResourceSet(EObject object, String name) {
        var resourceFactory = new ResourceFactoryImpl();

        var resource = resourceFactory.createResource(URI.createFileURI(name));
        resource.getContents().add(object);

        var resourceSet = new ResourceSetImpl();
        resourceSet.getResources().add(resource);

        return resourceSet;
    }

}
