package com.heihuli.test;

import com.heihuli.util.CommonJdbcUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author heihuli
 */
public class CommonJdbcUtilTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * CommonJdbcUtil.preparedStatementSetter
     * CommonJdbcUtil.CommonPreparedStatementSetter
     * CommonJdbcUtil.getSearchWordLikeStr
     */
    @Test
    public void test() {
        int id = 1;
        String name = "heihuli";
        String address = "sz%";
        String sql = "select * from a where id = ?, name = ?, addr like ?";
        jdbcTemplate.query(sql, new CommonJdbcUtil.CommonPreparedStatementSetter() {
            @Override
            public void customSetValues(PreparedStatement ps) throws SQLException {
                // 设置参数
                CommonJdbcUtil.preparedStatementSetter(ps, idx(), id, Types.VARCHAR);
                CommonJdbcUtil.preparedStatementSetter(ps, idx(), name, Types.VARCHAR);
                CommonJdbcUtil.preparedStatementSetter(ps, idx(), CommonJdbcUtil.getSearchWordLikeStr(address), Types.VARCHAR);
            }
        }, new ResultSetExtractor<Object>() {
            @Override
            public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {

                return null;
            }
        });
    }

    /**
     * CommonJdbcUtil.getInSql
     * CommonJdbcUtil.getSearchWordLikeStr
     * CommonJdbcUtil.unionSql
     */
    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("3");
        list.add("a");
        list.add("b");
        System.out.println(CommonJdbcUtil.getInSql(list));
        // (?, ?, ?, ?)

        String searchWord = "%heihuli";
        System.out.println(CommonJdbcUtil.getSearchWordLikeStr(searchWord));
        // %\%heihuli%

        String sql1 = "and name = ?";
        String sql2 = "and id = ?";
        System.out.println(CommonJdbcUtil.unionSql(sql1, sql2));
        //  AND ((1=1 and name = ?) OR (1=1 and id = ? ))
    }
}
