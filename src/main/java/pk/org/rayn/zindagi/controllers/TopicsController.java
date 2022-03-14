package pk.org.rayn.zindagi.controllers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pk.org.rayn.zindagi.domain.ResponseObject;
import pk.org.rayn.zindagi.domain.Topic;
import pk.org.rayn.zindagi.domain.VerseObject;
import pk.org.rayn.zindagi.domain.GroupedVerseObject;
import pk.org.rayn.zindagi.services.TopicsService;

@CrossOrigin
@RestController
@RequestMapping("/v1/topics")
public class TopicsController {
	@Autowired
	TopicsService topicsService;
	@GetMapping
	public ResponseObject<List<Topic>> listTopics(@RequestParam(required = false) UUID scriptureId) {
		return new ResponseObject<>(topicsService.listTopics(scriptureId));
	}

	@GetMapping("/{id}/relatedtopics")
	public ResponseObject<List<Topic>> relatedTopics(@PathVariable("id") UUID id) {
		return new ResponseObject<>(topicsService.getRelatedTopics(id));
	}

	@GetMapping("/{id}/versegroups")
	public ResponseObject<Collection<GroupedVerseObject>> getVerseGroups(@PathVariable("id") UUID id,
			@RequestParam(required = false, defaultValue = "") List<UUID> languages,
			@RequestParam(required = false, defaultValue = "") List<UUID> scriptures) {
		return new ResponseObject<>(topicsService.getVerseGroups(id, languages, scriptures));
	}

	@GetMapping("/{id}/verses")
	public ResponseObject<Collection<VerseObject>> getVerses(@PathVariable("id") UUID id,
			@RequestParam(required = false, defaultValue = "") List<UUID> languages) {
		return new ResponseObject<>(topicsService.getVerses(id, languages));
	}
}
