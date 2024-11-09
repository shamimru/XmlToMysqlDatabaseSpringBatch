//package com.spring.xmltomysql;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name="person")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@XmlRootElement(name = "persons")
//
//public class PersonMapping {
//
//	@Id
//	
//	long myId;
//	
//	@XmlElement(name="id")
//	long id;
//	
//	@XmlElement(name="firstName")
//	String firstName;
//	
//	
//	@XmlElement(name="lasttName")
//	String lasttName;
//	
//	@XmlElement(name="age")
//	int age;
//}
