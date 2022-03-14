package pk.org.rayn.zindagi.services;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

@Service
public class VersionService {
	private static final Logger LOG = LoggerFactory.getLogger(VersionService.class);
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneId.of("UTC"));

	private final Properties build;

	private Instant localBuildDate;
	private String localBuildRevision;

	public VersionService() throws IOException {
		build = PropertiesLoaderUtils.loadAllProperties("build.properties");
	}

	public Instant getLocalBuildDate() {
        if (localBuildDate == null) {
            try {
                String date = build.getProperty("timestamp");
                localBuildDate = DATE_FORMAT.parse(date, Instant::from);
            } catch (Exception x) {
                LOG.warn("Failed to resolve build date.", x);
            }
        }
        return localBuildDate;
    }

    public String getLocalBuildRevision() {
        if (localBuildRevision == null) {
            try {
                localBuildRevision = build.getProperty("revision");
            } catch (Exception x) {
                LOG.warn("Failed to resolve build revision.", x);
            }
        }
        return localBuildRevision;
    }

}
