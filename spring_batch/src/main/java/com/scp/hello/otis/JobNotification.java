package com.scp.hello.otis;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobNotification extends JobExecutionListenerSupport {

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("-+-任务已完成-+-");
		}else{
			System.out.println("-+-数据处理过程发生异常-+-");
		}
	}
}
