package se.giron.moviecenter.core.controller;

import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.giron.moviecenter.model.resource.LogEntryResource;

@Api(tags = "Log", value = "Api for handling logging of messages.")
@RestController
@RequestMapping({"/log"})
public class LogController {

    private static final Logger LOG = LoggerFactory.getLogger(LogController.class);

    @PostMapping
    public ResponseEntity<Void> addLogEntry(@RequestBody LogEntryResource logEntry) {
        createLogEntry(logEntry, null);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/frontend")
    public ResponseEntity<Void> addFrontendLogEntry(@RequestBody LogEntryResource logEntry) {
        createLogEntry(logEntry, "FRONTEND");
        return ResponseEntity.noContent().build();
    }

    private void createLogEntry(LogEntryResource logEntry, final String prefix) {
        if (logEntry != null && logEntry.getLevel() != null) {
            switch (logEntry.getLevel()) {
                case ERROR:
                    LOG.error(createLogString(logEntry, prefix));
                    break;
                case WARN:
                    LOG.warn(createLogString(logEntry, prefix));
                    break;
                case DEBUG:
                    LOG.debug(createLogString(logEntry, prefix));
                    break;
                case INFO:
                default:
                    LOG.info(createLogString(logEntry, prefix));
            }
        }
    }

    private String createLogString(LogEntryResource logEntry, final String prefix) {
        if (logEntry == null) {
            return null;
        }
        StringBuilder strbLogMessage = new StringBuilder(prefix != null ? prefix + " - " : "");

        strbLogMessage.append("Message: ").append(logEntry.getMessage() != null ? logEntry.getMessage() : "");

        if (StringUtils.isNotEmpty(logEntry.getStackTrace())) {
            strbLogMessage.append(", StackTrace: ").append(logEntry.getStackTrace());
        }
        if (StringUtils.isNotEmpty(logEntry.getUserAgent())) {
            strbLogMessage.append(", UserAgent: ").append(logEntry.getUserAgent());
        }
        return strbLogMessage.toString();
    }
}
