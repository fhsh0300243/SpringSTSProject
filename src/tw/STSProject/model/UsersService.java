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
	
	public boolean insertUsers(String userName, String userPassword, int totalMoney) {
		return uDAO.insertUsers(userName, userPassword, totalMoney);
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
	
	public int updateUserMoney(int userID, int totalMoney) {
		return uDAO.updateUserMoney(userID, totalMoney);
	}

}
