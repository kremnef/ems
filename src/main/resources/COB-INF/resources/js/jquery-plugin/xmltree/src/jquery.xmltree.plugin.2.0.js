/*
 * jXMLTree jQuery Plugin
 * Examples and documentation at: http://roy-jin.appspot.com/jsp/jqueryXmlMenuTreeDemo.jsp
 * Copyright (c) 2010 Roy Jin
 * Version: 2.0.0 (01-10-2010)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * Requires: jQuery v1.4.2 or later
 */
(function($){  
	$.fn.xmltree = function(options) {

		var defaults = {  
			xmlUrl: '',
			treePanelId: 'xmlJSTree',
			loadingText: 'Loading...',
			loadingError: 'Error: Loading Error.',
			initialExpanded: true,
			storeState: false,
			toggleSpeed: 400
		};
		
		var options = $.extend(defaults, options);
		
		var CONSTANT_PREFIX = "xmltree_";
		var toggle_hide_status = "HIDE";
		var toggle_show_status = "SHOW";
		
		var container = $(this);
		var treePanel = $('<div class="xmlJSTree"></div>');
		var parents = new Array();
		var XML_TREE_KEYWORDS = {
				menuTree : 'menuTree',
				link : 'link', 
				type : 'type',				
				parentId : 'parentId', 
				linkName : 'linkName', 
				linkTarget : 'linkTarget',				
				linkOnClick : 'linkOnClick', 
				linkTargetStatus : 'linkTargetStatus',
				parent : 'parent',
				child : 'child',
				parentIcon : 'parentIcon',
				childIcon : 'childIcon'
		};
		
		if(options.xmlUrl == ''){
			alert(options.loadingError);
			return;
		}
		
		//check browser support cookies?
		if(options.storeState){
			setCookie("testCookie", "testCookie");
			if(getCookie("testCookie") == ""){
				//browser doesn't support cooikes
				options.storeState = false;
			}else{
				del_cookie("testCookie");
			}
		}
		
		$.ajax({
			type: "POST",
            //data: "cocoon-ajax=true",
            data: "ajax=true",
			url: options.xmlUrl,
			dataType: ($.browser.msie) ? "text" : "xml",
			success: function(data) {wrapTree(data);},
			error:function(){alert(options.loadingError);}
		});
		
		function wrapTree(data){
			//Fix the IE local access xml issue
			//Only Google Chrome cannot access local xml
			//FF, Safari, Opera works fine
			var xml;
			if (typeof data == "string") {
				xml = new ActiveXObject("Microsoft.XMLDOM");
				xml.async = false;
				xml.loadXML(data);
			} else {
		       xml = data;
			}

			container.html(options.loadingText);
			traverseTree($(xml).find(XML_TREE_KEYWORDS.menuTree));
			container.html('');
			treePanel.appendTo(container);
			
			$('.toggleIcon').each(function(){
				var isInit = true;
				if(options.storeState){
					var parentId = $(this).attr('id');
					if(isCookieExist(parentId)){
						var status = getCookie(parentId);
						// check cookie status to restore the state when page refresh
						if(status == toggle_hide_status){
							hideChildren(this);
						}else{
							showChildren(this);
						}
						isInit = false;
					}
				}
				if(isInit){
					// expand/collapse based on initialExpanded
					initToggle(this);
				}
			});				

			$('.toggleIcon').bind('click', 
				function(){
					var parentId = $(this).attr('id');
					var status = "";
					if(isCookieExist(parentId)){
						del_cookie(parentId);
					}
					if($(this).hasClass('toggleIconDown')){
						status = toggle_hide_status;
						$(this).addClass('toggleIconUp').removeClass('toggleIconDown');
						$(this).parent('ul').children().not('div').not('span').slideUp(options.toggleSpeed);
					}else{
						status = toggle_show_status;
						$(this).addClass('toggleIconDown').removeClass('toggleIconUp');
						$(this).parent('ul').children().not('div').not('span').slideDown(options.toggleSpeed);
					}
					//if storeState is true, set show/hide status into the cookie
					if(options.storeState){
						setCookie(parentId, status);
					}
				}
			);
			
			$('.parentToggle').click(function(){
				var closeToggle = $(this).parent('span').siblings('.toggleIcon');
				closeToggle.trigger('click');
			});
		}
		
		function traverseTree(node){
			node.children().each(function(){
				if($(this).attr(XML_TREE_KEYWORDS.type) == XML_TREE_KEYWORDS.parent){
					buildParentBlock(this);
					traverseTree($(this));
				}
				if($(this).attr(XML_TREE_KEYWORDS.type) == XML_TREE_KEYWORDS.child){
					buildChildrenBlock(this);
					return;
				}
				return;
			});
		}
		
		function buildParentBlock(node){
			var treeData = getXmlTreeData(node);
			var parentId = $(node).attr(XML_TREE_KEYWORDS.parentId);
			
			var parentBlockUl = $('<ul></ul>');
			var parentBlockToggleIcon = $("<div class='toggleIcon' id='" + CONSTANT_PREFIX + parentId + "'></div>");
			var parentBlockParentIcon = $("<div class='parentIcon'></div>");
			if(treeData[XML_TREE_KEYWORDS.parentIcon] != ''){
				parentBlockParentIcon.addClass(treeData[XML_TREE_KEYWORDS.parentIcon]);
			}
			var parentBlockSpan = $("<span></span>");
			var parentBlockLink = $("<a>" + treeData[XML_TREE_KEYWORDS.linkName] + "</a>");
			if(treeData[XML_TREE_KEYWORDS.linkTarget] != ''){
				parentBlockLink.attr('href', treeData[XML_TREE_KEYWORDS.linkTarget]);
			}
			if(treeData[XML_TREE_KEYWORDS.linkTargetStatus] != ''){
				parentBlockLink.attr('target', treeData[XML_TREE_KEYWORDS.linkTargetStatus]);
			}
			if(treeData[XML_TREE_KEYWORDS.linkOnClick] != ''){
				//Fix for IE. Fire the click event other than simply pass the string
				var new_click = new Function(treeData[XML_TREE_KEYWORDS.linkOnClick]);
				parentBlockLink.click(new_click);
			}
			if(treeData[XML_TREE_KEYWORDS.linkTarget] == '' && treeData[XML_TREE_KEYWORDS.linkOnClick] == ''){
				parentBlockLink.attr('class', 'parentToggle');
			}
			
			//Append Parent DOM
			parentBlockUl.append(parentBlockToggleIcon);
			parentBlockUl.append(parentBlockParentIcon);
			parentBlockSpan.append(parentBlockLink);
			var parentBlockBuilder = parentBlockUl.append(parentBlockSpan);
			//Store to parent array
			parents[parentId] = parentBlockBuilder;
			
			if($(node).parent().attr(XML_TREE_KEYWORDS.type) == XML_TREE_KEYWORDS.parent){
				parentBlockBuilder.appendTo(parents[$(node).parent().attr(XML_TREE_KEYWORDS.parentId)]);
			}else{
				parentBlockBuilder.appendTo(treePanel);
			}
		}
		
		function buildChildrenBlock(node){
			var treeData = getXmlTreeData(node);
			
			var childBlockLi = $("<li></li>");
			if($(node).next().size() == 0){
				childBlockLi.addClass('last');
			}
			var childBlockChildIcon = $("<div class='childIcon'></div>");
			if(treeData[XML_TREE_KEYWORDS.childIcon] != ''){
				childBlockChildIcon.addClass(treeData[XML_TREE_KEYWORDS.childIcon]);
			}
			var childBlockSpan = $("<span></span>");
			var childBlockLink = $("<a>" + treeData[XML_TREE_KEYWORDS.linkName] + "</a>");
			if(treeData[XML_TREE_KEYWORDS.linkTarget] != ''){
				childBlockLink.attr('href', treeData[XML_TREE_KEYWORDS.linkTarget]);
			}
			if(treeData[XML_TREE_KEYWORDS.linkTargetStatus] != ''){
				childBlockLink.attr('target', treeData[XML_TREE_KEYWORDS.linkTargetStatus]);
			}
			if(treeData[XML_TREE_KEYWORDS.linkOnClick] != ''){
				//Fix for IE. Fire the click event other than simply pass the string
				var new_click = new Function(treeData[XML_TREE_KEYWORDS.linkOnClick]);
				childBlockLink.click(new_click);
			}
			
			//Append Children DOM
			childBlockLi.append(childBlockChildIcon);
			childBlockSpan.append(childBlockLink);
			var childBlockBuilder = childBlockLi.append(childBlockSpan);
			
			$(childBlockBuilder).appendTo(parents[$(node).parent().attr(XML_TREE_KEYWORDS.parentId)]);
		}
		
		function hideChildren(toggle){
			$(toggle).addClass('toggleIconUp').removeClass('toggleIconDown');
			$(toggle).parent('ul').children().not('div').not('span').hide();
		}
		
		function showChildren(toggle){
			$(toggle).addClass('toggleIconDown').removeClass('toggleIconUp');
		}
		
		function initToggle(toggle){
			if(options.initialExpanded){
				showChildren(toggle);
			}else{
				hideChildren(toggle);
			}
		}
		
		function getXmlTreeData(node){
			var treeData = new Array();
			treeData[XML_TREE_KEYWORDS.linkName] = $(node).attr(XML_TREE_KEYWORDS.linkName);
			treeData[XML_TREE_KEYWORDS.linkTarget] = typeof $(node).attr(XML_TREE_KEYWORDS.linkTarget) != 'undefined' ? $(node).attr(XML_TREE_KEYWORDS.linkTarget) : '';
			treeData[XML_TREE_KEYWORDS.linkTargetStatus] = typeof $(node).attr(XML_TREE_KEYWORDS.linkTargetStatus) != 'undefined' ? $(node).attr(XML_TREE_KEYWORDS.linkTargetStatus) : '';
			treeData[XML_TREE_KEYWORDS.linkOnClick] = typeof $(node).attr(XML_TREE_KEYWORDS.linkOnClick) != 'undefined' ? $(node).attr(XML_TREE_KEYWORDS.linkOnClick) : '';
			treeData[XML_TREE_KEYWORDS.parentIcon] = typeof $(node).attr(XML_TREE_KEYWORDS.parentIcon) != 'undefined' ? $(node).attr(XML_TREE_KEYWORDS.parentIcon) : '';
			treeData[XML_TREE_KEYWORDS.childIcon] = typeof $(node).attr(XML_TREE_KEYWORDS.childIcon) != 'undefined' ? $(node).attr(XML_TREE_KEYWORDS.childIcon) : '';
			return treeData;
		}
		
		function setCookie(c_name, value, expiredays){
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + expiredays);
			document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toUTCString());
		}
		
		function getCookie(c_name){
			if(document.cookie.length>0){
				c_start = document.cookie.indexOf(c_name + "=");
				if(c_start!=-1){
					c_start = c_start + c_name.length+1;
					c_end=document.cookie.indexOf(";",c_start);
					if(c_end==-1)
						c_end=document.cookie.length;
					return unescape(document.cookie.substring(c_start, c_end));
				}
			}
			return "";
		}
		
		function isCookieExist(c_name){
			var c_value = getCookie(c_name);
			if(c_value == ""){
				return false;
			}
			return true;
		}
		
		function del_cookie(c_name) {
			document.cookie = c_name + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
		} 
	}
})(jQuery);