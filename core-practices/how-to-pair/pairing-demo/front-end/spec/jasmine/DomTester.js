var React          = require('react')
var ReactAddons    = require('react/addons')
var ReactTestUtils = React.addons.TestUtils

module.exports = function(markup) {
    if (typeof document !== 'undefined') return
    var jsdom          = require("jsdom").jsdom
    global.document    = jsdom(markup || '')
    global.window      = document.createWindow()
}
