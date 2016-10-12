package com.scp.hello.reader;

import com.scp.hello.entity.Person;
import com.scp.hello.entity.PersonRowMapper;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@Configuration
public class ReaderService {
    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Person> readerDatabase() {
        JdbcCursorItemReader<Person> jdbcReader = new JdbcCursorItemReader<>();
        //设置数据源
        jdbcReader.setDataSource( dataSource );
        //设置SQL
        jdbcReader.setSql("select first_name, last_name from people");
        //设置参数
        //jdbcReader.setPreparedStatementSetter();
        //设置POJO映射类
        jdbcReader.setRowMapper(new PersonRowMapper());
        return jdbcReader;
    }
}
