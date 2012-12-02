<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                xmlns:dir="http://apache.org/cocoon/directory/2.0">
    <xsl:param name="entity"/>
    <!--<xsl:template match="/content">-->
    <xsl:template match="dir:directory/dir:directory">
        <!--<h1>xcbsdcnmdcabsd</h1>-->
        <content>
            <h1>xcnxmcx,cn</h1>
            <xsl:apply-templates/>
        </content>
    </xsl:template>
    <!-- <xsl:template match="dir:directory/dir:directory">
        <tr>
            <td>
                <a href="{@name}/">
                    <i>
                        <xsl:value-of select="@name"/>
                    </i>
                </a>
            </td>
            <td>
                <xsl:value-of select="@date"/>
            </td>
        </tr>
    </xsl:template>-->

    <xsl:template match="dir:file">
        <tr>
            <td>
                <a href="{@name}">
                    <xsl:value-of select="@name"/>
                </a>
            </td>
            <td>
                <xsl:value-of select="@date"/>
            </td>
            <td>
                <xsl:value-of select="@path"/>
            </td>
        </tr>
    </xsl:template>




</xsl:stylesheet>
