package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Accessors(chain = true)
@TableName(value = "sys_log")
public class SysLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /*
    日志类型。0:用户登录, 1:用户登出
     */
    private int type;

    private Long userId;

    @TableField(exist = false)
    private String username;

    private Date time;

    public SysLogEntity() {
    }

    public SysLogEntity(Long id, int type, Long userId, String username, Date time) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.username = username;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
