package com.mod.loan.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * sms_record
 * @author 
 */
@Table(name = "tb_sms_record")
public class SmsRecord implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 商户别名
     */
    private String merchant;

    /**
     * 短信渠道 1 创蓝  2 飞鸽
     */
    private Integer channel;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}