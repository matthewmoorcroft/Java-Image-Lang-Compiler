<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="letras"
  version="1.0">

  <xsl:output method="xml" indent="yes" encoding="ISO-8859-1" />

  <xsl:template match="/a">
    <xsl:element name="a" >
      <xsl:apply-templates select="d/child::e" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="*">
    <xsl:copy-of select="d/child::e"/>
  </xsl:template>

</xsl:stylesheet>
