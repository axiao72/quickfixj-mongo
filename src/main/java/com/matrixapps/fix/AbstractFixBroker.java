package com.matrixapps.fix;

import com.matrixapps.fix.client.ClientMessageInterceptor;
import com.matrixapps.fix.server.ServerMessageInterceptor;
import quickfix.*;

import java.io.FileInputStream;

public abstract class AbstractFixBroker implements FixBroker {

    final Connector connector;

    protected AbstractFixBroker(String configFilePath, Type type) {
        try {
            SessionSettings settings = new SessionSettings(new FileInputStream(configFilePath));
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            if (type == Type.ACCEPTOR) {
                connector = new SocketAcceptor
                        (new ServerMessageInterceptor(), storeFactory, settings, logFactory, messageFactory);
            } else {
                connector = new SocketInitiator
                        (new ClientMessageInterceptor(), storeFactory, settings, logFactory, messageFactory);
            }
        } catch(Exception e) {
            throw new IllegalStateException("Error initializing fix broker", e);
        }

    }

    @Override
    public void start()  {
        try {
            connector.start();
        } catch (ConfigError e) {
            throw new IllegalStateException("Error starting fix broker", e);

        }
    }

    @Override
    public void stop() {
        connector.stop();
    }

    public enum Type {
        ACCEPTOR, INITIATOR;
    }
}
