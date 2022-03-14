package pk.org.rayn.zindagi.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebInputException;

import pk.org.rayn.zindagi.domain.ResponseObject;
import pk.org.rayn.zindagi.services.FileHandlingService;

@CrossOrigin
@RestController
@RequestMapping("/v1/upload")
public class UploadController {
	private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	FileHandlingService fileHandlingService;

	@PostMapping("/topicverses/{id}")
	public ResponseObject<String> uploadTopicVersesFile(@PathVariable("id") UUID scriptureId,
			@RequestBody() MultipartFile file) {
		try {
			fileHandlingService.topicVerses(scriptureId, file);

			return new ResponseObject<>("Success");
		} catch (Exception e) {
			LOG.warn("Couldn't parse topic verses file", e);
			throw new ServerWebInputException(e.getMessage());
		}
	}

	@PostMapping("/relatedtopics")
	public ResponseObject<String> uploadRelatedTopicsFile(@RequestParam("file") MultipartFile file) {
		try {
			fileHandlingService.relatedTopics(file);

			return new ResponseObject<>("Success");
		} catch (Exception e) {
			LOG.warn("Couldn't parse related topics file", e);
			throw new ServerWebInputException(e.getMessage());
		}
	}
}
