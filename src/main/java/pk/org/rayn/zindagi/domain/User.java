package pk.org.rayn.zindagi.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public record User(UUID id, String fullName, String userName, String password) {
}

