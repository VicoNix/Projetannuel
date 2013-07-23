package com.project.createview;

public class Champ {
	
	private String name;
	private String type;
	private boolean primary_key;
	private String foreign_key;
	private String table;
	
	
	public Champ() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Champ(String name, String type, boolean primary_key,String foreign_key) {
		super();
		this.name = name;
		this.type = type;
		this.primary_key = primary_key;
		this.foreign_key = foreign_key;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isPrimary_key() {
		return primary_key;
	}


	public void setPrimary_key(boolean primary_key) {
		this.primary_key = primary_key;
	}


	public String getForeign_key() {
		return foreign_key;
	}


	public void setForeign_key(String foreign_key) {
		this.foreign_key = foreign_key;
	}
	
	
	
	
	
	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}


	public String toString(){
		return table +" "+name+" "+type+" "+primary_key+" "+foreign_key+"\n";
	}
	
	
	

}
