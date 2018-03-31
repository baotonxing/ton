package com.example.demo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;

public class EstimationFS {

	private Format fs = new Format();

	private Map<String, Object> itemMap = new HashMap<String, Object>();
	// private List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
	private String reqContent;// 需要解析的文本

	public void addItems() throws Exception {

		Map<Integer, Map<String, Object>> rowMap = new HashMap<Integer, Map<String, Object>>();
		fs.addFixItem("estimateNo", 6, 85, 8, "String");
		fs.addFixItem("accountNo", 6, 102, 8, "String");
		fs.addFixItem("nameAndAddress", 7, 1, 40, "String");
		fs.addFixItem("nameAndAddress", 8, 1, 40, "String");
		fs.addFixItem("nameAndAddress", 9, 1, 40, "String");
		fs.addFixItem("nameAndAddress", 10, 1, 40, "String");
		fs.addFixItem("nameAndAddress", 11, 1, 40, "String");
		fs.addFixItem("jobNo", 9, 61, 6, "String");
		fs.addFixItem("wipNo", 9, 83, 5, "String");
		fs.addFixItem("regNo", 15, 1, 20, "String");
		fs.addFixItem("chassisNo", 15, 28, 20, "String");
		fs.addBodyItem("no", 3, 3, "String");
		fs.addBodyItem("code", 9, 20, "String");
		fs.addBodyItem("descripion", 36, 24, "String");
		fs.addBodyItem("unitPrice", 89, 14, "String");
		fs.addBodyItem("qty", 104, 14, "String");
		fs.addBodyItem("disc", 120, 6, "String");
		fs.addBodyItem("amount", 129, 24, "String");
		fs.addFixItem("partPrice", 48, 12, 14, "String");
		fs.addFixItem("laborPrice", 49, 12, 14, "String");
		fs.addFixItem("package", 51, 12, 14, "String");
		fs.addFixItem("total", 52, 12, 14, "String");

		fs.open(reqContent);
		itemMap.put("EstimateNo", fs.getItemHeader("estimateNo"));
		itemMap.put("AccountNo", fs.getItemHeader("accountNo"));
		itemMap.put("NameAndAddress", fs.getItemHeader("nameAndAddress"));
		itemMap.put("JobNo", fs.getItemHeader("jobNo"));
		itemMap.put("WipNo", fs.getItemHeader("wipNo"));
		itemMap.put("RegNo", fs.getItemHeader("regNo"));
		itemMap.put("ChassisNo", fs.getItemHeader("chassisNo"));
		itemMap.put("PartPrice", fs.getItemFooter("partPrice"));
		itemMap.put("LaborPrice", fs.getItemFooter("laborPrice"));
		itemMap.put("Package", fs.getItemFooter("package"));
		itemMap.put("Total", fs.getItemFooter("total"));
		String body = fs.getBodySum();
		// 表体一共有多少行
		int rowCount = fs.rownumcount(body).size() - 1;
		for (int i = 0; i < rowCount; i++) {
			Map<String, Object> innerMap = new HashMap<String, Object>();
			innerMap.put("No", fs.getBodyItem("no").get(i));
			innerMap.put("Code", fs.getBodyItem("code").get(i));
			innerMap.put("Descripion", fs.getBodyItem("descripion").get(i));
			innerMap.put("UnitPrice", fs.getBodyItem("unitPrice").get(i));
			innerMap.put("Qty", fs.getBodyItem("qty").get(i));
			innerMap.put("Disc", fs.getBodyItem("disc").get(i));
			innerMap.put("Amount", fs.getBodyItem("amount").get(i));
			rowMap.put(i + 1, innerMap);
		}
		itemMap.put("BODY", rowMap);

	}

	public EstimationFS(String reqContent) throws Exception {
		open(reqContent);
		addItems();
	}

	public String getItemValue(String itemName) throws Exception {
        return (String) itemMap.get(itemName);
	}

	public Map<String, Object> getItemMap() {
		return itemMap;
	}

	/**
	 * 加载字符串
	 * 
	 * @param reqContent
	 */
	public void open(String reqContent) {
		this.reqContent = reqContent;
	}

}
