package com.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.createview.Champ;
import com.project.createview.Champ_Etranger;
import com.project.createview.GenerateView;
import com.project.createview.Table;
import com.project.createview.View;

public class CreateView extends HttpServlet {
	
	private com.project.createview.Tables tables = null;
	private View view = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(tables==null){
			GenerateView gv = new GenerateView();
			gv.buildTables();
			tables = new com.project.createview.Tables();
			tables.setTables(gv.getTables());
			
		}
		
		else{
			
		}
		
		request.setAttribute("tables", tables);
		//System.out.println(tables);
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CreateView.jsp" ).forward( request, response );
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		
		ArrayList<Table>tables_choisies = new ArrayList<Table>();
		ArrayList<Champ>champs_choisis = new ArrayList<Champ>();
		ArrayList<Champ>foreign_choisis = new ArrayList<Champ>();
		
		// System.out.println(request.getParameter("TAB_POTENTIEL.NOM_POTENTIEL"));
		
		for(int compteur_de_table=0; compteur_de_table<tables.getTables().size();compteur_de_table++){
			Table table = tables.getTables().get(compteur_de_table);
			
			for (int compteur_de_champs = 0; compteur_de_champs < table.getChamps().size(); compteur_de_champs++) {
				Champ champ = table.getChamps().get(compteur_de_champs);
				if(request.getParameterValues(table.getName()+"."+champ.getName()) != null){
					champs_choisis.add(champ);
					if(tables_choisies.contains(table)!= true){
						tables_choisies.add(table);
						foreign_choisis.addAll(table.getForeignKeys());
						
						
					}
					
				}
			}
		}
		
		
		view = new View(request.getParameter("nom_vue"),tables_choisies,champs_choisis,foreign_choisis);
		view.createView();
		
		request.setAttribute("structure", view.getStructure());
		this.getServletContext().getRequestDispatcher( "/WEB-INF/ViewCreated.jsp" ).forward( request, response );
		
		
	}

}
