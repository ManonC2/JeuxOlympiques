package org.resources.Models;

public class Utilisateur {
	private int id;
	
	private String login;
	
	private String password;
	
	private RoleUtilisateur role;
	
	public Utilisateur(String login, String password, RoleUtilisateur role) {
		super();
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public Utilisateur(int id, String login, String password, RoleUtilisateur role) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.role = role;
	}

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
