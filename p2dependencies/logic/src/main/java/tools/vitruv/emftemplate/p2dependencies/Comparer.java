package tools.vitruv.emftemplate.p2dependencies;

import java.util.List;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class Comparer {
    private Comparer() {}

    public static List<Diff> getDiffs(ResourceSet resourceSetA, ResourceSet resourceSetB) {
        var scope = new DefaultComparisonScope(resourceSetA, resourceSetB, null);
        var comparison = EMFCompare.builder().build().compare(scope);

        return comparison.getDifferences();
    }
}
