// submit.js
//获取应用实例
var app = getApp()
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showView: true,
    loadHidden: true,
    confirmHidden: true,
    loadingtext: "加载中...",
    promat: "",
    pictures: ['../../images/img/add.png'],

    depname: "",
    name: "",
    mynumber: "",
    positiontype: "",
    code: "",

    userInfo: {},
    applicationtype: '1',
    paytype: '1',
    ListAttachment: [
    ],
    formData:'',
    allValue: ''
  },
  /**
    * 生命周期函数--监听页面加载
    */
  onLoad: function (options) {
    var that = this;
    if (!getApp().globalData.isregister) {//是否已经注册
      that.login();
    } else {
      that.getUserInfo();
    }
  },
  //选择支付方式
  payType: function (e) {
    console.log('radioChange', e.detail.value)
    var value = e.detail.value;
    var that = this;
    if (value == "1") {
      that.setData({
        showView: true
      })
    } else {
      that.setData({
        showView: false
      })
    }
    that.setData({
      paytype: value
    })
  },
  //选择申请类型
  applicationType: function (e) {
    this.setData({ applicationtype: e.detail.value });
    console.log(this.data.applicationtype)
  },
  //提交数据
  formSubmit: function (e) {
    var that = this
    this.data.formData = e.detail.value
    this.data.formData.ApplyType = this.data.applicationtype
    this.data.formData.PayType = this.data.paytype
    this.data.formData.SysUserID = app.globalData.SysUserID
    if (this.data.formData.Name.length == 0 || this.data.formData.IDNO.length == 0 || this.data.formData.Account.length == 0) {
      that.setData({ promat: "请完整填写提交信息!", confirmHidden: false })
      return false
    }
    this.data.ListAttachment=[]
    if (this.data.formData.PayType==1){
      if (this.data.pictures.length == 1 && this.data.pictures[0] == '../../images/img/add.png'){
        that.setData({ promat: "请选择要上传的图片!", confirmHidden: false })
        console.log('没有图片')
        return false
      }
      that.myuploadimg({
        // url: 'http://172.16.100.156:8011/api/Application/uploadImg',
        url: app.url + 'Application/uploadImg',
        path: this.data.pictures//这里是选取的图片的地址数组
      })
    }else{
      this.data.formData.ListAttachment=[];
      that.uploadData();
    }
    this.setData({//显示加载中的提示
      loadingtext: "提交中...",
      loadHidden: false
    })
  },
  uploadData: function (){
    var that=this
    this.data.formData.ListAttachment = this.data.ListAttachment;
    wx.request({
      // url: 'http://172.16.100.156:8011/api/Application/AddApplication',
      url: app.url + 'Application/AddApplication',
      method: 'POST',
      header: { 'Content-type': 'application/json' },
      data: this.data.formData,
      success: function (res) {
        console.log("-----", res.data)
        that.setData({ loadHidden: true })
        console.log(that.data.formData)
        wx.showToast({
          title: '提交成功',
          icon: 'succes',
          duration: 1500,
          mask: true,
        })
        that.setData({
          redata:'',
          pictures: ['../../images/img/add.png'
          ]
        })
      },
    })
  },
  //选择图片
  seleteimg: function () {
    var that = this
    wx.chooseImage({
      count: 9, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'], // , 'camera'可以指定来源是相册还是相机，默认相册
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        console.log('图片路径为：', tempFilePaths);
        that.setData({
          pictures: tempFilePaths,
        });
        // that.updateImg(tempFilePaths);
      }
    })
  },
  //多张图片上传
  myuploadimg: function (data){
    var that= this,
    i=data.i ? data.i : 0,
    success=data.success ? data.success : 0,
    fail=data.fail ? data.fail : 0;
    wx.uploadFile({
      url: data.url,
      filePath: data.path[i],
      name: 'fileData',
      formData: null,
      success: (resp) => {
        success++;
        console.log(resp)
        console.log(i);
      
//---------------------------
        var datae = resp.data;
        console.log(datae)
        var imgobj= str2json(datae)
        console.log(imgobj)
        //这里可能有BUG，失败也会执行这里
        var imgdata = { FileName: imgobj.name, FileType: 0, FilePath: imgobj.url }
        console.log(imgdata)
        that.data.ListAttachment.push(imgdata);
      },
      fail: (res) => {
        fail++;
        console.log('fail:' + i + "fail:" + fail);
      },
      complete: () => {
        console.log(i);
        i++;
        if (i == data.path.length) {   //当图片传完时，停止调用   
          var dataimg = that.data.ListAttachment; 
          that.uploadData();
          console.log('执行完毕' , dataimg);
          console.log('成功：' + success + " 失败：" + fail);
        } else {//若图片还没有传完，则继续调用函数
          console.log(i);
          data.i = i;
          data.success = success;
          data.fail = fail;
          that.myuploadimg(data);
        }

      }
    });
  },


  //用户登录 获取code
  login: function () {
    var that = this
    console.log('登录')
    wx.login({
      success: function (res) {
        if (res.code) {
          console.log('获取用户code成功' + res.code)
          that.getOpenId(res.code);
        } else {
          console.log('获取用户登录态失败！' + res.errMsg)
        }
      }
    })
  },
  //获取用户信息
  getUserInfo: function () {
    var that = this;
    wx.getStorage({
      key: 'openid',
      success: function (res) {
        console.log(res.data)
        wx.request({
          url: app.url + 'user/GetSysUserByOpenID?',
          data: {
            openid: res.data
          },
          header: { 'Content-type': 'application/json' },
          success: function (res) {
            wx.stopPullDownRefresh()//关闭下拉刷新
            if (!res.data) {
              return false
            }
            console.log("-----", res.data)
            if (res.data.code == 'y') {
              that.setData({
                name: res.data.name,
                depname: res.data.depname,
                mynumber: res.data.number,
                positiontype: res.data.positiontype
              })
              app.globalData.SysUserID = res.data.id
            } else {
              console.log('获取用户信息失败！' + res.errMsg)
              //跳转
              wx.redirectTo({
                url: '../login/login',
              })
              //显示数据 
            }
          }
        })
      }
    }),
      //调用应用实例的方法获取全局数据(头像)
      app.getUserInfo(function (userInfo) {
        //更新数据
        that.setData({
          userInfo: userInfo
        })
        console.log(userInfo)
      })

  },
  //请求openId 
  getOpenId: function (code) {
    var that = this;
    wx.request({
      url: app.url + 'user/GetOpenID?',
      data: {
        redirect_url: '',
        code: code
      },
      header: { 'Content-type': 'application/json' },
      success: function (res) {
        if (!res.data) {
          return false
        }
        console.log("-----", res.data)
        var openid = res.data.openid
        console.log("--openid---", openid)
        wx.setStorage({
          key: 'openid',
          data: openid
        })
        if (res.data.code == 'n') {
          //跳转
          wx.redirectTo({
            url: '../login/login',
          })
        } else {
      
          app.globalData.isregister = true;
          //请求个人信息 
          that.getUserInfo()
        }
      }
    })
  },
  modalChange: function () {
    this.setData({ confirmHidden: true })
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
    console.log("下拉刷新")
    this.onLoad();
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


function json2Form(json) {
  var str = [];
  for (var p in json) {
    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(json[p]));
  }
  return str.join("&");
}
function str2json(str){
  var obj = JSON.parse(str);
  return obj;
}