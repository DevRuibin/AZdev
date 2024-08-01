package devruibin.github.azdev;

import devruibin.github.azdev.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AZdevApplication {

    public static void main(String[] args) {
        SpringApplication.run(AZdevApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository repo) {
        return args -> {
            System.out.println("Hello, World!");
            System.out.println(repo.getUserById(1L));
            repo.getUserById(1L).ifPresent(user -> {
                System.out.println(user.getPassword());
                System.out.println(user.getUsername());
                System.out.println(user.getCreatedAt());
            });

        };
    }
}
