package time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

import org.junit.Test;

public class OffsetTimeを変換する implements を変換する {

    private final OffsetTime offsetTime = OffsetTime.parse("16:08:27+09:00");

    @Override
    @Test
    public void Instantへ() {
        // OffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void ZonedDateTimeへ() {
        // OffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        LocalDate localDate = LocalDate.parse("2016-10-16");
        OffsetDateTime offsetDateTime = this.offsetTime.atDate(localDate);

        assertThat(offsetDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));
    }

    @Override
    @Test
    public void OffsetTimeへ() {
        // そのまま。
    }

    @Override
    @Test
    public void LocalDateTimeへ() {
        // OffsetDateTimeを経由する。
    }

    @Override
    @Test
    public void LocalDateへ() {
        // 変換できない。
    }

    @Override
    @Test
    public void LocalTimeへ() {
        LocalTime localTime = this.offsetTime.toLocalTime();

        assertThat(localTime, is(LocalTime.parse("16:08:27")));
    }
}
