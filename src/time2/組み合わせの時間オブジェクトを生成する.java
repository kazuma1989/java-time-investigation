
package time2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.DateTimeException;
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

    private ZoneOffset plus0900;

    private ZoneId asiaTokyo;

    @Before
    public void 独立した時間オブジェクトを用意する() {
        this.localDate = LocalDate.of(2016, 10, 16);
        this.localTime = LocalTime.of(16, 8, 27, 0);
        this.plus0900 = ZoneOffset.of("+09:00");
        this.asiaTokyo = ZoneId.of("Asia/Tokyo");
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
        OffsetTime offsetTime = OffsetTime.of(this.localTime, this.plus0900);

        assertThat(offsetTime, is(OffsetTime.parse("16:08:27+09:00")));

        // インスタンスメソッドで生成する。
        // ZoneOffsetにOffsetTimeを生成するメソッドはない。
        OffsetTime fromLocalTime = this.localTime.atOffset(this.plus0900);

        assertThat(fromLocalTime, is(OffsetTime.parse("16:08:27+09:00")));
    }

    @Test
    public void _3つの情報からOffsetDateTimeを生成する() {
        // staticメソッドで生成する。
        OffsetDateTime offsetDateTime = OffsetDateTime.of(this.localDate, this.localTime, this.plus0900);

        assertThat(offsetDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));

        // LocalDateTimeを使うこともできる。
        LocalDateTime localDateTime = LocalDateTime.of(this.localDate, this.localTime);
        OffsetDateTime offsetDateTime2args = OffsetDateTime.of(localDateTime, this.plus0900);

        assertThat(offsetDateTime2args, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));

        // インスタンスメソッドで生成するときはLocalDateTimeを経由する。
        OffsetDateTime fromLocalDateTime = localDateTime.atOffset(this.plus0900);

        assertThat(fromLocalDateTime, is(OffsetDateTime.parse("2016-10-16T16:08:27+09:00")));
    }

    @Test
    public void _3つの情報からZonedDateTimeを生成する() {
        // staticメソッドで生成する。
        ZonedDateTime zonedDateTime = ZonedDateTime.of(this.localDate, this.localTime, this.asiaTokyo);

        assertThat(zonedDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // LocalDateTimeを使うこともできる。
        LocalDateTime localDateTime = LocalDateTime.of(this.localDate, this.localTime);
        ZonedDateTime zonedDateTime2args = ZonedDateTime.of(localDateTime, this.asiaTokyo);

        assertThat(zonedDateTime2args, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // インスタンスメソッドで生成するときはLocalDateTimeを経由する。
        ZonedDateTime fromLocalDateTime = localDateTime.atZone(this.asiaTokyo);

        assertThat(fromLocalDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // ZonedDateTimeはオブジェクト内部にZoneOffsetを持つが、上記のように、ZoneOffsetを渡さなくても生成できる。
        // ZoneIdとLocalDateTimeからZoneOffsetを計算できるため。
        ZoneOffset zoneOffset = this.asiaTokyo.getRules().getOffset(localDateTime);

        assertThat(zoneOffset, is(ZoneOffset.of("+09:00")));
    }

    @Test
    public void _4つの情報からZonedDateTimeを生成する() {
        // 与えられたZoneOffsetを使い、OffsetDateTimeから生成することもできる。
        OffsetDateTime offsetDateTime = OffsetDateTime.of(this.localDate, this.localTime, this.plus0900);
        ZonedDateTime atSameInstant = offsetDateTime.atZoneSameInstant(this.asiaTokyo);
        ZonedDateTime atSameLocalDateTime = offsetDateTime.atZoneSimilarLocal(this.asiaTokyo);

        assertThat(atSameInstant, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(atSameLocalDateTime, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // 二つのメソッドの違いは、ZoneOffsetとZoneIdに整合性がないときに表れる。
        ZoneOffset plus0200 = ZoneOffset.of("+02:00");
        OffsetDateTime offsetDateTimeInParis = OffsetDateTime.of(this.localDate, this.localTime, plus0200);

        // Instantを保って変換するとLocalDateTimeは変化する。
        ZonedDateTime instantKept = offsetDateTimeInParis.atZoneSameInstant(this.asiaTokyo);

        assertThat(instantKept, is(ZonedDateTime.parse("2016-10-16T23:08:27+09:00[Asia/Tokyo]")));
        assertThat(instantKept.toInstant(), is(offsetDateTimeInParis.toInstant()));
        assertThat(instantKept.toLocalDateTime(), is(not(offsetDateTimeInParis.toLocalDateTime())));

        // LocalDateTimeを保って変換するとInstantは変化する。
        ZonedDateTime localDateTimeKept = offsetDateTimeInParis.atZoneSimilarLocal(this.asiaTokyo);

        assertThat(localDateTimeKept, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(localDateTimeKept.toInstant(), is(not(offsetDateTimeInParis.toInstant())));
        assertThat(localDateTimeKept.toLocalDateTime(), is(offsetDateTimeInParis.toLocalDateTime()));

        // ZoneOffsetとZoneIdに整合性があるときは、InstantもLocalDateTimeも保たれる。
        assertThat(atSameInstant.toInstant(), is(offsetDateTime.toInstant()));
        assertThat(atSameInstant.toLocalDateTime(), is(offsetDateTime.toLocalDateTime()));
        assertThat(atSameLocalDateTime.toInstant(), is(offsetDateTime.toInstant()));
        assertThat(atSameLocalDateTime.toLocalDateTime(), is(offsetDateTime.toLocalDateTime()));

        // staticメソッドで変換することもできる。
        LocalDateTime localDateTime = LocalDateTime.of(this.localDate, this.localTime);
        ZonedDateTime ofLocal = ZonedDateTime.ofLocal(localDateTime, this.asiaTokyo, this.plus0900);
        ZonedDateTime ofStrict = ZonedDateTime.ofStrict(localDateTime, this.plus0900, this.asiaTokyo);

        assertThat(ofLocal, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(ofStrict, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));

        // 二つのメソッドの違いは、インスタンスメソッド同様、ZoneOffsetとZoneIdに整合性がないときに表れる。
        // ofLocal()はLocalDateTimeを保ちながら、整合性があればZoneOffsetを採用、そうでなければ無視する。
        ZonedDateTime offsetIgnored = ZonedDateTime.ofLocal(localDateTime, this.asiaTokyo, plus0200);

        assertThat(offsetIgnored, is(ZonedDateTime.parse("2016-10-16T16:08:27+09:00[Asia/Tokyo]")));
        assertThat(offsetIgnored.toLocalDateTime(), is(localDateTime));
        assertThat(offsetIgnored.getOffset(), is(not(plus0200)));

        // ofStrictは、整合性がないときに例外を発生させる。
        try {
            ZonedDateTime.ofStrict(localDateTime, plus0200, this.asiaTokyo);
            fail("+02:00 is not an offset for Asia/Tokyo.");
        }
        catch (DateTimeException offsetIsNotValid) {
        }
    }

}
