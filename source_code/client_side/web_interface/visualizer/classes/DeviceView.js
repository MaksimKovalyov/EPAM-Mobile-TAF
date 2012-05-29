/**
 * @fileOverview Contains device view related logic.
 * @version      0.5
 * @author       <a href="mailto:Denis_Shamgin@epam.com">Denis Shamgin</a>
 */

/**
 * Wraps the HTML &lt;canvas&gt; element.Some methods and properties are not moved to a prototype since there are private properties and methods (+ a single object instance is expected to be used).
 * @class
 * @param {String} locator jQuery-ready element locator.
 */
function DeviceView(locator) {
    'use strict';
    var _that = this;

    var _instance = $(locator);
    var _pureInstance = _instance.get(0);
    var _context = _pureInstance.getContext('2d');
    
    // TODO: generate frame and container dynamically?
    var _container = $('#device_view_container');
    var _highlightFrame = $('#highlight_frame')
        .hover(function () {
            $(this).css('background-color', 'rgba(255, 255, 0, 0.5)');
        }, function () {
            $(this).css('background-color', '');
        })
        .click(function () {
            $(this).fadeOut();
        });
    var _dialogConfig = {
        title: 'Device View',
        width: 'auto',
        height: 'auto',
        modal: false,
        resizable: false,
        buttons: {
            'Refresh' : function () {
                $.ajax({
                    url: Vz.Config.getURL() + 'take_screenshot',
                    //url: 'data/takeScreenshotResponse.json',
                    dataType: 'json',
                    success: _that.drawDevice
                });
            },
            'Live Update' : function () {
                // TODO: request screenshot data from server periodically 
                // TODO: replace this button with checkbox/switch
            },
            'Save Image' : function () {
                _that.saveImage();
            }
        }
    };
    var _currentState = {
        device: null,
        orientation: { name: '', angle: 0 },
        screenshot: null,
        zoom: 100,
        useHighlightFrame: false
    };

    /**
     * @private
     * @param {Number} angle
     */
    function _rotateInstance(angle) {
        // TODO: perform rotation of the instance (switch width/height, redraw image)
    }

    /**
     * @private
     * @param {String} name
     */
    function _setCurrentDevice(name) {
        _currentState.device = DeviceView.getProperties(name) || DeviceView.getProperties('iphone');
    }

    /**
     * 
     * @private
     * @param   {String} value
     * @returns {Array}
     */
    function _setCurrentOrientation(value) {
        var angle;
        
        switch (value) {
            case 'Unknown':
                angle = 0;
                break;
            case 'Portrait':
                angle = 0;
                break;
            case 'Portrait Upside Down':
                angle = 0;
                break;
            case 'Landscape Left':
                angle = -90;
                break;
            case 'Landscape Right':
                angle = 90;
                break;
            case 'Face Up':
                angle = 0;
                break;
            case 'Face Down':
                angle = 0;
                break;
            default:
                angle = 0;
        }
        
        _rotateInstance(angle);
        _currentState.orientation.name = value;
        _currentState.orientation.angle = angle;

        return [value, angle];
    }

    /**
     * @public
     * @returns {Object}
     */
    this.getInstance = function () {
        return _instance;
    };

    /**
     * @public
     * @returns {Object}
     */
    this.getCurrentDevice = function () {
        return _currentState.device;
    };

    /**
     * @public
     * @returns {Object}
     */
    this.getCurrentOrientation = function () {
        return _currentState.orientation;
    };

    /**
     * Performs element rotation.
     * @param   {String} direction Either 'left' or 'right' string.
     * @returns {Number}           Current orientation angle (in degrees).
     * @throws  {RangeError}       If the specified direction is unknown.
     */
    this.rotate = function (direction) {
        switch (direction.toLowerCase()) {
            case 'right':
                if (_currentState.orientation + 90 === 360) {
                    _currentState.orientation = 0;
                } else {
                    _currentState.orientation += 90;
                }
                break;
            case 'left':
                if (_currentState.orientation - 90 === -360) {
                    _currentState.orientation = 0;
                } else {
                    _currentState.orientation -= 90;
                }
                break;
            default:
                throw new RangeError('Invalid rotate direction!');
        }
        return _currentState.orientation;
    };

    /**
     * Draws an image (device view) on a canvas.
     * @public
     * @param {Object} device Mandatory properties: name and orientation.
     */
    this.drawDevice = function (device) {
        var deviceImage;

        // is device already drawn?
        if (_currentState.device) {
            return;
        }

        deviceImage = new Image();
        _setCurrentDevice(device.name);
        _setCurrentOrientation(device.orientation);

        deviceImage.onload = function () {
            _instance.attr('width', deviceImage.width);
            _instance.attr('height', deviceImage.height);
            _context.drawImage(deviceImage, 0, 0);
        };

        deviceImage.src = _currentState.device.src;
    };

    /**
     * Draws an image (screenshot) on a canvas.
     * @public
     * @param {Object} image Mandatory properties: data (base64 string) and format.
     */
    this.drawScreenshot = function (image) {
        var updatePerfiod = 100;
        var screenshot = new Image();
        var source = 'data:image/{imageFormat};base64,{imageData}'.supplant({
            imageFormat: image.format,
            imageData  : image.data
        });
        var start = {
            x: _currentState.device.offset[_currentState.orientation.name].x,
            y: _currentState.device.offset[_currentState.orientation.name].y
        };

        screenshot.onload = function () {
            var interval = setInterval(function () {
                // is device already drawn?
                if (_currentState.device) {
                    _context.drawImage(screenshot, start.x, start.y);
                    clearInterval(interval);
                } else {
                    return;
                }
            }, updatePerfiod);
        };

        screenshot.src = source;
        _currentState.screenshot = screenshot;
    };

    /**
     * @public
     * @param {Object} frame
     */
    this.displayHighlightFrame = function (frame) {
        var useHighlight = _currentState.useHighlightFrame;
        var deviceDrawn = !!_currentState.device;
        var screenshotDrawn = !!_currentState.screenshot;
        var zoomFactor = _currentState.zoom / 100;
        var offset;

        if (!useHighlight || !(deviceDrawn && screenshotDrawn)) {
            return;
        }
        
        offset = {
            x: _currentState.device.offset[_currentState.orientation.name].x,
            y: _currentState.device.offset[_currentState.orientation.name].y
        };

        // TODO: update slider location
        
        _highlightFrame
        .css({
            display   : 'block',
            marginLeft: (frame.coord.x + offset.x) * zoomFactor,
            marginTop : (frame.coord.y + offset.y) * zoomFactor,
            width     : (frame.size.x) * zoomFactor,
            height    : (frame.size.y) * zoomFactor
        });
        //.text(/* object class name */);
    };

    /**
     * Gets current device view zoom value (an integer, representing percentage).
     * @public
     * @returns {Number} Current zoom value.
     */
    this.getZoom = function () {
        return _currentState.zoom;
    };
    
    /**
     * Sets current device view zoom by applying 'width' and 'height' CSS properties.
     * Values are integers representing percentage.
     * @public
     * @param   {Number} zoomValue Zoom value to be set.
     * @returns {Number}           Zoom value set.
     */
    this.setZoom = function (zoomValue) {
        var zoomFactor = parseInt(zoomValue, 10) / 100;
        var viewSize = {
            width : _pureInstance.width,
            height: _pureInstance.height
        };
        var frameStyle = {
            width     : parseInt(_highlightFrame.css('width'), 10),
            height    : parseInt(_highlightFrame.css('height'), 10),
            marginLeft: parseInt(_highlightFrame.css('margin-left'), 10),
            marginTop : parseInt(_highlightFrame.css('margin-top'), 10)
        };

        // _instance.animate({
        _instance.css({
            width : Math.round(viewSize.width * zoomFactor),
            height: Math.round(viewSize.height * zoomFactor)
        });

        // TODO: resize frame
        _highlightFrame.hide();
        // _highlightFrame.css({
            // width     : Math.round(frameStyle.width * zoomFactor),
            // height    : Math.round(frameStyle.height * zoomFactor),
            // marginLeft: Math.round(frameStyle.marginLeft * zoomFactor),
            // marginTop : Math.round(frameStyle.marginTop  * zoomFactor)
        // });
        
        _currentState.zoom = zoomValue;
        return zoomValue;
    };

    /**
     * @public
     * @param {Boolean} value
     */
    this.highlightElements = function (value) {
        _currentState.useHighlightFrame = !!value;
    };

    /**
     * Initiates image saving process (brings up a browser save dialog window).
     * @public
     */
    this.saveImage = function () {
        var imageString = _pureInstance.toDataURL().replace('data:image/png;base64,', '');
        
        Vz.Utils.submitFormInBackground('utils/download.php', {
            imageData: imageString,
            namePrefix: 'screen',
            fileFormat: 'png'
        });
    };
    
    //-----------------------------------------------------------
    //---[Event handlers]----------------------------------------
    //-----------------------------------------------------------
    
    _instance.click(function (evt) {
        // $(this).clone(true).dialog(_dialogConfig);
        // TODO: consider device view offset!
        var posX = parseInt(evt.pageX - $(this).offset().left, 10);
        var posY = parseInt(evt.pageY - $(this).offset().top, 10);
        Vz.UI.logStatus('click -> x:{x}, y:{y}'.supplant({ x: posX, y: posY }));
    });
}

/**
 * @public
 * @static
 * @param {String} deviceName
 * @returns {Object}
 */
DeviceView.getProperties = function (deviceName) {
    var data = {
        'ipad': {
            src: 'images/devices/iPad.png',
            screen: {
                width : 768,
                height: 1024
            },
            offset: {
                'Unknown': { x: 56, y: 50 },
                'Portrait': { x: 56, y: 50 },
                'Portrait Upside Down': { x: 0, y: 0 },
                'Landscape Left': { x: 0, y: 0 },
                'Landscape Right': { x: 0, y: 0 },
                'Face Up': { x: 0, y: 0 },
                'Face Down': { x: 0, y: 0 }
            }
        },
        'iphone': {
            src: 'images/devices/iPhone.png',
            screen: {
                width : 240,
                height: 320
            },
            offset: {
                'Unknown': { x: 38, y: 126 },
                'Portrait': { x: 38, y: 126 },
                'Portrait Upside Down': { x: 0, y: 0 },
                'Landscape Left': { x: 0, y: 0 },
                'Landscape Right': { x: 0, y: 0 },
                'Face Up': { x: 0, y: 0 },
                'Face Down': { x: 0, y: 0 }
            }
        }
    };

    return data[deviceName.toLowerCase()] || null;
};