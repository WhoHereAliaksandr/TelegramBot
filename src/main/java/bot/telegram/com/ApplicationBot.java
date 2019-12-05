package bot.telegram.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class ApplicationBot {
    public static void main(String ... args) {
        ApiContextInitializer.init();
        SpringApplication.run(ApplicationBot.class, args);
    }
}
