package pk.org.rayn.zindagi.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record UserRole(UUID id, UUID user_id, UUID role_id) {

}
