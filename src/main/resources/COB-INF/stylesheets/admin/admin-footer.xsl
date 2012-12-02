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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template name="analytics">
        <Script type="text/javascript">
            var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
            document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type = 'text/javascript' % 3E % 3C / script % 3E"));
        </Script>
        <Script type="text/javascript">
            try {
                var pageTracker = _gat._getTracker("UA-9643290-1");
                pageTracker._trackPageview();
            } catch(err) {
            }
        </Script>
        <!-- Yandex.Metrika -->
        <Script src="//mc.yandex.ru/resource/watch.js" type="text/javascript"></Script>
        <Script type="text/javascript">
            try {
                var yaCounter220849 = new Ya.Metrika(220849);
            } catch(e) {
            }
        </Script>
        <noScript>
            <div style="position: absolute;">
                <img src="//mc.yandex.ru/watch/220849" alt=""/>
            </div>
        </noScript>
        <!-- Yandex.Metrika -->

    </xsl:template>
    <xsl:template name="footer">

        <!--div id="footer">
            <p id="footer-left" class="alignleft">
                <span id="footer-thankyou">Спасибо вам за творчество с<a href="http://wordpress.org">WordPress</a>.
                </span>
                |
                <a href="http://codex.wordpress.org/Заглавная_страница">Документация</a>
                |
                <a href="http://ru.forums.wordpress.org/forum/20">Обратная связь</a>
            </p>
            <p id="footer-upgrade" class="alignright">Версия 2.8.4</p>
            <div class="clear"></div>
        </div-->
        <!--Script type='text/javascript'>
            /* <![CDATA[ */
        var commonL10n = {
            warnDelete: "Вы собираетесь удалить выделенные объекты.\n  &laquo;Отмена&raquo; &#8212; оставить, &laquo;OK&raquo; &#8212; удалить."
        };
        try{convertEntities(commonL10n);}catch(e){};
        var inlineEditL10n = {
            error: "Ошибка при сохранении изменений.",
            ntdeltitle: "Удалить из массового редактирования",
            notitle: "(без названия)"
        };
        try{convertEntities(inlineEditL10n);}catch(e){};
        /* ]]> */
        </Script>
        <Script type='text/javascript'
                src='http://armv/wp-admin/load-scripts.php?c=1&amp;load=hoverIntent,common,jquery-color,jquery-form,suggest,inline-edit-post&amp;ver=0a6df985ccca92b7904b73960a4def9a'></Script>

        <Script type="text/javascript">if(typeof wpOnload=='function')wpOnload();</Script-->

    </xsl:template>
</xsl:stylesheet>