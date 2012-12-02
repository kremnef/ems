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

    <xsl:param name="aloha-editor-lang">en</xsl:param>
    <xsl:param name="forms-resources"/>
    <xsl:param name="blockPrefix"/>

    <xsl:template match="head" mode="forms-aloha-editor">
        <xsl:variable name="doubleQuote">&#34;</xsl:variable>


        <!-- turn an element into editable Aloha a continuous text -->
        <!--todo: привязать локаль-->


        <!--<link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/js/jquery-plugin/aloha/css/aloha.css" media="screen"/>-->
    </xsl:template>

    <xsl:template match="body" mode="forms-aloha-editor"/>

    <!--+
    | fi:field with @type 'ckeditor'
    +-->
    <xsl:template match="fi:field[fi:styling[@type='aloha-editor']]">
        <xsl:variable name="doubleQuote">&#34;</xsl:variable>
        <xsl:variable name="singleQuote">&#39;</xsl:variable>
           <script type="text/javascript" src="/ems/resource/js/ext3/ext-all.js"/>
        <script type="text/javascript">GENTICS_Aloha_base="/ems/resource/js/jquery-plugin/aloha/";</script>

        <script type="text/javascript" src="/ems/resource/js/extjs/cmp-foundation-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/data-foundation-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/data-json-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/data-list-views-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/ext-dd-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/ext-foundation-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/ext-jquery-adapter-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-buttons-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-forms-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-grid-editor-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-grid-foundation-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-tabs-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-tips-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-toolbars-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/pkg-tree-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/resizable-debug.js"/>
        <script type="text/javascript" src="/ems/resource/js/extjs/window-debug.js"/>

        <script type="text/javascript" src="/ems/resource/js/jquery-plugin/aloha/aloha-nodeps.js"/>
        <link rel="stylesheet" type="text/css" href="/ems/resource/js/jquery-plugin/aloha/css/aloha.css"
              media="screen"/>
        <script type="text/javascript"  src="/ems/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Format/plugin.js"/>


        <script type="text/javascript">
          GENTICS.Aloha.settings = {
    logLevels: {'error': true, 'warn': true, 'info': true, 'debug': false},
    errorhandling : false,
    ribbon: false,
    "i18n": {
        // you can either let the system detect the users language (set acceptLanguage on server)
        // "acceptLanguage": 'de-de,de;q=0.8,it;q=0.6,en-us;q=0.7,en;q=0.2'
        // or set current on server side to be in sync with your backend system
        "current": "en"

    },

    "plugins": {
        "com.gentics.aloha.plugins.Format": {
            // all elements with no specific configuration get this configuration
            config : [ 'b', 'i','sub','sup'],
            editables : {
                // no formatting allowed for title
                '#title' : [ ],
                // formatting for all editable DIVs
                'div' : [ 'b', 'i', 'del', 'sub', 'sup' ],
                // content is a DIV and has class .article so it gets both buttons
                '.article' : [ 'b', 'i', 'p', 'title', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'pre', 'removeFormat']
            }
        }
    }
};

        </script>

        <script type="text/javascript">
            <!--function initAloha(contentId) {
            alert(contentId);-->
            $(function () {
            $("#contentAloha").aloha();
            <!--};-->
            });
        </script>

        <!--<script type="text/javascript"
              src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Table/plugin.js"/>
      <script type="text/javascript"
              src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.List/plugin.js"/>
      <script type="text/javascript"
              src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Link/plugin.js"/>
      <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.HighlightEditables/plugin.js"/>-->
        <!--<script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.TOC/plugin.js"/>
      <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Link/delicious.js"/>
      <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Link/LinkList.js"/>
      <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Paste/plugin.js"/>
      <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Paste/wordpastehandler.js"/>-->
        <!--<script type="text/javascript">GENTICS_Aloha_base="<xsl:value-of select="$blockPrefix"/>/resource/js/jquery-plugin/aloha/";
        </script>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/aloha/aloha.js"/>
        <script type="text/javascript"
                src="{$blockPrefix}/resource/js/jquery-plugin/aloha/plugins/com.gentics.aloha.plugins.Format/plugin.js"/>
        <script type="text/javascript">
            GENTICS.Aloha.settings = {
            logLevels: {'error': true, 'warn': true, 'info': true, 'debug': false},
            errorhandling : false,
            ribbon: false,
            "i18n": {
            // you can either let the system detect the users language (set acceptLanguage on server)
            // "acceptLanguage": 'de-de,de;q=0.8,it;q=0.6,en-us;q=0.7,en;q=0.2'
            // or set current on server side to be in sync with your backend system
            "current": "en",

            },
            plugins": {
            "com.gentics.aloha.plugins.Format": {
            // all elements with no specific configuration get this configuration
            config : [ 'b', 'i','sub','sup'],
            editables : {
            // no formatting allowed for title
            '#title' : [ ],
            // formatting for all editable DIVs
            'div' : [ 'b', 'i', 'del', 'sub', 'sup' ],
            // content is a DIV and has class .article so it gets both buttons
            '.article' : [ 'b', 'i', 'p', 'title', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'pre', 'removeFormat']
            }
            }


            };

        </script>
        <script type="text/javascript">
            function initAloha(contentId) {
            $(contentId).aloha();
            };
        </script>-->
        <!-- "plugins": {
           "com.gentics.aloha.plugins.Format": {
               // all elements with no specific configuration get this configuration
              config : [ 'b', 'i','sub','sup'],
                editables : {
                  // no formatting allowed for title
                  '#title'	: [ ],
                  // formatting for all editable DIVs
                  'div'		: [ 'b', 'i', 'del', 'sub', 'sup'  ],
                  // content is a DIV and has class .article so it gets both buttons
                  '.article'	: [ 'b', 'i', 'p', 'title', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'pre', 'removeFormat']
                }
          },
           "com.gentics.aloha.plugins.List": {
               // all elements with no specific configuration get an UL, just for fun :)
              config : [ 'ul' ],
                editables : {
                  // Even if this is configured it is not set because OL and UL are not allowed in H1.
                  '#title'	: [ 'ol' ],
                  // all divs get OL
                  'div'		: [ 'ol' ],
                  // content is a DIV. It would get only OL but with class .article it also gets UL.
                  '.article'	: [ 'ul' ]
                }
          },
           "com.gentics.aloha.plugins.Link": {
               // all elements with no specific configuration may insert links
              config : [ 'a' ],
                editables : {
                  // No links in the title.
                  '#title'	: [  ]
                },
                // all links that match the targetregex will get set the target
               // e.g. ^(?!.*aloha-editor.com).* matches all href except aloha-editor.com
                targetregex : '^(?!.*aloha-editor.com).*',
                // this target is set when either targetregex matches or not set
              // e.g. _blank opens all links in new window
                target : '_blank',
                // the same for css class as for target
                cssclassregex : '^(?!.*aloha-editor.com).*',
                cssclass : 'aloha',
                // use all resources of type website for autosuggest
                objectTypeFilter: ['website'],
                // handle change of href
                onHrefChange: function( obj, href, item ) {
                    if ( item ) {
                      jQuery(obj).attr('data-name', item.name);
                    } else {
                      jQuery(obj).removeAttr('data-name');
                    }
                }
          },
           "com.gentics.aloha.plugins.Table": {
               // all elements with no specific configuration are not allowed to insert tables
              config : [ ],
                editables : {
                  // Allow insert tables only into .article
                  '.article'	: [ 'table' ]
                }
          }
        }-->

        <!--<script type="text/javascript">-->
        <!--$(document).ready(function() {-->
        <!--$('#<xsl:value-of select="@id" />').aloha();-->
        <!--$("#content").aloha();-->
        <!--$('#teaser').aloha();-->
        <!--$('#content').aloha();-->
        <!--});-->


        <!--</script>-->

        <!--<div id="{@id}" name="{@id}" title="{fi:hint}">
            <xsl:apply-templates select="." mode="styling"/>
            <xsl:apply-templates select="fi:value/node()" mode="aloha-editor-copy"/>
        </div>-->
        <!--aloha-->

        <div id="contentAloha" name="contentAloha" class="article">
            <hgroup>
                <h1>Aloha</h1>
                <h2>Etymology</h2>

            </hgroup>
            <p>The word
                <a href="http://en.wikipedia.org/wiki/Aloha" target="_blank" class="external">aloha</a>
                derives from the Proto-Polynesian root<i>*qalofa</i>. It has cognates in other Polynesian
                languages, such as Samoan alofa
                and Māori aroha, also meaning "love."
            </p>
            <p>
                <a href="http://aloha-editor.com/">Aloha Editor</a>
                is the word's most advanced browser based Editor made with aloha passion.
            </p>
            <p>A folk etymology claims that it derives from a compound of the
                <a href="http://en.wikipedia.org/wiki/Hawaii" target="_blank" class="external">Hawaiian</a>
                words alo meaning "presence", "front", "face", or "share"; and
                ha, meaning "breath of life" or "essence of life." Although alo does indeed mean "presence"
                etc., the word for breath is spelled with a macron
                or kahakō over the a (hā) whereas the word aloha does not have a long a.
            </p>

            <h2>Usage</h2>
            <p>Before contact with the West, the words used for greeting were welina and anoai. Today, "aloha
                kakahiaka" is the phrase for "good
                morning." "Aloha ʻauinalā" means "good afternoon" and "aloha ahiahi" means "good evening."
                "Aloha kākou" is a common form of "welcome to all."
            </p>
            <p>In modern Hawaiʻi, numerous businesses have aloha in their names, with more than 3 pages of
                listings in the Oʻahu phone book alone.
            </p>
            <h2>Trends</h2>
            <p>Recent trends are popularizing the term elsewhere in the United States. Popular entertainer,
                Broadway star and Hollywood actress Bette
                Midler, born in Honolulu, uses the greeting frequently in national appearances. The word was
                also used frequently in the hit television drama
                Hawaii Five-O. In the influential 1982 film comedy Fast Times at Ridgemont High, the eccentric
                teacher Mr. Hand makes use of the greeting. The
                Aloha Spirit is a major concept in Lilo and Stitch, a very popular Disney series of movies and
                TV shows, set in Hawaiʻi. The drama series Lost,
                shot in Hawaiʻi, has a thank you note at the end of the credits saying "We thank the people of
                Hawaiʻi and their Aloha Spirit". Aloha is a term
                also used in the Nickelodeon program Rocket Power.
            </p>
            <ul>
                <li>Arguably the most famous historical Hawaiian song, "Aloha ʻOe" was written by the last queen
                    of Hawaii, Liliʻuokalani.
                </li>
                <li>The term inspired the name of the ALOHA Protocol introduced in the 1970s by the University
                    of Hawaii.
                </li>

                <li>In Hawaii someone can be said to have or show aloha in the way they treat others; whether
                    family, friend, neighbor or stranger.
                </li>
            </ul>
        </div>

        <xsl:apply-templates select="." mode="common"/>

    </xsl:template>

    <xsl:template match="@*|*" mode="aloha-editor-copy">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" mode="aloha-editor-copy"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="text()" mode="aloha-editor-copy">
        <xsl:copy-of select="translate(., '&#13;', '')"/>
    </xsl:template>

</xsl:stylesheet>
