function displayBlock(blockId)
{
	document.getElementById(blockId).style.display = "inline-block";
}

function hideBlock(blockId)
{
	document.getElementById(blockId).style.display = "none";
}

function parseXML(xmlText)
{
	var xmlDoc;
	
	if (window.DOMParser)
	{
		parser=new DOMParser();
		xmlDoc=parser.parseFromString(txt,"text/xml");
	}
	else // Internet Explorer
	{
		xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async=false;
		xmlDoc.loadXML(txt); 
	}
	
	return xmlDoc;
}