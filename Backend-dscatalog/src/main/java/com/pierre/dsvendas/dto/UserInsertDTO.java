package com.pierre.dsvendas.dto;

public class UserInsertDTO extends UserDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	public UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}
		

}