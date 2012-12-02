<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <!--<xsl:import href="blockcontext:/ems/stylesheets/admin/list_template_base.xsl"/>-->
    <xsl:import href="blockcontext:/ems/stylesheets/admin/list_template.xsl"/>

    <xsl:template match="filter">
        <xsl:param name="requestURI"/>
        <xsl:param name="parentId"/>
        <xsl:param name="param"/>
        <br/>

        <div id="fileTypes">
            <script type="text/javascript">
                function changeObjectType(id){
                var url = '<xsl:value-of select="$requestURI"/>?parentId=<xsl:value-of select="$parentId"/>&amp;json=true&amp;fileTypeGroupId='+id;
                flexiGridFilter(url);
                }
            </script>
            <i18n:text catalogue="core" key="FileTypeGroup"/>:
            <select class="fileType-select" style="width:350px;" onChange="changeObjectType(this.value);">
                <option value="0">
                    --
                </option>

                <xsl:for-each select="file-type">
                    <!--  <xsl:if test="count(/root/content/filter/file-type[contains($requestURI, name)]) &gt; 0 ">
                        <xsl:attribute name="class">filtered</xsl:attribute>
                    </xsl:if>-->
                    <option value="{@id}">
                        <i18n:text catalogue="core" key="FileTypeGroup.{type}"/>
                    </option>

                </xsl:for-each>

            </select>
            <script type="text/javascript">
                $(".fileType-select").chosen();
            </script>

            <!--    <xsl:for-each select="doctype">
            <xsl:if test="count(/root/content/filter/doctype[contains($requestURI, name)]) &gt; 0 ">
                <xsl:attribute name="class">filtered</xsl:attribute>
            </xsl:if>
            <a href="{concat($requestURI,'/',name)}">
            &lt;!&ndash;<a href="{concat(substring-after('list', $requestURI),'/',name)}">&ndash;&gt;
                <xsl:value-of select="description"/>
            </a>
            ,
        </xsl:for-each>

           <a href="{concat(substring-after('/', $requestURI),'/',name)}">
            Все типы
            </a>-->
        </div>
        <br/>
    </xsl:template>
</xsl:stylesheet>
