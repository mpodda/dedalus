package dedalus;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"dedalus.domain"})
@EnableJpaRepositories(basePackages = {"dedalus.repository"})
public class RepositoryConfiguration {
	
}