var merge = require("webpack-merge");

var generatedConfig = require("./scalajs.webpack.config");

module.exports = merge(generatedConfig, {
  target: 'node'
});