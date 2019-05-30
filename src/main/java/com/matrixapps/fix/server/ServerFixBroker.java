package com.matrixapps.fix.server;

import com.matrixapps.fix.AbstractFixBroker;
import com.matrixapps.fix.FixBroker;

public class ServerFixBroker extends AbstractFixBroker {

    public static void main(String args[]) throws Exception {

        FixBroker fixBroker = new ServerFixBroker();
        fixBroker.start();
        Thread.sleep(6000000);
        fixBroker.stop();
    }

    public ServerFixBroker() {
        super("./src/main/resources/server.cfg", Type.ACCEPTOR);
    }
}
