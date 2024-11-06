package tools.vitruv.emftemplate.logic

import tools.vitruv.emftemplate.model.Component
import tools.vitruv.emftemplate.model.impl.ModelFactoryImpl;

class Example {

    def connect(Component a, Component b) {
        var possible = a.getSupportedProtocols().filter[b.getSupportedProtocols().contains(it)].toList

        possible.stream().findAny().map[
            val link = ModelFactoryImpl.eINSTANCE.createLink()
            link.setProtocol(it)
            link.getComponents().add(a)
            link.getComponents().add(b)
            link
        ]
    }

}
