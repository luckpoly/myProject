//app.js
App({
  onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },

  getUserInfo: function (cb) {
    var that = this
    if (this.globalData.userInfo) {
      typeof cb == "function" && cb(this.globalData.userInfo)
    } else {
      //调用登录接口
      wx.getUserInfo({
        withCredentials: false,
        success: function (res) {
          that.globalData.userInfo = res.userInfo
          typeof cb == "function" && cb(that.globalData.userInfo)
        }
      })
    }
  },

  globalData: {
    userInfo: null,
    isregister: false,
    SysUserID: '',
    isRefresh: false
  },
  url: 'https://sp.mufax.cn/api/',
  // url: 'http://172.16.10.40:9001/api/',
  // url:'http://172.16.100.185:81/api/',//宇哥
  // url:'http://172.16.100.156:8011/api/',//花花
})
