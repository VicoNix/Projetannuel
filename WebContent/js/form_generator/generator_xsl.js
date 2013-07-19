var xsl_form = document.createElement("form");
var xsleditor = CodeMirror.fromTextArea(document.getElementById("xsl_source"), {
	lineNumbers:  "true",
	mode: "text/html",
	htmlMode: true,
	autoCloseTags: true,
	autofocus: "true",
	reindentOnLoad: "true",
	readOnly: "true"
	});

function XSL_updateForm()
{
	var string_representation = xsl_form.innerHTML;
	
	xsleditor.setValue(string_representation);
	
	document.getElementById("xsl_source").innerHTML = string_representation.
	replace (/</ig, '&lt;').
	replace (/>/ig, '&gt;');
	
	xsleditor.setValue(string_representation);
	
	var totalLines = xsleditor.lineCount();
    var totalChars = xsleditor.getTextArea().value.length;
    xsleditor.autoFormatRange({line:0, ch:0}, {line:totalLines, ch:totalChars});
}

function XSL_resetForm()
{
	xsl_form.innerHTML = '';
	
	XSL_updateForm();
}

function XSL_addElement(element)
{
	xsl_form.appendChild(element);
	
	XSL_updateForm();
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

function XSL_getDocument()
{
	return xsl_form;
}
