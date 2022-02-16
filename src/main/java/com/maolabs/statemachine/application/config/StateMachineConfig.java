package com.maolabs.statemachine.application.config;

import com.maolabs.statemachine.application.EventProcessEnum;
import com.maolabs.statemachine.application.StateProcessEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<StateProcessEnum, EventProcessEnum> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<StateProcessEnum, EventProcessEnum> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<StateProcessEnum, EventProcessEnum> states) throws Exception {
        states.withStates()
                .initial(StateProcessEnum.NOVO)
                .states(EnumSet.allOf(StateProcessEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StateProcessEnum, EventProcessEnum> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(StateProcessEnum.NOVO).target(StateProcessEnum.EM_PROCESSAMENTO).event(EventProcessEnum.PROCESSAR)
                .and()
                .withExternal()
                .source(StateProcessEnum.EM_PROCESSAMENTO).target(StateProcessEnum.PROCESSADO).event(EventProcessEnum.FINALIZAR_PROCESSAMENTO);
    }

    @Bean
    public StateMachineListener<StateProcessEnum, EventProcessEnum> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<StateProcessEnum, EventProcessEnum> from, State<StateProcessEnum, EventProcessEnum> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}