
function HTML_resetForm()
{
	document.getElementById("form").innerHTML = '';
}

function HTML_addElement(element)
{
	document.getElementById("form").appendChild(element);
}

function HTML_updateForm()
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

function HTML_setDocument(html)
{
	document.getElementById("form").innerHTML = html;
}