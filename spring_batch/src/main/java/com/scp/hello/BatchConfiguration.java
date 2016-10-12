package com.scp.hello;

import com.scp.hello.entity.Person;
import com.scp.hello.otis.JobNotification;
import com.scp.hello.process.PersonItemProcessor;
import com.scp.hello.reader.ReaderService;
import com.scp.hello.writer.WriterService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackageClasses = DefaultBatchConfigurer.class)
public class BatchConfiguration {

    //读数据
    @Autowired private ReaderService readerService;
    //处理数据
    @Autowired private PersonItemProcessor processor;
    //写数据
    @Autowired private WriterService writerService;
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    /**
     * 异常监听
     * @return
     */
    @Bean
    public JobExecutionListener listener() {
        return new JobNotification();
    }

    /**
     * 导入任务
     */
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1())
                .end()
                .build();
    }

    /**
     * 执行步骤
     */
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10)
                .reader( readerService.readerDatabase() )
                .processor( processor )
                .writer( writerService.writerDatabase() )
                .build();
    }
}
