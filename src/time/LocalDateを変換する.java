
package time;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Test;

public class LocalDateを変換する implements を変換する {

    private final LocalDate localDate = LocalDate.parse("2016-10-16");

    @Override
    @Test
    public void Instantへ() {
        // ZonedDateTimeまたはOffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void ZonedDateTimeへ() {
        // 午前0時へ変換するときはZoneIdを渡すだけでよい。
        // 時刻を指定したい場合は、一度LocalDateTimeへ変換する必要がある。
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime zonedDateTime = this.localDate.atStartOfDay(zoneId);

        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-10-16T00:00:00+09:00[Asia/Tokyo]")));

        // ZoneOffsetはZoneIdの子クラスなので、atStartOfDay()に渡して変換することも可能。
        // ただし、Asia/Tokyoのような情報を持たない形式となることに注意。
        ZoneOffset zoneOffset = ZoneOffset.of("+09:00");
        ZonedDateTime notOffsetDateTime = this.localDate.atStartOfDay(zoneOffset);

        assertThat(notOffsetDateTime, is(not(instanceOf(OffsetDateTime.class))));
        assertThat(notOffsetDateTime, is(ZonedDateTime.parse("2016-10-16T00:00:00+09:00")));
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        OffsetTime offsetTime = OffsetTime.parse("16:08:27+09:00");
        OffsetDateTime offsetDateTime = this.localDate.atTime(offsetTime);

        assertThat(offsetDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));
    }

    @Override
    @Test
    public void OffsetTimeへ() {
        // 変換できない。
    }

    @Override
    @Test
    public void LocalDateTimeへ() {
        LocalTime localTime = LocalTime.parse("16:08:27");
        LocalDateTime localDateTime = this.localDate.atTime(localTime);

        assertThat(localDateTime, is(LocalDateTime.parse("2016-10-16T16:08:27")));

        // 午前0時へ変換するときは追加の情報が不要。
        LocalDateTime startOfDay = this.localDate.atStartOfDay();
        assertThat(startOfDay, is(LocalDateTime.parse("2016-10-16T00:00:00")));
    }

    @Override
    @Test
    public void LocalDateへ() {
        // そのまま
    }

    @Override
    @Test
    public void LocalTimeへ() {
        // 変換できない
    }
}
