package com.matrixapps.fix;

import com.matrixapps.fix.client.ClientMessageInterceptor;
import com.matrixapps.fix.server.ServerMessageInterceptor;
import oracle.jdbc.pool.OracleDataSource;
import quickfix.*;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.SQLException;

public abstract class AbstractFixBroker implements FixBroker {

    final Connector connector;

    protected AbstractFixBroker(String configFilePath, Type type) {
        try {
            SessionSettings settings = new SessionSettings(new FileInputStream(configFilePath));
            JdbcStoreFactory storeFactory = new JdbcStoreFactory(settings);
            storeFactory.setDataSource(getDataSource());
            JdbcLogFactory logFactory = new JdbcLogFactory(settings);
            logFactory.setDataSource(getDataSource());
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

    protected DataSource getDataSource() throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL("jdbc:oracle:thin:@10.254.102.200:1522/DEV400");
        ods.setUser("MCADMIN");
        ods.setPassword("Password1");
        return ods;
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
