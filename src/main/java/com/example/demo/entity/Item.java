package com.example.demo.entity;

public class Item {
	
	
	private String itemName;
	private Integer lineno;
	private Integer itemWidth;
	private Integer itemStart;
	private String itemType;
	
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getItemWidth() {
		return itemWidth;
	}
	
	public void setItemWidth(Integer itemWidth) {
		this.itemWidth = itemWidth;
	}
	public Integer getItemStart() {
		return itemStart;
	}
	public void setItemStart(Integer itemStart) {
		this.itemStart = itemStart;
	}
	public String getItemType() {
		return itemType;
	}
	public Integer getLineno() {
		return lineno;
	}
	public void setLineno(Integer lineno) {
		this.lineno = lineno;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public Item(String itemName, Integer lineno, Integer itemStart,Integer itemWidth, String itemType) {
		super();
		this.itemName = itemName;
		this.itemWidth = itemWidth;
		this.itemStart = itemStart;
		this.itemType = itemType;
		this.lineno = lineno;
		
	}
	public Item(String itemName, Integer itemStart,Integer itemWidth, String itemType) {
		super();
		this.itemName = itemName;
		this.itemWidth = itemWidth;
		this.itemStart = itemStart;
		this.itemType = itemType;
		
	}
	@Override
	public String toString() {
		return "Item [itemName=" + itemName + ", lineno=" + lineno+ ", itemWidth=" + itemWidth + ", itemStart=" + itemStart + ", itemType="
				+ itemType + "]";
	}
	
	
}
