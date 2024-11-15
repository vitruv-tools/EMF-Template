package tools.vitruv.emftemplate.imports.dsl.linking;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;

import com.google.inject.Inject;

public class ImportsLanguageLinkingService extends DefaultLinkingService {
    
    @Inject
    IValueConverterService valueConverterService;
    
    @Override
    public List<EObject> getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
        if (ref.getEType().equals(EcorePackage.Literals.EPACKAGE))
            return getEPackage((ILeafNode) node);

        return super.getLinkedObjects(context, ref, node);
    }
    
    /**
     * from org.eclipse.xtext.xtext.XtextLinkingService.getPackage(ReferencedMetamodel, ILeafNode)
     */
    public List<EObject> getEPackage(ILeafNode text) {
        var nsUri = getMetamodelNsURI(text);
        if (nsUri == null)
            return Collections.emptyList();
            
        var resolvedEPackage = (EObject) EPackage.Registry.INSTANCE.getEPackage(nsUri);
        if (resolvedEPackage == null)	
            return Collections.emptyList();
            
        return List.of(resolvedEPackage);
    }

    /**
     * from org.eclipse.xtext.xtext.XtextLinkingService.getMetamodelNsURI(ILeafNode)
     */
    private String getMetamodelNsURI(ILeafNode text) {
        try {
            return (String) valueConverterService.toValue(text.getText(), getLinkingHelper().getRuleNameFrom(text.getGrammarElement()), text);
        } catch (ValueConverterException e) {
            return null;
        }
    }

}
