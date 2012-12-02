<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ft="http://apache.org/cocoon/forms/1.0#template"
                xmlns:fi="http://apache.org/cocoon/forms/1.0#instance"
                xmlns:jx="http://apache.org/cocoon/templates/jx/1.0"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">
    <xsl:param name="blockPrefix"/>
    <xsl:param name="requestURI"/>
    <xsl:param name="locale"/>
    <xsl:param name="dojo-resources"/>
    <xsl:param name="forms-resources"/>

    <xsl:template match="/">
        <html>
            <head>
                <title><i18n:text catalogue="core" key="Folder.tree"/></title>
                <link rel="stylesheet" href="{$blockPrefix}/resource/css/main.css" title="Default Style" />
            </head>
            <body class="wp-admin js edit-php">
                <div id="wpwrap">
                    <div id="wpcontent">

                        <div id="wpbody">

                            <div id="wpbody-content">
                                <xsl:copy-of select="script" />
                                <xsl:copy-of select="fi:form-template " />
                                <div class="clear"></div>
                            </div>
                            <!-- wpbosssdy-SystemNode -->
                            <div class="clear"></div>
                        </div>
                        <!-- wpbody dd-->
                        <div class="clear"></div>
                    </div>
                    <!-- wpSystemNode -->
                </div>
                <!-- wpwrap -->

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>