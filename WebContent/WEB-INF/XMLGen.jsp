<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    

<%@ page import="java.util.ArrayList" %>
<% com.project.servlets.Tables notreBean;%>
<!DOCTYPE html>

<html>
    <head>

<script language="javascript" src="js/pa-ajax.js"></script>

        <meta charset="utf-8" />
        <title>BDDTOXML</title>
    </head>

    <body>
    <%@ include file="Header.jsp"%>
         <link href="css/menu_assets/styles.css" rel="stylesheet" type="text/css">

<div id='cssmenu'>
<ul>
   <li class='active'><a href='XMLGen'><span>Génération de fichier XML</span></a></li>
   <li><a href='XSLGen'><span>Génération de fichier XSL</span></a></li>
   <li><a href='Navigation'><span>Navigation</span></a></li>
   <li ><a href='FormViewer'><span>Remplissage de formulaire</span></a></li>
   <li class='last'><a href='Contact'><span>Contact</span></a></li>
</ul>
</div>
        <h2><p align="center">Génération de fichier XML</p></h2>

		<br>Sélectionnez les champs à afficher<table style="height: 121px; width: 145px;">
			<tr>
				
				
			</tr>
			<tr>
				<td><select id="table" name="table" size="10" onChange="envoyerRequete();" onclick="envoyerRequete();">
				<% 
				notreBean = (com.project.servlets.Tables) request.getAttribute("tables");
            ArrayList<String> listetable = notreBean.getListeTable();
            for(int i=0;i<listetable.size();i++)
            {
            out.println( "<option>"+listetable.get(i)+"</option>" );
            }
            %>
			</select></td>
				<td><select multiple id="champs" name="champs" size="10">
				<% 
            ArrayList<String> listechamps = notreBean.getListechamp();
            if(listechamps!=null)
            {
            for(int i=0;i<listechamps.size();i++)
            {
            out.println( "<option>"+listechamps.get(i)+"</option>" );
            }
            }
            else
            {
            out.println( "<option></option>");
            }
            %>
            </select></td>
			</tr> 
		</table>
		<br><br>
	
		<script>
		function getSelectValue(selectId)
		{
			var elmt = document.getElementById(selectId);

			var values = new Array();
			for(var i=0; i< elmt.options.length; i++)
			{
				if(elmt.options[i].selected == true)
				{
					values[values.length] = elmt.options[i].value; 
				}
			}	

			return values.toString();
		}
		
		function getXML()
		{
			document.getElementById("xml").value = getSelectValue('champs');
			document.getElementById("where").value = "WHERE "+document.getElementById('condtable').options[document.getElementById('condtable').selectedIndex].value+"."+document.getElementById('condchamps').options[document.getElementById('condchamps').selectedIndex].value+document.getElementById('condop').options[document.getElementById('condop').selectedIndex].value+"'"+document.getElementById('condition').value+"'";
			document.getElementById('XMLGenform').submit();
		}
</script>
		<br/>
		Condition de génération :
		
		<select id="condtable" name="condtable" onChange="envoyerRequetecond();" onclick="envoyerRequetecond();">
		<% 
           for(int i=0;i<listetable.size();i++)
           {
           	out.println( "<option>"+listetable.get(i)+"</option>" );
           }
		
           %>
		</select>
		<select id="condchamps" name="condchamps">
		</select> 
		<select id="condop" name="condop">
		<option>=</option>
		<option><</option>
		<option>></option>
		<option><=</option>
		<option>>=</option>
		<option>!=</option>
		</select>
		
		
		<input id="condition" name="condition" onChange="envoyerRequetecondition();" onclick="envoyerRequetecondition();"/>
		<form method="POST" action="XMLGen" id="XMLGenform">
		<input type="hidden"  name="xml" id="xml">
		<input type="hidden"  name="where" id="where">
		</form>
		<input type="submit" onclick="getXML();" value="Génerer le XML">
	
    </body>
</html>