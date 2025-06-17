package pe.com.capince;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CapinceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapinceApplication.class, args);
		
		System.out.println(new BCryptPasswordEncoder().encode("111111"));

	}

}
