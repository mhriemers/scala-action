// noinspection JSUnresolvedVariable,JSUnresolvedFunction,JSFileReferences

const generatedConfig = require('./scalajs.webpack.config')
module.exports = {
  ...generatedConfig,
  target: 'node'
}
