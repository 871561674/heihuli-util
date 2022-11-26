package com.heihuli.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/**
 * @author heihuli
 *
 * JDBC工具类
 */
public class CommonJdbcUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CommonJdbcUtil.class);

    public static final Integer SQL_IN_LIMIT = 1000;

    /**
     * 带空值处理的PreparedStatementSetter工具
     *
     * @param ps             PreparedStatement实例
     * @param parameterIndex 参数索引，从1开始
     * @param obj            参数的对象
     * @param sqlType        参数对应的sql类型，使用java.sql.Types取值
     * @throws SQLException 设置出现异常时抛出
     */
    public static void preparedStatementSetter(PreparedStatement ps, int parameterIndex, Object obj, int sqlType) throws SQLException {
        if (null == obj) {
            ps.setNull(parameterIndex, Types.NULL);
        } else {
            ps.setObject(parameterIndex, obj, sqlType);
        }
        if (LOG.isTraceEnabled()) {
            String objClass = null == obj ? "null" : obj.getClass().getName();
            String sqlTypeName = JDBCType.valueOf(sqlType).getName();
            LOG.trace("Setting SQL statement parameter value: index {}, value [{}], class [{}], SQL type {}", parameterIndex, obj, objClass, sqlTypeName);
        }
    }

    /**
     * 从String类型的sql中生成指定长度的(?, ?, ?)<br>
     * 括号前后不带空格<br>
     * 数据列表长度不超过1000
     *
     * @param objs 数据列表 不超过1000
     * @return 指定长度的(?, ?, ?)
     */
    public static String getInSql(Collection<?> objs) {
        if (null == objs || objs.size() == 0) {
            return null;
        }
        if (objs.size() > SQL_IN_LIMIT) {
            LOG.error("[JdbcUtil] sql IN-Item too many, size: {}", objs.size());
            throw new RuntimeException("IN子句中超过1000个候选项");
        }
        StringBuffer sb = new StringBuffer("(");
        sb.append("?");
        for (int idx = 1; idx < objs.size(); idx++) {
            sb.append(", ?");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 模糊搜索 %xxx%
     *
     * @param searchWord 关键字
     * @return
     */
    public static String getSearchWordLikeStr(String searchWord) {
        if (searchWord == null)
            return "%%";
        return "%" + searchWord.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_") + "%";
    }

    /**
     * @param sql  AND a in('1')
     * @param sql2 AND a in('2')
     * @return AND (    (1=1 sql)     OR        (1=1 sql2)     )
     */
    public static String unionSql(String sql, String sql2) {
        if (StringUtils.isBlank(sql)) {
            return sql2;
        }
        if (StringUtils.isBlank(sql2)) {
            return sql;
        }
        return " AND ((1=1 " + sql + ") OR (1=1 " + sql2 + " )) ";
    }

    public static abstract class CommonPreparedStatementSetter implements PreparedStatementSetter {

        private int idx = 1;

        /**
         * 获取自动增加的字段游标，请按顺序写入字段即可
         *
         * @return 字段游标
         */
        public int idx() {
            return idx++;
        }

        /**
         * 标准实现setValues
         *
         * @param ps PreparedStatement
         * @throws SQLException SQL异常
         */
        @Override
        public void setValues(PreparedStatement ps) throws SQLException {
            customSetValues(ps);
        }

        /**
         * 用户的setValues
         *
         * @param ps 从setValues()透传过来的PreparedStatement
         * @throws SQLException SQL异常
         */
        public abstract void customSetValues(PreparedStatement ps) throws SQLException;
    }

}
