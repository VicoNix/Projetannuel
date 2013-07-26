<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=">
	<title>Mise en page XML</title>

	<link type="text/css" rel="stylesheet" href="css/codemirror.css" />
	<script type="text/javascript" src="js/codemirror.js"></script>
	<script type="text/javascript" src="js/xml.js"></script>
	<script type="text/javascript" src="js/closetag.js"></script>
	<script type="text/javascript" src="js/searchcursor.js"></script>
	<script type="text/javascript" src="js/formatting.js"></script>
	<script type="text/javascript" src="js/pa-codemirror.js"></script>

	<link rel="stylesheet" type="text/css" href="css/form_generator.css" />
	<script type="text/javascript" src="js/form_generator/bootstrap.js"></script>
	<script type="text/javascript"
		src="js/form_generator/generator_html.js"></script>
	<script type="text/javascript" src="js/form_generator/generator_xsl.js"></script>
	<script type="text/javascript" src="js/form_generator/generator.js"></script>
	<script type="text/javascript" src="js/utility/utility.js"></script>
	<script type="text/javascript" src="js/utility/request.js"></script>
	<script type="text/javascript" src="js/utility/cookies.js"></script>

	<script src="js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="js/jquery.easing.min.js" type="text/javascript"></script>
	<script src="js/jqueryFileTree/jqueryFileTree.js"
		type="text/javascript"></script>
	<link href="js/jqueryFileTree/jqueryFileTree.css" rel="stylesheet"
		type="text/css" media="screen" />

	<script type="text/javascript">
		$(document).ready(function() {

			//includeRequiredFiles();

			$('#fileTree').fileTree({
				root : '/',
				script : 'js/jqueryFileTree/connectors/jqueryFileTree.jsp'
			}, function(file) {
				createCookie('xml_datasource', file, 1);
				launchGenerator();
			});
		});
	</script>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<link href="css/menu_assets/styles.css" rel="stylesheet"
		type="text/css">

		<div id='cssmenu'>
			<ul>
				<li class='first'><a href='XMLGen'><span>Génération
							de fichier XML</span></a></li>
				<li class='active'><a href='XSLGen'><span>Génération
							de fichier XSL</span></a></li>
				<li><a href='Navigation'><span>Navigation</span></a></li>
				<li><a href='FormViewer'><span>Remplissage de
							formulaire</span></a></li>
				<li class='last'><a href='Contact'><span>Contact</span></a></li>
			</ul>
		</div>

		<h2 align="center">Génération de mise en forme XSL</h2>
		<div id="globalContainer">
			<div id="fileTree" style="width: 90px; float: left;"></div>
			<div id="container" style="display: none;">
				<div>
					<button id="btnSwap" onMouseUp="swap()">Afficher source</button>
				</div>
				<div id="xsl_container" style="display: none;">
					<textarea id="xsl_source"></textarea>
				</div>
				<div id="editor">
					<div id="form"></div>
					<div id="toolBox">
						<fieldset>
							<legend>Toolbox</legend>
							<span onMouseUp="addLabel()" class="item">Label</span><br />
							<span onMouseUp="addTextField()" class="item">TextField</span><br /> <span
								onMouseUp="addPasswordField()" class="item">PasswordField</span><br /> <span
								onMouseUp="addCombobox()" class="item">Combobox</span><br />
							<span onMouseUp="addRadioButton()" class="item">Bouton
								radio
							</span><br /> 
							<span onMouseUp="addEOL()" class="item">End of line</span><br />
							<span onMouseUp="addCheckbox()" class="item">Checkbox</span><br /> <span onMouseUp="addTable()"
								class="item">Table</span><br />
						</fieldset>
						<!-- 
						<fieldset disabled>
							<legend>Properties</legend>
							<label>Title:</label><input id="action" onblur="" type="text" />
							<label>Action:</label><input id="action" onblur="" type="text" />
							<label>Check function:</label><input id="onReturn" onblur=""
								type="text" />
						</fieldset>
						 -->
					</div>
					<div id="propertiesBox">
						<fieldset>
							<legend>Options</legend>
							<button>Generate form</button>
							<button>Preview form</button>
							<button>Remove element</button>
							<button onMouseUp="resetForm()">Reset</button>
							<button onMouseUp="displayBlock('toolBox')">Display
								properties</button>
							<button onMouseUp="hideBlock('toolBox')">Hide properties</button>
						</fieldset>
						<fieldset id="basicProperties">
							<legend>Propriétés communes</legend>
							<label>Id:</label> <input id="currentId"
								onblur="updateSelectedId();" type="text" />
						</fieldset>
						<fieldset id="textProperties" style="display: none;">
							<legend>Propriétés texte</legend>
							<label>Texte:</label> <input id="defaultValue" onblur="updateSelectedValue();"
								type="text" />
						</fieldset>
						<fieldset id="comboProperties" style="display: none;">
							<legend>Datasource</legend>
							<label>Url:</label><input id="dataSourceUrl"
								onblur="updateDatasourceUrl();" type="text" /> <label>Static:</label><input
								id="defaultValue" onblur="updateDatasourceFromText();"
								type="text" /> <label>Function:</label><input id="" onblur=""
								type="text" />
						</fieldset>
						<fieldset id="comboProperties" style="display: none;">
							<legend>Datasource</legend>
							<label>Url:</label><input id="dataSourceUrl"
								onblur="updateDatasourceUrl();" type="text" /> <label>Static:</label><input
								id="defaultValue" onblur="updateDatasourceFromText();"
								type="text" /> <label>Function:</label><input id="" onblur=""
								type="text" />
						</fieldset>
						<fieldset id="checkboxProperties" style="display: none;">
							<legend>Options radio</legend>
							<label>Groupe:</label><input id="radioGroup"
								onblur="updateRadioGroup();" type="text" />
						</fieldset>
						<fieldset id="radioProperties" style="display: none;">
							<legend>Options radio</legend>
							<label>Groupe:</label><input id="radioGroup"
								onblur="updateRadioGroup();" type="text" />
						</fieldset>
						<fieldset id="tableProperties" style="display: none;">
							<legend>Options de tableau</legend>
							<label>Nouvelle</label><input id="radioGroup"
								onblur="updateRadioGroup();" type="text" />
						</fieldset>
					</div>
				</div>
			</div>
		</div>
</body>
</html>