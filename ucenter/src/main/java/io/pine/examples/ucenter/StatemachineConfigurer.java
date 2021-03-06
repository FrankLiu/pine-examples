package io.pine.examples.ucenter;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author frank.liu
 * @since 2019/1/14 0014.
 */
@Configuration
@EnableStateMachine
public class StatemachineConfigurer extends EnumStateMachineConfigurerAdapter<TurnstileStates, TurnstileEvents> {
    @Override
    public void configure(StateMachineStateConfigurer<TurnstileStates, TurnstileEvents> states) throws Exception {
        states
                .withStates()
                .initial(TurnstileStates.LOCKED)
                .states(EnumSet.allOf(TurnstileStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TurnstileStates, TurnstileEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(TurnstileStates.LOCKED).target(TurnstileStates.UNLOCKED)
                .event(TurnstileEvents.Push).action(customPassAndLock())
                .source(TurnstileStates.UNLOCKED).target(TurnstileStates.LOCKED)
                .event(TurnstileEvents.Coin).action(turnstileUnlock());
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<TurnstileStates, TurnstileEvents> config)
            throws Exception {
        config
                .withConfiguration()
                .machineId("turnstileStateMachine");
    }

    public Action<TurnstileStates, TurnstileEvents> turnstileUnlock() {
        return context -> System.out.println("解锁旋转门，以便乘客能够通过");
    }

    public Action<TurnstileStates, TurnstileEvents> customPassAndLock() {
        return context -> System.out.println("乘客通过，锁定旋转门");
    }
}
