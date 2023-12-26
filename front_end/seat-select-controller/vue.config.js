const UnoCSS = require('@unocss/webpack').default
module.exports = {
  // 基本路径
  publicPath: '/',
  productionSourceMap: false,
  css: {
    loaderOptions: {
      stylus: {
        javascriptEnabled: true
      }
    },
    extract: {
      filename: '[name].[hash:9].css',
      ignoreOrder: true
    }
  },
  devServer: {
    port: 9099,
    proxy: {
      '/api': {
        target: 'http://localhost:9080',
        changeOrigin: true, // 是否允许跨越
        pathRewrite: {
          '^/api': '' // 重写,
        }
      }
    }
  },
  configureWebpack: {
    plugins: [UnoCSS()]
  }
}
