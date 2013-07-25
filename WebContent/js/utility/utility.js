function displayBlock(blockId)
{
	changeVisibility(blockId, true);
}

function hideBlock(blockId)
{
	changeVisibility(blockId, false);
}

function changeVisibility(blockId, visible)
{
	var element = document.getElementById(blockId);
	
	element.style.display = visible ? "inline-block" : "none";
	
	if (visible && undefined != element.focus)
	{
		element.focus();
	}
}

function parseXML(xmlText)
{
	var xmlDoc;
	
	if (window.DOMParser)
	{
		parser=new DOMParser();
		xmlDoc=parser.parseFromString(xmlText,"text/xml");
	}
	else // Internet Explorer
	{
		xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async=false;
		xmlDoc.loadXML(xmlText); 
	}
	
	return xmlDoc;
}