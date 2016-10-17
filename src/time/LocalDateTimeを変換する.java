
package time;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Test;

public class LocalDateTimeを変換する implements を変換する {

    private final LocalDateTime localDateTime = LocalDateTime.parse("2016-10-16T16:08:27");

    @Override
    @Test
    public void Instantへ() {
        // ZonedDateTimeかOffsetDateTimeを経由する必要があるように思えるが、ZoneOffsetを渡せば直接Instantに変換できる（ZoneIdではだめ）。
        ZoneOffset zoneOffset = ZoneOffset.of("+09:00");
        Instant instant = this.localDateTime.toInstant(zoneOffset);

        assertThat(instant, is(Instant.ofEpochSecond(1476601707, 0)));
    }

    @Override
    @Test
    public void ZonedDateTimeへ() {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime zonedDateTime = this.localDateTime.atZone(zoneId);

        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
    }

    @Test
    public void ZonedDateTimeへ_ギャップ() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        LocalDateTime localDateTime = LocalDateTime.parse("2016-03-27T02:15:00");
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-03-27T03:15:00+02:00[Europe/Paris]")));
        assertThat(zonedDateTime.getOffset(), is(ZoneOffset.of("+02:00")));
    }

    @Test
    public void ZonedDateTimeへ_重複() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        LocalDateTime localDateTime = LocalDateTime.parse("2016-10-30T02:15:00");
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-10-30T02:15:00+02:00[Europe/Paris]")));
        assertThat(zonedDateTime.getOffset(), is(ZoneOffset.of("+02:00")));
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        ZoneOffset zoneOffset = ZoneOffset.of("+09:00");
        OffsetDateTime offsetDateTime = this.localDateTime.atOffset(zoneOffset);

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
        // そのまま。
    }

    @Override
    @Test
    public void LocalDateへ() {
        LocalDate localDate = this.localDateTime.toLocalDate();

        assertThat(localDate, is(LocalDate.parse("2016-10-16")));
    }

    @Override
    @Test
    public void LocalTimeへ() {
        LocalTime localTime = this.localDateTime.toLocalTime();

        assertThat(localTime, is(LocalTime.parse("16:08:27")));
    }
}
