<?xml version="1.0"?>
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
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://apache.org/cocoon/forms/1.0#instance"
                exclude-result-prefixes="fi">
    <!--+
    | This stylesheet is designed to be included by 'forms-advanced-styling.xsl'.
    +-->

    <xsl:param name="ckeditor-lang">en</xsl:param>
    <xsl:param name="forms-resources" />
    <xsl:param name="blockPrefix" />

    <xsl:template match="head" mode="forms-ckeditor">
        <xsl:variable name="doubleQuote">&#34;</xsl:variable>
        <script type="text/javascript">
            var CKEDITOR_BASEPATH = '<xsl:value-of select="$blockPrefix"/>/ckeditor/';
        </script>
        <script type="text/javascript" src="{$blockPrefix}/ckeditor/ckeditor.js"></script>
    </xsl:template>

    <xsl:template match="body" mode="forms-ckeditor"/>

    <!--+
    | fi:field with @type 'ckeditor'
    +-->
    <xsl:template match="fi:field[fi:styling[@type='ckeditor']]">
        <xsl:variable name="doubleQuote">&#34;</xsl:variable>
        <xsl:variable name="singleQuote">&#39;</xsl:variable>
        <textarea id="{@id}" name="{@id}" title="{fi:hint}">
            <xsl:apply-templates select="." mode="styling"/>
            <xsl:apply-templates select="fi:value/node()" mode="ckeditor-copy"/>
        </textarea>
        <script type="text/javascript">
	        CKEDITOR.replace('<xsl:value-of select="@id" />',
            {
                toolbar : 'EmsToolbar',
                uiColor : '#efefef',
                language : '<xsl:value-of select="$ckeditor-lang" />',
                filebrowserBrowseUrl : '1',
                <!--filebrowserBrowseUrl : '<xsl:value-of select="$blockPrefix" />/core/folderTree',-->
            });
        </script>
        <xsl:apply-templates select="." mode="common"/>
        <!--xsl:choose>
            <xsl:when test="fi:styling/conf">
                <script type="text/javascript">
                    var handler = new Object();
                    <xsl:value-of select="concat('handler.fieldId = ', $doubleQuote, @id, $doubleQuote, ';')"/>
                    handler.forms_onload = function() {
                    <xsl:value-of select="concat('var id = ', $doubleQuote, @id, $doubleQuote, ';')"/>
                    var textarea = document .getElementById(id);
                    var editor = new ckeditor(id);
                    textarea.ckeditor = editor;
                    var conf = editor.config;
                    <xsl:value-of select="fi:styling/conf/text()"/>
                    editor.generate();
                    }
                    cocoon.forms.addOnLoadHandler(handler);
                </script>
            </xsl:when>
            <xsl:when test="fi:styling/initFunction and not(fi:styling/conf)">
                <script type="text/javascript">
                    var handler = new Object();
                    <xsl:value-of select="concat('handler.fieldId = ', $doubleQuote, @id, $doubleQuote, ';')"/>
                    <xsl:value-of
                            select="concat('if(typeof(', fi:styling/initFunction, ')!=', $doubleQuote, 'function', $doubleQuote, ')')"/>
                    {
                    <xsl:value-of
                            select="concat('alert(', $doubleQuote, fi:styling/initFunction, ' is not a function ', $doubleQuote)"/>
                    +
                    <xsl:value-of
                            select="concat($doubleQuote, 'or not available! Can', $singleQuote, 't render widget ', $singleQuote, @id, $singleQuote, $doubleQuote, ');')"/>
                    }
                    <xsl:value-of select="concat('handler.forms_onload = ', fi:styling/initFunction, ';')"/>
                    cocoon.forms.addOnLoadHandler(handler);
                </script>
            </xsl:when>
            <xsl:otherwise>
                <script type="text/javascript">
                    var handler = new Object();
                    <xsl:value-of select="concat('handler.fieldId = ', $doubleQuote, @id, $doubleQuote, ';')"/>
                    handler.forms_onload = function() {
                    <xsl:value-of select="concat('ckeditor.replace(', $singleQuote, @id, $singleQuote, ');')"/>
                    }
                    cocoon.forms.addOnLoadHandler(handler);
                </script>
            </xsl:otherwise>
        </xsl:choose-->
    </xsl:template>

    <xsl:template match="@*|*" mode="ckeditor-copy">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" mode="ckeditor-copy"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="text()" mode="ckeditor-copy">
        <xsl:copy-of select="translate(., '&#13;', '')"/>
    </xsl:template>

</xsl:stylesheet>
