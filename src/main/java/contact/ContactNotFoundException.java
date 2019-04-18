package contact;

class ContactNotFoundException extends RuntimeException {

	ContactNotFoundException(Long id) {
		super("Could not find contact " + id);
	}

	ContactNotFoundException(String str) {
		super("Could not find contact with that string" + str);
	}
}
