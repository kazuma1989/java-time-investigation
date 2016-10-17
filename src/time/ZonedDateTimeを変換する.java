package time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import org.junit.Test;

public class ZonedDateTimeを変換する implements を変換する {

    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]");

    @Override
    @Test
    public void Instantへ() {
        Instant instant = this.zonedDateTime.toInstant();

        assertThat(instant, is(Instant.ofEpochSecond(1476601707, 0)));
    }

    @Override
    @Test
    public void ZonedDateTimeへ() {
        // そのまま。
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        OffsetDateTime offsetDateTime = this.zonedDateTime.toOffsetDateTime();

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
        LocalDateTime localDateTime = this.zonedDateTime.toLocalDateTime();

        assertThat(localDateTime, is(LocalDateTime.parse("2016-10-16T16:08:27")));
    }

    @Override
    @Test
    public void LocalDateへ() {
        LocalDate localDate = this.zonedDateTime.toLocalDate();

        assertThat(localDate, is(LocalDate.parse("2016-10-16")));
    }

    @Override
    @Test
    public void LocalTimeへ() {
        LocalTime localTime = this.zonedDateTime.toLocalTime();

        assertThat(localTime, is(LocalTime.parse("16:08:27")));
    }
}
