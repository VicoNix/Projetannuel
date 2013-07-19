<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=">
		<title>Mise en page XML</title>
		
		<link type="text/css" rel="stylesheet" href="css/codemirror.css"/>
		<script type="text/javascript" src="js/codemirror.js"></script>
		<script type="text/javascript" src="js/xml.js"></script>
		<script type="text/javascript" src="js/closetag.js"></script>
		<script type="text/javascript" src="js/searchcursor.js"></script>
		<script type="text/javascript" src="js/formatting.js"></script>
		<script type="text/javascript" src="js/pa-codemirror.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/form_generator.css" />
		<script type="text/javascript" src="js/form_generator/bootstrap.js" ></script>
		
		<script src="js/jquery-1.10.1.min.js" type="text/javascript"></script>
		<script src="js/jquery.easing.min.js" type="text/javascript"></script>
		<script src="js/jqueryFileTree/jqueryFileTree.js" type="text/javascript"></script>
		<link href="js/jqueryFileTree/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
		
		<script type="text/javascript">
			
			$(document).ready( function() {
				
				includeRequiredFiles();
				
				$('#fileTree').fileTree({ root: '/', script: 'js/jqueryFileTree/connectors/jqueryFileTree.jsp' }, function(file) { 
					
					executeFormPostRequest(
							"XSLGen",
							"file|"+file,
							function(responseText)
							{
								launchGenerator(responseText);
							}
						);
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
   <li class='active'><a href='XSLGen'><span>Génération de fichier XSL</span></a></li>
   <li><a href='Navigation'><span>Navigation</span></a></li>
   <li ><a href='FormViewer'><span>Remplissage de formulaire</span></a></li>
   <li class='last'><a href='Contact'><span>Contact</span></a></li>
</ul>
</div>

		<h2 align="center">Génération de mise en forme XSL</h2>
		<div id="fileTree" style="width: 100px;"></div>
		<div id="container" style="display: none;">
			<form id="form"></form>
			<textarea id="xsl_source" ></textarea>
			<div id="toolBox">
				<fieldset>
					<legend>Toolbox</legend>
					<span onMouseUp="addLabel()" class="item"><label>Label</label></span><br />
					<span onMouseUp="addTextField()" class="item"><input type="text" value="TextField" disabled  /></span><br />
					<span onMouseUp="addPasswordField()" class="item"><input type="password" value="PasswordField" disabled  /></span><br />
					<span onMouseUp="addCombobox()" class="item"><select disabled><option>Combobox</option></select></span><br />
					<span onMouseUp="addRadioButton()" class="item">Bouton radio<input type="radio" disabled  /></span><br />
					<span onMouseUp="addCheckbox()" class="item">Checkbox<input type="checkbox" disabled  /></span><br />
					<span onMouseUp="addTable()" class="item">Table</span><br />
				</fieldset>
				<fieldset disabled>
					<legend>Properties</legend>
					<label>Title:</label><input id="action" onblur="" type="text" />
					<label>Action:</label><input id="action" onblur="" type="text" />
					<label>Check function:</label><input id="onReturn" onblur="" type="text"  />
				</fieldset>
			</div>
			<div id="propertiesBox">
				<fieldset>
					<legend>Options</legend>
					<button>Generate form</button>
					<button>Preview form</button>
					<button>Remove element</button>
					<button onMouseUp="resetForm()">Reset</button>
					<button onMouseUp="addEOL()">End of line</button>
					
					<button onMouseUp="displayBlock('toolBox')">Display properties</button>
					<button onMouseUp="hideBlock('toolBox')">Hide properties</button>
				</fieldset>
				<fieldset id="basicProperties">
					<legend> Basic properties</legend>
					<label>Id:</label><input id="currentId" onblur="updateSelectedId();" type="text" />
					<label>Default value:</label><input id="defaultValue" onblur="updateSelectedValue();" type="text"  />
					<label>Label:</label><input id="" onblur="updateSelected();" type="text"  />
				</fieldset>
				<fieldset id="comboProperties" style="display: none;">
					<legend>Datasource</legend>
					<label>Url:</label><input id="dataSourceUrl" onblur="updateDatasourceUrl();" type="text" />
					<label>Static:</label><input id="defaultValue" onblur="updateDatasourceFromText();" type="text"  />
					<label>Function:</label><input id="" onblur="" type="text"  />
				</fieldset>
				<fieldset id="checkboxProperties"  style="display: none;">
					<legend>Options radio</legend>
					<label>Groupe:</label><input id="radioGroup" onblur="updateRadioGroup();" type="text" />
				</fieldset>
				<fieldset id="radioProperties"  style="display: none;">
					<legend>Options radio</legend>
					<label>Groupe:</label><input id="radioGroup" onblur="updateRadioGroup();" type="text" />
				</fieldset>
				<fieldset id="tableProperties" style="display: none;" >
					<legend>Options de tableau</legend>
					<label>Nouvelle</label><input id="radioGroup" onblur="updateRadioGroup();" type="text" />
				</fieldset>
			</div>
		</div>
	</body>
</html>