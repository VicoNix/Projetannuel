<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    

<%@ page import="java.util.ArrayList" %>
<% com.project.servlets.Tables notreBean;%>
<!DOCTYPE html>

<html>
    <head>

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
				
				$('#fileTreeDemo_1').fileTree({ root: '/', script: 'js/jqueryFileTree/connectors/jqueryFileTree.jsp' }, function(file) { 
					document.getElementById('file').value=file;
					document.getElementById('Navigationform').submit();
				});
			});
		</script>


	</head>
	
	<body>
 <%@ include file="Header.jsp"%>
     <link href="css/menu_assets/styles.css" rel="stylesheet" type="text/css">

<div id='cssmenu'>
<ul>
   <li class='first'><a href='XMLGen'><span>Génération de fichier XML</span></a></li>
   <li><a href='XSLGen'><span>Génération de fichier XSL</span></a></li>
   <li class='active'><a href='Navigation'><span>Navigation</span></a></li>
   <li ><a href='FormViewer'><span>Remplissage de formulaire</span></a></li>
   <li class='last'><a href='Contact'><span>Contact</span></a></li>
</ul>
</div>
<script>
function refreshHTML()
{
document.getElementById('xmlajax').value=document.getElementById('codexml').value;
document.getElementById('xslajax').value=document.getElementById('codexsl').value;
}
</script>
<h2><p align="center">Navigation</p></h2>
		<div class="example">
			<h2></h2>

			F11 : Plein Ecran</a><form method="POST" action="Navigation" id="Navigationform">
<input type="hidden" name="file" id="file"/>
<input type="hidden" name="xmlajax" id="xmlajax"/>
<input type="hidden" name="xslajax" id="xslajax"/>
<input value="Actualiser HTML" type="submit" onclick="refreshHTML();">
	</form>	<br>
	</div>
	
		<br><br>
<style>
table.dispo td {
  width:100%;
}
</style>
	<table class="dispo">
		<tr>
			<td></td>
			<td>XML <a href="javascript:autoFormatSelection(xmleditor)">
					Formater la selection </td>
			<td>XSL <a href="javascript:autoFormatSelection(xsleditor)">
					Formater la selection </td>
		</tr>
		<tr>
			<td><div id="fileTreeDemo_1" class="demo"></div></td>
			<td><textarea id="codexml" name="codexml" rows="5"><%
            String attribut = (String) request.getAttribute("filecontentxml");
            if(attribut!=null)
            {
            out.println( attribut );
            }
            %>
				</textarea> <script>
		var xmleditor = CodeMirror.fromTextArea(document.getElementById("codexml"), {
			lineNumbers:  "true",
			mode: "xml",
			autoCloseTags: true,
			autofocus: "true",
			extraKeys: {
		        "F11": function(cm) {
		          setFullScreen(cm, !isFullScreen(cm));
		        },
		        "Esc": function(cm) {
		          if (isFullScreen(cm)) setFullScreen(cm, false);
		        }
		      }
			});
		xmleditor.on("cursorActivity", function() {
			xmleditor.matchHighlight("CodeMirror-matchhighlight");
			});
</script></td>
			<td><textarea id="codexsl" name="codexsl" rows="5"><%
            attribut = (String) request.getAttribute("filecontentxsl");
            if(attribut!=null)
            {
            out.println( attribut );
            }
            %>
				</textarea> <script>
		var xsleditor = CodeMirror.fromTextArea(document.getElementById("codexsl"), {
			lineNumbers:  "true",
			mode: "xml",
			autoCloseTags: true,
			autofocus: "true",
			extraKeys: {
		        "F11": function(cm) {
		          setFullScreen(cm, !isFullScreen(cm));
		        },
		        "Esc": function(cm) {
		          if (isFullScreen(cm)) setFullScreen(cm, false);
		        }
		      }
			});
		xsleditor.on("cursorActivity", function() {
			xsleditor.matchHighlight("CodeMirror-matchhighlight");
			});
</script></td>
		</tr>
		<tr>
			<td></td>
			<td>HTML</td>
			<td>XSD <a href="javascript:autoFormatSelection(xsdeditor)">
					Formater la selection </td>
		</tr>
		<tr>
			<td></td>
			<td><iframe id="HTMLframe"
					src="<%
            attribut = (String) request.getAttribute("filecontenthtml");
            if(attribut!=null)
            {
            out.print( attribut );
            }
            %>"
					width="500px" height="300px"></iframe></td>
			<td><textarea id="codexsd" name="codexsd" rows="5"><%
            attribut = (String) request.getAttribute("filecontentxsd");
            if(attribut!=null)
            {
            out.println( attribut );
            }
            %>
				</textarea> <script>
		var xsdeditor = CodeMirror.fromTextArea(document.getElementById("codexsd"), {
			lineNumbers:  "true",
			mode: "xml",
			autoCloseTags: true,
			autofocus: "true",
			extraKeys: {
		        "F11": function(cm) {
		          setFullScreen(cm, !isFullScreen(cm));
		        },
		        "Esc": function(cm) {
		          if (isFullScreen(cm)) setFullScreen(cm, false);
		        }
		      }
			});
		xsdeditor.on("cursorActivity", function() {
			xsdeditor.matchHighlight("CodeMirror-matchhighlight");
			});
			        function getSelectedRange(editor) {
            return { from: editor.getCursor(true), to: editor.getCursor(false) };
        }

        function autoFormatSelection(editor) {
            var range = getSelectedRange(editor);
            editor.autoFormatRange(range.from, range.to);
            CodeMirror.commands['goPageUp'](editor);

        }
</script></td>
		</tr>
	</table>




</body>
</html>