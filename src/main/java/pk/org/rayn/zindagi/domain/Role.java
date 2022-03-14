package pk.org.rayn.zindagi.domain;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.jdbc.core.RowMapper;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Role(UUID id, String name, String description) {

}
