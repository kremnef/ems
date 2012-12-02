<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <xsl:output method="text" encoding="UTF-8" omit-xml-declaration="yes" indent="no"/>

    <xsl:param name="entity"/>
    <xsl:param name="module"/>
    <xsl:param name="blockPrefix"/>
    <xsl:param name="locale"/>
    <xsl:param name="id"/>
    <xsl:param name="actionState"/>
    <xsl:param name="parentId"/>
    <xsl:param name="requestURI"/>
    <xsl:param name="servletPath"/>

    <xsl:variable name="objectParentId">
        <xsl:choose>
            <xsl:when test="$actionState='edit'">
                <xsl:value-of select="$id"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$parentId"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:template match="/content">

        <xsl:text>{</xsl:text>
        <xsl:text>"page":</xsl:text><xsl:value-of select="paging/@page"/>,
        <xsl:text>"total":</xsl:text><xsl:value-of select="paging/@total"/>,
        <xsl:text>"rows":[</xsl:text>
        <xsl:apply-templates select="items"/>
        <xsl:text>]}</xsl:text>
    </xsl:template>


    <xsl:template match="items">


        <xsl:variable name="folder" select="contains($requestURI, 'Folder')"/>
        <xsl:for-each select="item"><xsl:text>{</xsl:text>
            <xsl:text>"id":</xsl:text><xsl:value-of select="@id"/><xsl:text>,</xsl:text>
            <xsl:variable name="class" select="string(@class)"/>
            <xsl:variable name="file" select="fn:ends-with($class, 'FileObject')"/>
            <xsl:text>"cell":[</xsl:text>
            <xsl:if test="$folder">
                <xsl:text>"</xsl:text><xsl:variable name="previewPath" select="substring-before(path,name)"/>
                    <xsl:if test="$file and starts-with(contentType, 'image')">
                        <img src="{$blockPrefix}{$previewPath}{@systemName}.png"/>
                    </xsl:if><xsl:text>",</xsl:text>
            </xsl:if>
            <xsl:text>"</xsl:text><xsl:choose>
                    <xsl:when test="$file">
                        <xsl:variable name="path" select="path"/>
                        <a class="row-title-noChildren" i18n:attr="title" title="core:FileObject.download" href="{$blockPrefix}{$path}" target="_blank">
                            <xsl:value-of select="name"/>
                        </a>
                    </xsl:when>
                    <xsl:when test="@childrenCount > 0">


                            <![CDATA[
                        <h1>1-</h1>
                        <a class="row-title" i18n:attr="title" title="goToChildren" href="]]>
                            <xsl:value-of select="$entity"/><![CDATA[-list?parentId=]]><xsl:value-of select="@id"/><![CDATA[">
                            ]]><xsl:value-of select="name"/>
                        <![CDATA[</a>]]>


                    </xsl:when>
                    <xsl:otherwise>
                        <h1>2-</h1>
                        <a class="row-title-noChildren" i18n:attr="title" title="goToChildren" href="{$entity}-list?parentId={@id}">
                           <xsl:value-of select="name"/>
                        </a>
                    </xsl:otherwise>
                </xsl:choose><xsl:text>"</xsl:text>
            <xsl:if test="@systemName">
                <xsl:text>,"</xsl:text><xsl:value-of select="@systemName"/><xsl:text>"</xsl:text>
            </xsl:if>
            <xsl:if test="@position">
                <xsl:text>,"</xsl:text><xsl:value-of select="@position"/>
                    <!--![CDATA[<a href=&quot;?parentId=]]><xsl:value-of select="$objectParentId"/><![CDATA[&id=]]><xsl:value-of
                    select="@id"/><![CDATA[&act=down&quot;>
                        <img src=&quot;]]><xsl:value-of select="$blockPrefix"/><![CDATA[/resource/img/forms/move_down.gif&quot;/>
                    </a>
                    <a href=&quot;?parentId=]]><xsl:value-of select="$objectParentId"/><![CDATA[&id=]]><xsl:value-of
                    select="@id"/><![CDATA[&act=up&quot;>
                        <img src=&quot;]]><xsl:value-of select="$blockPrefix"/><![CDATA[/resource/img/forms/move_up.gif&quot;/>
                    </a>]]-->
                <xsl:text>"</xsl:text>
            </xsl:if>
            <xsl:call-template name="additionalProperties">
                <xsl:with-param name="currentItem" select="."/>
            </xsl:call-template>
            <xsl:text>]}</xsl:text>
            <xsl:if test="position() != last()"><xsl:text>,</xsl:text></xsl:if>
        </xsl:for-each>
    </xsl:template>


    <xsl:template name="additionalProperties">
        <xsl:param name="currentItem" select="."/>
        <xsl:for-each select="/content/items/headers/header">
            <xsl:variable name="field" select="."/>
            <xsl:variable name="tdValue" select="$currentItem/*[name(.) = $field]"/>
            <xsl:text>,"</xsl:text><xsl:value-of select="$tdValue"/><xsl:text>"</xsl:text>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
