package org.noah.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@MappedJdbcTypes(JdbcType.BLOB) // 声明数据库中对应数据类型
@MappedTypes(value = byte[].class) // 转化后的数据类型
public class BlobTypeHandler extends BaseTypeHandler<byte[]> {
    // 数据插入数据库时调用此方法
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, byte[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, parameter);
    }

    // 从数据库中读取数据时调用此方法
    @Override
    public byte[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getBytes(columnName);
    }

    // 读取方法同上，方法重载
    @Override
    public byte[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBytes(columnIndex);
    }

    // 读取方法同上，方法重载
    @Override
    public byte[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getBytes(columnIndex);
    }
}
