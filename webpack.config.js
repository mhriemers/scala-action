var generatedConfig = require("./scalajs.webpack.config");
module.exports = {
  ...generatedConfig,
  target: 'node'
}