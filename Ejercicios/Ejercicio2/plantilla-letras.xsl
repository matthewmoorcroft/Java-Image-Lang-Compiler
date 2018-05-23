<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="letras"
  version="1.0">


  <xsl:output method="xml" indent="yes" encoding="ISO-8859-1" />

  <xsl:strip-space elements="*" />

  <xsl:template match="*">
   <xsl:copy-of select="." />
  </xsl:template>

  <xsl:template match="e">
      <xsl:apply-templates select="child::d" />
  </xsl:template>

  <!-- <xsl:template match="/d">
    <xsl:element name="d" >
      <xsl:apply-templates select="child::d"/>
    </xsl:element>
  </xsl:template> -->

  <!-- <xsl:template match="/d">
    <xsl:copy-of select="//f"/>
  </xsl:template> -->
}


</xsl:stylesheet>
