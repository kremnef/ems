<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ft="http://apache.org/cocoon/forms/1.0#template"
                xmlns:fi="http://apache.org/cocoon/forms/1.0#instance"
                xmlns:jx="http://apache.org/cocoon/templates/jx/1.0"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <xsl:include href="servlet:forms:/resource/internal/xsl/forms-page-styling.xsl"/>
    <!--<xsl:include href="servlet:forms:/resource/internal/xsl/forms-field-styling.xsl"/>-->
    <xsl:include href="servlet:forms:/resource/internal/xsl/forms-advanced-field-styling.xsl"/>
    <xsl:include href="servlet:forms:/resource/internal/xsl/forms-calendar-styling.xsl"/>
    <xsl:include href="servlet:forms:/resource/internal/xsl/forms-htmlarea-styling.xsl"/>


    <!-- Location of the resources directories, where JS libs and icons are stored -->
    <xsl:param name="dojo-resources"/>
    <xsl:param name="forms-resources"/>


    <!--<xsl:template match="head">


        <xsl:apply-templates select="." mode="forms-page"/>
        <xsl:apply-templates select="." mode="forms-field"/>
        <xsl:apply-templates/>

    </xsl:template>

    <xsl:template match="body">

        <xsl:apply-templates select="." mode="forms-page"/>
        <xsl:apply-templates select="." mode="forms-field"/>
        <xsl:apply-templates/>

    </xsl:template>-->
    <xsl:template match="/">
        <html>

            <head>
                <link rel="stylesheet" type="text/css" href="/ems/formcss/forms.css"/>
                <!--<xsl:apply-templates/>-->
            </head>
            <body>
                <!--<xsl:apply-templates select="." mode="forms-page"/>-->
                <!--<xsl:apply-templates select="." mode="forms-field"/>-->
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>