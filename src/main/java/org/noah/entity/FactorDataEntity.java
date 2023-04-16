package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.noah.handler.BlobTypeHandler;

import java.io.Serializable;

@Accessors(chain = true)
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

    public FactorDataEntity() {
    }

    public FactorDataEntity(Long id, int type, String content, byte[] image) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.image = image;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
