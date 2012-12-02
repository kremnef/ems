<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fd="http://apache.org/cocoon/forms/1.0#definition">

    <xsl:template match="/">
        <fd:selection-list dynamic="true">
            <xsl:for-each select="items/item">
                <fd:item value="{@value}">
                    <fd:label><xsl:value-of select="@label" /></fd:label>
                </fd:item>
            </xsl:for-each>
        </fd:selection-list>
    </xsl:template>
</xsl:stylesheet>