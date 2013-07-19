
function getXMLHttpRequest() {
	var xhr = null;
	
	if (window.XMLHttpRequest || window.ActiveXObject) {
		if (window.ActiveXObject) {
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else {
			xhr = new XMLHttpRequest(); 
		}
	} else {
		alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
		return null;
	}
	
	return xhr;
}

function executeFormPostRequest(url, parameters, callback)
{
	executeRequest("POST", url, parameters, callback, "application/x-www-form-urlencoded");
}

function executeRequest(method, url, postParameters, callback, contentType)
{
	xhr = getXMLHttpRequest();
	
	xhr.open(method, url, true);
	xhr.setRequestHeader("Content-Type", contentType);
	xhr.send(postParameters);

	xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			if (callback)
				callback(xhr.responseText);
        }
	};
}
