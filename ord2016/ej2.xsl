<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" encoding="ISO-8859-1" doctype-system="matrix.dtd" indent="yes"/>

<xsl:strip-space elements="*" />

<xsl:template match="*">
 <xsl:copy-of select="." />
</xsl:template>

<xsl:template match="matrixE">
  <matrixE totalCeldas="{count(descendant::celdaE)}">
    <xsl:apply-templates select="filaE[position()&gt;1]"/>
  </matrixE>
</xsl:template>

<xsl:template match="filaE">
  <filaE celdas="{count(descendant::celdaE)}">
    <xsl:apply-templates select="celdaE[position()&gt;1]"/>
  </filaE>
</xsl:template>


</xsl:stylesheet>
