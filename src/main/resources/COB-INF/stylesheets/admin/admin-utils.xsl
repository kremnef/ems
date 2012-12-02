<?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                exclude-result-prefixes="xsl i18n">

    <xsl:template name="parents">
        <xsl:param name="entity"/>
        <xsl:variable name="checkedEntity">
            <xsl:choose>
                <xsl:when test="$entity = 'FileObject'">Folder</xsl:when>
                <xsl:otherwise><xsl:value-of select="$entity"/></xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <!--<h1><xsl:value-of select="$entity"/></h1>-->
        <div id="breadCrumb" class="breadCrumb module">
            <ul>
                <li>
                    <a href="{$checkedEntity}-list">Home</a>
                </li>
                <li>
                    <a href="{$checkedEntity}-list">
                        <i18n:text key="root"/>
                    </a>
                </li>
                <xsl:for-each select="//parent">

                    <!--<xsl:choose>
                        <xsl:when test="position() = last()">
                            <li>
                                <xsl:value-of select="name"/>
                            </li>
                        </xsl:when>
                        <xsl:otherwise>-->
                            <li>
                                <a href="{$checkedEntity}-list?parentId={@id}">
                                    <xsl:value-of select="name"/>
                                </a>
                            </li>
                        <!--</xsl:otherwise>
                    </xsl:choose>-->
                </xsl:for-each>
            </ul>

        </div>

      <!--  <xsl:template match="parents">
            <div id="bread-crumb" class="edit">
                <span id="bc-root">
                    <a href="list{$entity}">
                        <i18n:text catalogue="ems" key="root"/>
                    </a>
                </span>
                <xsl:for-each select="//parent">
                    <span>></span>
                    <span>
                        <a href="list{$entity}?parentId={@id}">
                            <xsl:value-of select="name"/>
                        </a>
                    </span>
                </xsl:for-each>

            </div>
        </xsl:template>-->
        <!--<div id="bread-crumb" class="edit">
            <span id="bc-root">
                <a href="list{$entity}">
                    <i18n:text key="root"/>
                </a>
            </span>
            <xsl:for-each select="//parent">
                <span>></span>
                <xsl:choose>
                    <xsl:when test="position() = last()">
                        <span>
                            <strong>
                                <xsl:value-of select="name"/>
                            </strong>
                        </span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span>
                            <a href="list{$entity}?parentId={@id}">
                                <xsl:value-of select="name"/>
                            </a>
                        </span>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </div>-->
    </xsl:template>
</xsl:stylesheet>