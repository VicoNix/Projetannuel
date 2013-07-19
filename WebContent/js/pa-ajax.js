		function getRequeteHttp()
		{ 
			var requeteHttp;
			if (window.XMLHttpRequest)
			requeteHttp=new XMLHttpRequest();
			else
			if (window.ActiveXObject)
			requeteHttp=new ActiveXObject("Microsoft.XMLHTTP");
			else
			requeteHttp=null;
			return requeteHttp;
		}
		
		function envoyerRequete()
		{ 
			var requeteHttp=getRequeteHttp();
			if (requeteHttp==null)
				alert("Impossible d'utiliser Ajax sur ce navigateur");
			else
			{ 
				requeteHttp.open('GET',"XMLgen?table="+document.getElementById('table').options[document.getElementById('table').selectedIndex].value,true);
				requeteHttp.onreadystatechange= function()
					{
					traitement(requeteHttp);
					};
				requeteHttp.send(null);
			}
		}
		
		function envoyerRequetecond()
		{ 
			var requeteHttp=getRequeteHttp();
			if (requeteHttp==null)
				alert("Impossible d'utiliser Ajax sur ce navigateur");
			else
			{ 
				requeteHttp.open('GET',"XMLgen?table="+document.getElementById('condtable').options[document.getElementById('condtable').selectedIndex].value,true);
				requeteHttp.onreadystatechange= function()
					{
					traitementcond(requeteHttp);
					};
				requeteHttp.send(null);
			}
		}
		
		function envoyerRequetecondition()
		{
			var requeteHttp=getRequeteHttp();
			if (requeteHttp==null)
				alert("Impossible d'utiliser Ajax sur ce navigateur");
			else
			{ 
				requeteHttp.open('GET',"XMLgen?condition="+document.getElementById('condchamps').options[document.getElementById('condchamps').selectedIndex].value,true);
				requeteHttp.onreadystatechange= function()
					{
					traitementcondition(requeteHttp);
					};
				requeteHttp.send(null);
			}
		}
		
		function traitement(requeteHttp)
		{ 
			if (requeteHttp.readyState==4)
			{ 
				if (requeteHttp.status==200)
					{
					document.getElementById("champs").innerHTML=requeteHttp.responseText;
					}
				else
					alert("La requ�te ne s'est pas correctement ex�cut�e lors du chargement");
			}
		}
		
		function traitementcond(requeteHttp)
		{ 
			if (requeteHttp.readyState==4)
			{ 
				if (requeteHttp.status==200)
					{
					document.getElementById("condchamps").innerHTML=requeteHttp.responseText;
					}
				else
					alert("La requ�te ne s'est pas correctement ex�cut�e lors du chargement de la condition");
			}
		}
		
		function traitementcondition(requeteHttp)
		{  
			if (requeteHttp.readyState==4)
			{ 
				if (requeteHttp.status==200)
					{
					document.getElementById("condition").innerHTML=requeteHttp.responseText;
					}
				else
					alert("La requ�te ne s'est pas correctement ex�cut�e lors du chargement de la condition");
			}
		}
		
		function insertData()
		{
			myCodeMirror.replaceSelection("["+document.getElementById('table').options[document.getElementById('table').selectedIndex].value+"].["+document.getElementById('champs').options[document.getElementById('champs').selectedIndex].value+"]");
		}
		
		function recupererrequeteHTML()
		{
			var requeteHttp=getRequeteHttp();
			if (requeteHttp==null)
				alert("Impossible d'utiliser Ajax sur ce navigateur");
			else
			{ 
				document.getElementById('xmlajax').value=document.getElementById('codexml').value;
				document.getElementById('xslajax').value=document.getElementById('codexsl').value;
				requeteHttp.open('POST',"Navigation",true);
				requeteHttp.onreadystatechange= function()
					{
					traitementHTML(requeteHttp);
					};
				requeteHttp.send(null);
			}
		}
		
		function traitementHTML()
		{
			document.getElementById('HTMLframe').innerHTML=requeteHttp.responseText;
		}