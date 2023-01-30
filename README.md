# ![DevSuperior logo](https://raw.githubusercontent.com/devsuperior/bds-assets/main/ds/devsuperior-logo-small.png) First project using Spring Batch
>  Case study to implement a hello world message using Spring Batch 5.0

#### Support material

https://spring.io/blog/2022/11/24/spring-batch-5-0-goes-ga

https://github.com/spring-projects/spring-batch/wiki/Spring-Batch-5.0-Migration-Guide (Guia para migração de versões mais antigas)

## Key topics for creating a spring batch application

### Step: Maven dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-batch</artifactId>
</dependency>
```
* Required: Include h2 dependency or any other relational database

### Step: Job and Step Configuration

```java
@Configuration
public class BatchConfig {

	@Bean
	public Job job(JobRepository jobRepository, Step step) {
		return new JobBuilder("job", jobRepository)
				.start(step)
				.build();
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step", jobRepository)
				.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
					System.out.println("Olá, mundo!");
					return RepeatStatus.FINISHED;
				}, transactionManager)
				.build();
	}
}
```

### Step: Refactoring project

```java
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
```

```java
@Configuration
public class PrintHelloStepConfig {

	@Bean
	public Step printHelloStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet printHelloTasklet) {
		return new StepBuilder("step", jobRepository)
				.tasklet(printHelloTasklet, transactionManager)
				.build();
	}

}
```

```java
@Component
@StepScope
public class PrintHelloTasklet implements Tasklet {

	@Value("#{jobParameters['nome']}")
  private String nome;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("Olá, " + nome +" !");
		return RepeatStatus.FINISHED;
	}
}
```







