package com.project.createview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.dao.DAOConfigurationException;
import com.project.dao.DAOFactory;

public class View {

	private String name;
	private ArrayList<Table>tables;
	private ArrayList<Champ>champs;
	private ArrayList<Champ>foreign_keys;
	private String request_view;
	private String request_trigger;


	public View() {
		super();
		// TODO Auto-generated constructor stub
	}


	public View(String name,ArrayList<Table> tables, ArrayList<Champ> champs,
			ArrayList<Champ> foreign_keys) {
		super();
		this.name = name;
		this.tables = tables;
		this.champs = champs;
		this.foreign_keys = foreign_keys;
	}


	public void createView(){
		formatName();
		formatRequest_View();
		execute_Query_View();
		formatRequest_Trigger();
		execute_Query_Trigger();
		enable_Trigger();
	}

	private void formatName(){
		StringBuilder name_provisoire = new StringBuilder(name.toUpperCase());
		StringBuilder name_final = new StringBuilder("VIEW_");

		for(int i=0;i<name_provisoire.length();i++){
			if( new String(""+name_provisoire.charAt(i)).matches("[A-Z0-9_]")   )name_final.append(name_provisoire.charAt(i));
		}

		if(name_final.length()<6)name_final.append(new String(""+(int) (Math.random()*1000000)  ));

		name = name_final.toString();	
	}

	private void formatRequest_View(){
		StringBuffer b = new StringBuffer();

		StringBuilder request = new StringBuilder("CREATE OR REPLACE FORCE VIEW "+name+" ");
		request.append("( ");

		for(Champ champ : champs){

			StringUtils su = new StringUtils();
			if(su.countMatches(request, champ.getName())>0){
				request.append("\""+champ.getName()+"_"+  (1+su.countMatches(request, champ.getName())) +"\", ");
			}
			else{
				request.append("\""+champ.getName()+"\", ");
			}


		}
		request.delete(request.length()-2, request.length());
		request.append(" ) AS SELECT ");

		for(Champ champ : champs){
			request.append(champ.getTable()+"."+champ.getName()+", ");
		}
		request.delete(request.length()-2, request.length());

		request.append(" FROM ");

		for(Table table : tables){
			request.append(table.getName()+", ");
		}
		request.delete(request.length()-2, request.length());



		if(foreign_keys.size()!=0){
			request.append(" WHERE ");
			boolean need_where = false;
			String table_pointe;


			for(Champ foreign_key : foreign_keys){
				//table_pointe = foreign_key.getForeign_key().substring(foreign_key.getForeign_key().indexOf(".")+1);
				table_pointe = foreign_key.getForeign_key().substring(0,foreign_key.getForeign_key().indexOf("."));
				System.out.println(table_pointe);
				System.out.println(tables.size());
				for(Table table_actuelle : tables){

					if(table_pointe.equals(table_actuelle.getName()) && ! foreign_key.getTable().equals( table_actuelle.getName())){
						need_where = true;
						request.append(foreign_key.getTable()+"."+foreign_key.getName()+" = "+foreign_key.getForeign_key()+" AND ");
					}
				}
			}

			if(need_where == false)request.delete(request.length()-7, request.length());
			else request.delete(request.length()-5, request.length());
		}

		request_view = request.toString();
	}

	private void formatRequest_Trigger(){
		StringBuilder request = new StringBuilder("");
		request.append("CREATE OR REPLACE TRIGGER \"INS_"+name+"\" ");
		request.append("INSTEAD OF INSERT ON "+name+" REFERENCING NEW AS NEW BEGIN ");

		for(Table table : tables){
			request.append("INSERT INTO "+table.getName()+" ");
			request.append("( ");

			for(Champ champ : champs){
				if(table.getName() == champ.getTable()){
					request.append(champ.getName()+", ");
				}
			}
			request.delete(request.length()-2, request.length());
			request.append(" ) ");
			request.append("VALUES ");
			request.append("( ");

			StringBuilder provisoire = new StringBuilder("");

			for(Champ champ : champs){
				if(table.getName() == champ.getTable()){

					StringUtils su = new StringUtils();
					if(su.countMatches(provisoire, champ.getName())>0){
						provisoire.append(":NEW."+champ.getName()+"_"+  (1+su.countMatches(provisoire, champ.getName())) +", ");
					}
					else{
						request.append(":NEW."+champ.getName()+", ");
					}

				}
			}
			request.append(provisoire);
			request.delete(request.length()-2, request.length());
			request.append(" ); ");

		}
		request.append("END INS_"+name+";");


		request_trigger = request.toString();
	}

	private void execute_Query_View(){
		try {

			DAOFactory df;
			String requete=request_view;
			ResultSet rs = null;
			df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			Statement state = con.createStatement();
			rs = state.executeQuery(requete);
			con.close();


		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void execute_Query_Trigger(){
		try {

			DAOFactory df;
			String requete=request_trigger;
			ResultSet rs = null;
			df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			Statement state = con.createStatement();
			rs = state.executeQuery(requete);

			con.close();
		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void enable_Trigger(){
		try {

			DAOFactory df;
			String requete="ALTER TRIGGER \"INS_"+name+"\" ENABLE";
			ResultSet rs = null;
			df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			Statement state = con.createStatement();
			rs = state.executeQuery(requete);
			con.close();

		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public ArrayList<String> getStructure(){
		ArrayList<String> resultat = new ArrayList<String>();
		resultat.add(name);


		try {

			DAOFactory df;
			//SELECT column_name FROM ALL_COL_COMMENTS WHERE TABLE_NAME = 'VIEW_TEST'
			String requete="SELECT COLUMN_NAME FROM ALL_COL_COMMENTS WHERE TABLE_NAME = '"+name+"'";
			ResultSet rs = null;
			df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			Statement state = con.createStatement();
			rs = state.executeQuery(requete);

			while(rs.next()){
				resultat.add(rs.getString(1));
			}
			con.close();
			return resultat;
		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return resultat;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Table> getTables() {
		return tables;
	}


	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}


	public ArrayList<Champ> getChamps() {
		return champs;
	}


	public void setChamps(ArrayList<Champ> champs) {
		this.champs = champs;
	}


	public ArrayList<Champ> getForeign_keys() {
		return foreign_keys;
	}


	public void setForeign_keys(ArrayList<Champ> foreign_keys) {
		this.foreign_keys = foreign_keys;
	}


	public String getRequest_view() {
		return request_view;
	}


	public void setRequest_view(String request_view) {
		this.request_view = request_view;
	}


	public String getRequest_trigger() {
		return request_trigger;
	}


	public void setRequest_trigger(String request_trigger) {
		this.request_trigger = request_trigger;
	}










}
