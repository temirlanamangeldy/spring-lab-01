package kz.iitu.springlab.web;

import kz.iitu.springlab.notify.NotificationService;
import kz.iitu.springlab.notify.Notifier;
import kz.iitu.springlab.scope.TicketOffice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;
    private final TicketOffice ticketOffice;
    private final Notifier timestampedNotifier;

    public Lab2Controller(
            NotificationService notifications,
            TicketOffice ticketOffice,
            @Qualifier("timestamped") Notifier timestampedNotifier) {

        this.notifications = notifications;
        this.ticketOffice = ticketOffice;
        this.timestampedNotifier = timestampedNotifier;
    }

    @GetMapping("/notify")
    public Map<String, Object> notify(
            @RequestParam(defaultValue = "Hello") String text) {

        return Map.of(
                "primary", notifications.viaPrimary(text),
                "console", notifications.viaConsole(text),
                "all", notifications.viaAll(text),
                "beanNames", notifications.names()
        );
    }

    @GetMapping("/lifecycle")
    public String lifecycle() {
        return "Lifecycle OK";
    }

    @GetMapping("/scopes")
    public List<String> scopes() {
        return ticketOffice.getTwoTickets();
    }

    @GetMapping("/custom")
    public String custom(@RequestParam String text) {
        return timestampedNotifier.send(text);
    }
}