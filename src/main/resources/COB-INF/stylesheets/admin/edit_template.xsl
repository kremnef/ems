<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                xmlns:fi="http://apache.org/cocoon/forms/1.0#instance">
    <xsl:param name="module"/>
    <xsl:param name="entity"/>
    <xsl:param name="actionState"/>
    <xsl:param name="blockPrefix"/>
    <xsl:param name="locale"/>
    <xsl:param name="id"/>
    <!--<xsl:param name="parentId"/>-->
    <xsl:param name="requestParam"/>
    <xsl:param name="requestURI"/>

    <xsl:include href="admin-utils.xsl"/>
    <xsl:variable name="parentId" select="//parents/parent[position()=last()]/@id"/>
    <xsl:template match="/root">
        <!--<xsl:template match="/content">-->
        <!--Uniform-->

        <!--
            function saveorder( n, task ) {
            checkAll_button( n, task );
            }

            //needed by saveorder function
            function checkAll_button( n, task ) {

            if (!task ) {
            task = 'saveorder';
            }

            for ( var j = 0; j<= n; j++ ) {
            box = eval( "document.adminForm.cb" + j );
            if ( box ) {
            if ( box.checked == false ) {
            box.checked = true;
            }
            } else {
            alert("You cannot change the order of items, as an item in the list is `Checked Out`");
            return;
            }
            }
            submitform(task);
            }-->
        <root>
            <xsl:apply-templates select="content"/>
            <xsl:copy-of select="interface"/>
        </root>
    </xsl:template>

    <xsl:template match="content">

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

        <content>
            <entity>
                <xsl:value-of select="$entity"/>
            </entity>
            <title>
                <i18n:text catalogue="{$module}" key="{$entity}.{$actionState}"/>
                <i18n:text i18n:catalogue="documenttypes" key="{$entity}.{$actionState}"/>
            </title>

            <!--<block id="breadcrumb">
                <xsl:apply-templates select="parents"/>
            </block>-->
            <xsl:copy-of select="userData"/>

            <block id="breadcrumb">
                <xsl:if test="$entity = 'FileObject'">
                    <xsl:variable name="entity" select="'Folder'"/>
                </xsl:if>
                <xsl:call-template name="parents">
                    <xsl:with-param name="entity" select="$entity"/>
                </xsl:call-template>
                <!--<xsl:apply-templates select="content/parents"/>-->
                <br/>
                <br/>
            </block>
            <xsl:call-template name="more">
                <xsl:with-param name="module" select="$module"/>
            </xsl:call-template>
            <block id="center">
                <xsl:copy-of select="fi:form-template|style|script|div"/>
            </block>


        </content>
        <meta>
            <!--[@id='contentForm' or @id='documentForm']-->
            <!--<block id="center">
                <xsl:copy-of select="content/fi:form-template|content/style|content/script|content/div"/>
            </block>-->
            <!--<xsl:copy-of select="meta/fi:form-template|meta/style|meta/script|meta/div"/>-->
            <!--<xsl:copy-of select="meta/fi:form-template|meta/style|meta/script|meta/div"/>-->
        </meta>
    </xsl:template>


    <xsl:template name="more">
        <xsl:param name="module"/>
        <xsl:if test="$entity = 'FileObject'">
            <xsl:variable name="entity" select="'Folder'"/>
        </xsl:if>
        <more>

            <!--<a href="{$blockPrefix}/{$module}/{$entity}-create?parentId={$parentId}"-->
            <a href="{$blockPrefix}/{$module}/{$entity}-create?parentId={$parentId}"
               cn="l.I.ui.newIssueLink"
               class="ui-button" title="⌃ N">
                <i18n:text catalogue="{$module}" key="{$entity}.addNew"/>
            </a>
            <xsl:if test="$entity= 'Content'">
                <a href="{$blockPrefix}/{$module}/{$entity}-create?parentId={$parentId}&amp;isFolder=true"
                   cn="l.I.ui.newIssueLink"
                   class="ui-button" title="⌃ N" style="margin-left:15px;">
                    <i18n:text catalogue="{$module}" key="{$entity}.addNewFolder"/>
                </a>
            </xsl:if>
        </more>
    </xsl:template>

</xsl:stylesheet>
