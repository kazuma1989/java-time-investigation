
package time;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.Test;

public class Instantを変換する implements を変換する {

    private final Instant instant = Instant.ofEpochSecond((long) 1476601707, (long) 0);

    @Override
    @Test
    public void Instantへ() {
        // そのまま。
    }

    @Override
    @Test
    public void ZonedDateTimeへ() {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime zonedDateTime = this.instant.atZone(zoneId);

        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        ZoneOffset zoneOffset = ZoneOffset.of("+09:00");
        OffsetDateTime offsetDateTime = this.instant.atOffset(zoneOffset);

        assertThat(offsetDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));
    }

    @Override
    @Test
    public void OffsetTimeへ() {
        // OffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void LocalDateTimeへ() {
        // ZonedDateTimeまたはOffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void LocalDateへ() {
        // ZonedDateTimeまたはOffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void LocalTimeへ() {
        // ZonedDateTimeまたはOffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void Dateへ() {
        Date date = Date.from(instant);

        assertThat(date, is(new Date((long) 1476601707 * 1000)));
    }
}
