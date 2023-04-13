package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.noah.handler.BlobTypeHandler;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "factor_data", autoResultMap=true)
public class FactorDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /*
    输入类型。0:图片, 1:文字
     */
    private int type;

    /*
    若输入类型为图片，则为OCR转化后的文字；
    若输入类型为文字，则为文字内容
    */
    private String content;

    /*
    若输入类型为图片，则为图片base64
     */
    @TableField(value = "image", typeHandler = BlobTypeHandler.class)
    private byte[] image;
}
