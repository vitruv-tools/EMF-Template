package tools.vitruv.emftemplate.model;

import java.util.List;

import tools.vitruv.emftemplate.model.impl.ModelFactoryImpl;

public class Example {
    
    Link generate() {
        Protocol protocol = ModelFactoryImpl.eINSTANCE.createProtocol();
        protocol.setName("BLE");

        Component lightBulb = ModelFactoryImpl.eINSTANCE.createComponent();
        lightBulb.setName("Light Bulb");
        lightBulb.getSupportedProtocols().add(protocol);

        Component server = ModelFactoryImpl.eINSTANCE.createComponent();
        server.setName("Smart Home Server");
        server.getSupportedProtocols().add(protocol);

        Link link = ModelFactoryImpl.eINSTANCE.createLink();
        link.setProtocol(protocol);
        link.getComponents().addAll(List.of(lightBulb, server));

        return link;
    }

}
