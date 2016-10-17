package time2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

public class 組み合わせの時間オブジェクトを生成する {

    private LocalDate localDate;
    private LocalTime localTime;
    private ZoneOffset zoneOffset;
    private ZoneId zoneId;

    @Before
    public void 独立した時間オブジェクトを用意する() {
        this.localDate = LocalDate.of(2016, 10, 16);
        this.localTime = LocalTime.of(16, 8, 27, 0);
        this.zoneOffset = ZoneOffset.of("+09:00");
        this.zoneId = ZoneId.of("Asia/Tokyo");
    }

    @Test
    public void _2つの情報からLocalDateTimeを生成する() {
        // staticメソッドで生成する。
        LocalDateTime localDateTime = LocalDateTime.of(this.localDate, this.localTime);

        assertThat(localDateTime, is(LocalDateTime.parse("2016-10-16T16:08:27")));

        // インスタンスメソッドで生成する。
        LocalDateTime fromLocalDate = this.localDate.atTime(this.localTime);
        LocalDateTime fromLocalTime = this.localTime.atDate(this.localDate);

        assertThat(fromLocalDate, is(LocalDateTime.parse("2016-10-16T16:08:27")));
        assertThat(fromLocalTime, is(LocalDateTime.parse("2016-10-16T16:08:27")));

        // 午前0時のときはLocalTimeを用意しなくてもよい。
        LocalDateTime startOfDay = this.localDate.atStartOfDay();

        assertThat(startOfDay, is(LocalDateTime.parse("2016-10-16T00:00:00")));
    }

    @Test
    public void _2つの情報からOffsetTimeを生成する() {
        // staticメソッドで生成する。
        OffsetTime offsetTime = OffsetTime.of(this.localTime, this.zoneOffset);

        assertThat(offsetTime, is(OffsetTime.parse("16:08:27+09:00")));

        // インスタンスメソッドで生成する。
        // ZoneOffsetにOffsetTimeを生成するメソッドはない。
        OffsetTime fromLocalTime = this.localTime.atOffset(this.zoneOffset);

        assertThat(fromLocalTime, is(OffsetTime.parse("16:08:27+09:00")));
    }

    @Test
    public void _3つの情報からOffsetDateTimeを生成する() {
        // staticメソッドで生成する。
        OffsetDateTime offsetDateTime = OffsetDateTime.of(this.localDate, this.localTime, this.zoneOffset);

        assertThat(offsetDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));

        // LocalDateTimeを使うこともできる。
        LocalDateTime localDateTime = LocalDateTime.of(this.localDate, this.localTime);
        OffsetDateTime offsetDateTime2args = OffsetDateTime.of(localDateTime, this.zoneOffset);

        assertThat(offsetDateTime2args, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));

        // インスタンスメソッドで生成するときはLocalDateTimeを経由する。
        OffsetDateTime fromLocalDateTime = localDateTime.atOffset(this.zoneOffset);

        assertThat(fromLocalDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));
    }

    @Test
    public void _3つの情報からZonedDateTimeを生成する() {
        // staticメソッドで生成する。
        ZonedDateTime zonedDateTime = ZonedDateTime.of(this.localDate, this.localTime, this.zoneId);

        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // LocalDateTimeを使うこともできる。
        LocalDateTime localDateTime = LocalDateTime.of(this.localDate, this.localTime);
        ZonedDateTime zonedDateTime2args = ZonedDateTime.of(localDateTime, this.zoneId);

        assertThat(zonedDateTime2args, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // インスタンスメソッドで生成するときはLocalDateTimeを経由する。
        ZonedDateTime fromLocalDateTime = localDateTime.atZone(this.zoneId);

        assertThat(fromLocalDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // ZonedDateTimeはオブジェクト内部にZoneOffsetを持つが、上記のように、ZoneOffsetを渡さなくても生成できる。
        // ZoneIdとLocalDateTimeからZoneOffsetを計算できるため。
        ZoneOffset zoneOffset = this.zoneId.getRules().getOffset(localDateTime);

        assertThat(zoneOffset, is(ZoneOffset.of("+09:00")));
    }

    @Test
    public void _4つの情報からZonedDateTimeを生成する() {
        // 与えられたZoneOffsetを使い、OffsetDateTimeから生成することもできる。
        // TODO 日本だと違いがわからない。Europe/Parisにしよう。←別で書こう。
        OffsetDateTime offsetDateTime = OffsetDateTime.of(this.localDate, this.localTime, this.zoneOffset);
        ZonedDateTime atSameInstant = offsetDateTime.atZoneSameInstant(this.zoneId);
        ZonedDateTime atSimilarLocal = offsetDateTime.atZoneSimilarLocal(this.zoneId);

        assertThat(atSameInstant, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(atSimilarLocal, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // TODO LocalとStrictの違い。日本だと違いがわからない。Europe/Parisにしよう。
        // TODO 日本だと違いがわからない。Europe/Parisにしよう。←別で書こう。
        LocalDateTime localDateTime = LocalDateTime.of(this.localDate, this.localTime);
        ZonedDateTime ofLocal = ZonedDateTime.ofLocal(localDateTime, this.zoneId, this.zoneOffset);
        ZonedDateTime ofStrict = ZonedDateTime.ofStrict(localDateTime, this.zoneOffset, this.zoneId);

        assertThat(ofLocal, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(ofStrict, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
    }

}
