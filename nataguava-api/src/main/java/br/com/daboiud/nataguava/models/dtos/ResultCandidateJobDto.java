package br.com.daboiud.nataguava.models.dtos;

import br.com.daboiud.nataguava.models.Candidate;
import br.com.daboiud.nataguava.models.Job;
import br.com.daboiud.nataguava.models.ResultCandidateJob;
import lombok.Data;

@Data
public class ResultCandidateJobDto {

    private Long id;
    private Long jobId;
    private Long candidateId;
    private int result;

    public ResultCandidateJob toObject() {
        ResultCandidateJob resultCandidateJob = new ResultCandidateJob();
        Job job = new Job();
        job.setId(this.id);
        Candidate candidate = new Candidate();
        candidate.setId(this.candidateId);

        resultCandidateJob.setJob(job);
        resultCandidateJob.setCandidate(candidate);
        resultCandidateJob.setResult(this.result);

        return resultCandidateJob;
    }
}