<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">
    <xsl:import href="blockcontext:/ems/stylesheets/admin/list_template.xsl"/>
    <!--<xsl:import href="blockcontext:/ems/stylesheets/admin/list_template_common.xsl"/>-->

    <!--<xsl:template name="additionalProperties">
        <xsl:param name="currentItem" select="."/>
        <xsl:for-each select="/content/items/headers/header">


            <xsl:variable name="field" select="."/>
            <xsl:variable name="tdValue" select="$currentItem/*[name(.) = $field]"/>
            <xsl:variable name="systemNodeId" select="$currentItem/systemNodeId"/>
            <td class="post-title column-title">
                <xsl:choose>
                    <xsl:when test="$systemNodeId &gt; 0">
                        <a href="editSystemNode?id={$systemNodeId}">
                            <xsl:value-of
                                    select="$tdValue"/>
                        </a>
                    </xsl:when>
                    <xsl:otherwise>&#160;</xsl:otherwise>
                </xsl:choose>
            </td>

        </xsl:for-each>

    </xsl:template>-->

</xsl:stylesheet>
