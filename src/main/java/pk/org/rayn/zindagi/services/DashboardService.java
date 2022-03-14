package pk.org.rayn.zindagi.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import pk.org.rayn.zindagi.daos.DashboardDao;
import pk.org.rayn.zindagi.domain.DashboardActions;
import pk.org.rayn.zindagi.domain.DashboardTypes;
import pk.org.rayn.zindagi.domain.PendingContent;
import pk.org.rayn.zindagi.domain.TopicVerseFragments;

@Service
public class DashboardService {
    @Autowired
    DashboardDao dashboardDao;

    public int insertPending(Map<String, Object> data, DashboardActions action, DashboardTypes type) throws DataAccessException, JsonProcessingException {
        return dashboardDao.insertPending(data, action, type);
    }
    
    public List<PendingContent> getPendingContent(DashboardActions action, DashboardTypes type, String status){
        return dashboardDao.getPendingContent(action,type, status);
    }

    public List<PendingContent>  getPendingHighlightsVerse(String verseId,String languageId,String topicId) {
        return dashboardDao.getPendingHighlightsVerse(verseId,languageId,topicId);
    }

    public List<TopicVerseFragments>  getHighlightsVerse(String verseId, String languageId, String topicId) {
        return dashboardDao.getHighlightsVerse(verseId,languageId,topicId);
    }

    public int approvePendingContent(UUID id) throws DataAccessException, JsonProcessingException{
        return dashboardDao.approvePendingContent(id);
    }

    public int rejectPendingContent(UUID id){
        return dashboardDao.rejectPendingContent(id);
    }
}
