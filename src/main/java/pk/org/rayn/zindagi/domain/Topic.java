package pk.org.rayn.zindagi.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Topic(UUID id, String name, String area) {

}
