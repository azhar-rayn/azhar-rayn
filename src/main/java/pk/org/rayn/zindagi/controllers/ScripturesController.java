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
import pk.org.rayn.zindagi.domain.ScripturesObject;
import pk.org.rayn.zindagi.domain.VerseObject;
import pk.org.rayn.zindagi.services.ScripturesService;

@CrossOrigin
@RestController
@RequestMapping("/v1/scriptures")
public class ScripturesController {

	@Autowired
	ScripturesService scripturesService;

	@GetMapping
	public ResponseObject<Collection<ScripturesObject>> listScriptures() {
		return new ResponseObject<>(scripturesService.listScriptures());
	}

	@GetMapping("/{id}/verses")
	public ResponseObject<Collection<VerseObject>> getVerses(@PathVariable("id") UUID id,
			@RequestParam(required = false, defaultValue = "") List<UUID> languages,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer resultsPerPage) {
		return new ResponseObject<>(scripturesService.getVerses(id, languages, page, resultsPerPage));
	}
}
