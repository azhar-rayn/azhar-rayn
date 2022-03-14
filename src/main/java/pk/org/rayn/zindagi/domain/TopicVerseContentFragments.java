package pk.org.rayn.zindagi.domain;

import java.util.Map;
import java.util.UUID;

public record TopicVerseContentFragments(UUID id, UUID scriptureId, Integer verseOrder, Map<String, String> metadata,
        UUID languageId, String content, String fragmentType, Integer fragmentBeginning, Integer fragmentEnding) {

}
