package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long  id;

    private String username;
    private String password;
    private String salt;

    private Integer status;

    private Integer isDelete;

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

    public enum DELETE_FLAG{
        DELETE(1),
        NOT_DELETE(0);
        private Integer flag;

        DELETE_FLAG(Integer flag) {
            this.flag = flag;
        }

        public Integer getFlag() {
            return flag;
        }
    }
}
