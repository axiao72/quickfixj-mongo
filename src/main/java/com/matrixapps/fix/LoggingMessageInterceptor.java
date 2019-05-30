package com.matrixapps.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

public abstract class LoggingMessageInterceptor extends MessageCracker implements quickfix.Application {

    final Logger log = LoggerFactory.getLogger("message.interceptor");

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("onCreate " + sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("onLogon " + sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("onLogout " + sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        log.info("toAdmin " + message.getClass().getSimpleName());
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("fromAdmin " + message.getClass().getSimpleName());
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        log.info("toApp " + message.getClass().getSimpleName());
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("fromApp " + message.getClass().getSimpleName());
    }
}
