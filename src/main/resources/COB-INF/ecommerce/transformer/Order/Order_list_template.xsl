<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <xsl:import href="blockcontext:/ems/stylesheets/admin/list_template.xsl"/>

    <!--xsl:variable name="entity" select="/content/entity"/>
    <xsl:template match="/content">
        <content>
            <xsl:copy-of select="entity" />
            <title>
                <i18n:text>
                    <xsl:attribute name="key"><xsl:value-of select="$entity"/>.title</xsl:attribute>
                </i18n:text>
            </title>
            <block id="breadcrumb">
                <xsl:apply-templates select="parents" />
            </block>
            <block id="list">
                <xsl:apply-templates select="items" />
            </block>
        </content>
    </xsl:template>


    <xsl:template match="parents">
        <div id="bread-crumb" class="edit">
            <span id="bc-root"><a href="listSystemNode"><i18n:text key="root"/></a></span>
            <xsl:for-each select="//parent">
                <span> > </span>
                <xsl:choose>
                    <xsl:when test="position() = last()">
                        <span><strong><xsl:value-of select="name"/></strong></span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span><a href="listSystemNode?parentId={@id}"><xsl:value-of select="name"/></a></span>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>

        </div>
    </xsl:template>


    <xsl:template match="items">
        <table class="order post fixed" cellspacing="0">
            <thead>
                <tr>
                    <th scope="col" id="cb" class="manage-column column-cb check-column"
                        style="">
                        <input type="checkbox"/>
                    </th>
                    <th scope="col" id="title" class="manage-column column-title" style=""><i18n:text key="emsObject.name"/></th>
                    <th scope="col" id="systemName" class="manage-column column-author" style=""><i18n:text key="emsObject.systemName"/></th>
                    <th scope="col" id="categories" class="manage-column column-categories" style=""><i18n:text key="emsObject.children"/></th>
                    <xsl:for-each select="headers/header">
                        <th scope="col" class="manage-column column-title" style="">
                            <i18n:text>
                                <xsl:attribute name="key"><xsl:value-of select="$entity"/>.<xsl:value-of select="."/></xsl:attribute>
                            </i18n:text>
                        </th>
                    </xsl:for-each>
                </tr>
            </thead>

            <tfoot>
                <tr>
                    <th scope="col" class="manage-column column-cb check-column"
                        style="">
                        <input type="checkbox"/>
                    </th>
                    <th scope="col" id="title" class="manage-column column-title" style=""><i18n:text key="emsObject.name"/></th>
                    <th scope="col" id="systemName" class="manage-column column-author" style=""><i18n:text key="emsObject.systemName"/></th>
                    <th scope="col" id="categories" class="manage-column column-categories" style=""><i18n:text key="emsObject.children"/></th>
                    <xsl:for-each select="headers/header">
                        <th scope="col" class="manage-column column-title" style="">
                            <i18n:text>
                                <xsl:attribute name="key"><xsl:value-of select="$entity"/>.<xsl:value-of select="."/></xsl:attribute>
                            </i18n:text>
                        </th>
                    </xsl:for-each>
                </tr>
            </tfoot>

            <tbody>
                <xsl:for-each select="item">
                    <tr id="{@id}" class="alternate author-self status-private iedit" valign="top">

                        <th scope="row" class="check-column">
                            <input name="content[]" value="{@id}" type="checkbox"/>
                        </th>
                        <td class="post-title column-title">
                            <strong>
                                <xsl:choose>
                                    <xsl:when test="@childrenCount > 0">
                                        <a class="row-title" href="listSystemNode?parentId={@id}" i18n:attr="title" title="goToChildren">
                                            <xsl:value-of select="name"/>
                                        </a>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="name"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </strong>
                            <div class="row-actions">
                                <span class="edit">
                                    <a href="editSystemNode?id={@id}" i18n:attr="title" title="Modify">
                                        <i18n:text catalogue="ems" key="Modify"/>
                                    </a>
                                    |
                                </span>
                                <span class="inline hide-if-no-js">
                                    <a href="createSystemNode?parentId={@id}" class="editinline"
                                       title="CreateChild" i18n:attr="title"><i18n:text key="CreateSUB"/>
                                    </a>
                                    |
                                </span>
                                <span class="delete">
                                    <a class="submitdelete" i18n:attr="title" title="Delete" href="deleteSystemNode?id={@id}">
                                        <i18n:text key="Delete"/>
                                    </a>
                                    |
                                </span>
                            </div>
                        </td>
                        <td class="post-title column-title">
                            <xsl:value-of select="@systemName"/>
                        </td>
                        <td class="categories column-categories">

                            <xsl:choose>
                                <xsl:when test="@childrenCount > 0">
                                    <a href="listSystemNode?parentId={@id}" title="EXPAND">
                                        <i18n:text key="Yes"/>
                                    </a>
                                </xsl:when>
                                <xsl:otherwise>
                                    <i18n:text key="No"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                        <xsl:variable name="currentItem" select="." />
                        <xsl:for-each select="../headers/header">
                            <xsl:variable name="field" select="." />
                            <xsl:variable name="tdValue" select="$currentItem/*[name(.) = $field]" />
                            <td class="post-title column-title">
                                <abbr title="{$tdValue}"><xsl:value-of
                                    select="$tdValue"/></abbr>
                            </td>
                        </xsl:for-each>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template-->

</xsl:stylesheet>
