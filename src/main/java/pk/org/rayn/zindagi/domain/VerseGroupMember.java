package pk.org.rayn.zindagi.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record VerseGroupMember(VerseObject vo, boolean primaryVerse) {

};
