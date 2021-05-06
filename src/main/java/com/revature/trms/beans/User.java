package com.revature.trms.beans;

public class User {
	private String username;
	private String password;
	private String name;
	private Type type;
	private String supervisorUsername;
	private Department department;
	private double totalReimbursement;
	private double pendingReimbursement;
	private double awardedReimbursement;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department2) {
		this.department = department2;
	}
	public double getTotalReimbursement() {
		return totalReimbursement;
	}
	public void setTotalReimbursement(double totalReimbursement) {
		this.totalReimbursement = totalReimbursement;
	}
	public double getPendingReimbursement() {
		return pendingReimbursement;
	}
	public void setPendingReimbursement(double pendingReimbursement) {
		this.pendingReimbursement = pendingReimbursement;
	}
	public double getAwardedReimbursement() {
		return awardedReimbursement;
	}
	public void setAwardedReimbursement(double awardedReimbursement) {
		this.awardedReimbursement = awardedReimbursement;
	}
	public String getSupervisorUsername() {
		return supervisorUsername;
	}
	public void setSupervisorUsername(String supervisorUsername) {
		this.supervisorUsername = supervisorUsername;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(awardedReimbursement);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		temp = Double.doubleToLongBits(pendingReimbursement);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((supervisorUsername == null) ? 0 : supervisorUsername.hashCode());
		temp = Double.doubleToLongBits(totalReimbursement);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Double.doubleToLongBits(awardedReimbursement) != Double.doubleToLongBits(other.awardedReimbursement))
			return false;
		if (department != other.department)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (Double.doubleToLongBits(pendingReimbursement) != Double.doubleToLongBits(other.pendingReimbursement))
			return false;
		if (supervisorUsername == null) {
			if (other.supervisorUsername != null)
				return false;
		} else if (!supervisorUsername.equals(other.supervisorUsername))
			return false;
		if (Double.doubleToLongBits(totalReimbursement) != Double.doubleToLongBits(other.totalReimbursement))
			return false;
		if (type != other.type)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", type=" + type
				+ ", supervisorUsername=" + supervisorUsername + ", department=" + department + ", totalReimbursement="
				+ totalReimbursement + ", pendingReimbursement=" + pendingReimbursement + ", awardedReimbursement="
				+ awardedReimbursement + "]";
	}
	
	
	
	

}
