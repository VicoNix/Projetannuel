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



function includeRequiredFiles()
{
	include('js/form_generator/generator_html.js');
	include('js/form_generator/generator_xsl.js');
	include('js/form_generator/generator.js');
	include('js/utility/utility.js');
	include('js/utility/request.js');
	include('js/utility/cookies.js');
}
