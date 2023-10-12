package org.resources.Models;

public class Utilisateur {
	private int id;
	
	private String login;
	
	private String password;
	
	private RoleUtilisateur role;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleUtilisateur getRole() {
		return role;
	}

	public void setRole(RoleUtilisateur role) {
		this.role = role;
	}
}
