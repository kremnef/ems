<?xml version="1.0" encoding="UTF-8"?>
<!--<!DOCTYPE xsl:stylesheet SYSTEM "../../../dtd/escape.dtd">-->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

                xmlns:date="http://exslt.org/dates-and-times"
                xmlns:str="http://exslt.org/strings"
                xmlns:func="http://exslt.org/functions"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                exclude-result-prefixes="date str func i18n">
    <xsl:template name="search">
        <H1>Результаты поиска</H1>
        <!--<xsl:param mame="query"/>-->
        <xsl:variable name="SEARCH_KEY" select="//request/query"/>
        <xsl:variable name="TEXT_KEY" select="xmlSource"/>
        <xsl:for-each select="//search-results/item">
            <div class="search-item">
                <span class="hot">
                    <a href="{url}" title="{url}">
                        <xsl:value-of select="title"/>
                    </a>
                </span>

                <xsl:if test="contains(text, $SEARCH_KEY)">

                    <xsl:value-of select="substring-before($TEXT_KEY,$SEARCH_KEY)" disable-output-escaping="yes"/>
                    <font class="search-key">
                        <xsl:value-of select="$SEARCH_KEY"/>
                    </font>
                    <xsl:value-of select="substring-after($TEXT_KEY, $SEARCH_KEY)" disable-output-escaping="yes"/>
                </xsl:if>


            </div>


        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

