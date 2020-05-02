package tw.STSProject.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column(name = "USERID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "USERPASSWORD")
	private String userPassword;

	@Column(name = "TOTALMONEY")
	private int totalMoney;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ACTIVATED")
	private boolean activated;
	
	@Column(name = "UUID")
	private UUID uUID;
	
	
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public UUID getuUID() {
		return uUID;
	}

	public void setuUID(UUID uuid) {
		this.uUID = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}



}
