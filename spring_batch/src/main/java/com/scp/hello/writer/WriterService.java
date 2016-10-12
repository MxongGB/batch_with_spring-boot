package com.scp.hello.writer;

import com.scp.hello.entity.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@Configuration
public class WriterService {
    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource dataSource;

    @Bean
    public  JdbcBatchItemWriter<Person> writerDatabase() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        //设置数据源
        writer.setDataSource( dataSource );
        //设置SQL
        writer.setSql("INSERT INTO user (first_name, last_name) VALUES (:firstName, :lastName)");
        //设置参数
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        return writer;
    }
}
