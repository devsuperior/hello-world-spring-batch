package com.devsuperior.primeiroprojetospringbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {
	
	@Bean
	public Job job(JobRepository jobRepository, Step printHelloStep) {
		return new JobBuilder("job", jobRepository)
				.start(printHelloStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
