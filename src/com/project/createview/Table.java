package com.project.createview;

import java.util.ArrayList;

public class Table {
	
	private String name;
	private ArrayList<Champ> champs;
	
	public Table() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Table(String name, ArrayList<Champ> champs) {
		super();
		this.name = name;
		this.champs = champs;
	}
	
	public ArrayList<Champ> getForeignKeys(){
		ArrayList<Champ> foreignKeys = new ArrayList<Champ>();
		
		for(int i=0;i<champs.size();i++){
			if(champs.get(i).getForeign_key()!=null)foreignKeys.add(champs.get(i));
		}
		
		return foreignKeys;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Champ> getChamps() {
		return champs;
	}

	public void setChamps(ArrayList<Champ> champs) {
		this.champs = champs;
	}
	
	public String toString(){
		String result = "name : "+name+"\n";
		for(Champ champ : champs)result+=champ.toString();
		return result;
	}
	
	
	

}
