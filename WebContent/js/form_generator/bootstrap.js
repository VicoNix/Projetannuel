function include(src, attributes)
{
	try {
		attributes = attributes || {};
		attributes.type = "text/javascript";
		attributes.src = src;

		var script = document.createElement("script");
		for(aName in attributes)
			script[aName] = attributes[aName];

		document.getElementsByTagName("head")[0].appendChild(script);
		return true;
	} catch(e) { return false; }
}

Node.prototype.transformNode = function (oXslDom) {
    var oProcessor = new XSLTProcessor();
    oProcessor.importStylesheet(oXslDom);
    var oResultDom = oProcessor.transformToDocument(this);
    var sResult = oResultDom.xml;
    if (sResult.indexOf("<transformiix:result") > -1) {
        sResult = sResult.substring(sResult.indexOf(">") + 1, 
                                    sResult.lastIndexOf("<"));
    }
    return sResult;                
};

function includeRequiredFiles()
{
	include('js/form_generator/generator_html.js');
	include('js/form_generator/generator_xsl.js');
	include('js/form_generator/generator.js');
	include('js/utility/utility.js');
	include('js/utility/request.js');
}
