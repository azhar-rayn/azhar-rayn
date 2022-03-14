package pk.org.rayn.zindagi.controllers;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import pk.org.rayn.zindagi.domain.TopicVerseTemporaryFragments;
import pk.org.rayn.zindagi.domain.ResponseObject;
import pk.org.rayn.zindagi.services.HighlighterService;

@CrossOrigin
@RestController
@RequestMapping("/v1/highlights")
public class HighlightsController {
        @Autowired
        HighlighterService highlighterService;

//        @PostMapping
//        public ResponseObject<Integer> insertFragmentPending(
//                        @RequestParam(required = true, defaultValue = "") UUID languageId,
//                        @RequestParam(required = true, defaultValue = "") UUID topicId,
//                        @RequestParam(required = true, defaultValue = "") String type,
//                        @RequestParam(required = true, defaultValue = "") int beginning,
//                        @RequestParam(required = true, defaultValue = "") int ending,
//                        @RequestParam(required = true, defaultValue = "") String highlightedBy,
//                        @RequestBody(required = false)Map<String,Object> metadata) throws DataAccessException, JsonProcessingException {
//
//                return new ResponseObject<>(highlighterService.addFragmentPending(languageId, topicId, type,
//                                beginning, ending, highlightedBy, metadata));
//        }
//
//        @DeleteMapping("/remove-highlight")
//        public ResponseObject<Integer> removeFragment(
//                        @RequestBody(required = false) Map<String,Object> metadata,
//                        @RequestParam(required = false, defaultValue = "") UUID topicId)  throws DataAccessException, JsonProcessingException{
//                return new ResponseObject<>(highlighterService.removeFragmentPending(metadata, topicId));
//        }
//
//        @DeleteMapping("/remove-single-highlight")
//        public ResponseObject<Integer> removeFragment(@RequestParam(required = false, defaultValue = "") UUID id) {
//                return new ResponseObject<>(highlighterService.removeSingleFragmentPending(id));
//        }

        @GetMapping("/verse-fragments")
        public ResponseObject<List<TopicVerseTemporaryFragments>> getVerseFragments(
                        @RequestBody(required = false)Map<String,Object> metadata,
                        @RequestParam(required = false, defaultValue = "") UUID topicId,
                        @RequestParam(required = false, defaultValue = "") UUID languageId) throws DataAccessException, JsonProcessingException {
                return new ResponseObject<>(highlighterService.getVerseFragments(metadata, topicId, languageId));
        }

//        @GetMapping("/verse-fragments-topics")
//        public ResponseObject<List<TopicVerseTemporaryFragments>> getVerseFragmentsByTopic(
//                        @RequestParam(required = false, defaultValue = "") UUID topicId,
//                        @RequestParam(required = false, defaultValue = "") UUID languageId,
//                        @RequestParam(required = false, defaultValue = "") boolean approved) {
//                return new ResponseObject<>(highlighterService.getVerseFragmentsByTopic(topicId, languageId, approved));
//        }

//        @PutMapping("/approve-highlight")
//        public ResponseObject<Integer> approveHighlight(@RequestParam(required = false, defaultValue = "") UUID id) {
//                return new ResponseObject<>(highlighterService.approveHighlight(id));
//        }

}
