package pe.edu.utp.apiusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApiusuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiusuariosApplication.class, args);
	}

}
