/**
 * Detects if passed argument represents the instance of an Array.
 * 
 * @public
 * @param  {Object} obj
 * @return {Boolean}
 * 
 * @example
 * isArray([1, 3, 5]) // -> true
 */
window.isArray = function (obj) {
    return (Object.prototype.toString.call(obj) === '[object Array]');
};

/**
 * Detects if passed argument represents the instance of an Object.
 * 
 * @public
 * @param  {Object} obj
 * @return {Boolean}
 * 
 * @example
 * isObject({}) // -> true
 */
window.isObject = function (obj) {
    return (Object.prototype.toString.call(obj) === '[object Object]');
};

/**
 * Array clean up method. Deletes elements with specified value or corresponding to the specified regular expression.
 * 
 * @public
 * @augments Array
 * 
 * @param {Object} deleteValue Value to be searched for and deleted from an array.
 * @return {Array}             Cleaned up array.
 * 
 * @example
 * [1, "abc", 5].cleanUp(/[^\d]+/); // -> [1, 5]
 */
Array.prototype.cleanUp = function (deleteValue) {
    for (var i = 0; i < this.length; i++) {
        if (deleteValue instanceof RegExp) {
            if (deleteValue.test(this[i])) {
                this.splice(i, 1);
                i--;
            }
        } else {
            //skip cycling through the array if sought item is absent:
            if (this.indexOf(deleteValue) != -1) {
                if (this[i] === deleteValue) {   
                    this.splice(i, 1);
                    i--;
                }
            } else {
                return this;
            }
        }
    }
    return this;
};

/**
 * Cross-browser 'toSource' array method. Handles IE problem.
 * @public
 * @augments Array
 * 
 * @return {String}
 * 
 * @example
 * var Arr = [[0, 0], [340, 480]];
 * Arr.toSourceX(); // -> "[[0, 0], [340, 480]]"
 */
Array.prototype.toSourceX = function () {
    var i;
    var delimiter = ", ";
    var result = "";
    
    result += "[";
    for (i = 0; i < this.length; i++) {
        if (i === (this.length - 1)) {
            delimiter = "";
        }
        if (this[i] instanceof Array) {
            result += this[i].toSourceX() + delimiter;
        } else {
            result += this[i] + delimiter;
        }
    }
    result += "]";
    
    return result;
};

if (!Array.prototype.indexOf) {
    /**
     * ECMA-262, 5th edition.
     * indexOf compares searchElement to elements of the Array using strict equality (the same method used by the ===, or triple-equals, operator).
     * @augments Array
     * @param  {Object} searchElement
     * @param  {Number} fromIndex
     * @return {Number}
     */
    Array.prototype.indexOf = function (searchElement /*, fromIndex */ ) {
        "use strict";
        if (this === void 0 || this === null) {
            throw new TypeError();
        }
        var t = Object(this);
        var len = t.length >>> 0;
        if (len === 0) {
            return -1;
        }
        var n = 0;
        if (arguments.length > 0) {
            n = Number(arguments[1]);
            if (n !== n) { // shortcut for verifying if it's NaN
                n = 0;
            } else if (n !== 0 && n !== (1 / 0) && n !== -(1 / 0)) {
                n = (n > 0 || -1) * Math.floor(Math.abs(n));
            }
        }
        if (n >= len) {
            return -1;
        }
        var k = n >= 0 ? n : Math.max(len - Math.abs(n), 0);
        for (; k < len; k++) {
            if (k in t && t[k] === searchElement) {
                return k;
            }
        }
        return -1;
    }
}

/**
 * Tests an array for presence of reference type elements (objects, arrays).
 * 
 * @public
 * @augments Array
 * 
 * @returns {Boolean}
 * 
 * @example
 * [1, 3, ['a', 'b', 'c']].containsRefTypes(); //returns 'true'
 */
Array.prototype.containsRefTypes = function () {
    var re1 = /^\[(.*)\]$/; //substring within square brackets at both ends of the entire analyzed string
    var re2 = /(["']).*\1/; //strings with serialized reference type elements
    var re3 = /[\[\]\{\}]/; //opening/closing square/curly brackets
    var str = this.toSource();

    str = str.replace(re1, '$1').replace(re2, '');

    return re3.test(str);
};

/**
 * supplant() does variable substitution on the string.
 * It scans through the string looking for expressions enclosed in { } braces.
 * If an expression is found, use it as a key on the object, and if the key has a string value or number value, it is substituted for the bracket expression and it repeats. 
 * This is useful for automatically fixing URLs.
 * Thanks to <a href="http://javascript.crockford.com/remedial.html">Douglas Crockford</a>.
 * 
 * @augments String
 * @param    {Object} o
 * @returns  {String} Supplanted string.
 */
if (!String.prototype.supplant) {
    String.prototype.supplant = function (o) {
        return this.replace(/{([^{}]*)}/g,
            function (a, b) {
                var r = o[b];
                return typeof r === 'string' || typeof r === 'number' ? r : a;
            }
        );
    };
}

/**
 * Text wrapper.
 * 
 * @public
 * @augments String
 * 
 * @param  {String} wrapper Text wrapper.
 * @return {String}         Wrapped text.
 * 
 * @example
 * "abc".wrapIn("\""); //wraps string in double quotes
 */
String.prototype.wrapIn = function (wrapper) {
    wrapper = String(wrapper);
    return wrapper + this + wrapper;
};

/**
 * Wraps string in a tag. Tag attributes may also be specified (use JSON).
 * All web-unsafe characters within a tag are deleted. 
 * 
 * @public
 * @augments String
 * 
 * @param  {String} tag   Tag used to wrap source string with it.
 * @param  {JSON} attr    JSON data container: a set of key-value pairs representing HTML attribute name and its value.
 * @throws {TypeError}    If argument was not specified.
 * @throws {SyntaxError}  If JSON text as 'attr' cannot be parsed.
 * @return {String}       Source string wrapped with tag.
 * 
 * @example
 * "Text".wrapInTag("span", '&#123; "id" : "id_001", "class" : "key" &#125;');
 * //Result: &lt;span id="id_001" class="key"&gt;Text&lt;/span&gt;
 */
String.prototype.wrapInTag = function (tag, attr) {
    var prop, Attributes = {}, str = "";
    var htmlRE = /[<>&'"\/\\]/gim;
    
    if (!tag) {
        throw new TypeError("Argument 'tag' not specified!");
    } else if (htmlRE.test(tag)) {
        //wipes off illegal characters from the tag name:
        tag = tag.replace(htmlRE, "");
    } else if (attr) {
        try {
            Attributes = JSON.parse(attr);
            for (prop in Attributes) {
                str += " " + prop + "=\"" + Attributes[prop] + "\"";
            }
        } catch (e) {
            throw e;
        }
    }

    return "<" + tag + str + ">" + this + "</" + tag + ">";
};

/**
 * Converts a degree number to radians.
 * @public
 * @augments Number
 * @returns {Number}
 */
Number.prototype.toRadian = function () {
    return this * Math.PI / 180;
};

HTMLElement.prototype.center = function () {
    var cssPosition = window.getComputedStyle(this, null).getPropertyValue('position');
    
    if (cssPosition !== 'fixed') {
        this.style.position = 'fixed';
    }
    this.style.top = (window.innerHeight - this.scrollHeight) / 2 + 'px';
    this.style.left = (window.innerWidth - this.scrollWidth) / 2 + 'px';
    
    return this;
};