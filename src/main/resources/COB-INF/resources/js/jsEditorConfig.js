function prepareJSEditor(div, locale, imageTreePath) {
    (function($) {
        $(document).ready(function() {
            $(div).wysiwyg(
                /* "addControl", "quote", {
                 groupIndex: 2,
                 icon: './../tests/images/quote02.gif',
                 tooltip: 'Quote',
                 tags: ['blockquote'],
                 exec: function () {
                 var range = this.getInternalRange(),
                 common = range.commonAncestorContainer,
                 blockquote = this.dom.getElement("blockquote");

                 // if a text node is selected, we want to make the wrap the whole element, not just some text
                 if (common.nodeType === 3) {
                 common = common.parentNode;
                 }

                 if (blockquote && $(blockquote).hasClass("quote")) {
                 $(common).unwrap();
                 }
                 else {
                 if ("body" !== common.nodeName.toLowerCase()) {
                 $(common).wrap("<blockquote class='quote' />");
                 }
                 }
                 },
                 callback: function (event, Wysiwyg) {
                 alert("callback triggered!");
                 }
                 },*/


                {

                    xhtml5: true,
                    unicode: 1,
                    plugins: {
//                        autoload: true,
                        i18n:{
                            lang: locale
                        }/*,
                         rmFormat: {
                         rmMsWordMarkup: true
                         }*/
                    },
                    controls : {
                        /*"fileManager": {
                         visible: true,
                         groupIndex: 12,
                         tooltip: "File Manager",
                         exec: function () {
                         $.wysiwyg.fileManager.init(function (file) {
                         file ? alert(file) : alert("No file selected.");
                         });
                         }
                         },*/
                        fullscreen: {
                            groupIndex: 12,
                            visible
                                :
                                true,
                            exec
                                :
                                function () {

                                    if ($.wysiwyg.fullscreen) {

                                        $.wysiwyg.fullscreen.init(this);
                                    }
                                }

                            ,
                            tooltip: "Fullscreen"
                        }
                        ,
                        strikeThrough : {
                            visible : false
                        }
                        ,
                        insertHorizontalRule : {
                            visible : false
                        }
                        ,
                        h1mozilla : {
                            visible : false
                        }
                        ,
                        h2mozilla : {
                            visible : false
                        }
                        ,
                        h3mozilla : {
                            visible : false
                        }
                        ,
                        h1 : {
                            visible : false
                        }
                        ,
                        h2 : {
                            visible : true
                        }
                        ,
                        h3 : {
                            visible : false
                        }
                        ,
                        increaseFontSize : {
                            visible : false
                        }
                        ,
                        decreaseFontSize : {
                            visible : false
                        }
                        ,
                        html : {
                            visible : true
                        }

                    }

                });
//            $.wysiwyg.fileManager.setAjaxHandler(imageTreePath);
            /*$('a[href="#insertImage"]').click(function() {
             $('#wysiwyg').wysiwyg('insertImage', 'https://gs1.wac.edgecastcdn.net/80460E/assets/images/modules/header/logov6-hover.png', { 'class': 'myClass', 'className': 'myClass' });
             });*/

        });
    })(jQuery);
}