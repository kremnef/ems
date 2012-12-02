function flexiGridFilter(url) {
    /*
     var param = [{
     name: 'page',
     value: '1'
     }, {
     name: 'rp',
     value: '25'
     }, {
     name: 'sortname',
     value: 'publishDateTime'
     }, {
     name: 'sortorder',
     value: 'desc'
     }, {
     name: 'query',
     value: ''
     }, {
     name: 'qtype',
     value: 'publishDateTime'
     }];
     */

    $.ajax({
        type: 'post',
        url: url,
        data: param,
        dataType: 'xml',
        success: function (data) {
            $('#flex1').flexAddData(data);
//                                                        $("#flex1").flexReload();

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            try {
                if (p.onError) p.onError(XMLHttpRequest, textStatus, errorThrown);
            } catch (e) {
            }
        }
    });

}


function openXMLTree(entity, filter, checkbox, divTree, entityId, rowId) {

    globalRowId = rowId;
    globalEntityId = entityId;
    globalDivTree = divTree;
    globalDivTreeContainer = divTree + "Container";
    globalEntity = entity;
    globalFilter = filter;
    globalCheckbox = checkbox;
    globalButtonSave = globalButtonSave;


    document.getElementById('treeRootNode').value = globalEntityId;
    document.getElementById('treeRootNode').innerHTML = globalEntity;


    var ajaxUrl = "/ems/core/treeObjects?entity=" + globalEntity;

    if (globalFilter != 'null') {
        ajaxUrl = ajaxUrl + "&amp;filter=" + globalFilter;

    }
    if (globalCheckbox == 'true') {
        checkXmlTree(globalDivTree, ajaxUrl);
    }
    else {
        initXmlTree(globalDivTree, ajaxUrl);

    }
    openModalDialog(globalDivTreeContainer, globalButtonSave);

    /*jQuery("#treeRootNode").click(function () {
     updateRowData(globalEntity, globalEntityId, true);
     });*/


    /*jQuery(globalDivTree).bind("loaded.jstree", function (e, data) {
     jQuery(globalDivTree + ' a').click(function() {
     var li = this.parentNode;
     var id = li.id;
     var name = li.getAttribute('name');
     //            updateRowData(name, id, false);
     });
     });
     jQuery(globalDivTree).bind("open_node.jstree", function (e, data) {
     jQuery(globalDivTree + ' a').unbind('click').click(function() {
     var li = this.parentNode;
     var id = li.id;
     var name = li.getAttribute('name');
     });
     });*/


}
function openModalDialog(globalDivTreeContainer) {
    jQuery(globalDivTreeContainer + ":ui-dialog").dialog("destroy");
    jQuery(globalDivTreeContainer).dialog({
        width: 800,
        height:500,
        modal: true,
        buttons: {
            сбросить: function() {
                reset_tree(globalDivTree);
                jQuery(':input', globalDivTree)
                    .not(':button, :submit, :reset, :hidden')
                    .val('')
                    .removeAttr('checked')
                    .removeAttr('selected');
            },
            выбрать: function() {
                if (globalCheckbox == 'true') {
                    checkValues(globalDivTree);
                    jQuery(this).dialog("close");
//                    updateAttachedFilesTable();
                }
                else {
                    jQuery(this).dialog("close");
                }
            }
        }
    });
}

function updateAttachedFilesTable() {
    var time = new Date().getTime();
    var url = "Content-list";
    //jQuery('#attachedFiles').load(url+'&time=' + time + '&parentId=${cocoon.request.getParameter("parentId")} #listTable');
    jQuery('#fileSystemObjects').load(url + '&time=' + time + '&id=${cocoon.request.getParameter("id")} #listTable');
}


