package pk.org.rayn.zindagi.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Map;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public record TopicVerseFragments(UUID id, UUID languageId, UUID TopicId, String type,UUID verseId,Integer beginning, Integer ending) {
}
