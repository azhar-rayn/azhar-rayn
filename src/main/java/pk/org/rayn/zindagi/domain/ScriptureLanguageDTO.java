package pk.org.rayn.zindagi.domain;

import java.time.Instant;
import java.util.UUID;

public record ScriptureLanguageDTO(UUID scriptureId, String scriptureName, String author, Instant publishDate, UUID languageId, boolean nativeLanguage, String languageName, String language, String translator) {

}