// Reset form
function reset_tree(divTree) {
//    divTree = document.getElementById(folderTree);
    jQuery(divTree).jstree("uncheck_all");
    jQuery(divTree).jstree("close_all");
}
function resetAddFileInfo() {
    jQuery("#addFile").val('');
    jQuery("#addFileName").val('');
    jQuery("#addFileSystemName").val('');
    jQuery("#addFilePath").val('');
    jQuery("#addFileType").val('');

}
function checkValues(globalDivTree) {
    jQuery(globalDivTree).jstree("get_checked").each(function() {
//    jQuery(globalDivTree).jstree().get_checked ( context, get_all ).each(function() {
//        if(this.data.ui) { this.data.ui.selected = this.get_checked(); }
//        var addFile = null;
        resetAddFileInfo();
//        var t = jQuery.jstree._focused();
//        var th_id = t.data.ui.selected.attr("id");
//        alert (this.id);

        var newId = this.id;
        var newName = document.getElementById(newId).getAttribute("name");
        var newSystemName = document.getElementById(newId).getAttribute("systemName");
        var newPath = document.getElementById(newId).getAttribute("path");
        var newContentType = document.getElementById(newId).getAttribute("contentType");
        if (newContentType == '') {
            newContentType = 'folder';
        }

//        alert (newName +" - "+ newSystemName +" - "+ newPath +" - "+  newContentType)
        jQuery("#addFileName").val(newName);
        jQuery("#addFileSystemName").val(newSystemName);
        jQuery("#addFilePath").val(newPath);
        jQuery("#addFileType").val(newContentType);
        jQuery("#addFile").val(newId);

//        addFile = document.getElementById('addFile');
//                addFile.disabled = false;
//                addFile.value = newId;
        cocoon.forms.submitForm(addFile);
    });

}
;

jQuery(document).ready(function() {

    // if no POST data, reset tree (to prevent restore cache)
    if (jQuery('#reset_form').val() == 'yes') {
        setTimeout("reset_tree(globalDivTree)", 100);
    }
    /* // reset form by reset button
     jQuery('#reset').click(function(event) {

     event.preventDefault();
     alert(1);
     reset_tree(globalDivTree);
     // jQuery('#advanced-search_form')[0].reset(); // reset to defaults (not cleaning)
     jQuery(':input', globalDivTree)
     .not(':button, :submit, :reset, :hidden')
     .val('')
     .removeAttr('checked')
     .removeAttr('selected');
     });
     */
    jQuery('#thumbnailTree').click(function (e) {
        var t = jQuery.jstree._focused();


//        var selected_node_id = jQuery('#thumbnailTree').jstree('get_selected').data('id');
        var selected_node_id =t.data.ui.selected.attr("id");
        if (selected_node_id > 0) {
            var selected_node_name = document.getElementById(selected_node_id).getAttribute("name");
            var selected_node_path = document.getElementById(selected_node_id).getAttribute("path");
            var prepath = selected_node_path.substring(0, selected_node_path.lastIndexOf('/'));
            $("#thumbnailId").val(selected_node_id);
            $("#chooseThumbnail").attr('src', '/ems/' + prepath + '/thumbnails/' + selected_node_id + "-" + selected_node_name + "-small.jpg");
            $("#chooseThumbnail").attr('alt', selected_node_name);
        } else {
            alert("ERORR!!! selected_node_id " + selected_node_id+ t.data.ui.selected.attr("id"));
        }


    });

});


