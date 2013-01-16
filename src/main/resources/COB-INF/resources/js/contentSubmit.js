dojo.provide("cocoon.forms.AjaxForm");
dojo.require("cocoon.forms.SimpleForm");
dojo.require("cocoon.ajax.BUHandler");

dojo.widget.defineWidget(

    "cocoon.forms.AjaxForm",
    cocoon.forms.SimpleForm,
    {

        // widget properties
        widgetType: "AjaxForm",

        /**
         * Submit the form via Ajax.
         * Choose the right transport depending on the widgets in the form and the browser's capabilities.
         *
         * @param name the name of the widget that triggered the submit (if any)
         * @param params an object containing additional parameters to be added to the form data (optional)
         */
        submit: function(name, params, afterSubmitFunction) {
            var form = this.domNode;
            /* the form node */
            var mimetype = "text/xml";
            /* the default mime-type */
            if (!params) params = {};
            /* create if not passed */

            // TODO: should CForm's onSubmit handlers be called for Ajax events ?
            //if (cocoon.forms.callOnSubmitHandlers(form)) == false) return; /* call CForm's onSubmit handlers */

            // Provide feedback that something is happening.
            document.body.style.cursor = "wait";

            // The "ajax-action" attribute specifies an alternate submit location used in Ajax mode.
            // This allows to use Ajax in the portal where forms are normally posted to the portal URL.
            var uri = form.getAttribute("ajax-action");
            if (!uri) uri = form.action;
            if (uri == "") uri = document.location;

            params["forms_submit_id"] = name;
            /* name of the button doing the submit */
            params["cocoon-ajax"] = true;
            /* tell Cocoon we want AJAX-style browser updates */
            if (dojo.io.formHasFile(form)) {                        /* check for file-upload fields */
                dojo.require("dojo.io.IframeIO");
                /* using IframeIO as we have file-upload fields */
                mimetype = "text/html";
                /* a different mime-type is required for IframeIO */
            }

            dojo.io.bind({
                url: uri,
                handle: dojo.lang.hitch(this, function(type, data) {
                    this._handleBrowserUpdate(this, name, type, data, afterSubmitFunction)
                }),
                method: "post",
                mimetype: mimetype,                                 /* the mimetype of the response */
                content: params,                                    /* add extra params to the form */
                formNode: form,                                     /* the form */
                sendTransport: true                                 /* tell cocoon what transport we are using */
            });
            // Toggle the click target off, so it does not get resubmitted if another submit is fired before this has finished
            // NB. This must be done after the form is assembled by dojo, or certain onChange handlers may fail
            // Avoid the use of widget.lastClickTarget as it may already be out of date
            if (form[name]) form[name].disabled = true;
        },

        /**
         * Handle the server's BrowserUpdate response.
         * Update the part of the form referenced by ids in the reponse.
         */
        _handleBrowserUpdate: function(widget, name, type, data, afterSubmitFunction) {
            // Restore normal cursor
            document.body.style.cursor = "auto";
            // Attempt to re-enable the click target
            if (this.domNode[name]) this.domNode[name].disabled = false;
            // get a BrowsewrUpdateHandler which will replace the updated parts of the form
            var updater = new cocoon.ajax.BUHandler();
            if (type == "load") {
                if (!data) {
                    cocoon.ajax.BUHandler.handleError("No xml answer", data);
                    return;
                }
                // add the continue handler for CForms
                updater.handlers['continue'] = function() {
                    widget._continue(afterSubmitFunction);
                }
                // Handle browser update directives
                updater.processResponse(data);
            } else if (type == "error") {
                updater.handleError("Request failed", data);
            } else {
                dojo.debug("WARNING: dojo.io.bind returned an unhandled state : " + type);
            }
        },

        /**
         * Handle the server continue message.
         * The server is signalling in a BrowserUpdate response that the CForm is finished.
         * Return an acknowledgement to the continuation so cocoon.sendForm may complete.
         */
        _continue: function(afterSubmitFunction) {

            var form = this.domNode;
            var content = {'cocoon-ajax-continue' : true};
            var contParam = 'cocoon-ajax-continue=true';
            if (form.elements["continuation-id"]) {
                content['continuation-id'] = form.elements["continuation-id"].value;
                contParam += "&continuation-id=" + form.elements["continuation-id"].value;
            }
            var xhrArgs = {
                url: form.action,
                load: function(data, ioargs) {
                    alert(ioargs.xhr.status);
                }
            }
//                   alert(dojo.xhrPost);
//                    dojo.xhrPost({
//                        url: form.action,
//                        content: content
//                        load : function(data, ioargs) {
//                            alert(data);
//                        }
//                    });
//                    var deferred = dojo.xhrPost(xhrArgs);
//                    $('.button-primary').ajaxComplete(function(event, XMLHttpRequest, ajaxOptions){
//                        alert(event);
//                        alert(XMLHttpRequest.status);
//                        alert(ajaxOptions);
//                    });
            //jQuery.ajaxSetup({complete: onRequestCompleted});
//                    jQuery.ajax({
//                        type: 'POST',
//                        url: form.action,
//                        data: contParam,
//                        success: function(data, textStatus) {
//                            if (data.redirect) {
//                                alert(data.redirect);
//                                // data.redirect contains the string URL to redirect to
//                                window.location.href = data.redirect;
//                            }
//                            else {
//                                if (afterSubmitFunction) {
//                                    afterSubmitFunction();
//                                }
//                            }
//                        }
//                        complete : function(xmlHttpRequest, textStatus) {
//                            alert(textStatus);
//                            if (textStatus == 'success') {
//                                if (afterSubmitFunction) {
//                                    afterSubmitFunction();
//                                }
//                            }
//                        }
//                    });
//                    function onRequestCompleted(xhr,textStatus) {
//                        alert(xhr.status);
//                       if (xhr.status == 302) {
//                          location.href = xhr.getResponseHeader("Location");
//                       }
//                    }
            jQuery.post(form.action, contParam,
                function(data, textStatus, xmlHttpRequest) {
                    if (afterSubmitFunction) {
                        afterSubmitFunction();
                    }
//                    if (data != null) {
//                        alert(data);
                    /*if(data.getElementsByTagName("url")!= null){
                        var url = data.getElementsByTagName("url")[0].childNodes[0].nodeValue;

                        window.location.href = url;
                        }
                        //document.body.innerHTML = data;*/
//                    }
                });
//                    if (form.method.toLowerCase() == "post") {
//                        // Create a fake form and post it
//                        var div = document.createElement("div");
//                        var content = "<form action='" + form.action + "' method='POST'>" +
//                                            "<input type='hidden' name='cocoon-ajax-continue' value='true'/>";
//                        if (form.elements["continuation-id"]) {
//                            content += "<input type='hidden' name='continuation-id' value='" +
//                                    form.elements["continuation-id"].value + "'/>";
//                        }
//                        content += "</form>";
//                        div.innerHTML = content;
//                        document.body.appendChild(div);
//                        div.firstChild.submit();
//                    } else {
//                       // Redirect to the form's action URL
//                        var contParam = '?cocoon-ajax-continue=true';
//                        if (form.elements["continuation-id"]) {
//                            contParam += "&continuation-id=" + form.elements["continuation-id"].value;
//                        }
//                        window.location.href = form.action + contParam;
//                    }
        }
    });


