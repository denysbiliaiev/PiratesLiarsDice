package org.game.piratesliarsdice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        Environment env = SpringApplication.run(Application.class, args).getEnvironment();

        Game game = new Game(env.getProperty("dice.url"));

        game.addPlayer("Denys");
        game.addPlayer("Will");

        game.start();
    }
}
