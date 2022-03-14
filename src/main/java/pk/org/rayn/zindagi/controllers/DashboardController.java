package pk.org.rayn.zindagi.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pk.org.rayn.zindagi.domain.*;
import pk.org.rayn.zindagi.services.DashboardService;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true" )
@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {    
    @Autowired
    DashboardService dashboardService;    

    @PreAuthorize("hasRole('User') || hasRole('Admin') || hasRole('Approver')")
    @PostMapping
    public ResponseObject<Integer> insertPending(
        @RequestBody(required = true) Map<String,Object> data,
        @RequestParam(required = true,defaultValue="") DashboardActions action,
        @RequestParam(required = true,defaultValue="") DashboardTypes type
    ) throws DataAccessException, JsonProcessingException{
        return new ResponseObject<>(dashboardService.insertPending(data, action, type));
    }

    @PreAuthorize("hasRole('User') || hasRole('Admin') || hasRole('Approver')")
    @GetMapping("/pending/highlights")
    public List<PendingContent>  getPendingHighlightsVerse(
            @RequestParam(required = true) String verseId,
            @RequestParam(required = true) String topicId,
            @RequestParam(required = true) String languageId
    ) {
        return dashboardService.getPendingHighlightsVerse(verseId,languageId,topicId);
    }

    @PreAuthorize("hasRole('User') || hasRole('Admin') || hasRole('Approver')")
    @GetMapping("/highlights")
    public List<TopicVerseFragments>  getHighlightsVerse(
            @RequestParam(required = true) String verseId,
            @RequestParam(required = true) String topicId,
            @RequestParam(required = true) String languageId
    ) {
        return dashboardService.getHighlightsVerse(verseId,languageId,topicId);
    }

        @PreAuthorize("hasRole('User') || hasRole('Admin') || hasRole('Approver')")
    @GetMapping
    public ResponseObject<List<PendingContent>> getPendingContent(
        @RequestParam(required = false) DashboardActions action,
        @RequestParam(required=false) String status,
        @RequestParam(required=true) DashboardTypes type
    ) throws JsonProcessingException{
        return new ResponseObject<>(dashboardService.getPendingContent(action,type,status));
    }

    @PreAuthorize("hasRole('Admin') || hasRole('Approver')")
    @PutMapping()
    public ResponseObject<Integer> approvePendingContent (
        @RequestParam(required = true) UUID id
        ) throws DataAccessException, JsonProcessingException {
        return new ResponseObject<>(dashboardService.approvePendingContent(id));
    }


    @PreAuthorize("hasRole('Admin') || hasRole('Approver')")
    @DeleteMapping()
    public ResponseObject<Integer> rejectPendingContent(
            @RequestParam(required = true) UUID id
    ) {
        return new ResponseObject<>(dashboardService.rejectPendingContent(id));
    }
}
