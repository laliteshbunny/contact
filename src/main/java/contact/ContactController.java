package contact;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ContactController {

	private final ContactRepository repository;

	ContactController(ContactRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping(path = "/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
	Resources<Resource<Contact>> all() {

		List<Resource<Contact>> contacts = repository.findAll().stream()
			.map(contact -> new Resource<>(contact,
				linkTo(methodOn(ContactController.class).one(contact.getId())).withSelfRel(),
				linkTo(methodOn(ContactController.class).all()).withRel("contacts")))
			.collect(Collectors.toList());
		
		return new Resources<>(contacts,
			linkTo(methodOn(ContactController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping(path ="/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
	Contact newContact(@RequestBody Contact newContact) {
		return repository.save(newContact);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping(path ="/contacts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	Resource<Contact> one(@PathVariable Long id) {
		
		Contact contact = repository.findById(id)
			.orElseThrow(() -> new ContactNotFoundException(id));
		
		return new Resource<>(contact,
			linkTo(methodOn(ContactController.class).one(id)).withSelfRel(),
			linkTo(methodOn(ContactController.class).all()).withRel("contacts"));
	}
	// end::get-single-item[]

	@PutMapping(path ="/contacts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	Contact replaceContact(@RequestBody Contact newContact, @PathVariable Long id) {
		
		return repository.findById(id)
			.map(contact -> {
				contact.setName(newContact.getName());
				contact.setCompany(newContact.getCompany());
				contact.setAddress(newContact.getAddress());
				contact.setBirthDate(newContact.getBirthDate());
				contact.setEmail(newContact.getEmail());
				contact.setPhoneNumber(newContact.getPhoneNumber());
				contact.setProfileImage(newContact.getProfileImage());
				return repository.save(contact);
			})
			.orElseGet(() -> {
				newContact.setId(id);
				return repository.save(newContact);
			});
	}

	@DeleteMapping(path ="/contacts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	void deleteContact(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping(path ="/contacts/search/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	Contact searchByName(@PathVariable String name) {

		Contact contact = repository.findByName(name);
		if(contact==null)
			throw  new ContactNotFoundException(name);
		return contact;
	}

}
