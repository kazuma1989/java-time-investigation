
package time;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import org.junit.Test;

public class java_timeパッケージの使い方 {

    @Test
    public void 色々試す() {

    }

    @Test
    public void ZonedDateTimeからInstantへ変換する() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]");
        Instant instant = zonedDateTime.toInstant();

        assertThat(instant, is(Instant.ofEpochSecond(1476601707, 0)));
    }

    @Test
    public void OffsetDateTimeからInstantへ変換する() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2016-10-16T16:08:27+09:00");
        Instant instant = offsetDateTime.toInstant();

        assertThat(instant, is(Instant.ofEpochSecond(1476601707, 0)));
    }

    @Test
    public void LocalDateとOffsetTimeからOffsetDateTimeを計算する() {
        LocalDate localDate = LocalDate.parse("2016-10-16");
        OffsetTime offsetTime = OffsetTime.parse("16:08:27+09:00");
        OffsetDateTime offsetDateTime = offsetTime.atDate(localDate);

        assertThat(offsetDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));
    }

    @Test
    public void LocalDateとLocalTimeからLocalDateTimeを計算する() {
        LocalDate localDate = LocalDate.parse("2016-10-16");
        LocalTime localTime = LocalTime.parse("16:08:27");
        LocalDateTime localDateTime = localTime.atDate(localDate);

        assertThat(localDateTime, is(LocalDateTime.parse("2016-10-16T16:08:27+09:00")));
    }
}
