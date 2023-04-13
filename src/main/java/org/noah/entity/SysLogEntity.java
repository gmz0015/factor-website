package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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
}
