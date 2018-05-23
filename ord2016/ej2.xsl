<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" encoding="ISO-8859-1" doctype-system="matrix.dtd" indent="yes"/>

<xsl:strip-space elements="*" />

<xsl:template match="*">
 <xsl:copy-of select="." />
</xsl:template>

<xsl:template match="matrixE">
  <matrixE totalCeldas="{count(descendant::celdaE)}">
    <xsl:apply-templates />
  </matrixE>
</xsl:template>

<xsl:template match="filaE">
  <filasE celdas="{count(descendant::celdaE)}">
    <xsl:apply-templates select="celdaE[position()=1]|celdaE[.>preceding-sibling::*]"/>
  </filasE>
</xsl:template>


</xsl:stylesheet>
