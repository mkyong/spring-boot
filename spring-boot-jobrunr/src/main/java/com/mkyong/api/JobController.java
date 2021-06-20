package com.mkyong.api;

import com.mkyong.job.SampleJobService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
public class JobController {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private SampleJobService sampleJobService;

    @GetMapping("/run-job")
    public String runJob(
            @RequestParam(value = "name", defaultValue = "Hello World") String name) {

        jobScheduler.enqueue(() -> sampleJobService.execute(name));
        return "Job is enqueued.";

    }

    @GetMapping("/schedule-job")
    public String scheduleJob(
            @RequestParam(value = "name", defaultValue = "Hello World") String name,
            @RequestParam(value = "when", defaultValue = "PT3H") String when) {

        // old API, job first followed by time
        /*
            jobScheduler.schedule(() -> sampleJobService.execute(name),
                Instant.now().plus(Duration.parse(when)));
        */

        // new API, time first followed by job
        jobScheduler.schedule(
                Instant.now().plus(Duration.parse(when)),
                () -> sampleJobService.execute(name)
        );

        return "Job is scheduled.";
    }

}
