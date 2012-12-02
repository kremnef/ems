<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <xsl:import href="blockcontext:/ems/stylesheets/admin/list_template.xsl"/>
    <xsl:template match="filter">
        <xsl:param name="requestURI"/>
        <xsl:param name="param"/>
        <h1>${children}-111</h1>
    </xsl:template>

</xsl:stylesheet>
