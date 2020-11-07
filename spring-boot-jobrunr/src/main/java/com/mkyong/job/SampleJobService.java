package com.mkyong.job;

import org.jobrunr.jobs.annotations.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleJobService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Job(name = "The sample job without variable")
    public void execute() {
        execute("Hello world!");
    }

    @Job(name = "The sample job with variable %0")
    public void execute(String input) {
        logger.info("The sample job has begun. The variable you passed is {}", input);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error("Error while executing sample job", e);
        } finally {
            logger.info("Sample job has finished...");
        }
    }

}