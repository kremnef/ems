/**
 * Controls: Image plugin
 *
 * Depends on jWYSIWYG
 */
(function ($) {
    /*var imgSelected = false;

     function checkImgSelected() {
     alert(imgSelected);
     if (imgSelected != false) {
     return 1;
     } else {
     return 2;
     }
     }*/
    var imagePath;


    "use strict";
    if (undefined === $.wysiwyg) {
        throw "wysiwyg.image.js depends on $.wysiwyg";
    }

    if (!$.wysiwyg.controls) {
        $.wysiwyg.controls = {};
    }
    /*
     * Wysiwyg namespace: public properties and methods
     */
    $.wysiwyg.controls.image = {
        groupIndex: 6,
        visible: true,
        exec: function () {
            $.wysiwyg.controls.image.init(this);
        },
        tags: ["img"],
        tooltip: "Insert image",
        init: function (Wysiwyg) {
            var self = this, elements, adialog, dialog, formImageHtml, regexp, dialogReplacements, key, translation,
                img = {
                    alt: "",
                    self: Wysiwyg.dom ? Wysiwyg.dom.getElement("img") : null, // link to element node
                    src: "http://",
                    title: ""
                };

            dialogReplacements = {
                legend    : "Insert Image",
                preview : "Preview",
                url     : "URL",
                title   : "Title",
                description : "Description",
                width   : "Width",
                height  : "Height",
                original : "Original W x H",
                "float"    : "Float",
                floatNone : "None",
                floatLeft : "Left",
                floatRight : "Right",
                submit  : "Insert Image",
                reset   : "Cancel",
                fileManagerIcon : "Select file from server"
            };

            formImageHtml = '<form class="wysiwyg" id="wysiwyg-addImage"><fieldset>' +
                '<div class="form-row"><span class="form-row-key">{preview}:</span><div class="form-row-value"><img src="" id="img-preview" alt="{preview}" style="margin: 2px; padding:5px; max-width: 100%; overflow:hidden; max-height: 100px; border: 1px solid rgb(192, 192, 192);"/></div></div>' +
                '<div class="form-row"><label class="label" for="name">{url}:</label><div class="form-row-value"><input class="text" type="text" name="src" value=""/>' +
                '<div class="wysiwyg-fileManager" title="{fileManagerIcon}"/>';

            if ($.wysiwyg.fileManager && $.wysiwyg.fileManager.ready) {
                // Add the File Manager icon:
                formImageHtml += '<div class="wysiwyg-fileManager"  title="{fileManagerIcon}"/>';
            }

            formImageHtml += '</div></div>' +
                '<div class="form-row"><label class="label" for="name">{title}:</label><div class="form-row-value"><input class="text"  type="text" name="imgtitle" value=""/></div></div>' +
                '<div class="form-row"><label class="label" for="name">{description}:</label><div class="form-row-value"><input class="text" type="text" name="description" value=""/></div></div>' +
                '<div class="form-row"><label class="label" for="name">{width} x {height}:</label><div class="form-row-value"><input class="text width-small" type="text" name="width" value=""/> x <input class="text width-small" type="text" name="height" value=""/></div></div>' +
                '<div class="form-row"><label class="label" for="name">{original}:</label><div class="form-row-value"><input class="text width-small" type="text" name="naturalWidth" value="" disabled="disabled"/> x ' +
                '<input class="text width-small" type="text" name="naturalHeight" value=""  disabled="disabled"/></div></div>' +
                '<div class="form-row"><label class="label" for="name">{float}:</label><div class="form-row-value"><select name="float">' +
                '<option value="">{floatNone}</option>' +
                '<option value="left">{floatLeft}</option>' +
                '<option value="right">{floatRight}</option></select></div></div>' +
                '<div class="form-row form-row-last"><label class="label" for="name"></label><div class="form-row-value"><input type="submit" value="{submit}"/> ' +
                '<input type="reset" value="{reset}"/></div></div></fieldset></form>';

            for (key in dialogReplacements) {
                if ($.wysiwyg.i18n) {
                    translation = $.wysiwyg.i18n.t(dialogReplacements[key], "dialogs.image");

                    if (translation === dialogReplacements[key]) { // if not translated search in dialogs
                        translation = $.wysiwyg.i18n.t(dialogReplacements[key], "dialogs");
                    }

                    dialogReplacements[key] = translation;
                }

                regexp = new RegExp("{" + key + "}", "g");
                formImageHtml = formImageHtml.replace(regexp, dialogReplacements[key]);
            }

            if (img.self) {
                img.src = img.self.src ? img.self.src : "";
                img.alt = img.self.alt ? img.self.alt : "";
                img.title = img.self.title ? img.self.title : "";
                img.width = img.self.width ? img.self.width : "";
                img.height = img.self.height ? img.self.height : "";
                img.styleFloat = $(img.self).css("float");
            }


            adialog = new $.wysiwyg.dialog(Wysiwyg, {
                "title"   : dialogReplacements.legend,
                "content" : formImageHtml
            });

            $(adialog).bind("afterOpen", function (e, dialog) {
//                alert ("нужно вызвать функцию openInsertImageDialog('#insertImageTreeContainer'), получить imagePath и запихать его в перменную srcs") ;
                dialog.find("form#wysiwyg-addImage").submit(function (e) {
                    e.preventDefault();
                    self.processInsert(dialog.container, Wysiwyg, img);

                    adialog.close();
                    return false;
                });

                // File Manager (select file):
//                if ($.wysiwyg.fileManager) {
                $("div.wysiwyg-fileManager").bind("click", function () {
                    openInsertImageDialog('#insertImageTreeContainer');
//                        $.wysiwyg.fileManager.init(function (selected) {
//                            dialog.find("input[name=src]").val(selected);
//                            dialog.find("input[name=src]").trigger("change");
//                        });
                });
//                }

                $("input:reset", dialog).click(function (e) {
                    adialog.close();

                    return false;
                });

                $("fieldset", dialog).click(function (e) {
                    e.stopPropagation();
                });

                self.makeForm(dialog, img);
            });


//            img.src = '';
            adialog.open();
            $(Wysiwyg.editorDoc).trigger("editorRefresh.wysiwyg");
        }
        ,

        processInsert
            :
            function (context, Wysiwyg, img) {
                var image,
                    url = $('input[name="src"]', context).val(),
                    title = $('input[name="imgtitle"]', context).val(),
                    description = $('input[name="description"]', context).val(),
                    width = $('input[name="width"]', context).val(),
                    height = $('input[name="height"]', context).val(),
                    styleFloat = $('select[name="float"]', context).val(),
                    styles = [],
                    style = "",
                    found,
                    baseUrl;

                if (Wysiwyg.options.controlImage && Wysiwyg.options.controlImage.forceRelativeUrls) {
                    baseUrl = window.location.protocol + "//" + window.location.hostname
                        + (window.location.port ? ":" + window.location.port : "");
                    if (0 === url.indexOf(baseUrl)) {
                        url = url.substr(baseUrl.length);
                    }
                }

                if (img.self) {
                    // to preserve all img attributes
                    $(img.self).attr("src", url)
                        .attr("title", title)
                        .attr("alt", description)
                        .css("float", styleFloat);

                    if (width.toString().match(/^[0-9]+(px|%)?$/)) {
                        $(img.self).css("width", width);
                    } else {
                        $(img.self).css("width", "");
                    }

                    if (height.toString().match(/^[0-9]+(px|%)?$/)) {
                        $(img.self).css("height", height);
                    } else {
                        $(img.self).css("height", "");
                    }

                    Wysiwyg.saveContent();
                } else {
                    found = width.toString().match(/^[0-9]+(px|%)?$/);
                    if (found) {
                        if (found[1]) {
                            styles.push("width: " + width + ";");
                        } else {
                            styles.push("width: " + width + "px;");
                        }
                    }

                    found = height.toString().match(/^[0-9]+(px|%)?$/);
                    if (found) {
                        if (found[1]) {
                            styles.push("height: " + height + ";");
                        } else {
                            styles.push("height: " + height + "px;");
                        }
                    }

                    if (styleFloat.length > 0) {
                        styles.push("float: " + styleFloat + ";");
                    }

                    if (styles.length > 0) {
                        style = ' style="' + styles.join(" ") + '"';
                    }

                    image = "<img src='" + url + "' title='" + title + "' alt='" + description + "'" + style + "/>";
                    Wysiwyg.insertHtml(image);
                }
            }

        ,

        makeForm: function (form, img) {
            form.find("input[name=src]").val(img.src);
            form.find("input[name=imgtitle]").val(img.title);
            form.find("input[name=description]").val(img.alt);
            form.find('input[name="width"]').val(img.width);
            form.find('input[name="height"]').val(img.height);
            form.find('select[name="float"]').val(img.styleFloat);
            form.find('img').attr("src", img.src);

            form.find('img').bind("load", function () {
                if (form.find('img').get(0).naturalWidth) {
                    form.find('input[name="naturalWidth"]').val(form.find('img').get(0).naturalWidth);
                    form.find('input[name="naturalHeight"]').val(form.find('img').get(0).naturalHeight);
                } else if (form.find('img').attr("naturalWidth")) {
                    form.find('input[name="naturalWidth"]').val(form.find('img').attr("naturalWidth"));
                    form.find('input[name="naturalHeight"]').val(form.find('img').attr("naturalHeight"));
                }
            });

            form.find("input[name=src]").bind("change", function () {
                form.find('img').attr("src", this.value);
            });

            return form;
        }
    };

    $.wysiwyg.insertImage = function (object, url, attributes) {
        return object.each(function () {
            var Wysiwyg = $(this).data("wysiwyg"),
                image,
                attribute;

            if (!Wysiwyg) {
                return this;
            }

            if (!url || url.length === 0) {
                return this;
            }

            if ($.browser.msie) {
                Wysiwyg.ui.focus();
            }

            if (attributes) {
                Wysiwyg.editorDoc.execCommand("insertImage", false, "#jwysiwyg#");
                image = Wysiwyg.getElementByAttributeValue("img", "src", "#jwysiwyg#");

                if (image) {
                    image.src = url;

                    for (attribute in attributes) {
                        if (attributes.hasOwnProperty(attribute)) {
                            image.setAttribute(attribute, attributes[attribute]);
                        }
                    }
                }
            } else {
                Wysiwyg.editorDoc.execCommand("insertImage", false, url);
            }

            Wysiwyg.saveContent();

            $(Wysiwyg.editorDoc).trigger("editorRefresh.wysiwyg");

            return this;
        });
    };
    function openInsertImageDialog() {
        $("div.wysiwyg-dialog").hide();
        $("#insertImageTree:ui-dialog").dialog("destroy");
        $("#insertImageTree").dialog({
            width: 800,
            modal: true,
            buttons: {
                "insert": function() {
                    $(this).dialog("close");

                    if (imagePath != '') {
                        $('input[name="src"]').val(imagePath);
                        $('#img-preview').val(imagePath);
                        $("div.wysiwyg-dialog").show();
                    }
                    else {
                        alert("Вы не выбрали файл или указали на папку!")
                    }


                }
            }
        });


        var divId2 = "#insertImageTree";
        var entity2 = "Folder";
        var ajaxUrl2 = "/ems/core/treeObjects?entity=" + entity2;

        document.getElementById('parentTreeRootNode').value = 0;
        document.getElementById('parentTreeRootNode').innerHTML = entity2;
        initXmlTree(divId2, ajaxUrl2);

        $("#insertImageTree").click(function (e) {
            var f = jQuery.jstree._focused();
            var id = f.data.ui.selected.attr("id");
            var name = f.data.ui.selected.attr("name");
            var contentType = f.data.ui.selected.attr("contentType");
            if (contentType != '') {
                imagePath = f.data.ui.selected.attr("path");
            } else {
                imagePath = '';
            }

        });

    }

})(jQuery);
