package Smart.Data.Sharing.Platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "entities.concretes")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
