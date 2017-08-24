//获取应用实例
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    newList: [

    ],
    beginIndex:1,
    btnIndex: 1,
    loadMoreIndex:false,
    btn1:true,
    btn2:false,
    btn3:false,
    loadHidden: true
  },
  loadData:function(e){
    this.setData({
      loadHidden: false
    })
    console.log('btnIndex:'+e, '开始'+this.data.beginIndex)
    var that=this 
    wx.request({
      // url:'http://172.16.10.40:9001/api/Application/PostApplicationByType',
      url: app.url +'Application/PostApplicationByType',  
      method: 'POST',
      data: { approvalStatus: e, startIndex: that.data.beginIndex, endIndex: that.data.beginIndex + 10, userID:'4'},
      header:{'Content-type': 'application/json'},
      success:function(res){
        if (!res.data){
          return false
        }
        var dataArr = that.data.newList
        var newData=dataArr.concat(res.data);
        console.log("-----", res.data) 
        if (!that.data.loadMoreIndex){ 
          console.log("-asdfasdfsaf") 
          that.setData({ newList: res.data.data})
         }else{
          that.setData({ newList: that.data.newList.concat(res.data.data) })
         }
      },
      complete:function(){
        that.setData({ loadMoreIndex: false,
                       beginIndex: that.data.beginIndex + 10, 
                       loadHidden: true
        })
      }
    })
  },
  // 底部上滑加载更多
  onReachBottom: function () {
    console.log('circle 下一页')
    // 判断是否在加载中，为了防止重复加载
    if (this.loading) { // this.loading在开始请求的时候设为true，
      // 加载完再设置为false
      console.info('loading is processing...');
      return;
    }
    this.setData({loadMoreIndex:true})
    console.log('loading more...');

    this.loadData(this.data.btnIndex);
    wx.showToast({
      title: '正在加载数据',
      icon: 'loading'
    })
  },
  //下拉刷新界面
  onPullDownRefresh: function () {
    console.log('刷新');
    var that = this;
    if (this.loading) {
      console.info('refresh is processing...');
      return;
    }
    this.setData({
      beginIndex: 1
    })
    console.log('refreshing...')
    // 拿到第一个项的的id
    this.loadData(this.data.btnIndex);
    wx.showToast({
      title: '正在刷新数据',
      icon: 'loading'
    })
    wx.stopPullDownRefresh()
  },
  seletebtn1: function () {
    var that = this
    that.setData({
      btn1: true,
      btn2: false,
      btn3: false,
      beginIndex:1,
      btnIndex: 1
    })
    this.loadData(1);
  },
  seletebtn2:function(){
    var that = this
    that.setData({
      btn1: false,
      btn2: true,
      btn3: false,
      beginIndex: 1,
      btnIndex: 3
    })
    this.loadData(3);
  },
  seletebtn3: function () {
    var that = this
    that.setData({
      btn1: false,
      btn2: false,
      btn3: true,
      beginIndex: 1,
      btnIndex: 2
    })
    this.loadData(2);
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var that = this
    this.loadData(1);
  }
  
})