//jQuery(function () {
var initXmlTree = function (divId, ajaxurl) {


    // Settings up the tree - using jQuery(selector).jstree(options);
    // All those configuration options are documented in the _docs folder
//    jQuery("#xmlMenuTree")
    jQuery("#treeSearchBtn").click(function () {
        var str = document.getElementById("treeSearch").value;
        jQuery(divId).jstree("search", str);
    });
    jQuery(divId)
        .jstree({
            // the list of plugins to include
//                        "plugins" : [ "themes", "xml_data", "ui", "crrm", "cookies", "dnd", "search", "types", "hotkeys", "contextmenu" ],

            "plugins" : ["themes", "xml_data", "ui", "cookies", "search", "types", "hotkeys"],
            // Plugin configuration

            // I usually configure the plugin that handles the data first - in this case JSON as it is most common
            "xml_data" : {
                xsl : "flat", clean_node : false,
                // I chose an ajax enabled tree - again - as this is most common, and maybe a bit more complex
                // All the options are the same as jQuery's except for `data` which CAN (not should) be a function
                "ajax" : {
                    // the URL to fetch the data
//                                "url" : "/ems/core/treeNavigation",
                    "url" : ajaxurl,
                    // this function is executed in the instance's scope (this refers to the tree instance)
                    // the parameter is the node being loaded (may be -1, 0, or undefined when loading the root nodes)
                    /*"data" : function (n) {
                     // the result is fed to the AJAX request `data` option
                     return {
                     "operation" : "get_children",
                     "id" : n.attr ? n.attr("id").replace("node_","") : 1
                     };
                     }*/
                    "data" : function (n) {
                        return {
                            parentId : n.attr ? n.attr("id") : 0,
                            rand : new Date().getTime()
                        };
                    }
                }
            },
            // Configuring the search plugin
            "search" : {
                // As this has been a common question - async search
                // Same as above - the `ajax` config option is actually jQuery's object (only `data` can be a function)
                "ajax" : {
                    "url" :ajaxurl,
                    // You get the search string as a parameter
                    "data" : function (str) {
                        return {
                            "operation" : "search",
                            "search_str" : str
                        };
                    }
                }
            },
            // Using types - most of the time this is an overkill
            // Still meny people use them - here is how
            "types" : {
                // I set both options to -2, as I do not need depth and children count checking
                // Those two checks may slow jstree a lot, so use only when needed
                "max_depth" : -2,
                "max_children" : -2,
                // I want only `drive` nodes to be root nodes
                // This will prevent moving or creating any other type as a root node
                "valid_children" : [ "drive", "leaf" ],
                "types" : {
                    // The default type
                    "default" : {
                        // I want this type to have no children (so only leaf nodes)
                        // In my case - those are files
                        "valid_children" : "none"
                        // If we specify an icon for the default type it WILL OVERRIDE the theme icons
                        /*"icon" : {
                         "image" : "/ems/resource/js/jquery-plugin/xmltree/images/node.png"
                         }*/
                    },
                    "leaf" : {
                        "max_children" : 0,
                        // "valid_children" : "all",
                        "icon" : {
                            "image" : "/ems/resource/js/jquery-plugin/xmltree/images/page_white_cup.png"
                        }
                    },
                    // The `folder` type
                    "folder" : {
                        // can have files and other folders inside of it, but NOT `drive` nodes
                        "valid_children" : [ "default", "folder" ],
                        "icon" : {
                            "image" : "/ems/resource/js/jquery-plugin/xmltree/images/node.png"
                        }
                    },
                    // The `drive` nodes
                    "drive" : {
                        // can have files and folders inside, but NOT other `drive` nodes
                        "valid_children" : [ "default", "folder" ],
                        /*"icon" : {
                         "image" : "/ems/resource/js/jquery-plugin/xmltree/images/house.png"
                         },*/
                        // those options prevent the functions with the same name to be used on the `drive` type nodes
                        // internally the `before` event is used
                        "start_drag" : false,
                        "move_node" : false,
                        "delete_node" : false,
                        "remove" : false
                    }
                }
            },
            // For UI & core - the nodes to initially select and open will be overwritten by the cookie plugin

            // the UI plugin - it handles selecting/deselecting/hovering nodes
            "ui" : {
                // this makes the node with ID node_4 selected onload
                // "initially_select" : [ "52" ]

            },
            // the core plugin - not many options here
            "core" : {
                // just open those two nodes up
                // as this is an AJAX enabled tree, both will be downloaded from the server
                //"initially_open" : [ "node_2" , "node_3" ]
            },
            "themes" : {
                "theme" : "apple",
                "dots" : true,
                "icons" : true
            }


        })

        .bind("search.jstree", function (e, data) {
            alert("Found " + data.rslt.nodes.length + " nodes matching '" + data.rslt.str + "'.");
        });

//           .bind("_get_node.jstree", function (e, data) {
//           .click( function (e) {
//                      var f = jQuery.jstree._focused();
//    jQuery("#test1").html(f.data.ui.selected.attr("id"));
//    jQuery("#test1").html(data.inst._get_node().attr("id"));
//})


//});
};
var checkXmlTree = function (divId, ajaxurl) {
    // Settings up the tree - using jQuery(selector).jstree(options);
    // All those configuration options are documented in the _docs folder
//    jQuery("#xmlMenuTree")
    jQuery("#treeSearchBtn").click(function () {
        var str = document.getElementById("treeSearch").value;
        jQuery(divId).jstree("search", str);
    });
    jQuery(divId)
        .jstree({
            // the list of plugins to include
//                        "plugins" : [ "themes", "xml_data", "ui", "crrm", "cookies", "dnd", "search", "types", "hotkeys", "contextmenu" ],

            "plugins" : ["themes", "checkbox", "xml_data", "ui", "cookies", "search", "types", "hotkeys"],
            // Plugin configuration
            "checkbox" : {
                two_state : true
            },

            // I usually configure the plugin that handles the data first - in this case JSON as it is most common
            "xml_data" : {
                xsl : "flat", clean_node : false,
                // I chose an ajax enabled tree - again - as this is most common, and maybe a bit more complex
                // All the options are the same as jQuery's except for `data` which CAN (not should) be a function
                "ajax" : {
                    // the URL to fetch the data
//                                "url" : "/ems/core/treeNavigation",
                    "url" : ajaxurl,
                    // this function is executed in the instance's scope (this refers to the tree instance)
                    // the parameter is the node being loaded (may be -1, 0, or undefined when loading the root nodes)
                    /*"data" : function (n) {
                     // the result is fed to the AJAX request `data` option
                     return {
                     "operation" : "get_children",
                     "id" : n.attr ? n.attr("id").replace("node_","") : 1
                     };
                     }*/
                    "data" : function (n) {
                        return {
                            parentId : n.attr ? n.attr("id") : 0,
                            rand : new Date().getTime()
                        };
                    }
                }
            },
            // Configuring the search plugin
            "search" : {
                // As this has been a common question - async search
                // Same as above - the `ajax` config option is actually jQuery's object (only `data` can be a function)
                "ajax" : {
                    "url" :ajaxurl,
                    // You get the search string as a parameter
                    "data" : function (str) {
                        return {
                            "operation" : "search",
                            "search_str" : str
                        };
                    }
                }
            },
            // Using types - most of the time this is an overkill
            // Still meny people use them - here is how
            "types" : {
                // I set both options to -2, as I do not need depth and children count checking
                // Those two checks may slow jstree a lot, so use only when needed
                "max_depth" : -2,
                "max_children" : -2,
                // I want only `drive` nodes to be root nodes
                // This will prevent moving or creating any other type as a root node
                "valid_children" : [ "drive", "leaf" ],
                "types" : {
                    // The default type
                    "default" : {
                        // I want this type to have no children (so only leaf nodes)
                        // In my case - those are files
                        "valid_children" : "none"
                        // If we specify an icon for the default type it WILL OVERRIDE the theme icons
                        /*"icon" : {
                         "image" : "/ems/resource/js/jquery-plugin/xmltree/images/node.png"
                         }*/
                    },
                    "leaf" : {
                        "max_children" : 0,
                        // "valid_children" : "all",
                        "icon" : {
                            "image" : "/ems/resource/js/jquery-plugin/xmltree/images/page_white_cup.png"
                        }
                    },
                    // The `folder` type
                    "folder" : {
                        // can have files and other folders inside of it, but NOT `drive` nodes
                        "valid_children" : [ "default", "folder" ],
                        "icon" : {
                            "image" : "/ems/resource/js/jquery-plugin/xmltree/images/node.png"
                        }
                    },
                    // The `drive` nodes
                    "drive" : {
                        // can have files and folders inside, but NOT other `drive` nodes
                        "valid_children" : [ "default", "folder" ],
                        /*"icon" : {
                         "image" : "/ems/resource/js/jquery-plugin/xmltree/images/house.png"
                         },*/
                        // those options prevent the functions with the same name to be used on the `drive` type nodes
                        // internally the `before` event is used
                        "start_drag" : false,
                        "move_node" : false,
                        "delete_node" : false,
                        "remove" : false
                    }
                }
            },
            // For UI & core - the nodes to initially select and open will be overwritten by the cookie plugin

            // the UI plugin - it handles selecting/deselecting/hovering nodes
            "ui" : {
                // this makes the node with ID node_4 selected onload
                // "initially_select" : [ "52" ]

            },
            // the core plugin - not many options here
            "core" : {
                // just open those two nodes up
                // as this is an AJAX enabled tree, both will be downloaded from the server
                //"initially_open" : [ "node_2" , "node_3" ]
            },
            "themes" : {
                "theme" : "apple",
                "dots" : true,
                "icons" : true
            },
            "rules":{
                "multiple" : false
            }


        })

        .bind("search.jstree", function (e, data) {
            alert("Found " + data.rslt.nodes.length + " nodes matching '" + data.rslt.str + "'.");
        });


};

