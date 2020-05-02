package tw.STSProject.model;

import java.util.List;

public interface IUsersDAO {
	public boolean insertUsers(String userName, String userPassword, int totalMoney, String email);
	public boolean updateUsersPassword(String userName, String oldUserPassword, String newUserPassword);
	public int deleteUsers(String userName, String userPassword);
	public List<Users> findUsers(String userName, String userPassword);
	public List<Users> findUsersByEmail(String email);
	public List<Users> findUsersByUserID(int userID);
	public int updateUserMoney(int userID, int totalMoney);
	public int updateUserActivated(int userID, boolean activated);
	public boolean updateUsersPassword(String userName, String userPassword);
}
