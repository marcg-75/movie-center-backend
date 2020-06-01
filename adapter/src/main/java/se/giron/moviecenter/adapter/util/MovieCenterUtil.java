package se.giron.moviecenter.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;

public class MovieCenterUtil {

	private static final DateFormat df = new SimpleDateFormat("yyMMdd_HHmmssSSS");

    private static final String X_CHAR="x";
    private static final String DASH_CHAR="-";
    private static final String UNDERSCORE_CHAR="_";

    public static final String IMPORT_FILE_SCHEMA_VALIDATION_FAILED_WITH_SCHEMA_VERSION = "Import file schema validation failed with schema version ";
    public static final String ERROR_NO_REQUEST = "Receival request was empty!";
    public static final String ERROR_NO_MESSAGE_NUMBER = "Message serial number missing!";
    public static final String ERROR_NO_IMPORT_FILE_CONTENTS = "The contents of the import file attachment were empty!";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(MovieCenterUtil.class);
    
    /**
	 * Returns a list of files with absolute path.
	 * @param filePath
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	public static List<Path> listFiles(String filePath, String pattern) throws IOException {
		final Pattern regex = Pattern.compile(pattern);
		ArrayList<Path> fileList = new ArrayList<>();
		Path dir = FileSystems.getDefault().getPath(filePath);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, entry -> regex.matcher(entry.getFileName().toString()).matches())) {
			for (Path path : stream) {
			    fileList.add(path.toAbsolutePath());
			}			
		}
		return fileList;
	}

	public static void moveFile(@NotNull Path fileToMove, @NotNull Path targetDirectory) throws IOException {
	    if (!fileToMove.toFile().exists()) {
	        LOG.error("File to move ({}) doesn't exist!", fileToMove);
	        return;
        }
	    if (!targetDirectory.toFile().exists()) {
            Files.createDirectories(targetDirectory);
        }
        Files.move(fileToMove, targetDirectory.resolve(fileToMove.getFileName()), ATOMIC_MOVE);
    }

	public static Function<XMLGregorianCalendar, Date> toDate = xmlDate -> xmlDate.toGregorianCalendar().getTime();

}
