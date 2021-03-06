package br.com.daboiud.nataguava.services.impl;

import br.com.daboiud.nataguava.models.ContentTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daboiud.nataguava.models.Question;
import br.com.daboiud.nataguava.repositories.QuestionRepository;
import br.com.daboiud.nataguava.services.QuestionService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
    private QuestionRepository questionRepository;

	@Override
	public Question save(Question question) {
		return questionRepository.save(question);
	}

	@Override
	@Transactional
	public List<Question> findByContentTag(ContentTag contentTag) {
		return this.questionRepository.findByTag(contentTag);
	}
}
