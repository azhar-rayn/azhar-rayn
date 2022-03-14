package pk.org.rayn.zindagi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import pk.org.rayn.zindagi.domain.ResponseObject;
import pk.org.rayn.zindagi.services.VersionService;

@CrossOrigin
@RestController
@RequestMapping("/v1/ping")
public class PingController {
	@Autowired
	VersionService versionService;

	@GetMapping
	public ResponseObject<Object> ping() {
		return new ResponseObject<Object>(ImmutableMap.of("revision", versionService.getLocalBuildRevision(), "date", versionService.getLocalBuildDate()));
	}
}
