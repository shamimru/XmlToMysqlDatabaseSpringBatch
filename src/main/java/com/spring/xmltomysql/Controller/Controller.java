package com.spring.xmltomysql.Controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private final JobLauncher jobLauncher;
	private final Job importPersonJob;

	@Autowired
	public Controller(JobLauncher jobLauncher, Job importPersonJob) {
		this.jobLauncher = jobLauncher;
		this.importPersonJob = importPersonJob;
	}

	@GetMapping("/launch")
	public ResponseEntity<String> launchJob() {
		System.out.println("Launching the job...");

		try {
			// Adding job parameters to ensure each job run is unique
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()) // Ensure
																													// unique
																													// parameters
					.toJobParameters();

			// Launch the job with the parameters
			JobExecution jobExecution = jobLauncher.run(importPersonJob, jobParameters);

			// Return job status to the client
			return ResponseEntity.ok("Job started successfully with ID: " + jobExecution.getId());
		} catch (Exception e) {
			// If there is an error starting the job, return the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Job failed to start. Error: " + e.getMessage());
		}
	}
	
	
	
	@GetMapping("/h")
	public String say() {
		System.out.println("say hello");
		return "hello";
	}

}
