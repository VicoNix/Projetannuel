package com.project.createview;

import java.util.ArrayList;

public class Tables {
	
	private ArrayList<Table>tables;

	public Tables() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tables(ArrayList<Table> tables) {
		super();
		this.tables = tables;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	
	
	public String toString(){
		String result = "";
		for(Table table : tables)result+=table.toString();
		return result;
	}

}
