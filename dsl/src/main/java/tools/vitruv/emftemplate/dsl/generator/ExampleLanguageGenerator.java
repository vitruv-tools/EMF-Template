/*
 * generated by Xtext 2.35.0
 */
package tools.vitruv.emftemplate.dsl.generator;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.common.collect.Iterators;

import tools.vitruv.emftemplate.model.Component;
import tools.vitruv.emftemplate.model.Device;
import tools.vitruv.emftemplate.model.Link;
import tools.vitruv.emftemplate.model.Protocol;
import tools.vitruv.emftemplate.model.Server;

/**
 * Generates code from your model files on save.
 * 
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
public class ExampleLanguageGenerator extends AbstractGenerator {

    @Override
    public void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
        var sb = new StringBuilder();
        
        Supplier<Stream<Link>> links = () -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(Iterators.filter(resource.getAllContents(), Link.class), Spliterator.ORDERED), false);
        for (var item : (Iterable<EObject>) (() -> resource.getAllContents())) {
            if (item instanceof Protocol protocol) {
                sb.append(compile(protocol));
            } else if (item instanceof Device device) {
                sb.append(compile(device, links.get()));
            } else if (item instanceof Server server) {
                sb.append(compile(server, links.get()));
            } else {
                continue;
            }
            sb.append(System.lineSeparator());
        }

        fsa.generateFile("system.txt", sb.toString());
    }

    private String compile(Protocol protocol) {
        return String.format(
            "protocol: %s",
            protocol.getName());
    }

    private String compile(Device device, Stream<Link> allLinks) {
        return String.format(
            "device: %s -> %s",
            device.getName(),
            String.join(", ", allLinks
                .filter(link -> link.getComponents().contains(device))
                .flatMap(link -> link.getComponents().stream().filter(component -> !component.equals(device)))
                .map(Component::getName)
                .toList()));
    }

    private String compile(Server server, Stream<Link> allLinks) {
        return String.format(
            "server: %s -> %s",
            server.getName(),
            String.join(", ", allLinks
                .filter(link -> link.getComponents().contains(server))
                .flatMap(link -> link.getComponents().stream().filter(component -> !component.equals(server)))
                .map(Component::getName)
                .toList()));
    }

}