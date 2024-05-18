package susussg.pengreenlive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import susussg.pengreenlive.config.CustomObjectMapperConfig;

@SpringBootApplication
@Import(CustomObjectMapperConfig.class)
public class PenGreenLiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(PenGreenLiveApplication.class, args);
	}

}
