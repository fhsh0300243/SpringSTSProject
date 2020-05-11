package tw.STSProject.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.STSProject.util.STSNecessaryTools;

@Controller
@RequestMapping(path = "/tools")
public class ToolsController {
	STSNecessaryTools stsnt = new STSNecessaryTools();
	private Process proc;	
	
	@RequestMapping(path = "/getIndexFromInternet", method = RequestMethod.GET, produces = "html/text;charset=UTF-8")
	public @ResponseBody String index() throws Exception {
		proc=Runtime.getRuntime().exec("python GetTAIEXFromWeb.py");
		BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
		String line;
        line=in.readLine();
        JSONObject JL= new JSONObject(line);
        JSONArray JLmsgArray = JL.getJSONArray("msgArray");
        JSONObject msgArray0 = (JSONObject) JLmsgArray.get(0);
        JSONObject msgArray1 = (JSONObject) JLmsgArray.get(1);
        JSONObject msgArray2 = (JSONObject) JLmsgArray.get(2);
        JSONObject respondIndex= new JSONObject();
        respondIndex.put("TAIEX", msgArray0.getString("z"));
        respondIndex.put("TAIEXname", msgArray0.getString("n"));
        respondIndex.put("TAIEXY", msgArray0.getString("y"));
        respondIndex.put("TAIEXZ_Y", Float.toString((float)Math.round((msgArray0.getFloat("z")-msgArray0.getFloat("y"))*100)/100));
        respondIndex.put("BUY", msgArray1.getString("z"));
        respondIndex.put("BUYname", msgArray1.getString("n"));
        respondIndex.put("BUYY", msgArray1.getString("y"));
        respondIndex.put("BUYZ_Y", Float.toString((float)Math.round((msgArray1.getFloat("z")-msgArray1.getFloat("y"))*100)/100));
        respondIndex.put("Formosa", msgArray2.getString("z"));
        respondIndex.put("FormosaName", msgArray2.getString("n"));
        respondIndex.put("FormosaY", msgArray2.getString("y"));
        respondIndex.put("FormosaZ_Y", Float.toString((float)Math.round((msgArray2.getFloat("z")-msgArray2.getFloat("y"))*100)/100));
		return respondIndex.toString();
	}
	
	@RequestMapping(path = "/getStockInfoFromInternet", method = RequestMethod.GET, produces = "html/text;charset=UTF-8")
	public @ResponseBody String stockInfo() {
		return null;
	}
}
