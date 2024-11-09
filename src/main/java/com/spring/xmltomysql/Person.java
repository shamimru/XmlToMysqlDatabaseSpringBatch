package com.spring.xmltomysql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personssss")
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "persons")
public class Person {
	@Id
	@XmlElement(name = "id")
	String id;

	@XmlElement(name = "firstName")
	String firstName;

	@XmlElement(name = "lastName")
	String lastName;

	@XmlElement(name = "age")
	int age;
}
