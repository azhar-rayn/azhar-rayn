package pk.org.rayn.zindagi.domain;

import java.util.Map;
import java.util.UUID;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record GroupedVerseObject(UUID id, Map<String, String> metadata, List<VerseGroupMember> verseGroupMembers) {

}
