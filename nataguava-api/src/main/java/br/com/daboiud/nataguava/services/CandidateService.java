package br.com.daboiud.nataguava.services;

import br.com.daboiud.nataguava.models.Candidate;
import br.com.daboiud.nataguava.models.Job;
import br.com.daboiud.nataguava.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CandidateService {

    User createOrUpdate(Candidate candidate);
    List<Candidate> findAll();

    Candidate findByUserId(Long id);

    Job registerJob(Long userId, Long jobId) throws Exception;
}
