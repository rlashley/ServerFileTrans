package serverTransFile;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
public class SenderController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method=POST, "/sender")
    public Feedback feedback(@RequestParam(value="feedbackText", defaultValue="Not Available") String feedbackText) {
        return new Feedback(counter.incrementAndGet(), String.format(feedbackText));
    }
}

public class Feedback {

    private String content;

    public Feedback(String content) {
	 //Modify to feed file into this constructor, data then passed to Spring Application
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
