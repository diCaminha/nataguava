package br.com.daboiud.nataguava.controllers;

import br.com.daboiud.nataguava.models.*;
import br.com.daboiud.nataguava.models.dtos.ResultCandidateJobDto;
import br.com.daboiud.nataguava.security.jwt.JwtTokenUtil;
import br.com.daboiud.nataguava.services.CandidateService;
import br.com.daboiud.nataguava.services.JobService;
import br.com.daboiud.nataguava.services.ResultCandidateJobService;
import br.com.daboiud.nataguava.services.UserCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/jobs")
@CrossOrigin(value = "*")
public class JobController {

    private JobService jobService;
    private UserCompanyService userCompanyService;
    private CandidateService candidateService;
    private ResultCandidateJobService resultCandidateJobService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    public JobController(JobService jobService,
                         UserCompanyService userCompanyService,
                         ResultCandidateJobService resultCandidateJobService,
                         CandidateService candidateService) {
        this.jobService = jobService;
        this.userCompanyService = userCompanyService;
        this.candidateService = candidateService;
        this.resultCandidateJobService = resultCandidateJobService;
    }

    @PostMapping
    public ResponseEntity<Job> save(@RequestBody Job job) {
        Job savedJob;
        try {
            savedJob = this.jobService.createOrUpdate(job);
            return ResponseEntity.ok(savedJob);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping
    public ResponseEntity<Job> update(@RequestBody Job job) {
        Job savedJob;
        try {
            savedJob = this.jobService.createOrUpdate(job);
            return ResponseEntity.ok(savedJob);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/{jobId}/add-candidate/{userId}")
    public ResponseEntity<Job> registerCandidateToJob(@PathVariable("jobId") Long jobId, @PathVariable("userId") Long userId) throws Throwable {
        try {
            Job job = this.candidateService.registerJob(userId, jobId);
            return ResponseEntity.ok(job);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{jobId}/close")
    public ResponseEntity<Job> close(HttpServletRequest httpServletRequest, @PathVariable("jobId") Long jobId) throws Exception {
        String authToken = httpServletRequest.getHeader("Authorization");
        String usernameFromToken = jwtUtil.getUsernameFromToken(authToken);
        Job jobToClose = this.jobService.findById(jobId).orElseThrow(Exception::new);
        if(isTheOwnerOfTheJob(usernameFromToken, jobToClose)) {
            jobToClose.setStatus(JobStatus.CLOSED);
            this.jobService.createOrUpdate(jobToClose);
            return ResponseEntity.ok(jobToClose);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/company/{userId}")
    public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable("userId") Long userId) {
        List<Job> jobs;
        try {
            UserCompany userCompany = this.userCompanyService.findByUserId(userId);
            jobs = this.jobService.findAllByCompanyId(userCompany.getId());
            return ResponseEntity.ok(jobs);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/home")
    public ResponseEntity<List<Job>> getJobsAvaliable() {
        List<Job> jobs;
        try {
            jobs = this.jobService.findAllActive();
            return ResponseEntity.ok(jobs);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Job>> getByFilter(@RequestParam("content") String content,
                                                 @RequestParam("place") String place) {
        List<Job> jobs;
        try {
            jobs = this.jobService.findAllByFilter(content, place);
            return ResponseEntity.ok(jobs);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Job> getById(@PathVariable("id") Long id) throws Throwable {
       return ResponseEntity.ok(this.jobService.findById(id).orElseThrow(Exception::new));
    }

    @GetMapping(value = "/{jobId}/results")
    public ResponseEntity<List<ResultCandidateJobDto>> getResults(@PathVariable("jobId") Long id) throws Exception {
        return ResponseEntity.ok(this.resultCandidateJobService.findByJobId(id));
    }

    @GetMapping(value = "/{jobId}/users/{userId}/cancel")
    public ResponseEntity<String> cancelJobForCandidate(@PathVariable("jobId") Long jobId,
                                                   @PathVariable("userId") Long userId) {

       try {
           Candidate candidate = this.candidateService.findByUserId(userId);
           this.jobService.cancelJobForCandidate(jobId, candidate.getId());

           return ResponseEntity.ok("canceled");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }

    private boolean isTheOwnerOfTheJob(String usernameFromToken, Job jobToClose) {
        return usernameFromToken.equals(jobToClose.getUserCompany().getUser().getEmail());
    }

}
