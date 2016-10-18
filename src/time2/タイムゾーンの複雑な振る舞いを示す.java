
package time2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

public class タイムゾーンの複雑な振る舞いを示す {

    private LocalDate localDate;

    private LocalTime localTime;

    private ZoneOffset zoneOffset;

    private ZoneId zoneId;

    private LocalDateTime localInGap;

    private LocalDateTime localInOverlap;

    @Before
    public void 独立した時間オブジェクトを用意する() {
        this.localInGap = LocalDateTime.of(2016, 3, 27, 2, 15);
        this.localInOverlap = LocalDateTime.of(2016, 10, 30, 2, 15);
        this.zoneId = ZoneId.of("Europe/Paris");
    }

    @Test
    public void getOffsetを試す() {
        ZoneOffset standardOffset = this.zoneId.getRules().getOffset(this.localInGap);
        ZoneOffset dstOffset = this.zoneId.getRules().getOffset(this.localInOverlap);

        assertThat(standardOffset, is(ZoneOffset.of("+01:00")));
        assertThat(dstOffset, is(ZoneOffset.of("+02:00")));
    }

    @Test
    public void ZonedDateTimeへ変換した場合はどうか() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(this.localInGap, this.zoneId);
        ZoneOffset dstOffset = zonedDateTime.getOffset();

        assertThat(dstOffset, is(ZoneOffset.of("+02:00")));
    }

    @Test
    public void ZonedDateTimeへ変換するプロセスを詳細に見ていく() {
        ZoneOffset standardOffset = this.zoneId.getRules().getOffset(this.localInGap);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(this.localInGap, standardOffset);
        ZonedDateTime zonedDateTime = offsetDateTime.atZoneSameInstant(this.zoneId);
        ZoneOffset dstOffset = zonedDateTime.getOffset();

        assertThat(dstOffset, is(ZoneOffset.of("+02:00")));
    }

    // @Test
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
