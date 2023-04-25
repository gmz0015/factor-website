package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Accessors(chain = true)
@TableName(value = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long  id;

    private String username;
    private String password;
    private String salt;

    private Integer status;

    private Double onlineTime;

    @TableField(exist = false)
    private Set<String> roles;

    public enum STATUS_FLAG{
        ENABLE(1),
        DISABLE(0);
        private Integer flag;

        STATUS_FLAG(Integer flag) {
            this.flag = flag;
        }

        public Integer getFlag() {
            return flag;
        }
    }

    public UserEntity() {
    }



    public UserEntity(Long id, String username, String password, String salt, Integer status, Double onlineTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.status = status;
        this.onlineTime = onlineTime;
    }

    public UserEntity(Long id, String username, String password, String salt, Integer status, Double onlineTime, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.status = status;
        this.onlineTime = onlineTime;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Double getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Double onlineTime) {
        this.onlineTime = onlineTime;
    }
}
