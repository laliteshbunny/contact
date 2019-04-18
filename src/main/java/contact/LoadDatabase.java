package contact;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(ContactRepository repository) {

		Contact contact = new Contact("Lalitesh","Deloitte","Facebook Pic",
				"dlalitesh@deloitte.com","13-02-1997","1234567890","hyderabad");
		return args -> {
			log.info("Preloading " + repository.save(contact));

		};
	}
}
