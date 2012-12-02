/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 21.04.11
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
function trim(str) {
    str = str.replace(/^\s+/, '');
    for (var i = str.length - 1; i >= 0; i--) {
        if (/\S/.test(str.charAt(i))) {
            str = str.substring(0, i + 1);
            break;
        }
    }
    return str;
}


/*
 function togglePagesButton(button) {
 if (document.getElementById('treeContainer').style.display == 'none') {
 document.getElementById('treeContainer').style.display = 'block';
 //button.value = "<i18n:text catalogue="core" key="SystemNode.hide"/>";
 button.value = "Спрятать";
 }
 else {
 document.getElementById('treeContainer').style.display = 'none';
 //        button.value = "<i18n:text catalogue="core" key="SystemNode.choose"/>";
 button.value = "Выбрать";
 }
 }
 */

var selectedClassName = 'checked';

function togglePagesSpanClass(span, on) {
    var clName = "";
    if (span.className.indexOf(selectedClassName) > -1) {
        clName = selectedClassName;
    }
    if (on) {
        clName += ' mouseover';
        if (clName.charAt(0) == ' ') {
            clName = clName.substring(1);
        }
    }
    span.className = clName;
}


function togglePagesSpanState(span) {
    var idPrefix = 'page';
    var clName = "";
//    var selectedClassNameIndex = span.className.indexOf(selectedClassName);
    var selectedClassNameIndex = span.className.indexOf(selectedClassName);
    var spanClassName = span.className;
    if (selectedClassNameIndex == -1) {
        cleanSpanStates(document.getElementById('treex'));
        clName = span.className + ' ' + selectedClassName;
        clName = trim(clName);
        var id = parseInt(link.id.substring(idPrefix.length));
        if (id > 0) {
            var systemNodeNameElement = document.getElementById('systemNodeName');
            systemNodeNameElement.innerHTML = span.innerHTML;
            systemNodeNameElement.href = 'editSystemNode?id=' + id;
            document.getElementById('systemNodeId').value = id;
        }
    }
    span.className = clName;
}

function cleanSpanStates(parentElement) {
    if (parentElement.tagName) {
        if (parentElement.tagName.toLowerCase() == 'span') {
            parentElement.className = '';
        }
        for (var i = 0; i < parentElement.childNodes.length; i++) {
//        for (var i = 0; i &lt;  parentElement.childNodes.length; i++) {
            cleanSpanStates(parentElement.childNodes[i]);
        }
    }
}

function isNodeList(o) {
// return o &amp;&amp; typeof o.length == 'number' &amp;&amp; typeof o.item == 'function';
    return o && typeof o.length == 'number' && typeof o.item == 'function';
}

function TreeAction(id, act, path) {
    var elt = document.getElementById(id);
    if (!elt) {
        alert("Error: cannot find element with id '" + id + "'");
        return;
    }
// alert("elt:" + elt + "-id:" + id);

    var form = cocoon.forms.getForm(elt);
    var actInput = id + ":action";
    var pathInput = id + ":path";
    //alert(actInput);
    var elem = form[actInput];
    if (isNodeList(elem)) {
// for (var i = 0; i &lt; elem.length; i++) {
        for (var i = 0; i < elem.length; i++) {
            var el = elem.item(i);
            el.value = act;
        }
    }
    else {
        elem.value = act;
    }
    elem = form[pathInput];
    if (isNodeList(elem)) {
// for (var i = 0; i &lt; elem.length; i++) {
        for (var i = 0; i < elem.length; i++) {
            var el = elem.item(i);
            el.value = path;
        }
    }
    else {
        elem.value = path;
    }
    cocoon.forms.submitForm(elt, id);

    // Reset fields (this form may be reposted later when in Ajax mode)
    var elem = form[actInput];
    if (isNodeList(elem)) {
// for (var i = 0; i &lt; elem.length; i++) {
        for (var i = 0; i < elem.length; i++) {
            var el = elem.item(i);
            el.value = "";
        }
    }
    else {
        elem.value = "";
    }

    elem = form[pathInput];
    if (isNodeList(elem)) {
// for (var i = 0; i &lt; elem.length; i++) {
        for (var i = 0; i < elem.length; i++) {
            var el = elem.item(i);
            el.value = "";
        }
    }
    else {
        elem.value = "";
    }

    return false;
}
function TreeToggleCollapse(id, path) {
    //alert ("id-"+id+"-path-"+path);
    return TreeAction(id, "toggle-collapse", path);
}
function TreeToggleSelect(id, path) {
    return TreeAction(id, "toggle-select", path);
}


//


var noParentLabel = ' !<i18n:text key="EmsObject.noParentElement"/>';

function trim(str) {
    str = str.replace(/^\s+/, '');
    for (var i = str.length - 1; i >= 0; i--) {
        if (/\S/.test(str.charAt(i))) {
            str = str.substring(0, i + 1);
            break;
        }
    }
    return str;
}



var selectedClassName = 'checked';

function toggleParentsSpanClass(span, on) {
    var clName = "";
    if (span.className.indexOf(selectedClassName) > -1) {
        clName = selectedClassName;
    }
    if (on) {
        clName += ' mouseover';
        if (clName.charAt(0) == ' ') {
            clName = clName.substring(1);
        }
    }
    span.className = clName;
}


function toggleParentsSpanState(span) {

    var idPrefix = 'parent';
    var clName = "";
    var selectedClassNameIndex = span.className.indexOf(selectedClassName);
    var spanClassName = span.className;
    if (selectedClassNameIndex == -1) {
        cleanSpanStates(document.getElementById('parentsTree'));
        clName = span.className + ' ' + selectedClassName;
        clName = trim(clName);
        var idStr = span.id.substring(idPrefix.length);
        var id = idStr.length > 0 ? parseInt(span.id.substring(idPrefix.length)) : 0;
        if (id > 0) {
            document.getElementById('parentId').value = id;
            document.getElementById('parentName').innerHTML = '&lt;a href="edit${entity}?id=' + id + '"&gt;'
                    + span.innerHTML + '&lt;/a&gt;';
        }
        else {
            document.getElementById('parentId').value = id;
            document.getElementById('parentName').innerHTML = noParentLabel;
        }
    }
    span.className = clName;
}

function cleanSpanStates(parentElement) {
    if (parentElement.tagName) {
        if (parentElement.tagName.toLowerCase() == 'span') {
            parentElement.className = '';
        }
        for (var i = 0; i &lt; parentElement.childNodes.length; i++) {
            cleanSpanStates(parentElement.childNodes[i]);
        }
    }
}