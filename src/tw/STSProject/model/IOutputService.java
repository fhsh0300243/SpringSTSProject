package tw.STSProject.model;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONArray;

public interface IOutputService {
	public void outputFavoriteStockToCsv(int userID) throws IOException, SQLException;
	public JSONArray outputStockInformationToJSON(int userID) throws IOException, SQLException;
	public JSONArray outputInventoryToJSON(int userID) throws IOException, SQLException;
	public JSONArray outputTransactionRecordToJSON(int userID) throws IOException, SQLException;
}
