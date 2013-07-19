<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="utf-8" indent="yes"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Formulaire de saisie de potentiel</title>
			</head>
			<body>
				<h1 style="text-align: center;">Formulaire de saisie de potentiel</h1>
				<form name="saisiepotentiel">
					Vehicule :

					<select>
						<xsl:for-each select="lien_vehicule_potentiel_view/Row">
							<option>
								<xsl:value-of select="immatriculation" />
							</option>
						</xsl:for-each>
					</select>

					<table border="1" style="height: 135px;" width="226">
						<tr>
							<td>
								<b>Potentiel</b>
							</td>
							<td>
								<b>Valeur</b>
							</td>
						</tr>
						<xsl:for-each select="Result/LIEN_VEHICULE_POTENTIEL_VIEW">
							<tr>
								<td>
									<xsl:value-of select="NOM_POTENTIEL" />
								</td>
								<td>
									<input>
										<xsl:attribute name="value">
   <xsl:value-of select="valeur" />
 </xsl:attribute>
									</input>
								</td>
							</tr>
						</xsl:for-each>

					</table>

					<input type="submit" value="Envoyer" />

				</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>

				