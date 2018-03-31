package com.example.demo.entity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Format {

	private String reqContent;//需要解析的文本
	
	private List<Item> LL = new ArrayList<Item>();//表示文件头部的列表
	private List<Item> LLB = new ArrayList<Item>();//表示文件body部分的列表
	private String[] pages;
	//private List<Item> LLF;//表示最后一页表尾的列表

	private final int HeaderLine = 29;
	private final int bodyLine = 17;// 其中第一行为body表表头描述
	//private final int footline = 20;
	
   
	/**
	 * 设置表头数据格式配置
	 * @param itemName
	 * @param rowNo
	 * @param itemSIndex
	 * @param itemWidth
	 * @param itemType
	 */
	public void addFixItem(String itemName,int rowNo,
			int itemStart,int itemWidth,String itemType) {
		Item Item = new Item(itemName,rowNo,itemStart,itemWidth,itemType);
		LL.add(Item);
	}
	/**
	 * 设置表体数据格式配置
	 * @param itemName
	 * @param itemSIndex
	 * @param itemWidth
	 * @param itemType
	 */
	public void addBodyItem(String itemName,
			int itemStart,int itemWidth,String itemType){
		Item Item = new Item(itemName,itemStart,itemWidth,itemType);
		LLB.add(Item);
	}
	
	/**
	 * 根据页面和位置设置得到值
	 * @param pageNo
	 * @param itemName
	 * @return
	 * @throws Exception 
	 */
	public String getFixItem(int pageNo,String itemName) throws Exception {
	    List<Item> ItemL= getItemAndName(itemName);
	    String rstr="";
	    for(int i=0;i<ItemL.size();i++) {
	    	Item ih = ItemL.get(i);
	    	rstr += getPageNLNNN(pageNo, ih.getLineno(), ih.getItemStart(), ih.getItemWidth(),reqContent);
	    }
	    return rstr;
	}
	/**
	 * 根据表头位置设置得到值
	 * @param itemName
	 * @return
	 * @throws Exception
	 */
	public String getItemHeader(String itemName) throws Exception{
		return getFixItem(1,itemName);
	}
	/**
	 * 根据表尾位置信息得到值
	 * @param itemName
	 * @return
	 * @throws Exception
	 */
	public String getItemFooter(String itemName) throws Exception{
		int pageSize = getPageTotal();
		return getFixItem(pageSize,itemName);
	}
	
	/**根据rownum 取每一列的数据并封装到ArrayList 中
	 * @throws Exception 
	 * 
	 */
	public List<Object> getBodyRow(int rownum) throws Exception {
		String bodySum = getBodySum();
		return getrownumStr(rownum,bodySum,LLB);
	}
	/**
	 * @throws Exception 
	 * 
	 */
	public List<Object> getBodyItem(String itemName) throws Exception{
		String bodySum = getBodySum();
		Item Item = getItembyItemName(itemName);
		return getColumnArrayStr(bodySum,Item);
	}
	
	/**
	 * 取body中一列的值，并保存在ArrayList 里面
	 * 
	 */
	public List<Object> getColumnArrayStr(String bodySum,Item Item) throws Exception {
		
		Map<Integer,Integer> mapL = rownumcount(bodySum);
		 //计算第N 行的起始位置
		List<Object> lo = new ArrayList<Object>();
		int rowCount = mapL.size()-1;
		for(int i=1;i<=rowCount;i++) {
			int nrowIndex =0;
			String lineStr="";
			for(int k=0;k<i;k++) {
				nrowIndex= nrowIndex + mapL.get(k + 1);
			}
			int linetoline = mapL.get(i+1);
			for(int j=0;j<linetoline;j++) {
				lineStr += getPageNLNNN(1, nrowIndex+1+j, Item.getItemStart(), Item.getItemWidth(),
						bodySum);
			}
			
			lo.add(lineStr);
		}
		return lo;
	}

	/**
	 * 根据某一行rownum 取值，rownum 为 1,2,3,4,5,...rownumcount
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public List<Object> getrownumStr(int rowNum, String bodyStr, List<Item> lLB) throws Exception {
		List<Object> lo = new ArrayList<Object>();
		Map<Integer, Integer> rowCountL = rownumcount(bodyStr);
		int totalRow = rowCountL.size();
		//如果输入的行数大于总行数，返回null;
		if(rowNum >= totalRow) return lo;
		int rowCount = rowCountL.get(rowNum + 1);
         //计算第N 行的起始位置
		int nrowIndex =0;
		for(int i=0;i<rowNum;i++) {
			nrowIndex= nrowIndex + rowCountL.get(i + 1);
		}
		for (int i = 0; i < lLB.size(); i++) {
			String lineStr="";
			for (int j = 0; j < rowCount; j++) {
				Item itemB = lLB.get(i);
				lineStr += getPageNLNNN(1, nrowIndex+1+j, itemB.getItemStart(), itemB.getItemWidth(),
						bodyStr).trim();
				
				
			}
			lo.add(lineStr+"  ");
		}
        return lo;
	}

	/**
	 * 获取第一页第3行第3页宽度6的字符 startnum 指的是字节数 endnum 指的是字节数
	 * @throws UnsupportedEncodingException 
	 */

	public String getPageNLNNN(int pageNo, int lineNo, int startnum, int endnum,String content) throws Exception {
		String pageNStr = getPageN(pageNo,content);
		String charsetdef ="GB18030";
		String lineNStr = getPageLineN(lineNo, pageNStr);
		//System.out.println(lineNStr.length());
		int lineNStrLen = lineNStr.getBytes(charsetdef).length;
		if(startnum>=lineNStrLen) return "";
		if((startnum+endnum)>lineNStrLen) return new String(lineNStr.getBytes(charsetdef),startnum,lineNStrLen-startnum,charsetdef);
		return new String(lineNStr.getBytes(charsetdef),startnum,endnum,charsetdef);
	}

	/**
	 * 获取每页的body 组合成一个大组合body
	 */
	public String getBodySum() {
		
		String bodySum = "";
		int pageCounts = pages.length;
		for (int i = 0; i < pageCounts; i++) {
			String pageN = getPageN(i + 1);
			for (int j = HeaderLine+2; j < HeaderLine+bodyLine+1; j++) {
				bodySum = bodySum+ getPageLineN(j, pageN);
				bodySum = bodySum +"\r\n";
			}
		}
		return bodySum;
	}

	/**
	 * 取所在字符串第N页的字符串文本
	 */
	public String getPageN(int n,String content) {
		String[] pages = content.split("\\r\\n\\f");
		return pages[n - 1];
	}
	/**
	 * 取第N页的字符串文本
	 */
	public String getPageN(int n) {
		
		return pages[n - 1];
	}
	/**
	 * 取总页数
	 */
	public int getPageTotal() {
		return pages.length;
	}
	/**
	 * 取第N页第N行的字符串文本
	 */
	public String getPageLineN(int n, String page) {
		String[] lines = page.split("\\r\\n");
		return lines[n - 1];
	}
	
	/**
	 * 得到大body一共有多少行？ List<Map<Integer,Integer>> 第一个Integer 表示行号，第二个Integer
	 * 表示与前面一行的行距
	 */
	public Map<Integer, Integer> rownumcount(String str) {

		Map<Integer, Integer> rowtorowMap = new HashMap<Integer, Integer>();
		Item onrow = getItembyItemName("no");
		int linetoline = 0;
		String rownos = "";
		int rowCount = str.split("\\r\\n").length;
		for (int i = 0; i < rowCount; i++) {
			String lineNStr = getPageLineN(i + 1, str);
			if(lineNStr.trim().equals("")) {
				rowtorowMap.put(Integer.parseInt(rownos)+1, ++linetoline);
				linetoline = 0;
				continue;
			}
			rownos = lineNStr.substring(0, onrow.getItemStart()+onrow.getItemWidth()).trim();

			if (!"".equals(rownos)) {
				rowtorowMap.put(Integer.parseInt(rownos), linetoline);

				linetoline = 0;
			}
			linetoline++;
			
		}
		return rowtorowMap;
	}
	
	
	
	/**
	 * 根据pageNo 和 itemName查询 Item 设置的数据
	 * @param pageNo
	 * @param itemName
	 * @return
	 */
	public List<Item> getItemAndName(String itemName) {
		
		List<Item> itemNameL = new ArrayList<Item>();
		for(int i =0;i<LL.size();i++) {
			Item Item = LL.get(i);
		    if(itemName.equals(Item.getItemName())){
		    	itemNameL.add(Item);
		    }
		}
		return itemNameL;
	}
	
	
	/**
	 * 根据表体的名称取得表体的设置数据。
	 * @return
	 */
	public Item getItembyItemName(String name) {
		for(int i=0;i<LLB.size();i++) {
			Item Item = LLB.get(i);
			if(Item.getItemName().equals(name)) {
				return Item;
			}
		}
		return null;
	}
	/**
	 * 加载字符串
	 * @param reqContent
	 */
	public void open(String reqContent) {
		this.reqContent = reqContent;
		pages = reqContent.split("\\r\\n\\f");
	}
	
    /**
     * 关闭字符串
     */
	public void close() {
		this.reqContent = "";
		this.pages= null;
		this.LL = null;
		this.LLB = null;
		//this.LLF = null;
	}
	
}
