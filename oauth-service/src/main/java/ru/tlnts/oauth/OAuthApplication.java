package ru.tlnts.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mamedov
 */
@SpringBootApplication(scanBasePackages = "ru.tlnts.oauth")
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }
}
