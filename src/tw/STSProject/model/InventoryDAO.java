package tw.STSProject.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryDAO implements IInventoryDAO {
	
	private SessionFactory sessionFacotry;

	@Autowired
	public InventoryDAO(@Qualifier(value = "sessionFactory") SessionFactory sessionFacotry) {
		this.sessionFacotry = sessionFacotry;
	}
	
	public boolean insertOrUpadteInventory(int userID, String stockCode, int quantity) {
		Session session = sessionFacotry.getCurrentSession();
		Query<Inventory> query = session.createQuery("from Inventory where UserID = :userID and StockCode = :stockCode", Inventory.class);
		query.setParameter("userID", userID);
		query.setParameter("stockCode", stockCode);
		List<Inventory> list = query.list();
		if(list.size()<=0) {
			Inventory iTemp = new Inventory();
			iTemp.setUserID(userID);;
			iTemp.setStockCode(stockCode);;
			iTemp.setQuantity(quantity);;
			session.save(iTemp);
			return true;
		}
		Query<?> query1 = session.createQuery("update Inventory set Quantity=:quantity where UserID = :userID and  StockCode = :stockCode");
		query1.setParameter("quantity", quantity);
		query1.setParameter("userID", userID);
		query1.setParameter("stockCode", stockCode);
		query1.executeUpdate();
		return false;
	}
	
	public int deleteInventory(int userID, String stockCode) {
		Session session = sessionFacotry.getCurrentSession();
		Query<?> query = session.createQuery("delete from Inventory where UserID = :userID and StockCode = :stockCode");
		query.setParameter("userID", userID);
		query.setParameter("stockCode", stockCode);
		int numData=query.executeUpdate();
		return numData;
	}
	
	public List<Inventory> findAllUserInventory(int userID) {
		Session session = sessionFacotry.getCurrentSession();
		Query<Inventory> query = session.createQuery("from Inventory where UserID = :userID", Inventory.class);
		query.setParameter("userID", userID);
		List<Inventory> list = query.list();
		return list;
	}
}