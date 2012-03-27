$(function () {
    /*
     * --------------
     * Initialization
     * --------------
     */
    var Commander = Vz.Commander;
    var Config = Vz.Config;
    var PreParser = Vz.PreParser;
    var UI = Vz.UI;
    var Utils = Vz.Utils;

    var iFace = $('[name="interface"]');
    var treeField = $("#treeContainer");
    var metaField = $("#metadata");
    var descrField = $("#description");
    var executeButton = $("#execute").button({
        icons: {
            primary: 'ui-icon-circle-triangle-e'
        }
    });
    var commandLine = $('#command_line');
    var commandView = $('#command_view');
    var status = $('[name="status"]');
    var configButton = $('#configButton');
    var showDeviceViewButton = $('#show_device_view');
    var timeStampExample = $('#timeStampExample');
    var whitespaceSwitch = $('#whitespaceSwitch');
    
    var device = new DeviceView('#device_view');
    
    var ajaxRequests = {
        getScreenshot: {
            error: function (jqXHR, textStatus, errorThrown) {
                device.drawDevice();
                UI.logAjaxError(jqXHR, textStatus, errorThrown);
            },
            success: function (data) {
                device.drawDevice(data.result.device);
                device.drawScreenshot(data.result.image);
            }
        }
    };

    var configMenu = $('#configMenu').dialog({
        title: 'Global configuration',
        autoOpen: false,
        closeOnEscape: false,
        modal: true,
        resizable: false,
        draggable: false,
        width: '35em',
        height: 'auto',
        show: 'blind',
        hide: 'blind',
        buttons: {
            'Save': function () {
                Config.save(configMenu.serializeArray());
                $(this).dialog('close');
                configButton.toggle('slow');
            },
            'Cancel': function () {
                $(this).dialog('close');
                configButton.toggle('slow');
            },
            'Erase': function () {
                if (confirm('Erase configuration from local storage?')) {
                    window.localStorage.removeItem('config');
                    $(this).dialog('close');
                    configButton.toggle('slow');
                }
            }
        }
    });

    commandLine.line = {
        quantity: 1,
        lineHeight: parseFloat(commandLine.css('line-height')),
        updateHeight: function () {
            var lineBreaks = commandLine.val().match(/\n/gm);
            var lineQty = (lineBreaks === null ? [] : lineBreaks).length + 1;
            var totalHeight;

            if (this.quantity === lineQty) {
                return;
            } else {
                this.quantity = lineQty;
            }
 
            totalHeight = this.lineHeight * this.quantity;
            commandLine.height(totalHeight);
            commandView.height(totalHeight);
        }
    };

    Vz.init();

    /*
     * -------------
     * Configuration
     * -------------
     */
    $.ajaxSetup({
        timeout    : 30000,
        type       : "GET",
        dataType   : "json",
        crossDomain: "true",
        error      : UI.logAjaxError
    });

    $('#highlight_toggle')
    // .button()
    .click(function () {
        var state = $(this).prop('checked');
        device.highlightElements(state);
    });

    $('#device_view_toolbar > button:contains("Refresh")')
    .button({
        icons: {
            primary: 'ui-icon-refresh'
        }
    })
    .click(function () {
        var request = ajaxRequests.getScreenshot;

        request.url = Config.getURL() + 'take_screenshot';
        $.ajax(ajaxRequests.getScreenshot);
    });

    $('#device_view_toolbar > button:contains("Rotate Left")')
    .button({
        icons: {
            primary: 'ui-icon-arrow-1-w'
        }
    });

    $('#device_view_toolbar > button:contains("Rotate Right")')
    .button({
        icons: {
            secondary: 'ui-icon-arrow-1-e'
        }
    });

    $('#device_view_toolbar > button:contains("Save Image")')
    .button({
        icons: {
            primary: 'ui-icon-disk'
        }
    })
    .click(function () {
        device.saveImage();
    });

    $('#image_zoom_slider')
    .slider({
        animate: true,
        min: 10,
        max: 100,
        step: 1,
        value: 100,
        slide: function (event, ui) {
            device.setZoom(ui.value);
        }
    })
    .css('margin', '1em 0');
    
    $('#accordion').accordion({
        collapsible: true,
        active: false,
        autoHeight: false
    });

    //prevent anchors from scrolling to the '#' when clicked:
    $('a.jButton').attr('href', 'javascript: void(0);');

    /*
     * ---------------
     * Events handling
     * ---------------
     */

    window.onerror = function (msg, url, line) {
        UI.logStatus('Message: ' + msg + '<br/>URL: ' + url + '<br/>Line: ' + line, 'error');
        return true;
    };


    $("#get_AOM")
    .button({
        icons: {
            primary: 'ui-icon-document'
        }
    })
    .click(function () {
        if ($.jstree._reference(treeField)) {
            treeField.jstree('destroy');
            treeField.html(UI.placeholder.AOM);
        }
        
        if ($.jstree._reference(metaField)) {
            metaField.jstree('destroy');
            metaField.html(UI.placeholder.metadata);
            descrField.html(UI.placeholder.description);
        }

        treeField.bind("loaded.jstree", function () {
            UI.logStatus("Application tree structure loaded.");
        })
        .bind("hover_node.jstree", function (event, data) {
            // TODO: use treeField.bind()/.unbind() ?
            var elementData = $.data(data.rslt.obj[0], "jstree").data;
            var frame = {
                size: { x: 0, y: 0 },
                coord: { x: 0, y: 0 }
            };
            var parsedFrameData = [];
            var frameData;
            var p;

            for (p in elementData) {
                if (elementData.hasOwnProperty(p)) {
                    if (elementData[p]['data'] == 'accessibilityFrame') {
                        frameData = elementData[p]['children'][0]['metadata']['NSRect'];
                    }
                }
            }

            frameData.split(/\D+/g).forEach(function (value, index, array) {
                if (value) {
                    parsedFrameData.push(value);
                }
            });

            frame.coord.x = parseInt(parsedFrameData[0], 10);
            frame.coord.y = parseInt(parsedFrameData[1], 10);
            frame.size.x  = parseInt(parsedFrameData[2], 10);
            frame.size.y  = parseInt(parsedFrameData[3], 10);

            device.displayHighlightFrame(frame);
        })
        .bind("select_node.jstree", function (event, data) {
            var i, j;
            var levelOne, levelTwo;
            var baseClass = "none";
            var meta = data.rslt.obj.data("jstree");
            var identifier = data.rslt.obj.attr("id");
            var objClass = treeField.jstree(identifier).get_text();
    
            //get baseClass of an element:
            for (i = 0; i < meta.data.length; i++) {
                levelOne = meta.data[i];
                if (levelOne.children) {
                    for (j = 0; j < levelOne.children.length; j++) {
                        levelTwo = meta.data[i].children[j];
                        if (levelTwo.metadata && levelTwo.metadata.baseClass) {
                            baseClass = levelTwo.metadata.baseClass;
                        }
                    }
                }
            }
    
            //form up general description of an element:
            descrField
                .html("")
                .append("<p>" + "Class".wrapInTag("b") + " = " + objClass.wrapInTag("i") + "</p>")
                .append("<p>" + "BaseClass".wrapInTag("b") + " = " + baseClass.wrapInTag("i") + "</p>");
    
            if (meta) {
                metaField.jstree({
                    "core": {
                        "animation": 100
                    },
                    "contextmenu" : {
                        "select_node" : true,
                        "items"       : UI.CustomContextMenu.Meta
                    },
                    "ui": {
                        "select_limit": 1
                    },
                    "json_data"         : meta,
                    "progressive_render": true,
                    "progressive_unload": true,
                    "plugins"           : ["themes", "json_data", "ui", "sort", "contextmenu"]
                });
            } else {
                metaField.html(UI.placeholder.empty);
            }
        });
        
        treeField.jstree({
            "core": {
                "animation": 200
            },
            "themes": {
                "dots" : true,
                "icons": true,
                "theme": "apple"
            },
            "ui": {
                "select_limit": 1
            },
            "contextmenu": {
                "select_node": true,
                "items"      : UI.CustomContextMenu.Data
            },
            "json_data": {
                "ajax": {
                    "url"       : Config.getURL() + "dump",
                    "dataType"  : "json",
                    "dataFilter": function (data, type) {
                        return PreParser.JSTreeDataFilter(data, type);
                    }
                }
            },
            "progressive_render": true,
            "progressive_unload": true,
            "plugins"           : ["themes", "ui", "contextmenu", "json_data"/*, "sort" */]
        });
    });
    

    $(document).bind('keydown', 'Ctrl+return', function () {
        executeButton.click();
    });

    commandLine.bind('keydown', 'Ctrl+return', function () {
        executeButton.click();
    });

    commandLine.bind('keyup', 'return', function () {
        //TODO: refactor this to fire on 'keydown' event correctly
        commandLine.line.updateHeight();
    });

    commandLine.bind('keyup', 'backspace', function () {
        commandLine.line.updateHeight();
    });

    commandLine.bind('keyup', 'del', function () {
        commandLine.line.updateHeight();
    });

    executeButton.click(function () {
        Commander.sendCommand(commandLine.val());
    });
    
    commandView.click(function () {
        whitespaceSwitch.click();
    });

    $('input.switchButton').click(function () {
        var button = $(this);
        var state = button.val();

        if (state == 'off') {
            button.attr('value', 'on');
        } else {
            button.attr('value', 'off');
        }
        button
            .toggleClass('on')
            .toggleClass('off');
    });

    whitespaceSwitch.click(function () {
        var inputData = commandLine.val();
        var outputData = PreParser.replaceWebSymbols(inputData);

        if($(this).val() == 'on') {
            outputData = outputData.replace(/\s/gm, function (match, offset, str) {
                if (match == ' ') {
                    str = '.'.wrapInTag('span', '{"class":"whitespace"}');
                } else if (match == '\n' || match == '\r') {
                    //Paragraph or Pilcrow sign: &#182;
                    str = '&#182'.wrapInTag('span', '{"class":"whitespace"}') + '\n';
                }
                return str;
            });
        }

        outputData = outputData.wrapInTag('pre');
        UI.toggleConsoleViews(commandLine, commandView, outputData);
    });
    
    showDeviceViewButton.click(function () {
        var activeState = $('#accordion').accordion('option', 'active');
        var request = ajaxRequests.getScreenshot;

        if (activeState !== false) {
            return;
        }

        // request.url = 'data/takeScreenshotResponse.json';
        request.url = Config.getURL() + 'take_screenshot';
        $.ajax(ajaxRequests.getScreenshot);
    });

    $('[name="log_capacity"] option').click(function () {
        var userInput, parsedUserInput;
        var userChoice = $(this).val();

        if (isNaN(userChoice)) {
            userInput = prompt('Please enter the log limit:');
            if (!userInput) {
                return;
            }
            try {
                parsedUserInput = UI.setLogMessageLimit(userInput);
                $(this).clone(true).insertAfter(this);
                $(this).val(parsedUserInput);
                $(this).html('user: ' + parsedUserInput);
                $(this).prop('selected', 'selected');
            } catch (e) {
                alert(e.name + ': ' + e.message);
            }
        }
    });

    $('[name="time_stamp"]').change(function () {
        var format = $(this).val();
        var prefix = "Example: ".wrapInTag("b");

        timeStampExample.html(prefix + UI.generateTimestamp(format));
    });


    configButton.click(function () {
        $(this).toggle('slow', function () {
            var dateFormat = Config.getDateFormat();

            timeStampExample.html('Example: '.wrapInTag('b') + UI.generateTimestamp(dateFormat));
            $('[name="time_stamp"][checked="checked"]').removeAttr('checked');
            $('[name="time_stamp"][value=' + dateFormat + ']').attr('checked', 'checked');

            $('[name="tree_children"]').val(Config.getTreeChildren());
            $('[name="tree_parent"]').val(Config.getTreeParent());

            $('[name="url_port"]').val(Config.getPort());
            $('[name="url_protocol"]').val(Config.getProtocol());
            $('[name="url_source"]').val(Config.getSource());
            configMenu.dialog('open');
        });
    });
});