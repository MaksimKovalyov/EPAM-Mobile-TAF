/**
 * @fileOverview Visualizer core. Licensed under GNU GPL v3.
 * @version      1.0
 * @author       <a href="mailto:Denis_Shamgin@epam.com">Denis Shamgin</a>
 */

(function (window) {
    'use strict';
    /**
     * @namespace
     */
    var Vz = {

        /**
         * Performs system initialization.
         * @public
         */
        init: function () {
            this.Utils.stub.localStorage.call(window);
            this.Config.restore();
            //TODO: initialize modules (e.g., Config, UI, Commander, etc.)
        }
    };

    /**
     * @namespace Contains global system configuration (e.g., server URLs, remote device type, etc.).
     */
    var Config = (function () {
        /**
         * Setters container object.
         */
        var set = {
            url: {
                /**
                 * Sets the protocol (setter).
                 * @private
                 * @param   {String} value Protocol to be set.
                 * @returns {String}       A value that was set.
                 * @throws  {RangeError}   If the value is not valid.
                 */
                protocol: function (value) {
                    if (!value.length) {
                        URL.protocol = 'http'; //default protocol 
                    } else if (/http(?:s)?|ftp(?:s)?/i.test(value)) {
                        URL.protocol = value.toLowerCase();
                    } else {
                        throw new RangeError('Invalid protocol value!');
                    }
                    return URL.protocol;
                },
                /**
                 * Sets the source (setter).
                 * @private
                 * @param   {Object} value Source to be set.
                 * @returns {String}       A value that was set.
                 */
                source: function (value) {
                    URL.source = encodeURIComponent(value);
                    return URL.source;
                },
                /**
                 * Sets the port (setter).
                 * @private
                 * @param   {Number} value Port to be set.
                 * @returns {Number}       A value that was set.
                 * @throws  {RangeError}   If the value is not valid.
                 */
                port: function (value) {
                    if(/^\d+$/.test(value) || !value.length) {
                        URL.port = value;
                        return URL.port;
                    } else {
                        throw new RangeError('Invalid port value!');
                    }
                }
            },
            dateFormat: function (value) {
                var formats = UI.getTimeStampFormats();
                
                if (formats.indexOf(value) != -1) {
                    dateFormat = value;
                    return dateFormat;
                } else {
                    throw new RangeError('Invalid timestamp format!');
                }
            },
            device: {
                platform: function (value) {
                    // TODO: implement input data verification
                    devicePlatform = value;
                    return devicePlatform;
                }
            },
            log: {
                capacity: function (value) {
                    // TODO: remove extra messages from log
                    logCapacity = value;
                    return logCapacity;
                }
            },
            tree: {
                parent: function (value) {
                    tree.parent = String(value).replace(/\s/g, '_');
                    return tree.parent;
                },
                children: function (value) {
                    tree.children = String(value).replace(/\s/g, '_');
                    return tree.children;
                }
            }
        };
        
        var mapping = {
            'time_stamp': set.dateFormat,
            'tree_parent': set.tree.parent,
            'tree_children': set.tree.children,
            'url_protocol': set.url.protocol,
            'url_source': set.url.source,
            'url_port': set.url.port,
            'device_platform': set.device.platform,
            'log_capacity': set.log.capacity
        };

        var dateFormat = 'short';
        var devicePlatform = 'ios';
        var logCapacity = 3;
        var tree = {
            parent: 'class',
            children: 'subviews'
        };
        var URL = {
            protocol: 'http',
            source  : 'localhost',
            port    : '37265'
        };
    
        /** @scope Config */
        return {
            /**
             * Serializes user configuration data, applies changes and stores data in local storage.
             * @public
             * @param   {Array}   data Configuration data to be saved.
             * @returns {Boolean}      Action status (true => pass, false => fail).
             */
            save: function (data) {
                var configItem;
                var i;
                var serializedConfigData;

                for (i = 0; i < data.length; i++) {
                    configItem = data[i];
                    // TODO: serialize parsed values not the source ones (see mapping)!
                    data[i].value = mapping[configItem.name](configItem.value);
                }
                
                serializedConfigData = JSON.stringify(data);

                try {
                    localStorage.setItem('config', serializedConfigData);
                    UI.logStatus('Configuration has been successfully saved to local storage.');
                } catch (e) {
                    UI.logStatus('Could not save configuration: ' + e.message);
                    return false;
                }
                
                return true;
            },

            /**
             * Deserializes user configuration data (if any) from local storage and passes it to the 'save' method.
             * @public
             * @returns {Boolean} Action status (true => pass, false => fail).
             */
            restore: function () {
                var deserializedConfigData = JSON.parse(localStorage.getItem('config'));
                
                if (deserializedConfigData) {
                    this.save(deserializedConfigData);
                    return true;
                } else {
                    return false;
                }
            },
            /**
             * Gets the protocol (getter).
             * @public
             * @returns {String} Protocol value.
             */
            getProtocol: function () {
                return URL.protocol;
            },
            /**
             * Gets the source (getter).
             * @public
             * @returns {String} Source value.
             */
            getSource: function () {
                return URL.source;
            },

            /**
             * Gets the port (getter).
             * @public
             * @returns {String} Port value.
             */
            getPort: function () {
                return URL.port;
            },

            /**
             * Gets the URL (getter).
             * @public
             * @returns {String} Complete URL (protocol + source + port).
             */
            getURL: function () {
                return URL.protocol + "://" + URL.source + ":" + URL.port + "/";
            },
            
            /**
             * Gets timestamp date format (getter).
             * @public
             * @returns {String} Timestamp format value.
             */
            getDateFormat: function () {
                return dateFormat;
            },
            
            /**
             * Gets log capacity (getter).
             * @public
             * @returns {Number} Log capacity value.
             */
            getLogCapacity: function () {
                return logCapacity;
            },
            
            /**
             * Gets tree parent name (getter).
             * @public
             * @returns {String} Tree parent name.
             */
            getTreeParent: function () {
                return tree.parent;
            },
            
            /**
             * Gets tree children name (getter).
             * @public
             * @returns {String} Tree children name.
             */
            getTreeChildren: function () {
                return tree.children;
            }
        };
    })();
    
    /** 
     * @namespace Contains parsing methods, expands base class functionality (follows the module pattern).
     */
    var PreParser = (function () {
        var instanceCounter = [];
        
        /**
         * Checks if object is empty.
         * 
         * @private
         * @param   {Object} obj
         * @returns {Boolean}
         * 
         * @example
         * isObjectEmpty({}); // -> true
         */
        function isObjectEmpty(obj) {
            for (var p in obj) {
                if (obj.hasOwnProperty(p)) {
                    return false;
                }
            }
            return true;
        }
    
        /**
         * Creates new object transforming source object's structure to the one utilized by the jsTree plugin.
         * Handles deserialized application object model (AOM).
         * <b>Recursion utilized.</b>
         * 
         * @private
         * @param   {Object} obj Source object to be used as a basis for restructurization.
         * @returns {Object}     New structurized object instance.
         */
        function structurizeToNew(obj) {
            var i, prop, Result, arrayElement;
            var treeParent = Config.getTreeParent();
            var treeChildren = Config.getTreeChildren();

            if (!obj[treeParent] && !obj[treeChildren]) {
                return false;
            }        

            Result = new JSTreeObject();
    
            for (prop in obj) {
                if (obj.hasOwnProperty(prop)) {
                    switch (prop.toLowerCase()) {
                        //Root/parent:
                        case treeParent:
                            Result.data = obj[prop];
                            break;
                        //Children:
                        case treeChildren:
                            if (!obj[prop].length) {
                                Result.state = 'leaf';
                                delete Result.children; //redundant?
                            } else {
                                for (i = 0; i <= obj[prop].length; i++) {
                                    arrayElement = obj[prop][i];
                                    if (arrayElement instanceof Object) {
                                        arrayElement = structurizeToNew(arrayElement);
                                    }
                                    Result.children.push(arrayElement);
                                }
                            }
                            break;
                        //Attributes:
                        default:
                            Result.metadata.data.push(structurizeMetaToNew(prop, obj[prop]));
                    }
                }
            }
    
            return Result;
        }
    
        /**
         * Creates new object transforming source object's structure to the one utilized by the jsTree plugin.
         * Handles metadata provided by the application object model (AOM).
         * <b>Recursion utilized.</b>
         * 
         * @private
         * @param   {String}              property Property.
         * @param   {String|Array|Object} value    Value (either a string, an array or an object).
         * @returns {Object}
         */
        function structurizeMetaToNew(property, value) {
            var p, arrayString = "";
            var Meta = {
                data     : property,
                children : [],
                metadata : {},
                attr     : {
                    id : "meta_" + newInstance("metadata")
                }
            };
    
            if (isArray(value)) {
                Meta.data = property;
                arrayString = value.toSource ? value.toSource() : value.toSourceX();
                Meta.children.push(arrayString);
                Meta.metadata[property] = arrayString;
            } else if (value instanceof Object) {            
                for (p in value) {
                    if (value.hasOwnProperty(p)) {
                        Meta.children.push(structurizeMetaToNew(p, value[p]));
                        Meta.state = "closed";
                    }
                }
            } else {
                Meta.data = property + ": " + value;
                Meta.metadata[property] = value;
                delete Meta.children;
            }
    
            if (isObjectEmpty(Meta.metadata)) {
                delete Meta.metadata;
            }
    
            return Meta;
        }
    
        /**
         * Creates new object instance of the required by the jsTree plugin structure.
         * Assigns unique ID to each object instance as a parameter/field/member.
         * 
         * @constructor
         * @private
         * @returns {Object}     Resulting object instance.
         */
        function JSTreeObject() {
            var className = this.constructor.name;
            
            this.attr = {
                id: "data_" + newInstance(className)
            };
            this.data = "";
            this.metadata = {
                data : []
            };
            this.children = [];
            this.state = "closed";
        }
    
        /**
         * Counts the number of class instances.
         * 
         * @private
         * @param   {String} clazzName The name of the class (constructor), instances of which are to be counted.
         * @returns {Number}           Instance quantity of a particular class.
         */
        function newInstance(clazzName) {
            var qty = instanceCounter[clazzName];
            
            if (qty !== undefined) {
                instanceCounter[clazzName] = ++qty;
            } else {
                instanceCounter[clazzName] = qty = 0;
            }
    
            return qty;
        }
    
    
        /** @scope PreParser */
        return {
            /**
             * Parses JSON string, creates new structurized object, serializes new object to a JSON string.
             * @public
             * @param   {String} data Data received from server.
             * @param   {String} type Type of data received from server.
             * @returns {String}      Resulting JSON string.
             */
            JSTreeDataFilter: function (data, type) {
                var inputData = "";
                var parsedJSON = {}, JSTreeObject = {};
    
                parsedJSON = JSON.parse(data);
                JSTreeObject = this.createJSTreeObject(parsedJSON);
                inputData = JSON.stringify(JSTreeObject);
    
                return inputData;
            },
            
            /**
             * Symbols (<, >, &, ', ", /, \) are replaced with web-safe codes.
             * @public
             * @param   {String} txt Input text to be updated.
             * @returns {String}     Updated text.
             */
            replaceWebSymbols: function (txt) {
                var webRE = /[<>&'"\/\\]/g;
                return txt.replace(webRE, function (match) {
                    switch (match) {
                        case "<":
                            match = "&lt;";
                            break;
                        case ">":
                            match = "&gt;";
                            break;
                        case "&":
                            match = "&amp;";
                            break;
                        case "'":
                            match = "&#039;";
                            break;
                        case "\"":
                            match = "&quot;";
                            break;
                        case "/":
                            match = "&#47;";
                            break;
                        case "\\":
                            match = "&#92;";
                            break;
                    }
                    
                    return match;
                });
            },
            
            /**
             * Creates new JS object from source object so that it can be utilized by jsTree as input data.
             * @public
             * @param   {Object} obj
             * @returns {Object}
             */
            createJSTreeObject: function (obj) {
                //TODO: make it private?
                return structurizeToNew(obj);
            }
        };
    })();
    
    /**
     * @namespace Contains user interface interaction methods.
     */
    var UI = (function (statusField) {
        var messageCount = 0;
        var messageLimit = 3;
        var timestampFormat = ['none', 'short', 'medium', 'long'];

        var limitStatusLines = true;
        var statusLineLength = 200;
        var logStatusType = ['info', 'warn', 'error'];       

        /**
         * Message count setter.
         * @private
         * @param {Number} n
         */
        function udpateMessageCount(n) {
            if (n !== undefined && typeof n == "number") {
                if (n < 0) {
                    messageCount += parseInt(n, 10);
                } else {
                    messageCount = parseInt(n, 10);
                }
            } else {
                messageCount++;
            }
        }

        /** @scope UI */
        return {
            /**
             * Sets log message limit (setter). Performs user input parsing.
             * @public
             * @param   {Number} n    An integer representing the log message limit.
             * @returns {Number}      Parsed user input.
             * @throws  {RangeError}  If entered limit is less than 0.
             */
            setLogMessageLimit: function (n) {
                if (n !== undefined && !isNaN(Number(n))) {
                    if (n < 0) {
                        throw new RangeError('Message limit cannot be less than 0!');
                    } else {
                        messageLimit = parseInt(n, 10);
                        return messageLimit;
                    }
                } else {
                    throw new TypeError('Entered value is not (or cannot be parsed as) a number!');
                }
            },
            
            /**
             * Gets log message limit (getter).
             * @public
             * @returns {Number}
             */
            getLogMessageLimit: function () {
                return messageLimit;
            },
            
            /**
             * Gets available timestamp formats (getter).
             * @public
             * @returns {Array}
             */
            getTimeStampFormats: function () {
                return timestampFormat;
            },
            
            CustomContextMenu: {
                /**
                 * Creates custom data context menu.
                 * Contains option(s): 'Flash Element'.
                 * @param   {Object} node
                 * @returns {Object}
                 */
                Data: function (node) {
                    var items = {
                        getValue: {
                            label: "Get Value",
                            action: function () {}
                        },
                        setValue: {
                            label: "Set Value",
                            action: function () {}
                        },
                        doAction: {
                            label: "Actions",
                            separator_before: true,
                            separator_after: false,
                            submenu: {
                                flash : {
                                    label  : "Flash Element",
                                    action : function (node) {}
                                },
                                touch: {
                                    label: "Touch Element",
                                    action: function () {}
                                }
                            }
                        }
                    };
                    
                    return items;
                },
                
                /**
                 * Creates custom metadata context menu.
                 * Contains option(s): 'Display Value'.
                 * @param   {Object} node
                 * @returns {Object}
                 */
                Meta: function (node) {
                    var items = {
                        displayValue : {
                            label  : "Display Value",
                            action : function (node) {
                                var nodeID = node.attr("id");
                                var nodeValue = $("#metadata").jstree(nodeID).get_text();
        
                                UI.logStatus(nodeValue);
                            }
                        }
                    };
                    
                    return items;
                }
            },
            
            /**
             * Generates timestamp string. Configurable.
             * Adds line terminator at the end of the string if 2nd argument is set.
             * @public
             * @param   {String} format         Represents timestamp format: short, medium or long.
             * @param   {String} lineTerminator Input values: "CR", "LF", "CR+LF" or 'undefined' (if skipped).
             * @returns {String}                Formatted timestamp string.
             * 
             * @example
             * UI.generateTimestamp("short", "CR+LF"); //-> "[18:30:45.333]\r\n"
             */
            generateTimestamp: function (format, lineTerminator) {
                var timestamp = new Date();
                var year, month, day, dateTime, time, milliseconds;
                var result = "";
                
                if (!format) {
                    format = "none";
                } else if (timestampFormat.indexOf(format) == -1) {
                    throw new ReferenceError("Unknown format type!");
                }
                
                switch (lineTerminator) {
                    case "CR":
                        lineTerminator = "\r";
                        break;
                    case "LF":
                        lineTerminator = "\n";
                        break;
                    case "CR+LF":
                        lineTerminator = "\r\n";
                        break;
                    default:
                        lineTerminator = "";
                }
                
                switch (format) {
                    case "none":
                        result = "";
                        lineTerminator = "";
                        break;
                    case "short":
                        time = timestamp.toLocaleTimeString();
                        milliseconds = parseInt(timestamp.getMilliseconds() / 100, 10);
                        result = "[" + time + "." + milliseconds + "]";
                        break;
                    case "medium":
                        year = timestamp.getFullYear();
                        month = timestamp.getMonth() + 1;
                        day = timestamp.getDate();
                        time = timestamp.toLocaleTimeString();
                        result = "[" + day + "/" + month + "/" + year + " " + time + "]";
                        break;
                    case "long":
                        dateTime = timestamp.toLocaleString();
                        result = "[" + dateTime + "]";
                        break;
                }
    
                return result + lineTerminator;
            },
    
            /**
             * Note: utilizes jQuery functionality.
             * @public
             * @param {Object} input
             * @param {Object} view
             * @param {String} data  Optional.
             */
            toggleConsoleViews: function (input, view, data) {
                if (data === undefined) {
                    data = input.val();
                }
                view.html(data);
                input.toggle();
                view.toggle();
            },
    
            /**
             * Logs system messages.
             * Note: utilizes jQuery functionality.
             * @public
             * @param {String} data
             * @param {String} type
             */
            logStatus: function (data, type) {
                var closeButton, 
                    messageTxt, 
                    msgContainer;
                var dateFormat = Config.getDateFormat();
                var timestamp = UI.generateTimestamp(dateFormat);

                if (!data.length) {
                    return;
                } else if (!type || logStatusType.indexOf(type) == -1) {
                    type = 'info';
                }

                type = '<span class="log_{type}">[{type}]</span>'.supplant({ type : type });
                data = timestamp + type + data;

                //Multiplication Sign: &times; &#215; &#xD7;
                closeButton = $('<a href="javascript:void(0);">close</a>');
                messageTxt = $(data.wrapInTag('p'));
                msgContainer = $('<div class="status"></div>');

                if (limitStatusLines && messageTxt.text().length > statusLineLength) {
//                    debugger;
                    data = data.substring(0, statusLineLength) + "...";
                }                

                msgContainer.append(closeButton);
                msgContainer.append(messageTxt);
                
                if (messageCount === 0) {
                    statusField.html('');
                }

                udpateMessageCount();
                if (messageCount > messageLimit) {
                    statusField.children('.status:last').remove();
                    udpateMessageCount(-1);
                }
    
                statusField.prepend(msgContainer);
                closeButton.click(function () {
                    msgContainer.fadeOut('normal', function () {
                        udpateMessageCount(-1);
                        if (messageCount === 0) {
                            statusField.html(UI.placeholder.status);
                        } else {
                            msgContainer.remove();
                        }
                    });
                });
                msgContainer.fadeIn('slow');                
            },

            /**
             * Forms up a string with error information and passes it to the 'logStatus' method.
             * @public
             * @param {Object} jqXHR
             * @param {String} textStatus
             * @param {String} errorThrown
             */
            logAjaxError: function (jqXHR, textStatus, errorThrown) {
                var details = [];
                var placeHolder = "N/A";
                var message = "Server request not completed!";
                
                if (jqXHR.responseText) {
                    message = jqXHR.responseText;
                }
                
                if (!textStatus) {
                    textStatus = placeHolder;
                }
                
                if (!errorThrown) {
                    errorThrown = placeHolder;
                }
    
                details.push("Message: ".wrapInTag("b") + message);
                details.push("Text status: ".wrapInTag("b") + textStatus);
                details.push("Error thrown: ".wrapInTag("b") + errorThrown);
    
                UI.logStatus(details.join("<br/>"), "error");
            },
            
            //placeholder text for empty UI fields:
            placeholder: {
                AOM         : "[ application object model ]",
                description : "[ node description ]",
                metadata    : "[ metadata ]",
                status      : "[ status ]",
                screenshots : "[ screenshots ]",
                empty       : "[ empty ]"
            }
        };
    })($('[name="status"]'));
    
    /**
     * @namespace Contains commands validation and request formation modules (to be sent to back-end).
     */
    var Commander = (function () {    
        /**
         * @constructor
         * @private
         * @param   {Array}  line
         * @returns {Object}
         */
        function Command(line) {
            var cmd = line[0];
            var args = line.slice(1);

            this.isValid = true;
            this.cmd = this.parseCommand(cmd);
            this.args = this.parseArguments(args);
        }

        Command.prototype = {
            /**
             * braces in rules:
             * ----------------
             * square - reference to an array with predefined values.
             * curly  - reference to an object with regular expressions.
             * round  - optional argument.
             * none   - word (exact match).
             */
            syntax: {
                predefinedItems: {
                    elements: [
                        'button', 
                        'textfield', 
                        'image', 
                        'label', 
                        'slider', 
                        'switch'
                    ],
                    attributes: [
                        'title', 
                        'value', 
                        'width', 
                        'height', 
                        'x-pos', 
                        'y-pos'
                    ]
                },
                
                regExpLib: {
                    elementIndex    : /^#([1-9][0-9]*)$/,
                    quotedArgument  : /^(['"])(.*?)\1$/,
                    positiveInteger : /^\d+$/,
                    equalOrNotEqual : /^(?:=|!=)$/,
                    refToArray      : /^\[(\w+)\]$/,
                    refToRegExp     : /^\{(\w+)\}$/,
                    optionalArgument: /\((\S+)\)/
                },

                map: {
                    'list'           : 'predefinedItems',
                    'regexp'         : 'regExpLib',
                    'element'        : 'elements',
                    'attribute'      : 'attributes',
                    'quotedArgument' : 'quotedArgument',
                    'elementIndex'   : 'elementIndex',
                    'equalOrNotEqual': 'equalOrNotEqual'
                },
                
                rules: {
                    'touch' : [
                        ['[element]', '{elementIndex}'], 
                        ['[element]', 'with', '[attribute]', '{quotedArgument}', '({elementIndex})']
                    ],
                    'assert': [
                        ['[element]', '[attribute]', '{equalOrNotEqual}', '{quotedArgument}']
                    ],
                    'flash' : [],
                    'wait'  : [],
                    'set'   : [],
                    
                    /**
                     * Detects applicable rules.
                     * @param   {String} cmd
                     * @param   {Number} enteredQty
                     * @returns {Array}             An array with references to applicable rules.
                     */
                    detectApplicableRules: function (cmd, enteredQty) {
                        var parent = Command.prototype.syntax;
                        var optionalArgument = parent.regExpLib.optionalArgument;
                        var i, 
                            rule, 
                            ruleArgsQty, 
                            optArgs, 
                            optArgsQty, 
                            minArgsQty, 
                            range, 
                            applicableRules = [];

                        for (i = 0; i < this[cmd].length; i++) {
                            range = this[cmd][i]['range'];
                            if (!range) {
                                rule = this[cmd][i].join(' ');
                                ruleArgsQty = this[cmd][i].length;
                                optArgs = rule.match(optionalArgument);
                                optArgsQty = optArgs ? optArgs.slice(1).length : 0;
                                minArgsQty = ruleArgsQty - optArgsQty;
                                range = (optArgsQty === 0) ? [ruleArgsQty] : [minArgsQty, ruleArgsQty];
                                this[cmd][i]['range'] = range;
                            }
                            if (enteredQty >= range[0]) {
                                applicableRules.push(this[cmd][i]);
                            }
                        }
                        
                        return applicableRules;
                    },
                    
                    /**
                     * Identifies an abstract command argument.
                     * Includes extracted argument value (e.g.: '([abc])' -> 'abc'). 
                     * Also specifies if an argument is optional.
                     * @param   {String} data
                     * @returns {Object}
                     */
                    identifyArgument: function (data) {
                        var parent = Command.prototype.syntax;
                        var refToArray = parent.regExpLib.refToArray;
                        var refToRegExp = parent.regExpLib.refToRegExp;
                        var optionalArgument = parent.regExpLib.optionalArgument;
                        var result = { isOptional: false };
                        
                        if (optionalArgument.test(data)) {
                            result = this.identifyArgument(data.match(optionalArgument)[1]);
                            result.isOptional = true;
                        } else if (refToArray.test(data)) {
                            result.type = 'list';
                            result.value = data.match(refToArray)[1];
                        } else if (refToRegExp.test(data)) {
                            result.type = 'regexp';
                            result.value = data.match(refToRegExp)[1];
                        } else {
                            result.type = 'word';
                            result.value = data;
                        }

                        return result;
                    }
                }
            },
            
            /**
             * Marks object as invalid, specifying error message and value.
             * @param {String}        errorMessage
             * @param {String|Number} errorValue
             */
            markAsInvalid: function (errorMessage, errorValue) {
                this.isValid = false;
                this.error = {
                    message : errorMessage,
                    value   : errorValue
                };
            },
            
            /**
             * Checks if the argument corresponds to the according rule or value.
             * @param   {Object}  ruleArgument
             * @param   {String}  argument
             * @returns {Boolean}
             */
            checkArgument: function (ruleArgument, argument) {
                var type = ruleArgument.type;
                var value = ruleArgument.value;
                var mappedType = this.syntax.map[type];
                var mappedValue = this.syntax.map[value];

                switch (type) {
                    //check presence in the predefined list:
                    case 'list':
                        return this.syntax[mappedType][mappedValue].indexOf(argument) === -1 ? false : true;
                        //break;
                    //test using regular expression:
                    case 'regexp':
                        return this.syntax[mappedType][mappedValue].test(argument);
                        //break;
                    //check for exact match:
                    case 'word':
                        return (value === argument);
                        //break;
                }
            },
            
            /**
             * Checks if the first word in line is a valid command.
             * @param   {String} cmd  The first word in line of user input.
             * @returns {String|null} If command is found its name is returned, otherwise 'null' value is returned.
             */
            parseCommand: function (cmd) {
                if (cmd in this.syntax.rules) {
                    return cmd;
                } else {
                    this.markAsInvalid('unknown command', cmd);
                    return null;
                }
            },
            
            /**
             * Checks if the rest of the line corresponds to the rule.
             * @param   {Array} args  An array representing the arguments of a command.
             * @returns {Object|null} If arguments correspond to the rule an object is returned, otherwise 'null' value is returned.
             */
            parseArguments: function (args) {
                var applicableRules  = this.syntax.rules.detectApplicableRules(this.cmd, args.length);
                var identifyArgument = this.syntax.rules.identifyArgument;
                var i, 
                    j, 
                    applicableRule, 
                    ruleArgument, 
                    ruleArgumentType, 
                    ruleArgumentValue, 
                    argumentIsValid, 
                    ruleIsLast;

                if (!this.isValid) {
                    return null; 
                }

                if (!applicableRules.length) {
                    this.markAsInvalid('missing arguments', 'command [' + this.cmd + ']');
                    return null;
                }

                for (i = 0; i < applicableRules.length; i++) {
                    applicableRule = applicableRules[i];
                    for (j = 0; j < applicableRule.length; j++) {
                        ruleArgument = identifyArgument(applicableRule[j]);
                        ruleIsLast = (applicableRules[applicableRules.length - 1] === applicableRule);
                        argumentIsValid = this.checkArgument(ruleArgument, args[j]);
                        
                        if (!argumentIsValid && ruleIsLast) {
                            this.markAsInvalid('invalid argument', args[j]);
                            return null;
                        } else {
                            continue;
                        }
                    }
                }
            }
        };
    
        /**
         * Performs user data input validation.
         * @private
         * @param   {Array} data
         * @returns {Object}
         */
        function validateUserInput(data) {
            var i, line, lineCheckResult;
            var validationResult = {
                isValid: true,
                lines : []
            };

            //cycle through lines:
            for (i = 0; i < data.length; i++) {
                line = data[i];
                if (line.isComment) {
                    validationResult.lines[i] = line.toString();
                    continue;
                }
                lineCheckResult = new Command(line);
                if (lineCheckResult.isValid === true) {
                    validationResult.lines[i] = lineCheckResult;
                    //TODO: clean up the 'lineCheckResult' object, create new object (without inherited properties)
                } else {
                    validationResult.isValid = false; //setting the global validity flag
                    validationResult.lines[i] = lineCheckResult.error;
                    validationResult.lines[i].line = i + 1;
                    //TODO: add char index?
                    console.dir(validationResult);
                    return validationResult;
                }
            }

            console.dir(validationResult);
            return validationResult;
        }
    
        /**
         * Prepares user input data for validation: removes leading/trailing spaces, skips commented lines.
         * @private
         * @param   {String} data
         * @returns {Array}
         */
        function prepareForValidation(data) {
            //split text by line terminator(s):
            var lines = data.split(/\n+/).cleanUp('');
            var doubleForwardSlash = /^\/\//;
            var quotedArgument = /(['"])(.*?)\1/;
            var line, i, j;

            for (i = 0; i < lines.length; i++) {
                line = lines[i];
                line.isComment = false;
                //remove leading/trailing spaces:
                line = line.replace(/^\s+/, '');
                line = line.replace(/\s+$/, '');
                //skip commented lines:
                if (doubleForwardSlash.test(line)) {
                    line = new String(line);
                    line.isComment = true;
                    lines[i] = line;
                    continue;
                }
                //temporary replace spaces within quoted arguments with a set of predefined symbols: 
                if (quotedArgument.test(line)) {
                    line = line.replace(quotedArgument, function (match, p1, p2, offset, str) {
                        if (/\s+/.test(p2)) {
                            match = match.replace(/\s/g, '%%%');
                        }
                        return match;
                    });
                }
                line = line.split(' ').cleanUp('');
                //replace sets of predefined symbols back to spaces:
                for (j = 0; j < line.length; j++) {
                    if (/%%%/.test(line[j])) {
                        line[j] = line[j].replace(/%%%/g, ' ');
                    }
                }
                
                lines[i] = line;
            }

            //backup cleaned up version of the user input as a string:
            //lines.src = lines.join('\n');

            return lines;
        }
    
        /** @scope Commander */
        return {
            /**
             * Sends a command execution request after having the message validity check performed.
             * @public
             * @param {String} data Unprocessed user input.
             */
            sendCommand: function (data) {    
                var inputText,
                    validatedData;

                if (!data) {
                    UI.logStatus("Please enter the command to be sent to the server.");
                    return;
                }
    
                //TODO: remove leading/trailing spaces and replace input text with cleaned up version
                inputText = prepareForValidation(data);
                validatedData = validateUserInput(inputText);
    
                if (!validatedData.isValid) {
                    //TODO: report errors to console
                    return;
                }
                //TODO: serialize command to JSON and send
            }
        };
    })();

    Vz.Commander = Commander;
    Vz.Config = Config;
    Vz.PreParser = PreParser;
    Vz.UI = UI;
    Vz.Utils = {
        /**
         * Imports specified script by dynamically adding it to the <body> as a new <script> tag.
         * @public
         * @param {Object} script
         */
        importScript: function (script) {
            var scriptElement;
            
            if (!script.path) {
                return;
            }
            
            scriptElement = document.createElement('script');
            scriptElement.setAttribute('type', 'text/javascript');
            scriptElement.setAttribute('src', script.path);
            
            if (script.callback !== undefined && typeof script.callback == 'function') {
                scriptElement.onload = script.callback;
            }
            
            document.body.appendChild(scriptElement);
        },

        /**
         * Dynamically creates an HTML form with passed parameters and submits it in background.
         * Performs DOM clean up after form submission.
         *  
         * @public
         * @param {String} path
         * @param {Object} params
         * @param {String} method
         */
        submitFormInBackground: function (path, params, method) {
            var form, key, hiddenField;
            
            if (!path) {
                return;
            }
        
            method = method || 'post';
            form = document.createElement('form');
            form.setAttribute('method', method);
            form.setAttribute('action', path);
            form.style.display = 'none';
        
            for (key in params) {
                hiddenField = document.createElement('input');
                hiddenField.setAttribute('type', 'hidden');
                if (params.hasOwnProperty(key)) {
                    hiddenField.setAttribute('name', key);
                    hiddenField.setAttribute('value', params[key]);
                }
                form.appendChild(hiddenField);
            }
        
            document.body.appendChild(form);
            form.submit();
            document.body.removeChild(form);
        },
        
        /**
         * Feature stubbing object.
         */
        stub: {
            localStorage: function () {
                var noop = function () {};
                if (!('localStorage' in this) || this['localStorage'] === null) {
                    this['localStorage'] = {
                        length: 0,
                        getItem: noop,
                        setItem: noop,
                        removeItem: noop,
                        clear: noop
                    };
                }
            }
        }
    };

    window.Vz = Vz;
})(window);