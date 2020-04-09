package tw.STSProject.model;

import java.util.List;


public interface IInventoryDAO {
	public boolean insertOrUpadteInventory(int userID, String stockCode, int quantity);
	public int deleteInventory(int userID, String stockCode);
	public List<Inventory> findAllUserInventory(int userID);
}