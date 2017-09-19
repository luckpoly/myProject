//获取应用实例
var app = getApp()
// orderdetail.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    pictures: [],
    newlist: {},
    name: "",
    mobile: "",
    account: "",
    applyTime: "",
    approvalStatus: "",
    listAttac: [],
    payType: "",
    orderId: "",
    applyType: "",
    applicationNO: "",
    loadHidden: true,
    description:""
  },
  previewImage: function (e) {
    var that = this,
      //获取当前图片的下表
      index = e.currentTarget.dataset.index
    wx.previewImage({
      //当前显示下表
      current: this.data.pictures[index],
      //数据源
      urls: this.data.pictures
    })
  },
  btn_delete: function (e) {
    var that = this
    wx.showModal({
      title: '提示',
      content: '确认删除订单？',
      success: function (res) {
        that.setData({
          loadHidden: false
        })
        if (res.confirm) {
          console.log('用户点击确定', that.data.orderId)
          wx.request({
            url: app.url + 'Application/DelApplication',
            method: 'POST',
            data: { id: that.data.orderId, SysUserID: app.globalData.SysUserID },
            header: { 'Content-type': 'application/json' },
            success: function (res) {
              var deleteState = ''
              if (res.data.code == 'y') {
                deleteState = '删除成功'
                //获取页面栈
                var pages = getCurrentPages();
                if (pages.length > 1) {
                  //上一个页面实例对象
                  var prePage = pages[pages.length - 2];
                  //关键在这里
                  prePage.onPullDownRefresh()
                }
                wx.navigateBack();
              } else {
                deleteState = '删除失败'
              }
              wx.showToast({
                title: deleteState,
                icon: 'succes',
                duration: 1000,
                mask: true
              })
              console.log('详细信息：', res.data),
                that.setData({
                  loadHidden: true,
                })
            }
          })

        } else {
          console.log('用户点击取消')
        }
      }
    })
  },
  loadData: function () {
    this.setData({
      loadHidden: false
    })
    var that = this
    wx.request({
      url: app.url + 'Application/GetApplicationDetail',
      data: { id: that.data.orderId },
      header: { 'Content-type': 'application/json' },
      success: function (res) {
        if (!res.data) {
          return false
        }
        console.log('详细信息：', res.data),
          that.setData({
            newList: res.data,
            loadHidden: true,
            name: res.data.name,
            mobile: res.data.mobile,
            account: res.data.account,
            applyTime: res.data.applyTime,
            approvalStatus: res.data.approvalStatus,
            listAttac: res.data.listAttac,
            payType: res.data.payType,
            applyType: res.data.applyType,
            applicationNO: res.data.applicationNO,
            description: res.data.description
          })
        that.data.pictures = []
        if (that.data.listAttac!=null){
          for (var i = 0; i < that.data.listAttac.length; i++) {
            that.data.pictures.push("https://sp.mufax.cn/" + that.data.listAttac[i].FilePath)
          }
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      orderId: options.id,
      delBtnHidden: options.deleteBtnHidden
    })
    this.loadData()
  }

})