// login.js
var app = getApp()
var util = require('../../utils/util.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    confirmHidden: true,
    loadHidden:true,
    promat: ''
  },
  //提交表单  登录
  formSubmit: function (e) {
    var that = this;
    var formData = e.detail.value
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    if (e.detail.value.Mobile.length == 0 || e.detail.value.Number.length == 0) {
      that.setData({ promat: "手机号和工号不能为空!", confirmHidden: false })
      return false
    }
    that.setData({
      loadHidden: false,
    })
    console.log(formData),
      wx.getStorage({
        key: 'openid',
        success: function (res) {
          formData.OpenID = res.data
          wx.request({
            url: app.url + 'user/Register',
            method: 'POST',
            header: { 'Content-type': 'application/json' },
            data: formData,
            success: function (res) {
              that.setData({
                loadHidden: true,
              })
              console.log("-----", res.data)
              if (!res.data) {
                that.setData({ promat: '服务器异常！', confirmHidden: false })
                return false
              }
              if (res.data.code == 'y') {
                // 跳转到tabBar页面
                app.globalData.isregister = true;
                wx.switchTab({
                  url: '../submit/submit',
                })
              } else {
                that.setData({ promat: res.data.errmsg, confirmHidden: false })
              }
            }
          })
        }
      })
  },
  modalChange: function () {
    this.setData({ confirmHidden: true })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})