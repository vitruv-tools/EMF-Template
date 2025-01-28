package tools.vitruv.emftemplate.p2dependencies.utils;

import java.util.List;

import tools.vitruv.emftemplate.model.ModelFactory;
import tools.vitruv.emftemplate.model.System;

public class ModelGenerator {
    
    public System generateTestModelA() {
        var factory = ModelFactory.eINSTANCE;
        var system = factory.createSystem();
        
        var protocol = factory.createProtocol();
        protocol.setName("BLE");
        system.getProtocols().add(protocol);

        var device = factory.createDevice();
        device.setName("LightBulb");
        device.getSupportedProtocols().add(protocol);
        system.getComponents().add(device);

        var server = factory.createServer();
        server.setName("SmartHomeServer");
        server.getSupportedProtocols().add(protocol);
        system.getComponents().add(server);

        var link = factory.createLink();
        link.setProtocol(protocol);
        link.getComponents().addAll(List.of(device, server));
        system.getLinks().add(link);

        return system;
    }

    public System generateTestModelB() {
        var factory = ModelFactory.eINSTANCE;
        var system = factory.createSystem();
        
        var protocolBLE = factory.createProtocol();
        protocolBLE.setName("BLE");
        system.getProtocols().add(protocolBLE);

        var protocolWiFi = factory.createProtocol();
        protocolWiFi.setName("WiFi");
        system.getProtocols().add(protocolWiFi);

        var deviceLight = factory.createDevice();
        deviceLight.setName("LightBulb");
        deviceLight.getSupportedProtocols().add(protocolBLE);
        system.getComponents().add(deviceLight);

        var deviceHeating = factory.createDevice();
        deviceHeating.setName("UnderfloorHeatingController");
        deviceHeating.getSupportedProtocols().add(protocolWiFi);
        system.getComponents().add(deviceHeating);

        var server = factory.createServer();
        server.setName("SmartHomeServer");
        server.getSupportedProtocols().add(protocolBLE);
        server.getSupportedProtocols().add(protocolWiFi);
        system.getComponents().add(server);

        var linkLight = factory.createLink();
        linkLight.setProtocol(protocolBLE);
        linkLight.getComponents().addAll(List.of(deviceLight, server));
        system.getLinks().add(linkLight);

        var linkHeating = factory.createLink();
        linkHeating.setProtocol(protocolWiFi);
        linkHeating.getComponents().addAll(List.of(deviceHeating, server));
        system.getLinks().add(linkHeating);

        return system;
    }

}
