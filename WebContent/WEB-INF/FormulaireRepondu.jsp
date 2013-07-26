<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    

<%@ page import="java.util.ArrayList" %>
<% com.project.servlets.Tables notreBean;%>
<!DOCTYPE html>

<html>
    <head>
    
    <style>
    
    
  .box-table-a
{
	position:relative;
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	margin: 45px;
	width: 480px;
	text-align: left;
	border-collapse: collapse;
}
.box-table-a th
{
	font-size: 13px;
	font-weight: normal;
	padding: 8px;
	background: #b9c9fe;
	border-top: 4px solid #aabcfe;
	border-bottom: 1px solid #fff;
	color: #039;
}
.box-table-a td
{
	padding: 8px;
	background: #e8edff; 
	border-bottom: 1px solid #fff;
	color: #669;
	border-top: 1px solid transparent;
}
.box-table-a tr:hover td
{
	background: #d0dafd;
	color: #339;
}
</style>

<link type="text/css" rel="stylesheet" href="css/codemirror.css"/>
<script language="javascript" src="js/codemirror.js"></script>
<script language="javascript" src="js/xml.js"></script>
<script language="javascript" src="js/closetag.js"></script>
<script language="javascript" src="js/match-highlighter.js"></script>
<script language="javascript" src="js/searchcursor.js"></script>
<script language="javascript" src="js/formatting.js"></script>
<script language="javascript" src="js/pa-codemirror.js"></script>
<script language="javascript" src="js/pa-ajax.js"></script>
<script language="javascript" src="js/utility/request.js"></script>

<style type="text/css">
      .CodeMirror {border-top: 1px solid black; border-bottom: 1px solid black; background-color: white}
      span.CodeMirror-matchhighlight { background: #e9e9e9 }
      .CodeMirror-focused span.CodeMirror-matchhighlight { background: #e7e4ff; !important }
      
</style>
        <meta charset="utf-8" />
        <title>BDDTOXML</title>
		
		<script src="js/jquery-1.10.1.min.js" type="text/javascript"></script>
		<script src="js/jquery.easing.min.js" type="text/javascript"></script>
		<script src="js/jqueryFileTree/jqueryFileTree.js" type="text/javascript"></script>
		<link href="js/jqueryFileTree/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
		
		<script type="text/javascript">
			
			$(document).ready( function() {
				
				$('#fileTreeDemo_1').fileTree({ root: '/', script: 'js/jqueryFileTree/connectors/jqueryFileTreeHtml.jsp' }, function(file) { 
					document.getElementById('file').value=file;
					document.getElementById('FormViewerform').submit();
				});
			});
		</script>


	</head>
	
	
	<script type="text/javascript">
	var clicEnCours = false;
	var position_x = 0;
	var position_y = 0;
	var origineDiv_x = 0;
	var iExplorer = false;
	var deplacable = "";
	if (document.all) {
		iExplorer = true;
	}
	function boutonPresse(pDiv) {
		chaineX = document.getElementById(pDiv).style.left;
		chaineY = document.getElementById(pDiv).style.top;
		origineDiv_x = x - chaineX.substr(0, chaineX.length - 2);
		origineDiv_y = y - chaineY.substr(0, chaineY.length - 2);
		//document.getElementById(pDiv).innerHTML = document.getElementById(pDiv).style.left
		//+ '>' + origineDiv_y;
		clicEnCours = true;
		deplacable = pDiv;
		document.getElementById(deplacable).style.cursor = 'move';
		document.getElementById(deplacable).style.zIndex = 100;
		document.getElementById(deplacable).style.border = '1px #000000 dotted';
	}

	function boutonRelache(pDiv) {
		clicEnCours = false;
		document.getElementById(deplacable).style.cursor = 'default';
		document.getElementById(deplacable).style.zIndex = 1;
		document.getElementById(deplacable).style.border = '1px #000000 solid';
		deplacable = "";
	}

	function deplacementSouris(e) {

		x = (iExplorer) ? event.x + document.body.scrollLeft : e.pageX;
		y = (iExplorer) ? event.y + document.body.scrollTop : e.pageY;
		//document.title = x + '>' + y;

		if (clicEnCours && document.getElementById) {
			position_x = x - origineDiv_x;
			position_y = y - origineDiv_y;
			document.getElementById(deplacable).style.left = position_x;
			document.getElementById(deplacable).style.top = position_y;
		}
	}

	if (!iExplorer) {
		document.captureEvents(Event.MOUSEMOVE);
	}
	document.onmousemove = deplacementSouris;
	
	function ViewName() {
		var saisie = prompt("Comment souhaitez vous appeller votre vue","");
		
		while (saisie == ""){
			alert("Attention vous devez imperativement donner un nom a votre vue");
			saisie = prompt("Comment souhaitez vous appeller votre vue","");
		}
		
		document.getElementById('nom_vue').value=saisie;
		
	   }
</script>
	
	
	<body>
 <%@ include file="Header.jsp"%>
     <link href="css/menu_assets/styles.css" rel="stylesheet" type="text/css">

<div id='cssmenu'>
<ul>
   <li class='first'><a href='XMLGen'><span>Génération de fichier XML</span></a></li>
   <li><a href='XSLGen'><span>Génération de fichier XSL</span></a></li>
   <li ><a href='Navigation'><span>Navigation</span></a></li>
   <li class='active' ><a href='FormViewer'><span>Remplissage de formulaire</span></a></li>
      <li ><a href='CreateView'><span>Creation de vues</span></a></li>
   <li class='last'><a href='Contact'><span>Contact</span></a></li>
</ul>
</div>

<h3>Felicitations resultat enregistre</h3>

<table class="box-table-a" id="div0" onmousedown="boutonPresse('div0');" onmouseup="boutonRelache('div0');">

<thead>

<tr>
		
		<th scope="col">Resultat</th>
		
		</tr>
</thead>
<tbody>
	<% 
	out.println((String) request.getAttribute("tableau"));
	%>
	</tbody>
	</table>
</body>
</html>