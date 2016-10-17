package time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.Test;

public class LocalTimeを変換する implements を変換する {

    private final LocalTime localTime = LocalTime.parse("16:08:27");

    @Override
    @Test
    public void Instantへ() {
        // ZonedDateTimeまたはOffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void ZonedDateTimeへ() {
        // LocalDateTimeを経由する。
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        // LocalDateTimeまたはOffsetTimeを経由する。
    }

    @Override
    @Test
    public void OffsetTimeへ() {
        ZoneOffset zoneOffset = ZoneOffset.of("+09:00");
        OffsetTime offsetTime = this.localTime.atOffset(zoneOffset);

        assertThat(offsetTime, is(OffsetTime.parse("16:08:27+09:00")));
    }

    @Override
    @Test
    public void LocalDateTimeへ() {
        LocalDate localDate = LocalDate.parse("2016-10-16");
        LocalDateTime localDateTime = this.localTime.atDate(localDate);

        assertThat(localDateTime, is(LocalDateTime.parse("2016-10-16T16:08:27")));
    }

    @Override
    @Test
    public void LocalDateへ() {
        // 変換できない。
    }

    @Override
    @Test
    public void LocalTimeへ() {
        // そのまま。
    }
}
