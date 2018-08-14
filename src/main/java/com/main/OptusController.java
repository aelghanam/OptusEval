package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptusController {

	@RequestMapping("/counter-api")
	public String searchAndCount(@RequestBody String searchText) {
		if (searchText != null && searchText.length() > 0) {
			JSONObject tempObj = new JSONObject(searchText);
			JSONArray jsonArray = new JSONArray(tempObj.get("searchText").toString());
			ArrayList<String> tempArrLst = new ArrayList<String>();
			for (int i = 0; i < jsonArray.length(); i++) {
				tempArrLst.add(jsonArray.getString(i));
			}
			CounterResponse cr = new CounterResponse(tempArrLst);
			return new JSONObject("{'counts':" + cr.getCounts().toString() + "}").toString();
		} else {
			return new JSONObject("{'message':'" + "Please Enter a valid search text" + "'}").toString();
		}
	}

	@RequestMapping(value = "/top/{countReturn}")
	@ResponseBody
	public void fooAsCSV(@PathVariable("countReturn") int countReturn, HttpServletResponse response) {
		response.setContentType("text/csv; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=data.csv");
		List<Entry<String, Integer>> list = CounterResponse.getTop(countReturn);
		// String data = "a,b,c\n1,2,3\n3,4,5";
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next();
				response.getWriter().print(entry.getKey() + "|" + entry.getValue());
				if (iterator.hasNext())
					response.getWriter().print("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
