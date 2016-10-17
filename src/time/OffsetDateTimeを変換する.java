package time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

public class OffsetDateTimeを変換する implements を変換する {

    private final OffsetDateTime offsetDateTime = OffsetDateTime.parse("2016-10-16T16:08:27+09:00");

    @Override
    @Test
    public void Instantへ() {
        Instant instant = this.offsetDateTime.toInstant();

        assertThat(instant, is(Instant.ofEpochSecond(1476601707, 0)));
    }

    @Override
    @Test
    public void ZonedDateTimeへ() {
        // toZonedDateTime()を呼び出すだけでは、Asia/Tokyoという情報を持ったZoneDateTimeへ変換できない。
        ZonedDateTime zonedDateTime = this.offsetDateTime.toZonedDateTime();

        assertThat(zonedDateTime, is(not(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]"))));
        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00")));

        // 不足しているZoneIdの情報を渡す必要がある。
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime atSameInstant = this.offsetDateTime.atZoneSameInstant(zoneId);
        ZonedDateTime atSimilarLocal = this.offsetDateTime.atZoneSimilarLocal(zoneId);

        assertThat(atSameInstant, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(atSimilarLocal, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(atSameInstant, is(atSimilarLocal));

        // ZoneIdの渡し方は、Instantを保ったまま変換するか、LocalDateTimeを保ったまま変換するかの2通りある。
        // OffsetDateTimeがもともと持つZoneOffsetと、ZoneIdから計算されるZoneOffsetが異なるとき、違いが生じる。
        ZoneId zoneIdParis = ZoneId.of("Europe/Paris");
        ZonedDateTime atSameInstantParis = this.offsetDateTime.atZoneSameInstant(zoneIdParis);
        ZonedDateTime atSimilarLocalParis = this.offsetDateTime.atZoneSimilarLocal(zoneIdParis);

        // Instantを保とうとするとLocalDateTimeの値が変化する。
        assertThat(atSameInstantParis, is(ZonedDateTime.parse("2016-10-16T09:08:27+02:00[Europe/Paris]")));
        assertThat(atSameInstantParis.toInstant(), is(this.offsetDateTime.toInstant()));
        assertThat(atSameInstantParis.toLocalDateTime(), is(not(this.offsetDateTime.toLocalDateTime())));

        // LocalDateTimeを保とうとすると、ZoneOffset部分が無視されるのでInstantの値が変化する。
        assertThat(atSimilarLocalParis, is(ZonedDateTime.parse("2016-10-16T16:08:27+02:00[Europe/Paris]")));
        assertThat(atSimilarLocalParis.toInstant(), is(not(this.offsetDateTime.toInstant())));
        assertThat(atSimilarLocalParis.toLocalDateTime(), is(this.offsetDateTime.toLocalDateTime()));

        // LocalDateTimeを保つメソッド名が"Similar"となっているのは、同じLocalDateTimeにならない場合があるから。
        // 夏時間が開始したあとの1時間でこの現象が起きる。
        OffsetDateTime toBeWhenDstStarts = OffsetDateTime.parse("2016-03-27T02:15:00+09:00");
        ZonedDateTime whenDstStarts = toBeWhenDstStarts.atZoneSimilarLocal(zoneIdParis);

        // Europe/Parisでは2016-03-27T02:00:00に夏時間が開始したため、2時台が存在しない（01:59:59の1秒後が03:00:00）。
        // そのため02:15:00+01:00が03:15:00+02:00に変換されている。
        assertThat(whenDstStarts, is(ZonedDateTime.parse("2016-03-27T03:15:00+02:00[Europe/Paris]")));
        assertThat(whenDstStarts.toLocalDateTime(), is(not(toBeWhenDstStarts.toLocalDateTime())));
        //
        // OffsetDateTime toBeWhenDstEnds =
        // OffsetDateTime.parse("2016-10-30T03:15:00+02:00");
        // ZonedDateTime whenDstEnds =
        // toBeWhenDstEnds.atZoneSameInstant(zoneIdParis);
        // assertThat(whenDstEnds,
        // is(ZonedDateTime.parse("2016-10-30T02:15:00+01:00[Europe/Paris]")));
        // assertThat(whenDstEnds.toLocalDateTime(),
        // is(not(toBeWhenDstEnds.toLocalDateTime())));
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        // そのまま。
    }

    @Override
    @Test
    public void OffsetTimeへ() {
        OffsetTime offsetTime = this.offsetDateTime.toOffsetTime();

        assertThat(offsetTime, is(OffsetTime.parse("16:08:27+09:00")));
    }

    @Override
    @Test
    public void LocalDateTimeへ() {
        LocalDateTime localDateTime = this.offsetDateTime.toLocalDateTime();

        assertThat(localDateTime, is(LocalDateTime.parse("2016-10-16T16:08:27")));
    }

    @Override
    @Test
    public void LocalDateへ() {
        LocalDate localDate = this.offsetDateTime.toLocalDate();

        assertThat(localDate, is(LocalDate.parse("2016-10-16")));
    }

    @Override
    @Test
    public void LocalTimeへ() {
        LocalTime localTime = this.offsetDateTime.toLocalTime();

        assertThat(localTime, is(LocalTime.parse("16:08:27")));
    }
}
