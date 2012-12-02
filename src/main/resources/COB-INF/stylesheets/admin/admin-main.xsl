<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ft="http://apache.org/cocoon/forms/1.0#template"
                xmlns:fi="http://apache.org/cocoon/forms/1.0#instance"
                xmlns:jx="http://apache.org/cocoon/templates/jx/1.0"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                exclude-result-prefixes="xsl ft fi jx i18n">
    <xsl:param name="blockPrefix"/>
    <xsl:param name="requestURI"/>
    <xsl:param name="requestParam"/>
    <xsl:param name="locale"/>
    <xsl:param name="dojo-resources"/>
    <xsl:param name="forms-resources"/>

    <!--todo: установить 1 раз уникально hdjksahvdjashdvasd-->
    <xsl:param name="id"/>
    <xsl:param name="actionState"/>
    <xsl:param name="parentId"/>

    <!--todo: установить 1 раз уникальноsss-->
    <!--<xsl:variable name="corePrefix">core</xsl:variable>-->
    <!--<xsl:variable name="ecommercePrefix">ecommerce</xsl:variable>-->
    <!--<xsl:variable name="authPrefix">auth</xsl:variable>-->
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


    <xsl:include href="admin-header.xsl"/>
    <!--<xsl:include href="admin-menu.xsl"/>-->
    <xsl:include href="admin-footer.xsl"/>


    <xsl:template match="/root">
        <html>
            <head>
                <!--xsl:value-of select="content/title"/-->
                <xsl:call-template name="header">
                    <xsl:with-param name="title">
                        <xsl:copy-of select="content/title/*"/>
                    </xsl:with-param>
                </xsl:call-template>


            </head>

            <body>

                <div class="ui-layout-center">
                    <xsl:apply-templates select="content"/>
                </div>

                <div class="ui-layout-north">
                    <!--<h1 id="site-heading">-->
                    <div id="ems-context">
                        <div id="context-1">
                            <i18n:text catalogue="ems" key="interface.hallo"/>,
                            <a href="{$blockPrefix}/core/editUser?id={content/userData/@id}" title="Изменить профиль">
                                <xsl:value-of select="content/userData/firstName"/>
                                <xsl:value-of select="content/userData/lastName"/>
                            </a>
                            |
                            <a href="{$blockPrefix}/security/logout" i18n:attr="title" title="ems:interface.logout">
                                <i18n:text catalogue="ems" key="interface.logout"/>
                            </a>
                        </div>
                        <div id="context-2">
                            <a href="{$blockPrefix}/core/help" i18n:attr="title" title="ems:interface.help">
                                <i18n:text catalogue="ems" key="interface.help"/>
                            </a>
                            |
                            <a href="{$blockPrefix}/security/language" i18n:attr="title" title="ems:interface.language">
                                <i18n:text catalogue="ems" key="interface.language"/>
                            </a>

                        </div>
                    </div>
                    <div id="ems">
                        <script>
                            jQuery(document).ready(function() {

                                jQuery('#reindex-dialog').dialog({
                                        autoOpen: false,
                                        width: 250,
                                        height: 250,
                                        buttons: {
                                                "Reindex": function() {
                                                    document.getElementById('reindex-loading-image').style.display = 'block';
                                                    document.getElementById('reindex-message').style.display = 'none';
                                                    jQuery.post('/ems/core/reindex', function(data) {
                                                        document.getElementById('reindex-loading-image').style.display = 'none';
                                                        if (typeof data == 'object') {
                                                            document.getElementById('reindex-message').style.display = 'block';
                                                            document.getElementById('reindex-message').innerHTML = data['message'];
                                                        }
                                                    }, "json");

                                                },
                                                "Close": function() {
                                                        jQuery(this).dialog("close");
                                                }
                                        }
                                });
                            });


                            function showReindexDialog() {
                                jQuery('#reindex-dialog').dialog('open');
                            }
                        </script>
                        <div id="reindex-dialog" style="padding: 60px; margin 0 auto;">
                            <div id="reindex-loading-image" style="display:none"><img src="{$blockPrefix}/resource/img/loading.gif"/></div>
                            <div id="reindex-message" style="display:none"/>
                        </div>
                        <div id="ems-version">
                            <span id="ems-logo">ELKA CMS 1.0</span>
                            <a href="/" class="ui-button" title="Перейти на сайт">
                                <i18n:text catalogue="ems" key="interface.goSite"/>
                            </a>
                            <a class="ui-button link" onclick="showReindexDialog()" title="Переиндексировать">
                                <i18n:text catalogue="ems" key="interface.reindex"/>
                            </a>

                            <!-- <span id="site-title">
                                <label for="tags">Tags:</label>
                                <input id="tags"/>
                            </span>-->
                        </div>
                        <div id="ems-interface">
                            <div class="ui-widget">
                                <form id="searchForm" action="/ems/service/search" method="get">
                                    <input type="text" name="query" resulte="10" id="ems-search" i18n:attr="value" value="ems:interface.search"/>
                                </form>
                                <!--<select id="interface-combobox">
                                    <option value="content">
                                        <i18n:text catalogue="ems" key="interface.content"/>
                                    </option>
                                    <option value="structure">
                                        <i18n:text catalogue="ems" key="interface.structure"/>
                                    </option>
                                    <option value="administration">
                                        <i18n:text catalogue="ems" key="interface.administration"/>
                                    </option>
                                </select>-->
                            </div>

                        </div>
                    </div>


                </div>


                <!--<div class="ui-layout-south">
                    <xsl:call-template name="footer"/>
                </div>-->
                <div class="ui-layout-east">
                    <xsl:apply-templates select="widget"/>
                </div>
                <div class="ui-layout-west" style="border:1px solid red">
                    <!--<xsl:call-template name="admin-menu"/>-->
                    <div id="accordion">
                        <xsl:for-each select="//module">
                            <xsl:variable name="corePrefix" select="@corePrefix"/>
                            <h3 position="{position()}">
                                <xsl:if test="count(section/item[contains($requestURI, @entity)]) &gt; 0 ">
                                    <xsl:attribute name="class">selected</xsl:attribute>
                                </xsl:if>
                                <a href="#">
                                    <i18n:text catalogue="{@i18nCatalogue}" key="{@name}"/>
                                </a>

                            </h3>
                            <div>
                                <xsl:for-each select="section">
                                    <xsl:variable name="i18nCatalogue" select="@i18nCatalogue"/>

                                    <span class="ems-menu-title">
                                        <i18n:text catalogue="{$i18nCatalogue}" key="{concat('interface.', ./@name)}"/>
                                    </span>
                                    <ul class="ems-menu-container">
                                        <xsl:for-each select="item">
                                            <li>
                                                <xsl:choose>
                                                    <xsl:when test="./@entity !='#'">
                                                        <!--<h1>param1<xsl:value-of select="$requestParam"/></h1>-->
                                                        <a href="{concat($blockPrefix,'/',$corePrefix,'/', ./@entity,'-', ./@action)}"
                                                           tabindex="{position()}">
                                                            <xsl:if test="contains($requestURI, @entity)">
                                                                <xsl:attribute name="class">selected</xsl:attribute>
                                                            </xsl:if>
                                                            <i18n:text catalogue="{$i18nCatalogue}"
                                                                       key="{concat('interface.', ./@name)}"/>
                                                        </a>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <i18n:text catalogue="{$i18nCatalogue}"
                                                                   key="{concat('interface.', ./@name)}"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>


                                            </li>
                                        </xsl:for-each>
                                    </ul>
                                </xsl:for-each>
                            </div>

                        </xsl:for-each>
                    </div>
                </div>

            </body>

        </html>
    </xsl:template>

    <xsl:template mode="intreface" match="component">
        <div>3</div>

    </xsl:template>

    <xsl:template match="content">
        <div class="debug">
            <h2>
                <xsl:copy-of select="title/*"/>
            </h2>
            <xsl:apply-templates select="block[@id='breadcrumb']"/>
            <xsl:copy-of select="more/*"/>
            <!--<br/>
            <br/>-->

            <xsl:apply-templates select="block[@id='center']"/>
        </div>

    </xsl:template>
    <xsl:template match="widget">
        <div class="debug">
            Здесь нужно меню
        </div>
    </xsl:template>
    <xsl:template match="block">
        <xsl:copy-of select="*"/>
    </xsl:template>


    <!--xsl:template match="@*|node()" priority="-2">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>
    <xsl:template match="text()" priority="-1">
        <xsl:value-of select="." />
    </xsl:template-->

</xsl:stylesheet>