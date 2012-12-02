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
  limitations under the License. sss
-->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ft="http://apache.org/cocoon/forms/1.0#template"
                xmlns:fi="http://apache.org/cocoon/forms/1.0#instance"
                xmlns:jx="http://apache.org/cocoon/templates/jx/1.0"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1">
    <xsl:param name="requestURI"/>
    <xsl:param name="locale"/>
    <xsl:param name="dojo-resources"/>
    <xsl:param name="forms-resources"/>
    <xsl:param name="servletPath"/>
    <!--<xsl:param name="blockPrefix"/>-->


    <!--<xsl:include href="../../../../resources/forms-advanced-field-styling.xsl"/>-->
    <!--<xsl:include href="../../../../resources/forms-page-styling.xsl"/>-->
    <!--<xsl:include href="resource://org/apache/cocoon/forms/resources/forms-field-styling.xsl"/>-->
    <!--xsl:include href="servlet:forms:/resource/internal/xsl/forms-page-styling.xsl"/>
    <xsl:include href="servlet:forms:/resource/internal/xsl/forms-field-styling.xsl"/-->
    <xsl:template match="/">

        <html>
            <head>

                <xsl:variable name="blockPrefix" select="'/ems'"/>
                <title><xsl:value-of select="$blockPrefix"/></title>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <link rel="stylesheet" id="login-css" href="{$servletPath}/resource/css/login.css" type="text/css" media="all"/>

                <meta name="robots" content="noindex,nofollow"/>
                <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE"/>
                <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"/>
                <!--<link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/css/ems.css"/>-->
                <link rel="stylesheet" type="text/css" href="{$blockPrefix}/resource/themes/flick/styles.css"/>

                <!--lib JQUERY !!!!!! FIRST LOAD -->
                <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery/jquery.js"/>
                <!--jQUERY UI-->
                <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery/jquery-ui-1.8.11.custom.min.js"/>


                <!--Notify-->
                <link rel="stylesheet" type="text/css"
                      href="{$blockPrefix}/resource/js/jquery-plugin/notify/ui.notify.css"
                      media="all"/>
                <script type="text/javascript"
                        src="{$blockPrefix}/resource/js/jquery-plugin/notify/jquery.notify.min.js"/>

                 <!--Uniform-->
        <link rel="stylesheet" type="text/css"
              href="{$blockPrefix}/resource/js/jquery-plugin/uniform/css/uniform.default.css" media="screen"/>
        <script type="text/javascript" src="{$blockPrefix}/resource/js/jquery-plugin/uniform/jquery.uniform.js"/>
                <script type="text/javascript">
            $(function() {

            <!--ui uniform-->

            <!--$("input:text, textarea, select").uniform();-->
            $("input:text,input:password, textarea").uniform();
            <!-- ui buttons-->
            $( "button, input:submit, .ui-button, [type=button]" ).button();


            });
        </script>
            </head>
            <body class="login">

                <div id="login">

                    <div>
                        <!--xsl:apply-templates select="." mode="forms-field"/-->
                        <!--xsl:apply-templates /-->
                        <xsl:copy-of select="."/>
                    </div>
                    <!--<form name="loginform" id="loginform" action="" method="post">
                        <p>
                            <label>Логин
                                <br/>
                                <input name="login" id="user_login" class="input" value="" size="20" tabindex="10"
                                       type="text"/>
                            </label>
                        </p>
                        <p>
                            <label>Пароль
                                <br/>
                                <input name="password" id="user_pass" class="input" value="" size="20" tabindex="20"
                                       type="password"/>
                            </label>
                        </p>
                        <p class="forgetmenot">
                            <label>
                                <input name="rememberme" id="rememberme" value="forever" tabindex="90" type="checkbox"/>
                                Запомнить меня
                            </label>
                        </p>
                        <p class="submit">
                            <input name="wp-submit" id="wp-submit" value="Войти" tabindex="100" type="submit"/>
                            <input name="redirect_to" value="http://armv/wp-admin/users.php" type="hidden"/>
                            <input name="testcookie" value="1" type="hidden"/>
                        </p>
                    </form>-->

                    <!--p id="nav">
                        <a href="registration">Регистрация</a>
                        |
                        <a href="forgotpasswd" title="Восстановление пароля">Забыли
                            пароль?
                        </a>
                    </p-->

                </div>

                <!--p id="backtoblog">
                    <a href="http://armv/blog/" title="Потерялись?">← Назад к ARMV</a>
                </p-->

                <!--script type="text/javascript">
                    try {
                        document.getElement('loginform.username:input').focus();
                    } catch(e) {
                    }
                </script-->
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>