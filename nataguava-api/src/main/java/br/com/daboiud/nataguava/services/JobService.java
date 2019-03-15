package br.com.daboiud.nataguava.services;

import br.com.daboiud.nataguava.models.Job;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface JobService {

    Job createOrUpdate(Job job);
    List<Job> findAll();
    Optional findById(Long id);
    List<Job> findAllByCompanyId(Long companyId);
    List<Job> findAllByRegistedCandidate(Long candidateId);
}
