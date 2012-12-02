<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <xsl:param name="entity"/>
    <xsl:param name="module"/>
    <xsl:param name="blockPrefix"/>
    <xsl:param name="locale"/>
    <xsl:param name="id"/>
    <xsl:param name="actionState"/>
    <xsl:param name="parentId"/>
    <xsl:param name="requestURI"/>
    <xsl:param name="servletPath"/>
    <xsl:include href="admin-utils.xsl"/>

    <!--<xsl:variable name="corePrefix">core</xsl:variable>-->
    <!--<xsl:variable name="ecommercePrefix">ecommerce</xsl:variable>-->
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


    <xsl:template name="common">
        <script language="javascript">

            function confirmDelete(url) {
            apprise('<i18n:text key="AreYouSure"/>', {'verify':true}, function(r)
            {
            if(r)
            {
            document.location.href = url;
            }
            else
            {
            return false;
            }
            });
            }
        </script>
    </xsl:template>
    <xsl:template match="/root">
        <root>
            <xsl:apply-templates select="content"/>
            <xsl:copy-of select="interface"/>
        </root>
    </xsl:template>

    <xsl:template match="content">
        <content>
            <xsl:copy-of select="userData"/>
            <xsl:copy-of select="entity"/>
            <title>
                <i18n:text catalogue="{$module}" key="{$entity}.list"/>
            </title>

            <block id="breadcrumb">
                <xsl:call-template name="parents">
                    <xsl:with-param name="entity" select="$entity"/>
                </xsl:call-template>
            </block>

            <more>
                <!--<a href="{$blockPrefix}/{$module}/create{$entity}?parentId={$objectParentId}"-->
                <a href="{$blockPrefix}/{$module}/{$entity}-create?parentId={$objectParentId}"
                   cn="l.I.ui.newIssueLink"
                   class="ui-button" title="⌃ N">
                    <i18n:text catalogue="{$module}" key="{$entity}.addNew"/>
                </a>
                <br/>
                <br/>
            </more>
            <block id="center">
                <xsl:call-template name="common"/>
                <xsl:copy-of select="topData/*"/>
                <!--<xsl:if test="//filter">-->
                <xsl:apply-templates select="filter">
                    <xsl:with-param name="requestURI" select="$requestURI"/>
                </xsl:apply-templates>
                <!--</xsl:if>-->
                <xsl:apply-templates select="items"/>
                <!--<xsl:call-template name="show-items"/>-->
                <xsl:copy-of select="bottomData/*"/>
            </block>
        </content>
    </xsl:template>
    <xsl:template match="items">

        <!--<xsl:variable name="folder" select="contains($requestURI, 'Folder')"/>-->


        <div id="listTableContainer">

            <!--<xsl:choose>-->
            <!--<xsl:when test="">-->
            <!--</xsl:when>-->

            <!--<xsl:otherwise>-->
            <xsl:call-template name="basetable"/>
            <!--</xsl:otherwise>-->
            <!--</xsl:choose>-->

        </div>
    </xsl:template>


    <xsl:template name="basetable">
        <xsl:variable name="folder" select="contains($requestURI, 'Folder')"/>
        <table class="table order" cellspacing="8" cellpadding="0" id="listTable">
            <thead>
                <tr>
                    <!--<th scope="col" id="cb">
                        <input type="checkbox"/>
                    </th>-->
                    <xsl:if test="$folder">
                        <th scope="col" id="preview"/>
                    </xsl:if>
                    <xsl:if test="item/name">
                        <th scope="col" id="title">
                            <i18n:text key="EmsObject.name"/>
                        </th>
                    </xsl:if>
                    <xsl:if test="item/@systemName">
                        <th scope="col" id="systemName">
                            <i18n:text key="EmsObject.systemName"/>
                        </th>
                    </xsl:if>
                    <xsl:if test="item/@position">
                        <th scope="col" id="position">
                            <i18n:text key="EmsObject.position"/>
                        </th>
                    </xsl:if>
                    <!--<th scope="col" id="categories" class="manage-column column-categories" style=""><i18n:text key="EmsObject.children"/></th>-->
                    <xsl:call-template name="additionalHeaders"/>
                </tr>
            </thead>

            <tfoot>
                <tr>
                    <!--<th scope="col">
                        <input type="checkbox"/>
                    </th>-->
                    <xsl:if test="$folder">
                        <th scope="col" id="preview"/>
                    </xsl:if>
                    <xsl:if test="item/name">
                        <th scope="col" id="title">
                            <i18n:text key="EmsObject.name"/>
                        </th>
                    </xsl:if>
                    <xsl:if test="item/@systemName">
                        <th scope="col" id="systemName">
                            <i18n:text key="EmsObject.systemName"/>
                        </th>
                    </xsl:if>
                    <xsl:if test="item/@position">
                        <th scope="col" id="position">
                            <i18n:text key="EmsObject.position"/>
                        </th>
                    </xsl:if>
                    <!--<th scope="col" id="categories" class="manage-column column-categories" style=""><i18n:text key="EmsObject.children"/></th>-->
                    <xsl:call-template name="additionalHeaders"/>
                </tr>
            </tfoot>

            <tbody>
                <xsl:for-each select="item">

                    <xsl:sort select="@position" data-type="number" order="ascending"/>


                    <xsl:variable name="class" select="string(@class)"/>
                    <xsl:variable name="file" select="fn:ends-with($class, 'FileObject')"/>
                    <xsl:variable name="hasChildren" select="@hasChildren = 'true'"/>
                    <tr id="{@id}" class="alternate author-self status-private iedit" valign="top">

                        <!--<th scope="row" class="check-column">
                            <input name="content[]" value="{@id}" type="checkbox"/>
                        </th>-->
                        <xsl:if test="$folder">
                            <xsl:variable name="fileId" select="@id"/>
                            <xsl:variable name="fullName" select="concat($fileId, '-', name)"/>
                            <xsl:variable name="slashPosition">
                                <xsl:call-template name="getLastIndex">
                                    <xsl:with-param name="str" select="path"/>
                                    <xsl:with-param name="search" select="'/'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            <xsl:variable name="previewPath" select="substring(path, 0, $slashPosition + 1)" />

                            <td class="preview manage-column column-author" style="">
                                <xsl:if test="$file and starts-with(contentType, 'image')">
                                    <img src="{$blockPrefix}/{$previewPath}thumbnails/{@id}-{name}-square.jpg"/>
                                </xsl:if>
                            </td>
                        </xsl:if>
                        <td class="post-title column-title">
                            <strong>
                                <!--xsl:choose>
                             <xsl:when test="@childrenCount > 0"-->
                                <xsl:choose>
                                    <xsl:when test="$file">
                                        <xsl:variable name="path" select="path"/>
                                        <a class="row-title-noChildren" i18n:attr="title"
                                           title="core:FileObject.download"
                                           href="{$blockPrefix}/{$path}" target="_blank">
                                            <xsl:value-of select="name"/>
                                        </a>
                                    </xsl:when>
                                    <xsl:when test="@childrenCount > 0">
                                        <a class="row-title" i18n:attr="title" title="goToChildren"
                                           href="{$entity}-list?parentId={@id}">
                                            <xsl:value-of select="name"/>
                                        </a>
                                    </xsl:when>

                                    <!--<xsl:when test="$hasChildren">-->
                                    <!--    <a class="row-title" i18n:attr="title" title="goToChildren"
                                       href="list{$entity}?parentId={@id}">
                                        <xsl:value-of select="name"/>
                                    </a>-->
                                    <!--</xsl:when>-->
                                    <xsl:otherwise>
                                        <!--<xsl:value-of select="name"/>-->
                                        <a class="row-title-noChildren" i18n:attr="title" title="goToChildren"
                                           href="{$entity}-list?parentId={@id}">
                                            <xsl:value-of select="name"/>
                                        </a>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <!--/xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="name"/>
                                    </xsl:otherwise>
                                </xsl:choose-->
                            </strong>
                            <div class="row-actions">
                                <xsl:choose>
                                    <xsl:when test="$file">
                                        <span class="edit">
                                            <a i18n:attr="title" title="Modify" href="FileObject-edit?id={@id}">
                                                <i18n:text key="Modify"/>
                                            </a>
                                            |
                                        </span>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <span class="edit">
                                            <a i18n:attr="title" title="Modify" href="{$entity}-edit?id={@id}">
                                                <i18n:text key="Modify"/>
                                            </a>
                                            |
                                        </span>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <xsl:choose>
                                    <xsl:when test="$hasChildren">

                                        <span class="inline hide-if-no-js">
                                            <a href="{$entity}-create?parentId={@id}" class="editinline"
                                               title="CreateChild" i18n:attr="title">
                                                <i18n:text key="CreateSUB"/>
                                            </a>
                                            |
                                        </span>
                                    </xsl:when>
                                </xsl:choose>
                                <span class="delete">
                                    <a class="submitdelete" href="#" i18n:attr="title" title="Delete"
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
                                        <i18n:text catalogue="ems" key="Delete"/>
                                    </a>
                                    |
                                </span>
                                <!-- test !!-->

                            </div>
                        </td>
                        <xsl:if test="@systemName">
                            <td class="post-title column-title">
                                <xsl:value-of select="@systemName"/>
                            </td>
                        </xsl:if>
                        <xsl:if test="@position">
                            <td class="post-title column-title">
                                <xsl:value-of select="@position"/>
                                <a href="?parentId={$objectParentId}&amp;id={@id}&amp;act=down">
                                    <img src="{$blockPrefix}/resource/img/forms/move_down.gif"/>
                                </a>
                                <a href="?parentId={$objectParentId}&amp;id={@id}&amp;act=up">
                                    <img src="{$blockPrefix}/resource/img/forms/move_up.gif"/>
                                </a>

                                <!--<input type="text" name="position" value="{position}" style="width:40px"/>-->

                            </td>
                        </xsl:if>
                        <!--<td class="categories column-categories">

                            <xsl:choose>
                                <xsl:when test="@childrenCount > 0">
                                    <a href="list{$entity}?parentId={@id}" title="EXPAND">
                                        <i18n:text key="Yes"/>
                                    </a>
                                </xsl:when>
                                <xsl:otherwise>
                                    <i18n:text key="No"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>-->
                        <xsl:call-template name="additionalProperties">
                            <xsl:with-param name="currentItem" select="."/>
                        </xsl:call-template>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
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

    <xsl:template name="additionalHeaders">
        <xsl:for-each select="headers/header">
            <th scope="col" class="manage-column column-title" style="">
                <i18n:text>
                    <xsl:attribute name="catalogue">
                        <xsl:value-of select="./@i18nCatalogueName"/>
                    </xsl:attribute>
                    <xsl:attribute name="key"><xsl:value-of select="$entity"/>.<xsl:value-of select="."/>
                    </xsl:attribute>
                </i18n:text>
            </th>
        </xsl:for-each>
    </xsl:template>


    <xsl:template name="additionalProperties">
        <xsl:param name="currentItem" select="."/>
        <xsl:for-each select="/root/content/items/headers/header">
            <xsl:variable name="field" select="."/>
            <xsl:variable name="tdValue" select="$currentItem/*[name(.) = $field]"/>
            <td class="post-title column-title">
                <abbr title="{$tdValue}">
                    <xsl:value-of
                            select="$tdValue"/>
                </abbr>
            </td>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
