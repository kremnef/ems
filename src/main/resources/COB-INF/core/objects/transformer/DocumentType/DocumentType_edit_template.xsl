<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">

    <xsl:import href="blockcontext:/ems/stylesheets/admin/edit_template.xsl"/>


    <xsl:template match="parents">
        <div id="bread-crumb" class="edit">
            <span id="bc-root">
                <a href="Content-list">
                    <i18n:text key="root"/>
                </a>
            </span>
            <xsl:for-each select="//parent">
                <span> > </span>

                <xsl:choose>
                    <xsl:when test="position() = last()">
                        <span>
                            <a href="Content-edit?id={@id}">
                                <xsl:value-of select="name"/>
                            </a>
                        </span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span>
                            <a href="Content-list?parentId={@id}">
                                <xsl:value-of select="name"/>
                            </a>
                        </span>
                    </xsl:otherwise>
                </xsl:choose>

            </xsl:for-each>
            <span> > </span>
            <span>
                <a href="editDocument?id={document/@id}&amp;documentTypeId={document/@documentTypeId}">
                    <i18n:text catalogue="core" key="Document"/>
                </a>
            </span>
            <span> > </span>
            <span>
                <strong>
                    <i18n:text i18n:catalogue="documenttypes" key="{$entity}"/>
                </strong>
            </span>

        </div>
    </xsl:template>


    <xsl:template name="more">
        <xsl:param name="corePrefix"/>
    </xsl:template>


</xsl:stylesheet>
