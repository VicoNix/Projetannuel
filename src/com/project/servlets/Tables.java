package com.project.servlets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.dao.DAOConfigurationException;
import com.project.dao.DAOFactory;

public class Tables  {

	private ArrayList<String> listetable = new ArrayList<String>();
	private ArrayList<String> listechamp = new ArrayList<String>();
	private ArrayList<String> listevaleur = new ArrayList<String>(); 
	private ArrayList<String> listecondition = new ArrayList<String>();
	
	private Statement state;
	private DAOFactory df;
	private String selectedtable;
	private String selectedchamp;
	private String selectedcondition;
	
	public Tables()
	{
		try {
			try {
				df = DAOFactory.getInstance();
				Connection con = df.getConnection();
				//Création d'un objet Statement
				state = con.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result;
				if(df.getUrl().contains("mysql"))
				{
					 result= state.executeQuery("SHOW TABLES");
				}
				else
				{
					result = state.executeQuery("SELECT view_name FROM user_views");
				}
				
				while(result.next()){	
					listetable.add(result.getString(1));
				}
				
			} catch (SQLException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
				
			}
		} catch (DAOConfigurationException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getListechamp() {
		if(selectedtable!=null)
		{
			ResultSet result;
			try {
				if(df.getUrl().contains("mysql"))
				{
					result = state.executeQuery("SHOW COLUMNS FROM "+selectedtable);
				}
				else
				{
					result = state.executeQuery("select column_name from all_tab_columns where table_name = '"+selectedtable+"'");
				}
				while(result.next()){	
					listechamp.add(result.getString(1));
				}
			} catch (SQLException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
			
			return listechamp;
		}
		else
		{
		return null;
		}
	}

	public ArrayList<String> getListevaleur() {
		if(selectedchamp!=null)
		{
			ResultSet result;

			try {
					result = state.executeQuery("select "+selectedchamp+" from "+selectedtable);

				while(result.next()){	
					listevaleur.add(result.getString(1));
				}
			} catch (SQLException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
			
			return listevaleur;
		}
		else
		{
		return null;
		}
	}
	
	public ArrayList<String> getListecondition() {
		if(selectedchamp!=null)
		{
			ResultSet result;

			try {
					result = state.executeQuery("select distinct "+selectedchamp+" from "+selectedtable);

				while(result.next()){	
					listecondition.add(result.getString(1));
				}
			} catch (SQLException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
			
			return listecondition;
		}
		else
		{
		return null;
		}
	}
	
	public void setListechamp(ArrayList<String> listechamp) {
		this.listechamp = listechamp;
	}

	public ArrayList<String> getListeTable() {
		return listetable;
	}

	public void setListeTable(ArrayList<String> liste) {
		this.listetable = liste;
	}
	
	public String getSelectedtable() {
		return selectedtable;
	}

	public void setSelectedtable(String selectedtable) {
		this.selectedtable = selectedtable;
	}

	public void setSelectedchamp(String paramchamp) {
		this.selectedchamp = paramchamp;
		
	}
	
	public String getSelectedchamp() {
		return selectedchamp;
	}

	public void setSelectedcondition(String paramcondition) {
		// TODO Stub de la méthode généré automatiquement
		this.selectedcondition = paramcondition;
		}
}
