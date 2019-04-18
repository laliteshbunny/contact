package contact;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Contact {

	private @Id @GeneratedValue Long id;
	private String name;
	private String company;
	private String profileImage;
	private String email;
	private String birthDate;
	private String phoneNumber;
	private String address;

	Contact() {}

	public Contact(String name, String company, String profileImage, String email, String birthDate,
				   String phoneNumber, String address) {
		this.name = name;
		this.company = company;
		this.profileImage = profileImage;
		this.email = email;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
}
