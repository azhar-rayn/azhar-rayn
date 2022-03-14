package pk.org.rayn.zindagi.domain;

import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public record PendingContent(UUID id, Map<String,Object> data, DashboardActions action, DashboardTypes type, String addedDate) {

}