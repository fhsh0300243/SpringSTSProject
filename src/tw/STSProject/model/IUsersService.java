package tw.STSProject.model;

import java.util.List;

public interface IUsersService {
	public boolean insertUsers(String userName, String userPassword, int totalMoney);
	public boolean updateUsersPassword(String userName, String oldUserPassword, String newUserPassword);
	public int deleteUsers(String userName, String userPassword);
	public List<Users> findUsers(String userName, String userPassword);
	public int updateUserMoney(int userID, int totalMoney);
}
