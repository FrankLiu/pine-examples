package io.pine.examples.ucenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * @author frank.liu
 * @since 2019/1/14 0014.
 */
@SpringBootApplication
public class StatemachineApplication implements CommandLineRunner {

    @Autowired
    private StateMachine<TurnstileStates, TurnstileEvents> stateMachine;

    public static void main(String... args) {
        SpringApplication.run(StatemachineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        System.out.println("--- coin ---");
        stateMachine.sendEvent(TurnstileEvents.Coin);
        System.out.println("--- coin ---");
        stateMachine.sendEvent(TurnstileEvents.Coin);
        System.out.println("--- push ---");
        stateMachine.sendEvent(TurnstileEvents.Push);
        System.out.println("--- push ---");
        stateMachine.sendEvent(TurnstileEvents.Push);
        stateMachine.stop();
    }
}
