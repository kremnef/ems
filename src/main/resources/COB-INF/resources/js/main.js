/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
function toggle(id) {
    var element = document.getElementById(id);
    with (element.style) {
        if (display == "none") {
            display = ""
        } else {
            display = "none"
        }
    }
    var text = document.getElementById(id + "-switch").firstChild;
    if (text.nodeValue == "[show]") {
        text.nodeValue = "[hide]";
    } else {
        text.nodeValue = "[show]";
    }
}


$("#addedObjects.${repeater.getRow(repeaterLoop.index).getChild('objectPosition').value}.tags").dropdownchecklist({
                                                onItemClick: function(checkbox, selector) {
                                                    var thisIndex = checkbox.attr("id").split('-')[2].replace('i', '');
                                                    selector.options[thisIndex].selected = checkbox.attr("checked");

                                                    var values = "";
                                                    var newText = 'Checkbox ID = ' + checkbox.attr('id') + '';
                                                    for (i = 0; i &lt; selector.options.length; i++) {
                                                        newText += 'Option i = ' + i + ' || value = ' + selector.options[i].value + ' || checked = ' + selector.options[i].selected + '';
                                                        if (selector.options[i].selected &amp;&amp; (selector.options[i].value != "")) {
                                                            if (values != "")
                                                                values += ";";
                                                            values += selector.options[i].value;
                                                        }
                                                    }
                                                    newText += 'Selector Value = ' + values;
                                                    $returnTags.html(newText);
                                                }
                                            });

