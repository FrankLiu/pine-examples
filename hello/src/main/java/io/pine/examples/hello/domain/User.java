package io.pine.examples.hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "p_user")
public class User extends NamedEntity{

	@Column(nullable = false)
	private Integer age;
	
	public User(){
		
	}
	
	public User(String name, Integer age){
		setName(name);
		setAge(age);
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}	
