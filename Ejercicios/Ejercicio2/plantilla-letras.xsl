<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="letras"
  version="1.0">

  <xsl:output method="xml" indent="yes" encoding="ISO-8859-1" />

  <xsl:template match="/a">
    <xsl:element name="a" >
      <xsl:apply-templates select="descendant::e" />
      <xsl:apply-templates select="descendant::d" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="e">
    <xsl:copy-of select="e"/>
  </xsl:template>

</xsl:stylesheet>
