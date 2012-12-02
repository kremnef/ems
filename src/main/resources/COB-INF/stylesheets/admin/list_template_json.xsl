<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="no" indent="yes" cdata-section-elements="cell"/>


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

    <xsl:template match="content">
        <rows>
            <page>
                <xsl:value-of select="paging/@page"/>
            </page>
            <total>
                <xsl:value-of select="paging/@total"/>
            </total>
            <xsl:apply-templates select="items"/>
        </rows>
    </xsl:template>

    <xsl:template match="items">
        <xsl:variable name="folder" select="contains($requestURI, 'Folder') "/>
        <xsl:for-each select="item">
            <row id="{@id}">
                <!--<cell>
                    <xsl:value-of select="name"/>
                </cell>
                <cell>
                    <xsl:value-of select="@systemName"/>
                </cell>
                <cell>
                    <xsl:value-of select="@position"/>
                </cell>
                <cell>
                    <xsl:value-of select="publishDateTime"/>
                </cell>-->

                <xsl:variable name="class" select="string(@class)"/>
                <xsl:variable name="file" select="fn:ends-with($class, 'FileObject')"/>
                <xsl:variable name="hasChildren" select="@hasChildren = 'true'"/>


                <!--<xsl:variable name="class" select="string(@class)"/>
                <xsl:variable name="file" select="fn:ends-with($class, 'FileObject')"/>

                <xsl:if test="$folder">

                    <xsl:variable name="previewPath" select="substring-before(path,name)"/>

                    <xsl:if test="$file and starts-with(contentType, 'image')">
                        <cell>
                            <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                            <img src="{$blockPrefix}{$previewPath}{@systemName}.png"/>
                            <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                        </cell>
                    </xsl:if>


                </xsl:if>-->
                <cell>
                    <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                    <!--<a role="button" href="{object/type}-edit?id={@id}" class="ui-button ui-corner-all ui-state-highlight  ui-icon ui-icon-pencil"/>-->
                    <xsl:choose>
                        <xsl:when test="$file">
                            <a role="button" href="FileObject-edit?id={@id}"
                               class="ui-button ui-corner-all ui-state-highlight  ui-icon ui-icon-pencil"/>
                        </xsl:when>
                        <xsl:when test="$entity != 'TransactionHistory'">
                            <a role="button" href="{$entity}-edit?id={@id}"
                               class="ui-button ui-corner-all ui-state-highlight  ui-icon ui-icon-pencil"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <a role="button" href="{object/type}-edit?id={@id}"
                               class="ui-button ui-corner-all ui-state-highlight  ui-icon ui-icon-pencil"/>
                        </xsl:otherwise>
                    </xsl:choose>


                    <!--<a role="button" class="ui-button ui-corner-all ui-state-default ui-icon ui-icon-unlocked"/>-->
                    <!--<a role="button" class="ui-button ui-corner-all ui-state-default ui-icon ui-icon-link"/>-->
                    <a role="button" class="ui-button ui-corner-all ui-state-highlight ui-icon ui-icon-close"
                       onclick="confirmDelete()">
                        <xsl:attribute name="onclick">
                            <xsl:choose>
                                <xsl:when test="$file">
                                    confirmDelete('FileObject-delete?id=<xsl:value-of select="@id"/>');
                                </xsl:when>
                                <xsl:otherwise>confirmDelete('<xsl:value-of select="$entity"/>-delete?id=<xsl:value-of
                                        select="@id"/>');
                                </xsl:otherwise>
                            </xsl:choose>

                        </xsl:attribute>
                    </a>
                    <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                </cell>
                <xsl:choose>
                    <xsl:when test="$file">
                        <xsl:variable name="path" select="path"/>
                        <cell>
                            <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                            <!--<xsl:choose>
                                <xsl:when test="$folder">
                                    <xsl:variable name="fileId" select="@id"/>
                                    <xsl:variable name="fullName" select="concat($fileId, '-', name)"/>
                                    <xsl:variable name="slashPosition">
                                        <xsl:call-template name="getLastIndex">
                                            <xsl:with-param name="str" select="path"/>
                                            <xsl:with-param name="search" select="'/'"/>
                                        </xsl:call-template>
                                    </xsl:variable>
                                    <xsl:variable name="previewPath" select="substring(path, 0, $slashPosition + 1)"/>
                                    <xsl:if test="$file and starts-with(contentType, 'image')">
                                        &lt;!&ndash;<a class="screenshot"
                                        rel="{$blockPrefix}/{$previewPath}thumbnails/{@id}-{name}-square.jpg"/>&ndash;&gt;
                                        <a i18n:attr="title" title="core:FileObject.download"
                                           href="{$blockPrefix}{$path}" target="_blank"
                                           class="screenshot row-title-noChildren"
                                           rel="{$blockPrefix}/{$previewPath}thumbnails/{@id}-{name}-square.jpg">
                                            <xsl:value-of select="name"/>
                                        </a>
                                    </xsl:if>
                                </xsl:when>
                                <xsl:otherwise>
                                    <a i18n:attr="title" title="core:FileObject.download"
                                       href="{$blockPrefix}{$path}" target="_blank" class="row-title-noChildren">
                                        <xsl:value-of select="name"/>
                                    </a>
                                </xsl:otherwise>
                            </xsl:choose>-->
                            <xsl:value-of select="name"/>
                            <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                        </cell>
                    </xsl:when>
                    <xsl:when test="@childrenCount > 0 and $entity!='TransactionHistory'">
                        <cell>
                            <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                            <a role="button"
                               class="ui-button ui-corner-all ui-state-highlight ui-icon ui-icon-folder-collapsed"/>
                            <a class="row-title" i18n:attr="title" title="goToChildren"
                               href="{$entity}-list?parentId={@id}">
                                <xsl:value-of select="name"/>
                            </a>
                            <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                        </cell>
                    </xsl:when>
                    <xsl:when test="name">
                        <cell>
                            <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                            <!--<a role="button" class="ui-button ui-corner-all ui-state-default ui-icon ui-icon-folder-collapsed"/>-->
                            <a class="row-title-noChildren" i18n:attr="title" title="goToChildren"
                               href="{$entity}-list?parentId={@id}">
                                <xsl:value-of select="name"/>
                            </a>
                            <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                        </cell>
                    </xsl:when>
                    <!-- <xsl:otherwise>
                        <cell>
                            <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                            <a role="button" class="ui-button ui-corner-all ui-state-default ui-icon ui-icon-folder-collapsed"/>
                            <a class="row-title-noChildren" i18n:attr="title" title="goToChildren"
                               href="{$entity}-list?parentId={@id}">
                                <xsl:value-of select="name"/>
                            </a>
                            <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                        </cell>
                    </xsl:otherwise>-->
                </xsl:choose>

                <xsl:if test="@systemName">
                    <cell>
                        <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                        <xsl:value-of select="@systemName"/>
                        <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                    </cell>
                </xsl:if>


                <xsl:if test="@position">
                    <cell>
                        <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                        <xsl:value-of select="@position"/>
                        <a href="?parentId={$objectParentId}&amp;id={@id}&amp;act=down">
                            <img src="{$blockPrefix}/resource/img/forms/move_down.gif"/>
                        </a>
                        <a href="?parentId={$objectParentId}&amp;id={@id}&amp;act=up">
                            <img src="{$blockPrefix}/resource/img/forms/move_up.gif"/>
                        </a>
                        <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                    </cell>
                </xsl:if>
                <xsl:call-template name="additionalProperties">
                    <xsl:with-param name="currentItem" select="."/>
                </xsl:call-template>

                <!--<cell>
                    <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                    &lt;!&ndash;<a role="button" href="{object/type}-edit?id={@id}" class="ui-button ui-corner-all ui-state-highlight  ui-icon ui-icon-pencil"/>&ndash;&gt;
                    <xsl:choose>
                        <xsl:when test="$entity != 'TransactionHistory'">
                            <a role="button" href="{$entity}-edit?id={@id}"
                               class="ui-button ui-corner-all ui-state-highlight  ui-icon ui-icon-pencil"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <a role="button" href="{object/type}-edit?id={@id}"
                               class="ui-button ui-corner-all ui-state-highlight  ui-icon ui-icon-pencil"/>
                        </xsl:otherwise>
                    </xsl:choose>


                    &lt;!&ndash;<a role="button" class="ui-button ui-corner-all ui-state-default ui-icon ui-icon-unlocked"/>&ndash;&gt;
                    &lt;!&ndash;<a role="button" class="ui-button ui-corner-all ui-state-default ui-icon ui-icon-link"/>&ndash;&gt;
                    <a role="button" class="ui-button ui-corner-all ui-state-highlight ui-icon ui-icon-close"
                       onclick="confirmDelete()">
                        <xsl:attribute name="onclick">
                            <xsl:choose>
                                <xsl:when test="$file">
                                    confirmDelete('FileObject-delete?id=<xsl:value-of select="@id"/>');
                                </xsl:when>
                                <xsl:otherwise>confirmDelete('<xsl:value-of select="$entity"/>-delete?id=<xsl:value-of
                                        select="@id"/>');
                                </xsl:otherwise>
                            </xsl:choose>

                        </xsl:attribute>
                    </a>
                    <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
                </cell>-->
                <!--<xsl:if test="position() != last()">

                </xsl:if>-->
            </row>
        </xsl:for-each>

    </xsl:template>


    <xsl:template name="additionalProperties">
        <xsl:param name="currentItem" select="."/>
        <xsl:for-each select="/content/items/headers/header">
            <xsl:variable name="field" select="."/>
            <xsl:variable name="tdValue" select="$currentItem/*[name(.) = $field]"/>
            <cell>
                <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                <xsl:value-of select="$tdValue"/>
                <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
            </cell>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="getLastIndex">
        <xsl:param name="str"/>
        <xsl:param name="search"/>
        <xsl:choose>
            <xsl:when test="contains($str, $search)">
                <xsl:variable name="firstPart" select="string-length(substring-before($str, $search)) + 1"/>
                <xsl:variable name="otherPart">
                    <xsl:call-template name="getLastIndex">
                        <xsl:with-param name="str" select="substring-after($str, $search)"/>
                        <xsl:with-param name="search" select="$search"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:value-of select="$firstPart + $otherPart"/>
            </xsl:when>
            <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
