
var fieldCount = 0;
var newId = 'field_' + fieldCount;
var selectedId;
var currentElement;
var currentType;
var xmlDatasource;

function resetForm(xml)
{
	fieldCount = 0;
	selectedId = null;
	currentElement = null;
	currentType = null;
	
	xmlDatasource = xml;
	
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

	rowPattern.setAttribute("select", prompt("Source de données:", ""));

	var nbElems = prompt("Combien d'éléments dans le tableau ?", 1);

	for (var i = 0; i < nbElems; i++)
	{
		tmpCell = document.createElement("th");
		tmpCell.innerHTML = prompt("En-tête n°"+ (i+1) + ": ");

		headersRow.appendChild(tmpCell);

		tmpCell = document.createElement("td");
		tmpCell.innerHTML = prompt("Attribut n°"+ (i+1) + ": ");

		dataRow.appendChild(tmpCell);
	}

	rowPattern.appendChild(dataRow);

	currentElement.appendChild(headersRow);
	currentElement.appendChild(rowPattern);

	document.getElementById("form").appendChild(currentElement);

	displaySelectedProperties("table");
}

function addLabel()
{
	updateNewId("label");

	currentElement = document.createElement("label");

	currentElement.setAttribute("id", newId);
	currentElement.innerHTML = "Label";
	
	XSL_addElement(currentElement);
	HTML_setDocument(xmlDocument.transformNode(XSL_getDocument()));

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

	XSL_addElement(currentElement.cloneNode(true));
	HTML_addElement(currentElement.cloneNode(true));

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

	XSL_addElement(currentElement.cloneNode(true));
	HTML_addElement(currentElement.cloneNode(true));

	displaySelectedProperties("input");
}

function addCombobox()
{
	updateNewId("combobox");

	currentElement = document.createElement("select");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);
	
	HTML_addElement(currentElement.cloneNode(true));
	
	// XSL specific processing
	//currentElement = XSL_addCombobox(newId, newId, "vehicules/immatriculation", ".");

	XSL_addElement( XSL_createCombobox(newId, newId, "vehicules/immatriculation", "."));
	HTML_setDocument(xmlDocument.transformNode(XSL_getDocument()));
	
	displaySelectedProperties("select");
}

function addRadioButton()
{
	updateNewId("radio");

	currentElement = document.createElement("input");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);
	currentElement.setAttribute("type", "radio");

	XSL_addElement(currentElement.cloneNode(true));
	HTML_addElement(currentElement.cloneNode(true));

	displaySelectedProperties("radio");
}

function addCheckbox()
{
	updateNewId("checkbox");

	currentElement = document.createElement("input");

	currentElement.setAttribute("id", newId);
	currentElement.setAttribute("name", newId);
	currentElement.setAttribute("type", "checkbox");

	XSL_addElement(currentElement.cloneNode(true));
	HTML_addElement(currentElement.cloneNode(true));

	displaySelectedProperties("checkbox");
}

function addEOL()
{
	updateNewId("end");
	
	currentElement = document.createElement("br");

	currentElement.setAttribute("id", newId);

	XSL_addElement(currentElement.cloneNode(true));
	HTML_addElement(currentElement.cloneNode(true));
	
	displaySelectedProperties("eol");
}

function displaySelectedProperties(type)
{
	currentType = type;
	selectedId = currentElement.id;
	document.getElementById("currentId").value = currentElement.id;
	document.getElementById("defaultValue").value = currentElement.value;

	if (undefined == document.getElementById("defaultValue").value
		|| null == document.getElementById("defaultValue").value)
	{
		if ("input" == type)
			document.getElementById("defaultValue").value = currentElement.value;
		else
			document.getElementById("defaultValue").innerHTML = currentElement.value;
	}

	// handle properties panels to display
	if ('radio' == type)
	{
		displayBlock('radioProperties');
		hideBlock('checkboxProperties');
		hideBlock('comboProperties');
	}
	else if ('select' == type)
	{
		hideBlock('radioProperties');
		hideBlock('checkboxProperties');
		displayBlock('comboProperties');
	}
	else if ('checkbox' == type)
	{
		hideBlock('radioProperties');
		displayBlock('checkboxProperties');
		hideBlock('comboProperties');
	}
	else
	{
		hideBlock('radioProperties');
		hideBlock('checkboxProperties');
		hideBlock('comboProperties');
	}
}

function updateSelectedId()
{
	var element = document.getElementById(selectedId);

	element.id = document.getElementById("currentId").value;
	element.setAttribute("name", document.getElementById("currentId").value);
	element.value = document.getElementById("defaultValue").value;

}

function updateSelectedValue()
{
	document.getElementById(selectedId).value  = document.getElementById("defaultValue").value;
}