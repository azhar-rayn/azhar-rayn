package pk.org.rayn.zindagi.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ScripturesObject(UUID id, String name, String author, Instant publishDate, List<Language> languages) {

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	public record Language(UUID id, String name, String language, String translator, boolean nativeLanguage) {
		
	}
}
