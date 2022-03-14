package pk.org.rayn.zindagi.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record VerseObject(UUID id, UUID scriptureId, Integer verseOrder, Map<String, String> metadata,
    Map<UUID, VerseContent> languages) {

  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  public record VerseContent(String content, List<Fragment> fragments) {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public record Fragment(String type, Integer starting, Integer ending) {
    }

  }

}
