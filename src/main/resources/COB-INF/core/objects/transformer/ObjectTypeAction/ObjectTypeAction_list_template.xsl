<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">
    <xsl:import href="blockcontext:/ems/stylesheets/admin/list_template.xsl"/>
    <!--<xsl:import href="blockcontext:/ems/stylesheets/admin/list_template_common.xsl"/>-->
    <xsl:template match="filter">
        <xsl:param name="requestURI"/>
        <xsl:param name="param"/>
        <div id="objectTypes">
            <script type="text/javascript">
                function changeDocumentType(id){
                var dt = document.getElementById("objectTypeId");
                dt.value = id;
                }
            </script>

            <i18n:text catalogue="core" key="ObjectType"/>:
            <select class="objectType-select" style="width:350px;" onChange="changeObjectType(this.value);">
                <option value="0">
                    --
                </option>

                <xsl:for-each select="object-type">
                    <xsl:if test="count(/root/content/filter/object-type[contains($requestURI, name)]) &gt; 0 ">
                        <xsl:attribute name="class">filtered</xsl:attribute>
                    </xsl:if>
                    <option value="{@id}">
                        <i18n:text catalogue="core" key="ObjectType.{name}"/>
                    </option>

                </xsl:for-each>

            </select>
            <script type="text/javascript">
                $(".objectType-select").chosen();
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
    </xsl:template>
    <!--<xsl:template name="additionalProperties">
        <xsl:param name="currentItem" select="."/>
        <xsl:for-each select="/content/items/headers/header">


            <xsl:variable name="field" select="."/>
            <xsl:variable name="tdValue" select="$currentItem/*[name(.) = $field]"/>
            <xsl:variable name="systemNodeId" select="$currentItem/systemNodeId"/>
            <td class="post-title column-title">
                <xsl:choose>
                    <xsl:when test="$systemNodeId &gt; 0">
                        <a href="editSystemNode?id={$systemNodeId}">
                            <xsl:value-of
                                    select="$tdValue"/>
                        </a>
                    </xsl:when>
                    <xsl:otherwise>&#160;</xsl:otherwise>
                </xsl:choose>
            </td>

        </xsl:for-each>

    </xsl:template>-->

</xsl:stylesheet>
