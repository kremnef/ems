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
    <xsl:param name="param"/>
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
                    <xsl:with-param name="parentId" select="$parentId"/>
                </xsl:apply-templates>
                <!--</xsl:if>-->

                <xsl:apply-templates select="items"/>
                <xsl:copy-of select="bottomData/*"/>
            </block>
        </content>
    </xsl:template>


    <xsl:template match="items">
        <div id="listTableContainer">
            <xsl:call-template name="flexigrid"/>
        </div>
    </xsl:template>


    <xsl:template name="flexigrid">
        <!--<xsl:variable name="folder" select="contains($requestURI, 'Folder')"/>-->
        <link rel="stylesheet" type="text/css"
              href="{$blockPrefix}/resource/js/jquery-plugin/flexgrid/css/flexigrid.css"
              media="screen"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/flexgrid/js/flexigrid.js"/>

        <!--<style>
            .flexigrid div.fbutton .add {
                background: url(<xsl:value-of select="$blockPrefix"/>/resource/js/jquery-plugin/flexgrid/css/images/add.png) no-repeat center left;
            }

            .flexigrid div.fbutton .delete {
                background: url(<xsl:value-of select="$blockPrefix"/>/resource/js/jquery-plugin/flexgrid/css/images/close.png) no-repeat center left;
            }

        </style>-->

        <table id="flex1" style="display:none"/>


        <script type="text/javascript">

            var colModel = new Array();
            var index = 0;
            colModel[index++] = {display: '<i18n:text key="common.tools"/>', tools : 'edit', width : 100, sortable : false, align: 'left'};
            colModel[index++] = {display: '<i18n:text key="EmsObject.name"/>', name : 'name', width : 250, sortable : true, align: 'left'};
            <xsl:if test="item/@systemName">
                colModel[index++] = {display: '<i18n:text key="EmsObject.systemName"/>', name : 'systemName', width :  140, sortable : false, align: 'left'};
            </xsl:if>
            <xsl:if test="item/@position">
                colModel[index++] = {display: '<i18n:text key="EmsObject.position"/>', name : 'position', width : 40, sortable : true, align: 'left'};
            </xsl:if>

            <xsl:call-template name="additionalHeadersScript"/>

            $("#flex1").flexigrid({
            url: '<xsl:value-of select="$requestURI"/>?parentId=<xsl:value-of select="$parentId"/>&amp;json=true', dataType: 'xml', colModel : colModel,
            <!--buttons : [
                {name: 'Add', bclass: 'add', onpress : test},
                {name: 'Delete', bclass: 'delete', onpress : test},
                {separator: true}
            ],-->
            searchitems : [
            {display: 'Название', name : 'name'},
            <!--{display: 'Name', name : 'name', isdefault: true}-->
            ],
            <xsl:choose>
                <xsl:when test="item/publicationDate">
                     sortname: "publicationDate",
                </xsl:when>
                <xsl:when test="item/@position">
                     sortname: "position",
                </xsl:when>
                <xsl:otherwise></xsl:otherwise>
            </xsl:choose>
            <!--<xsl:if test="">

                &lt;!&ndash;sortorder: "asc",&ndash;&gt;
            </xsl:if>-->
            usepager: true,
            singleSelect: true,
            title: '<xsl:copy-of select="title/*"/>',
            useRp: true,
            rp: 10,
            showTableToggleBtn: true,
            height:350

            });


            function test(com,grid) {
            if (com=='Delete') {
            confirm('Delete ' + $('.trSelected',grid).length + ' items?')
            }
            else if (com=='Add') {
            alert('Add New Item');
            }
            }

            $('b.top').click(function() {
            $(this).parent().toggleClass('fh');
            });

        </script>
    </xsl:template>

    <xsl:template name="additionalHeadersScript">
        <xsl:for-each select="headers/header">
            colModel[index++] = {display: '<i18n:text>
                <xsl:attribute name="catalogue">
                    <xsl:value-of select="./@i18nCatalogueName"/>
                </xsl:attribute>
                <xsl:attribute name="key"><xsl:value-of select="$entity"/>.<xsl:value-of select="."/>
                </xsl:attribute>
            </i18n:text>', name : '<xsl:value-of select="."/>', width : 140, sortable : true, align: 'left'};
        </xsl:for-each>
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
