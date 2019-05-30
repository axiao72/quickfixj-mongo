package com.matrixapps.fix.client;

import com.matrixapps.fix.AbstractFixBroker;
import com.matrixapps.fix.FixBroker;
import com.matrixapps.fix.server.ServerFixBroker;

public class ClientFixBroker extends AbstractFixBroker {

    public static void main(String args[]) throws Exception {
        FixBroker fixBroker = new ClientFixBroker();
        fixBroker.start();
        Thread.sleep(6000000);
        fixBroker.stop();
    }

    public ClientFixBroker() {
        super("./src/main/resources/client.cfg", Type.INITIATOR);
    }

}
