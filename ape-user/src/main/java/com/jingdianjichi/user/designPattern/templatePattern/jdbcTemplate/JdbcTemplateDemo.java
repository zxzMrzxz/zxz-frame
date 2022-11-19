package com.jingdianjichi.user.designPattern.templatePattern.jdbcTemplate;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;

/**
 * @Author: ChickenWing
 * @Description: jdbcTemplate的demo
 * @DateTime: 2022/11/19 22:06
 */
@Component
public class JdbcTemplateDemo {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void demo() {
        Object execute = jdbcTemplate.execute(new StatementCallback<Object>() {
            @Override
            public Object doInStatement(Statement statement) throws SQLException, DataAccessException {
                return null;
            }
        });
    }

}
