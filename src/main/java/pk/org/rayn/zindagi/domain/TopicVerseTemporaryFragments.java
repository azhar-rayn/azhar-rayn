package pk.org.rayn.zindagi.domain;

import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public record TopicVerseTemporaryFragments(UUID id, UUID languageId, UUID TopicId, String type,
                Integer fragmentBeginning, Integer fragmentEnding, String highlightedBy, String addedDate,
                Boolean approved, Map<String, Object> metadata) {
}
