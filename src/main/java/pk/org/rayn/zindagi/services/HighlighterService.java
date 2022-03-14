package pk.org.rayn.zindagi.services;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;
import pk.org.rayn.zindagi.domain.TopicVerseTemporaryFragments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import pk.org.rayn.zindagi.daos.HighlighterDao;

@Service
public class HighlighterService {
	@Autowired
	HighlighterDao highlighterDao;
	
	public int addFragmentPending(UUID languageId, UUID topicId, String type, int beginning, int ending,
			String highlightedBy, Map<String,Object> metadata) throws DataAccessException, JsonProcessingException {

		return highlighterDao.addFragmentPending(languageId, topicId, type, beginning, ending, highlightedBy, metadata);
	}	

	public int removeFragmentPending(Map<String,Object> metadata, UUID topicId) throws DataAccessException, JsonProcessingException {
		return highlighterDao.removeFragmentPending(metadata, topicId);
	}

	public int removeSingleFragmentPending(UUID id) {
		return highlighterDao.removeSingleFragmentPending(id);
	}

	public List<TopicVerseTemporaryFragments> getVerseFragments(Map<String,Object> metadata, UUID topicId, UUID languageId) throws DataAccessException, JsonProcessingException {
		return highlighterDao.getVerseFragments(metadata, topicId, languageId);
	}

	public List<TopicVerseTemporaryFragments> getVerseFragmentsByTopic(UUID topicId, UUID languageId, boolean approved) {
		return highlighterDao.getVerseFragmentsByTopic(topicId, languageId, approved);
	}

	public int approveHighlight(UUID id) {
		return highlighterDao.approveHighlight(id);
	}


}
