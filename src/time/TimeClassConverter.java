package time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

public interface TimeClassConverter<T extends Temporal> {

    Instant toInstant(T time);

    ZonedDateTime toZonedDateTime(T time);

    OffsetDateTime toOffsetDateTime(T time);

    OffsetTime toOffsetTime(T time);

    LocalDateTime toLocalDateTime(T time);

    LocalDate toLocalDate(T time);

    LocalTime toLocalTime(T time);

}
