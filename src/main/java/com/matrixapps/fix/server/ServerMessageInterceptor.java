package com.matrixapps.fix.server;

import com.matrixapps.fix.LoggingMessageInterceptor;
import quickfix.*;

import java.util.Timer;
import java.util.TimerTask;

public class ServerMessageInterceptor extends LoggingMessageInterceptor {


    @Override
    public void onLogon(SessionID sessionId) {
        log.info("onLogon " + sessionId);

        new Timer("Timer").scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        sendDummyMessage(sessionId);
                    }
                }, 1000L, 1000L);
    }

    private void sendDummyMessage(SessionID sessionId) {
        try {
            String testMsgStr = "8=FIX.4.49=92635=834=24649=BLPFIX52=20150616-17:09:2256=READQFIX115=i.dcsvc1128=ZERO347=UTF-86=2514=10000015=USD17=VCON:20150616:50017:5:1222=131=2532=10000037=VCON:20150616:50017:538=10000039=248=037833AQ354=255=[N/A]60=20150616-17:09:2264=2015061975=20150616106=APPLE INC118=25250.83150=F151=0157=43159=250.83167=CORP198=3739:20150616:50017:5223=0.021228=1236=0.441390791381=25000423=1460=3470=US541=20190506669=257014=21453=6448=9001447=D452=1448=7302977447=D452=11802=5523=14803=4523=JOHN L. COPPETO803=9523=NEW YORK803=34523=US803=38523=6373289803=4000448=6204326447=D452=12802=3523=EVAN HOFFMAN803=9523=NEW YORK803=34523=US803=38448=WUNDERLICH SECURITIES INC447=D452=13448=BXT447=D452=16448=6204326447=D452=36802=3523=EVAN HOFFMAN803=9523=NEW YORK803=34523=US803=38454=4455=US037833AQ39456=4455=037833AQ3456=1455=EK248864456=A455=AAPL456=810=138";
            quickfix.Message testMessage = MessageUtils.parse(Session.lookupSession(sessionId), testMsgStr);
            Session.sendToTarget(testMessage, sessionId);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
