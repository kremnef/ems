<?xml version="1.0" encoding="UTF-8"?>
<!--//chenged-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="blockPrefix"/>
    <xsl:variable name="cleanURI" select="substring-after($blockPrefix, '/')"/>
    <xsl:variable name="topURI">
        <xsl:choose>
            <xsl:when test="contains($cleanURI, '/')">
                <xsl:value-of select="concat('/', substring-before(substring-after($blockPrefix, '/'),'/'))"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat('/',$cleanURI)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
    <!--<xsl:variable name="locale">ru</xsl:variable>-->
    <xsl:variable name="selectedMenuId" select="//site-navigation/site-menu[@next-uri = $topURI]/@id"/>
    <xsl:template name="header">

        <xsl:param name="title"/>

        <title>
            <xsl:copy-of select="$title"/>
        </title>
        <link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/css/ems.css"/>
        <link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/themes/flick/styles.css"/>

        <!--lib JQUERY !!!!!! FIRST LOAD -->
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery/jquery.js"/>
        <!--jQUERY UI-->
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery/jquery-ui-1.8.11.custom.min.js"/>
        <!--Layout-->
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/layout/jquery.layout-latest.js"/>

        <!--Notify-->
        <!--<link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/notify/ui.notify.css"
              media="all"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/notify/jquery.notify.min.js"/>-->

        <!--Uniform-->
        <link rel="stylesheet" type="text/css"
              href="{$blockPrefix}/resource/js/jquery-plugin/uniform/css/uniform.default.css" media="screen"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/uniform/jquery.uniform.js"/>

        <!--breadcrumb-->
        <link rel="stylesheet" type="text/css"
              href="{$blockPrefix}/resource/js/jquery-plugin/breadcrumb/css/BreadCrumb.css" media="all"/>
        <!--<link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/breadcrumb/css/Base.css"
              media="all"/>-->
        <script type="text/javascript"
                src="{$blockPrefix}/resource/js/jquery-plugin/breadcrumb/jquery.jBreadCrumb.1.1.js"/>

        <!--DateTime-->
        <link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/datetime/datetime.css"
              media="screen"/>
        <script type="text/javascript"
                src="{$blockPrefix}/resource/js/jquery-plugin/datetime/jquery-ui-timepicker-addon.js"/>
        <!-- Xml Tree -->
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery/jquery.cookie.js"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery/jquery.hotkeys.js"/>

        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/xmltree/src/jquery.jstree.js"/>
        <!--<script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/xmltree/src/xmltree_config.js"/>-->
        <script type="text/javascript" src="{$blockPrefix}/resource/js/fileTree.js"/>

        <!--Apprise-->
        <link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/apprise/apprise.min.css"
              media="screen"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/apprise/apprise-1.5.min.js"/>
        <!--Screenshot URL Preview-->
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/urlpreview/url_preview.js"/>


        <link media="all" href="/ems/resource/js/jquery-plugin/chosen/chosen.css"  type="text/css" rel="stylesheet"/>
        <script src="/ems/resource/js/jquery-plugin/chosen/chosen.jquery.min.js" type="text/javascript"></script>

        <!--FlowPlayer tools-->


        <script type="text/javascript">

            jQuery(function() {
            <!--layout-->
            jQuery('body').layout({applyDefaultStyles: true });
            <!--accodrion -->
            var accIndex = parseInt(jQuery('h3.selected').attr('position'));
            jQuery( "#accordion" ).accordion({fillSpace: true, active: accIndex-1});

            <!--$('h3.selected').accordion("activate", this.attr('position'));-->
            jQuery( "#accordionResizer" ).resizable({
            minHeight: 550,resize:
            function() {
            jQuery( "#accordion" ).accordion( "resize" );
            }
            });

            var stop = false;

            // When the Drop Down is Clicked, Set Stop Var to True
            jQuery("#drop-down").click(function(event) { stop=true; });

            // Prevent Accordion Close if Stop Var Set
            jQuery("#accordion").click(function(event) {
            if (stop) {
            event.stopImmediatePropagation();
            event.preventDefault();
            stop = false;
            }
            });
            <!-- ui breadCrumb-->
            jQuery("#breadCrumb").jBreadCrumb();

            <!--ui uniform-->

            <!--jQuery("input:text, input:email, input:url, input:password, input:number, textarea").uniform();-->
            jQuery("input:text, textarea").uniform();
            <!-- ui buttons-->
            jQuery( "button, input:submit, input:reset, .ui-button, [type=button]" ).button();


            });
        </script>

        <!--<script type="text/javascript" src="{$blockPrefix}/resource/js/CFormsSuggest.js"/>-->

    </xsl:template>
</xsl:stylesheet>
