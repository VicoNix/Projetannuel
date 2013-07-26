var xsl_document;
var xsl_stylesheet;
var xsl_template;
var xsl_form;
var string_representation;

var xsleditor;

function XSL_init()
{
	xsl_document = document.createElement("document");
	
	// create a real xsl document= document.createElement("xsl:stylesheet");
	xsl_stylesheet = document.createElement("xsl:stylesheet");
	xsl_stylesheet.setAttribute('version', '1.0');
	xsl_stylesheet.setAttribute('xmlns:xsl', 'http://www.w3.org/1999/XSL/Transform');
	
	xsl_document.appendChild(xsl_stylesheet);
	
	xsl_template = document.createElement("xsl:template");
	xsl_template.setAttribute('match', '/');
	
	xsl_stylesheet.appendChild(xsl_template);

	xsl_form = document.createElement('form');
	
	xsl_template.appendChild(xsl_form);
	
	xsleditor = CodeMirror.fromTextArea(document.getElementById("xsl_source"), {
		lineNumbers:  "true",
		mode: "text/html",
		htmlMode: true,
		autoCloseTags: true,
		autofocus: "false",
		reindentOnLoad: "true",
		readOnly: "true"
		});
}

function XSL_transform(xmlDatasource, callback)
{
	// use datasource filename, xsl and callback
	executeFormPostRequest(
			'XSLGen',
			'xslt|'+ readCookie('xml_datasource') + '|' + xsl_document.innerHTML,
			function(html)
			{
				if (undefined != callback)
				{
					callback(html);
				}
			});
}

function XSL_updateForm(viewVisible)
{
	if (!viewVisible)
	{
		string_representation = xsl_form.innerHTML;
		
		xsleditor.setValue(string_representation);
		
		document.getElementById("xsl_source").innerHTML = string_representation.
		replace (/</ig, '&lt;').
		replace (/>/ig, '&gt;');
		
		xsleditor.setValue(string_representation);
		
		var totalLines = xsleditor.lineCount();
	    var totalChars = xsleditor.getTextArea().value.length;
	    xsleditor.autoFormatRange({line:0, ch:0}, {line:totalLines, ch:totalChars});
	}
}

function XSL_resetForm()
{
	XSL_init();
}

function XSL_addElement(element, viewVisible)
{
	xsl_form.appendChild(element);
	
	XSL_updateForm(viewVisible);
}

function XSL_updateElement(id, newElement)
{
	var nodeToReplace = findFirstNode(xsl_form, newElement.tagName, 'id', id);
		
	xsl_form.replaceChild(newElement, nodeToReplace);
}

function XSL_deleteElement(id)
{
	if (undefined != xsl_form.firstChild)
	{
		xsl_form.removeChild(findFirstNode(xsl_form, '*', 'id', id));
		XSL_updateForm(viewVisible);
	}	
}

function XSL_createCombobox(newId, newId, datasource, displayedValue)
{
	//var combobox
	var xslDataLoop = document.createElement("xsl:for-each");

	xslDataLoop.setAttribute("select", datasource);

	var optionElement = document.createElement("option");
	var xslDataPattern = document.createElement("xsl:value-of");

	xslDataPattern.setAttribute("select", displayedValue);

	optionElement.appendChild(xslDataPattern);
	xslDataLoop.appendChild(optionElement);
	currentElement.appendChild(xslDataLoop);
		
	return currentElement;
}

function XSL_updateCombobox(id, datasource, displayedValue)
{
	var select = findFirstNode(xsl_form, 'select', 'id', id);
	
	select.innerHTML = '';
	
	//var combobox
	var xslDataLoop = document.createElement("xsl:for-each");

	xslDataLoop.setAttribute("select", datasource);

	var optionElement = document.createElement("option");
	var xslDataPattern = document.createElement("xsl:value-of");

	xslDataPattern.setAttribute("select", displayedValue);

	optionElement.appendChild(xslDataPattern);
	xslDataLoop.appendChild(optionElement);
	select.appendChild(xslDataLoop);
}

function XSL_getDocument()
{
	return xsl_document;
}
