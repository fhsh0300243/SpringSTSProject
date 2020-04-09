package tw.STSProject.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/rss")
public class RssNewsController {
	public static final String RSS_URL="https://money.udn.com/rssfeed/news/1001/5592/12040?ch=money";
	
	@RequestMapping(path = "/news", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void processRssNewsAction(HttpServletResponse response) throws Exception {
		response.setContentType("text/xml;charset=UTF-8");
		URL url=new URL(RSS_URL);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		StringBuilder sb=new StringBuilder(1024);
		String str;
		
		while((str=in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		response.getWriter().print(sb.toString());	
	}
}
