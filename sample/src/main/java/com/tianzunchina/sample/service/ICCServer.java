package com.tianzunchina.sample.service;

public class ICCServer {
    private String webServiceRoot;
    private String webSiteDiscountRoot;
    private String pictureRoot;
    private String messageRoot;
    private String visiServicerUrl;
    private String accessoryFileUrl;
    private String yqUrl;
    private String zsyxUrl;
    private String tzjlUrl;

    /**
     * @param webServiceRoot
     * @param webSiteDiscountRoot 二维码页面路径
     * @param pictureRoot         网络图片路径
     * @param messageRoot         消息
     * @param visiServicerUrl     移动数据库
     * @param accessoryFileUrl    述职评议详情页面附件详情
     * @param yqUrl               舆情工作室地址
     * @param zsyxUrl             掌上邮箱地址
     * @param tzjlUrl             台账记录地址
     */
    public ICCServer(String webServiceRoot, String webSiteDiscountRoot,
                     String pictureRoot, String messageRoot, String visiServicerUrl, String
                             accessoryFileUrl, String yqUrl, String zsyxUrl, String tzjlUrl) {
        this.webServiceRoot = webServiceRoot;
        this.webSiteDiscountRoot = webSiteDiscountRoot;
        this.pictureRoot = pictureRoot;
        this.messageRoot = messageRoot;
        this.visiServicerUrl = visiServicerUrl;
        this.accessoryFileUrl = accessoryFileUrl;
        this.yqUrl = yqUrl;
        this.zsyxUrl = zsyxUrl;
        this.tzjlUrl = tzjlUrl;
    }

    public String getWebServiceRoot() {
        return webServiceRoot;
    }

    public String getWebSiteDiscountRoot() {
        return webSiteDiscountRoot;
    }

    public String getPictureRoot() {
        return pictureRoot;
    }

    public String getMessageRoot() {
        return messageRoot;
    }

    public String getVisiServicerUrl() {
        return visiServicerUrl;
    }

    public String getAccessoryFileUrl() {
        return accessoryFileUrl;
    }

    public String getYqUrl() {
        return yqUrl;
    }

    public String getZsyxUrl() {
        return zsyxUrl;
    }

    public String getTzjlUrl() {
        return tzjlUrl;
    }
}