function submitForm(form, name, params, afterSubmitFunction) {
//    var dojoId = form.getAttribute("dojoWidgetId");
    var dojoId = form.getAttribute("id");
    if (dojoId) {
        // Delegate to the SimpleForm or AjaxForm widget
        dojo.widget.byId(dojoId).submit(name, params, afterSubmitFunction);
    }
}

   function checkDocumentTypeId(){
                var dt = document.getElementById("documentTypeId");
                if(dt.value == 0){
                dt.value = 1;
                };
            };
function submitAll() {
//    jQuery("#dynamicForm").attr("ajax", false);
    checkDocumentTypeId();
    var submitDocumentFormAndDocumentTypeForm = function() {
        submitForm(document.getElementById('documentForm'), 'saveDocument', {}, function() {
            var dynamicForm = document.getElementById('dynamicForm');
            if (dynamicForm) {

                submitForm(dynamicForm, 'saveDocumentType');
            }
        });
    };
    var submitDocumentForm = function() {
        submitForm(document.getElementById('documentForm'), 'saveDocument', {}, function() {});
    };

//    submitForm(document.getElementById('documentForm'), 'saveDocument', {}, function() {});

    if (document.getElementById('documentForm')){
        submitForm(document.getElementById('contentForm'), 'saveContent', {}, submitDocumentFormAndDocumentTypeForm);
    }else{
        submitForm(document.getElementById('contentForm'), 'saveContent', {}, submitDocumentForm);
    }


}

