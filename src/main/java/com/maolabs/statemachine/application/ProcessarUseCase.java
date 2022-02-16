package com.maolabs.statemachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Component
public class ProcessarUseCase implements CommandLineRunner {
    @Autowired
    private StateMachine<StateProcessEnum, EventProcessEnum> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.sendEvent(EventProcessEnum.PROCESSAR);
        stateMachine.sendEvent(EventProcessEnum.FINALIZAR_PROCESSAMENTO);
    }
}
