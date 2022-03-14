package pk.org.rayn.zindagi.domain;

import java.util.Map;
import java.util.UUID;

public record TopicGroupedVerseContentFragments(TopicVerseContentFragments tvcf, UUID verseGroupId, Boolean isPrimary,
        Map<String, String> groupMetadata) {

}
