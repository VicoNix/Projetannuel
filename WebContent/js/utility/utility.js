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
	
	element.style.display = visible ? "" : "none";
	
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

//returns the 1st node with the tag named 'tagName' where the attributeToTest has a value that
//matches valueToMatch
function findFirstNode(root, tagName, attributeToTest, valueToMatch)
{
	var tagElements = root.getElementsByTagName(tagName); 
	for(var i=0,j=tagElements.length; i<j; i+=1)
	{ 
		if(tagElements[i].getAttribute(attributeToTest) == valueToMatch ) 
		{ 
			return tagElements[i]; 
		}
	}
}
