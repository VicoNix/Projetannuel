
var fieldCount = 0;
var newId = 'field_' + fieldCount;
var selectedId;
var currentElement;
var currentType;
var xmlDatasource;
var viewVisible = true;

//xmlDatasource
function launchGenerator()
{
	resetForm();

	displayBlock("container");
}

function resetForm()
{
	fieldCount = 0;
	selectedId = null;
	currentElement = null;
	currentType = null;

	XSL_resetForm();
	HTML_resetForm();
}

function updateNewId(field_)
{
	if (undefined == field_)
		field_ = 'field_';

	fieldCount++;
	newId = field_ + fieldCount;
}

function updateHTML()
{
	XSL_transform(xmlDatasource, function(result){
		HTML_setDocument(result);
	});
}

function addTable()
{
	updateNewId("table");

	currentElement = document.createElement("table");
	currentElement.setAttribute("id", newId);

	// create headers row
	var headersRow = document.createElement("tr");
	var dataRow = document.createElement("tr");
	var rowPattern = document.createElement("xsl:for-each");
	var tmpCell;
	var xslDataPattern;

	rowPattern.setAttribute("select", prompt("Source de données:", ""));

	var nbElems = prompt("Combien d'éléments dans le tableau ?", 1);

	for (var i = 0; i < nbElems; i++)
	{
		tmpCell = document.createElement("th");
		tmpCell.innerHTML = prompt("En-tête n°"+ (i+1) + ": ");

		headersRow.appendChild(tmpCell);

		tmpCell = document.createElement("td");
		xslDataPattern = document.createElement("xsl:value-of");
		xslDataPattern.setAttribute("select", prompt("Champ n°"+ (i+1) + ": "));

		tmpCell.appendChild(xslDataPattern);

		dataRow.appendChild(tmpCell);
	}

	rowPattern.appendChild(dataRow);

	currentElement.appendChild(headersRow);
	currentElement.appendChild(rowPattern);

	XSL_addElement(currentElement, viewVisible);

	updateHTML();

	displaySelectedProperties("table");
}

function addLabel()
{
	updateNewId("label");

	currentElement = document.createElement("label");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("onMouseUp", "selectNode(\""+newId+"\", \"label\")");

	currentElement.innerHTML = "Label";

	XSL_addElement(currentElement, viewVisible);

	updateHTML();

	displaySelectedProperties("label");
}

function addTextField()
{ 
	updateNewId("textfield");

	currentElement = document.createElement("input");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);
	currentElement.setAttribute("type", "text");
	currentElement.setAttribute("value", "");
	currentElement.setAttribute("onMouseUp", "selectNode(\""+ newId +"\", \"input\")");

	XSL_addElement(currentElement, viewVisible);

	updateHTML();

	displaySelectedProperties("input");
}

function addPasswordField()
{
	updateNewId("password");

	currentElement = document.createElement("input");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);
	currentElement.setAttribute("type", "password");
	currentElement.setAttribute("value", "");
	currentElement.setAttribute("onMouseUp", "selectNode(\""+ newId +"\", \"input\")");

	XSL_addElement(currentElement, viewVisible);

	updateHTML();

	displaySelectedProperties("input");
}

function addCombobox()
{
	updateNewId("combobox");

	currentElement = document.createElement("select");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);

	XSL_addElement(XSL_createCombobox(newId, newId, "*/*/*[1]", "."), viewVisible);

	updateHTML();

	displaySelectedProperties("select");
}

function addRadioButton()
{
	updateNewId("radio");

	currentElement = document.createElement("input");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);
	currentElement.setAttribute("type", "radio");
	currentElement.setAttribute("onMouseUp", "selectNode(\""+ newId +"\", \"radio\")");

	XSL_addElement(currentElement, viewVisible);

	updateHTML();

	displaySelectedProperties("radio");
}

