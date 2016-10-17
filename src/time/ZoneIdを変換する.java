
package time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.Test;

public class ZoneIdを変換する {

    private final ZoneId zoneId = ZoneId.of("Asia/Tokyo");

    private final ZoneId almostZoneOffset = ZoneId.of("+09:00");

    @Test
    public void ZoneIdへ() {
        // そのまま。
    }

    @Test(expected = ClassCastException.class)
    public void ZoneOffsetへ変換できないパターン() {
        ZoneOffset.class.cast(this.zoneId);
    }

    @Test
    public void ZoneOffsetへ変換できるパターン() {
        ZoneOffset zoneOffset = (ZoneOffset) this.almostZoneOffset;

        assertThat(zoneOffset, is(ZoneOffset.of("+09:00")));
    }

    @Test
    public void ZoneOffsetへ変換する正式な手順_1() {
        Instant instant = Instant.parse("2016-10-16T07:08:27Z");
        ZoneOffset zoneOffset = this.zoneId.getRules().getOffset(instant);

        assertThat(zoneOffset, is(ZoneOffset.of("+09:00")));
    }

    @Test
    public void ZoneOffsetへ変換する正式な手順_2() {
        LocalDateTime localDateTime = LocalDateTime.parse("2016-10-16T16:08:27");
        ZoneOffset zoneOffset = this.zoneId.getRules().getOffset(localDateTime);

        assertThat(zoneOffset, is(ZoneOffset.of("+09:00")));
    }
}
