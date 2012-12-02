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
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                exclude-result-prefixes="fi">
    <!--+
    | This stylesheet is designed to be included by 'forms-advanced-styling.xsl'.
    +-->

    <xsl:param name="forms-resources"/>
    <xsl:param name="blockPrefix"/>

    <xsl:template match="head" mode="jWYSIWYG-editor">
        <xsl:variable name="doubleQuote">&#34;</xsl:variable>

        <link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/editor/jquery.wysiwyg.css"
              media="screen"/>
        <link rel="stylesheet" type="text/css"
              href="{$blockPrefix}/resource/js/jquery-plugin/editor/jquery.wysiwyg.modal.css" media="screen"/>
        <link rel="stylesheet" type="text/css"
              href="{$blockPrefix}/resource/js/jquery-plugin/editor/plugins/fileManager/wysiwyg.fileManager.css"
              media="screen"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/jquery.wysiwyg.js"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/controls/wysiwyg.image.js"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/controls/wysiwyg.link.js"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/controls/wysiwyg.table.js"/>
        <!--plugins-->
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/plugins/wysiwyg.i18n.js"/>
        <!--<script type="text/javascript"
                src="{$blockPrefix}/resource/js/jquery-plugin/editor/plugins/wysiwyg.autoload.js"/>-->
        <!--<script type="text/javascript"
                src="{$blockPrefix}/resource/js/jquery-plugin/editor/plugins/wysiwyg.rmformat.js"/>-->

        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/i18n/lang.ru.js"/>


        <script type="text/javascript" src="{$blockPrefix}/resource/js/jsEditorConfig.js"/>


        <!--<script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/plugins/wysiwyg.fullscreen.js"/>-->
        <!--<script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/plugins/wysiwyg.fileManager.js"/>-->


        <!-- turn an element into editable jWYSIWYG a continuous text -->
        <!--todo: привязать локаль-->


        <!--<link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/editor/css/blueprint/screen.css" media="screen, projection" />
<link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/editor/css/blueprint/print.css" media="print" />-->
        <!--[if lt IE 8]><link rel="stylesheet" href="{$blockPrefix}/resource/js/jquery-plugin/editor/css/blueprint/ie.css" type="text/css" media="screen, projection" /><![endif]-->


        <!--<link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/editor/jquery.wysiwyg.css" media="screen"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/jquery.wysiwyg.js"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/controls/wysiwyg.image.js"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/controls/wysiwyg.link.js"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/editor/controls/wysiwyg.table.js"/>-->


    </xsl:template>


    <xsl:template match="body" mode="jWYSIWYG-editor"/>

    <!--+
    | fi:field with @type 'ckeditor'
       $(function () {

                    var divId = "#xmlMenuTree";
                    var entity = "Folder";
                    var ajaxUrlTree;
                    var thisId = parseInt('${id}');
                    ajaxUrlTree = "/ems/core/treeObjects?entity="+entity;
                     });
    +-->
    <xsl:template match="fi:field[fi:styling[@type='HTML-editor']]">
        <xsl:variable name="doubleQuote">&#34;</xsl:variable>
        <xsl:variable name="singleQuote">&#39;</xsl:variable>

        <script type="text/javascript">

            <![CDATA[
                prepareJSEditor("#wysiwyg", "ru", "");
             ]]>
        </script>
        <!--<input type="button" value="Choose" i18n:attr="value" onclick="openInsertImageDialog('#insertImageTreeContainer')"/>-->
        <div i18n:attr="title" title="core:FileObject.choose" id="insertImageTreeContainer" class="dialogContent">
            <div class="common-div">
                <span class="ui-icon-home"></span>
                <a id="parentTreeRootNode" href="#" class=""></a>
            </div>
            <div id="insertImageTree"></div>
        </div>

        <textarea id="wysiwyg" rows="20" cols="100" name="{@id}" title="{fi:hint}">
            <xsl:apply-templates select="." mode="styling"/>
            <xsl:apply-templates select="fi:value/node()" mode="jWYSIWYG-editor-copy"/>
        </textarea>
        <!--jWYSIWYG-->


        <xsl:apply-templates select="." mode="common"/>

    </xsl:template>

    <xsl:template match="@*|*" mode="jWYSIWYG-editor-copy">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" mode="jWYSIWYG-editor-copy"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="text()" mode="jWYSIWYG-editor-copy">
        <xsl:copy-of select="translate(., '&#13;', '')"/>
    </xsl:template>

</xsl:stylesheet>