function addCheckbox()
{
	updateNewId("checkbox");

	currentElement = document.createElement("input");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);
	currentElement.setAttribute("type", "checkbox");
	currentElement.setAttribute("onMouseUp", "selectNode(\""+ newId +"\", \"checkbox\")");

	XSL_addElement(currentElement, viewVisible);

	updateHTML();

	displaySelectedProperties("checkbox");
}

function addEOL()
{
	updateNewId("end");

	currentElement = document.createElement("br");

	currentElement.setAttribute("id", newId);

	XSL_addElement(currentElement, viewVisible);

	updateHTML();

	displaySelectedProperties("eol");
}

function displaySelectedProperties(type)
{
	currentType = type;
	selectedId = currentElement.id;
	document.getElementById("currentId").value = currentElement.id;

	if ("input" == type)
	{
		document.getElementById("defaultValue").value = currentElement.value;
		document.getElementById("currentName").value = currentElement.getAttribute("name");
	}	
	else
	{
		document.getElementById("defaultValue").value = currentElement.innerHTML;
		document.getElementById("currentName").value = "";
	}

	// handle properties panels to display
	displayBlock('basicProperties');

	if ('input' == type || 'label' == type)
	{
		displayBlock('textProperties');
		hideBlock('tableProperties');
		hideBlock('comboProperties');
	}
	else if ('radio' == type)
	{
		hideBlock('textProperties');
		hideBlock('tableProperties');
		hideBlock('comboProperties');
	}
	else if ('select' == type)
	{
		hideBlock('textProperties');
		hideBlock('tableProperties');
		displayBlock('comboProperties');
	}
	else if ('table' == type)
	{
		hideBlock('textProperties');
		displayBlock('tableProperties');
		hideBlock('comboProperties');
	}
	else
	{
		hideBlock('textProperties');
		hideBlock('tableProperties');
		hideBlock('comboProperties');
	}
}

function selectNode(nodeId, type)
{
	currentElement = document.getElementById(nodeId);

	displaySelectedProperties(type);
}

function deleteSelected()
{
	XSL_deleteElement(selectedId);

	hideBlock('basicProperties');
	hideBlock('textProperties');
	hideBlock('tableProperties');
	hideBlock('comboProperties');

	updateHTML();
}

function updateSelectedId()
{
	var element = document.getElementById(selectedId);

	element.id = document.getElementById("currentId").value;
	element.setAttribute("onMouseUp", "selectNode(\""+element.id +"\", \"label\")");

	XSL_updateElement(selectedId, element);

	selectedId = element.id;

	updateHTML();
}

function updateSelectedName()
{
	var element = document.getElementById(selectedId);

	element.setAttribute("name", document.getElementById("currentName").value);

	XSL_updateElement(selectedId, element);

	selectedId = element.id;

	updateHTML();
}

//Allows to change value (for text inputs) and label content
function updateSelectedValue()
{
	var elementToUpdate = document.getElementById(selectedId);

	if (undefined != elementToUpdate.value)
		elementToUpdate.setAttribute("value", document.getElementById("defaultValue").value);
	else
		elementToUpdate.innerHTML = document.getElementById("defaultValue").value;

	XSL_updateElement(selectedId, elementToUpdate);

	updateHTML();
}

function updateSelectedDatasource()
{
	var select = document.getElementById("xmlColumns");
	var option = select.options[select.selectedIndex];
	
	XSL_updateCombobox(document.getElementById("currentId").value, option.value, ".");

	updateHTML();
}

function updateSelectedAttribute(attributeName, fieldValueId)
{
	var elementToUpdate = document.getElementById(selectedId);

	elementToUpdate.setAttribute(attributeName, document.getElementById(fieldValueId).value);

	XSL_updateElement(selectedId, elementToUpdate);

	updateHTML();
}

//swaps between view display and editor display
function swap()
{
	changeVisibility('xsl_container', viewVisible);
	changeVisibility('editor', !viewVisible);

	viewVisible = !viewVisible;

	document.getElementById('btnSwap').innerHTML = viewVisible ? 'Afficher source' : 'Afficher éditeur';

	XSL_updateForm(viewVisible);
}
