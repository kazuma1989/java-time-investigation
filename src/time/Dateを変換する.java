
package time;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class Dateを変換する implements を変換する {

    private final Date date = new Date((long) 1476601707 * 1000);

    @Override
    @Test
    public void Instantへ() {
        Instant instant = this.date.toInstant();

        assertThat(instant, is(Instant.ofEpochMilli((long) 1476601707 * 1000)));
    }

    /**
     * @see Instantを変換する#ZonedDateTimeへ()
     */
    @Override
    @Test
    public void ZonedDateTimeへ() {
        // Instantを経由する。
    }

    @Override
    @Test
    public void OffsetDateTimeへ() {
        // Instantを経由する。
    }

    @Override
    @Test
    public void OffsetTimeへ() {
        // Instantを経由する。
    }

    @Override
    @Test
    public void LocalDateTimeへ() {
        // Instantを経由する。
    }

    @Override
    @Test
    public void LocalDateへ() {
        // Instantを経由する。
    }

    @Override
    @Test
    public void LocalTimeへ() {
        // Instantを経由する。
    }

    @Override
    @Test
    public void Dateへ() {
        // そのまま。
    }

    @Override
    public void Calendarへ() {
        Calendar calendar = new Calendar.Builder().setInstant(this.date).build();

        assertThat(calendar, is(new Calendar.Builder().setInstant((long) 1476601707 * 1000).build()));
    }
}
