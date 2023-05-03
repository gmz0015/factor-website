package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.noah.handler.BlobTypeHandler;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@TableName(value = "factor_log", autoResultMap=true)
public class FactorLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /*
    标签
     */
    private String tag;

    private Long userId;

    private Double score;

    private Double onlineTime;

    private Date time;

    @TableField(exist = false)
    private String totalScore;

    @TableField(exist = false)
    private String totalOnlineTime;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private List<FactorDataEntity> datas;

    public FactorLogEntity() {
    }

    public FactorLogEntity(Long id, String tag, Long userId, Double score, Double onlineTime, Date time, String totalScore, String totalOnlineTime, String username, List<FactorDataEntity> datas) {
        this.id = id;
        this.tag = tag;
        this.userId = userId;
        this.score = score;
        this.onlineTime = onlineTime;
        this.time = time;
        this.totalScore = totalScore;
        this.totalOnlineTime = totalOnlineTime;
        this.username = username;
        this.datas = datas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Double onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(String totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<FactorDataEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<FactorDataEntity> datas) {
        this.datas = datas;
    }
}
