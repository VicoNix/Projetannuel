package com.project.createview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.project.dao.DAOConfigurationException;
import com.project.dao.DAOFactory;

public class GenerateView {

	private ArrayList<String>names;
	private ArrayList<Table> tables;


	private  void buildTableNames(){


		try {
			names = new ArrayList<String>();
			DAOFactory df;
			String requete="SELECT table_name FROM user_tables ";
			ResultSet rs = null;
			df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			Statement state = con.createStatement();
			rs = state.executeQuery(requete);
			//ResultSetMetaData rsmd = rs.getMetaData();


			while(rs.next()){
				names.add(rs.getString(1));
				//System.out.println(rs.getObject(1));
			}

		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildTables(){
		buildTableNames();
		tables = new ArrayList<Table>();
		
		int compteur_de_tables;
		for(compteur_de_tables = 0; compteur_de_tables < names.size(); compteur_de_tables++){
			
			try {
				ArrayList<String>primary_keys = getPrimaryKeys(names.get(compteur_de_tables));
				HashMap<String,String>foreign_keys = getForeignKeys(names.get(compteur_de_tables));
				
				DAOFactory df;
				String requete="Select column_name,data_type from USER_TAB_COLUMNS WHERE table_name = '"+names.get(compteur_de_tables)+"'";
				ResultSet rs = null;
				df = DAOFactory.getInstance();
				Connection con = df.getConnection();
				Statement state = con.createStatement();
				rs = state.executeQuery(requete);
				
				ArrayList<Champ> champs = new ArrayList<Champ>();
				Champ champ;
				
				while(rs.next()){
					champ = new Champ(rs.getString(1),rs.getString(2),false,null );
					if(primary_keys.contains(rs.getString(1)))champ.setPrimary_key(true);
					if(foreign_keys.containsKey(rs.getString(1)))champ.setForeign_key(foreign_keys.get(rs.getString(1)));				
					champ.setTable(names.get(compteur_de_tables));
					champs.add(champ);
					
				}
				con.close();
				Table table = new Table(names.get(compteur_de_tables),champs);
				tables.add(table);

			} 
			catch (DAOConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<String> getPrimaryKeys(String table){
		ArrayList<String> primary_keys = new ArrayList<String>();
		try {
			DAOFactory df;
			String requete="SELECT ALL_CONS_COLUMNS.COLUMN_NAME ";
			requete = requete + "FROM ALL_CONS_COLUMNS,ALL_CONSTRAINTS ";
			requete = requete + "WHERE ALL_CONS_COLUMNS.TABLE_NAME = '"+table+"' ";
			requete = requete + "AND ALL_CONSTRAINTS.TABLE_NAME = '"+table+"' ";
			requete = requete + "AND ALL_CONSTRAINTS.CONSTRAINT_TYPE = 'P' ";
			requete = requete + "AND ALL_CONSTRAINTS.CONSTRAINT_NAME = ALL_CONS_COLUMNS.CONSTRAINT_NAME ";
			
			ResultSet rs = null;
			df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			Statement state = con.createStatement();
			rs = state.executeQuery(requete);
			while(rs.next()){
				primary_keys.add(rs.getString(1));
			}
			con.close();
			return primary_keys;
		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return primary_keys;

	}
	
	private HashMap<String, String> getForeignKeys(String table){
		
		HashMap<String, String>foreign_keys = new HashMap<String,String>();
		try {
			DAOFactory df;
			String requete="SELECT ALL_CONS_COLUMNS.COLUMN_NAME,ALL_CONSTRAINTS.R_CONSTRAINT_NAME ";
			requete = requete + "FROM ALL_CONS_COLUMNS,ALL_CONSTRAINTS ";
			requete = requete + "WHERE ALL_CONS_COLUMNS.TABLE_NAME = '"+table+"' ";
			requete = requete + "AND ALL_CONSTRAINTS.CONSTRAINT_TYPE = 'R' ";
			requete = requete + "AND ALL_CONSTRAINTS.CONSTRAINT_NAME = ALL_CONS_COLUMNS.CONSTRAINT_NAME ";
			
			
			ResultSet rs = null;
			df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			Statement state = con.createStatement();
			rs = state.executeQuery(requete);
			while(rs.next()){
				foreign_keys.put(rs.getString(1), rs.getString(2));
				
			}
			con.close();
			
			if(!foreign_keys.isEmpty()){
				
				for(String key : foreign_keys.keySet()){
					DAOFactory df2;
					String requete2="SELECT TABLE_NAME,COLUMN_NAME FROM ALL_CONS_COLUMNS WHERE CONSTRAINT_NAME = '"+foreign_keys.get(key)+"'";
					
					
					ResultSet rs2 = null;
					df2 = DAOFactory.getInstance();
					Connection con2 = df2.getConnection();
					Statement state2 = con2.createStatement();
					rs2 = state2.executeQuery(requete2);
					while(rs2.next()){
						foreign_keys.put(key,rs2.getString(1)+"."+rs2.getString(2));
						
					}
					con2.close();
				}	
			}
			return foreign_keys;
		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return foreign_keys;
	}
	

	public ArrayList<String> getNames() {
		return names;
	}


	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	
	



}
