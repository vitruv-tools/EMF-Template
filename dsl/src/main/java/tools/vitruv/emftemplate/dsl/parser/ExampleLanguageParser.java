package tools.vitruv.emftemplate.dsl.parser;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

import tools.vitruv.emftemplate.dsl.utils.Result;
import tools.vitruv.emftemplate.model.System;

public class ExampleLanguageParser {
    
    public Result<System, List<Issue>> parse(String path) {
        // load
        var resourceServiceProvider = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(URI.createURI(path));
        var resourceSet = resourceServiceProvider.get(ResourceSet.class);
        var resource = resourceSet.getResource(URI.createFileURI(path), true);

        // validate
        var validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
        var issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
        if (!issues.isEmpty()) {
            java.lang.System.err.println(String.join(",", issues.stream().map(Issue::toString).toList()));
            return Result.fail(issues);
        }

        // return
        return Result.ok((System) resource.getContents().get(0));
    }

}
