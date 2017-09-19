package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by apple on 16/2/21.
 */
public class CategoryJsonData {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":"6","pid":"20","name":"牛油果","code":"FruitAvocado","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952408552732.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952415655611.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"11","pid":"20","name":"柠檬","code":"FruitLemon","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952592371660.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952605691777.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"13","pid":"20","name":"蓝莓","code":"FruitBlueberries","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952658226943.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952672485844.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"14","pid":"20","name":"葡萄","code":"FruitGrapes","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952690652125.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952702287579.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"16","pid":"20","name":"车厘子","code":"FruitCherries","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952748321544.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952760809798.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"18","pid":"20","name":"西瓜","code":"FruitWatermelon","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952827159278.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952841616601.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"10","pid":"20","name":"猕猴桃","code":"FruitKiwi","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952556359549.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952569902730.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"5","pid":"20","name":"菠萝蜜","code":"FruitJackfruit","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952378624024.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952386777465.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"},{"id":"1","pid":"20","name":"苹果","code":"FruitApple","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952196148646.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1464952204775121.jpg","typeName":"进口水果","typeCode":"ShopImportFruit","typeId":"135"}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * id : 6
     * pid : 20
     * name : 牛油果
     * code : FruitAvocado
     * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1464952408552732.jpg
     * imgs : http://7xn4ae.com1.z0.glb.clouddn.com/1464952415655611.jpg
     * typeName : 进口水果
     * typeCode : ShopImportFruit
     * typeId : 135
     */

    private List<DataBean> data;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String pid;
        private String name;
        private String code;
        private String icon;
        private String imgs;
        private String typeName;
        private String typeCode;
        private String typeId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }
    }
}
