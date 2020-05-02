package tw.STSProject.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements IUsersService {
	private UsersDAO uDAO;
	
	@Autowired
	public UsersService(UsersDAO uDAO) {
		this.uDAO = uDAO;
	}
	
	public boolean insertUsers(String userName, String userPassword, int totalMoney, String email) {
		return uDAO.insertUsers(userName, userPassword, totalMoney, email);
	}
	
	public boolean updateUsersPassword(String userName, String oldUserPassword, String newUserPassword) {
		return uDAO.updateUsersPassword(userName, oldUserPassword, newUserPassword);
	}
	
	public int deleteUsers(String userName, String userPassword) {
		return uDAO.deleteUsers(userName, userPassword);
	}
	
	public List<Users> findUsers(String userName, String userPassword) {
		return uDAO.findUsers(userName, userPassword);
	}
	
	public List<Users> findUsersByEmail(String email){
		return uDAO.findUsersByEmail(email);
	}
	
	public List<Users> findUsersByUserID(int userID){
		return uDAO.findUsersByUserID(userID);
	};
	
	public int updateUserMoney(int userID, int totalMoney) {
		return uDAO.updateUserMoney(userID, totalMoney);
	}
	
	public int updateUserActivated(int userID, boolean activated) {
		return uDAO.updateUserActivated(userID, activated);
	}
	
	public boolean updateUsersPassword(String userName, String userPassword) {
		return uDAO.updateUsersPassword(userName, userPassword);
	}

}
