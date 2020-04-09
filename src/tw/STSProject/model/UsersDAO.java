package tw.STSProject.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO implements IUsersDAO{
	private SessionFactory sessionFacotry;

	@Autowired
	public UsersDAO(@Qualifier(value = "sessionFactory") SessionFactory sessionFacotry) {
		this.sessionFacotry = sessionFacotry;
	}
	
	public boolean insertUsers(String userName, String userPassword, int totalMoney) {	
		Session session = sessionFacotry.getCurrentSession();
		Query<Users> query = session.createQuery("from Users where UserName = :userName and UserPassword = :userPassword", Users.class);
		query.setParameter("userName", userName);
		query.setParameter("userPassword", userPassword);
		List<Users> list = query.list();
		if(list.size()<=0) {
			Users uTemp = new Users();
			uTemp.setUserName(userName);
			uTemp.setUserPassword(userPassword);
			uTemp.setTotalMoney(totalMoney);
			session.save(uTemp);
			return true;
		}
		return false;
	}
	
	public boolean updateUsersPassword(String userName, String oldUserPassword, String newUserPassword) {
		Session session = sessionFacotry.getCurrentSession();
		Query<Users> query = session.createQuery("from Users where UserName = :userName and UserPassword = :userPassword", Users.class);
		query.setParameter("userName", userName);
		query.setParameter("userPassword", oldUserPassword);
		List<Users> list = query.list();
		if(list.size()<=0) {
			return false;
		}
		query  = session.createQuery("update Users set UserPassword=:newUserPassword where UserName = :userName and  UserPassword = :oldUserPassword", Users.class);
		query.setParameter("userName", userName);
		query.setParameter("newUserPassword", newUserPassword);
		query.setParameter("oldUserPassword", oldUserPassword);
		query.executeUpdate();
		return true;
	}
	
	public int deleteUsers(String userName, String userPassword) {
		Session session = sessionFacotry.getCurrentSession();
		Query<?> query = session.createQuery("delete from Users where UserName = :userName and UserPassword = :userPassword");
		query.setParameter("userName", userName);
		query.setParameter("userPassword", userPassword);
		int numData=query.executeUpdate();
		return numData;
	}
	
	public List<Users> findUsers(String userName, String userPassword) {
		Session session = sessionFacotry.getCurrentSession();
		Query<Users> query = session.createQuery("from Users where UserName = :userName and UserPassword = :userPassword", Users.class);
		query.setParameter("userName", userName);
		query.setParameter("userPassword", userPassword);
		List<Users> list = query.list();
		return list;
	}
	
	public int updateUserMoney(int userID, int totalMoney) {
		Session session = sessionFacotry.getCurrentSession();
		Query<?> query  = session.createQuery("update Users set TotalMoney=:totalMoney where UserID = :userID");
		query.setParameter("totalMoney", totalMoney);
		query.setParameter("userID", userID);
		int numData=query.executeUpdate();
		return numData;
	}
	
}
