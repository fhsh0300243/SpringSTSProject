package tw.STSProject.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteStockDAO implements IFavoriteStockDAO{
	
	private SessionFactory sessionFacotry;

	@Autowired
	public FavoriteStockDAO(@Qualifier(value = "sessionFactory") SessionFactory sessionFacotry) {
		this.sessionFacotry = sessionFacotry;
	}
	
	public boolean insertFavoriteStock(int userID, String stockCode) {	
		Session session = sessionFacotry.getCurrentSession();
		Query<FavoriteStock> query = session.createQuery("from FavoriteStock where UserID = :userID and StockCode = :stockCode", FavoriteStock.class);
		query.setParameter("userID", userID);
		query.setParameter("stockCode", stockCode);
		List<FavoriteStock> list = query.list();
		if(list.size()<=0) {
			FavoriteStock fsTemp = new FavoriteStock();
			fsTemp.setUserID(userID);
			fsTemp.setStockCode(stockCode);
			session.save(fsTemp);
			return true;
		}
		return false;
	}
	
	public int deleteFavoriteStock(int userID, String stockCode) {
		Session session = sessionFacotry.getCurrentSession();
		Query<?> query = session.createQuery("delete from FavoriteStock where UserID = :userID and StockCode = :stockCode");
		query.setParameter("userID", userID);
		query.setParameter("stockCode", stockCode);
		int numData=query.executeUpdate();
		return numData;
	}
	
	public List<FavoriteStock> findAllUserFavoriteStock(int userID) {
		Session session = sessionFacotry.getCurrentSession();
		Query<FavoriteStock> query = session.createQuery("from FavoriteStock where UserID = :userID", FavoriteStock.class);
		query.setParameter("userID", userID);
		List<FavoriteStock> list = query.list();
		return list;
	}
	
	public FavoriteStock findUserFavoriteStock(int userID, String stockCode) {
		Session session = sessionFacotry.getCurrentSession();
		Query<FavoriteStock> query = session.createQuery("from FavoriteStock where UserID = :userID and StockCode = :stockCode", FavoriteStock.class);
		query.setParameter("userID", userID);
		query.setParameter("stockCode", stockCode);
		FavoriteStock result =(FavoriteStock) query;
		return result;
	}
}
