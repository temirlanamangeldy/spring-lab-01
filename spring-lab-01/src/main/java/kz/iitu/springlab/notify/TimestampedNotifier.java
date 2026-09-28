package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("timestamped")
@Order(3)
public class TimestampedNotifier implements Notifier {

    @PostConstruct
    public void init() {
        System.out.println("TimestampedNotifier initialized");
    }

    @Override
    public String channel() {
        return "timestamped";
    }

    @Override
    public String send(String message) {
        return Instant.now().toString() + " " + message;
    }
}