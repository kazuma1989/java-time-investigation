package time2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;

public class 基本クラスを生成する {

    private Clock clock;

    @Before
    public void 時刻を固定する() {
        this.clock = Clock.fixed(Instant.ofEpochSecond(0), ZoneId.of("Asia/Tokyo"));
    }

    @Test
    public void LocalDateを生成する() {
        LocalDate now = LocalDate.now(this.clock);
        assertThat(now, is(LocalDateTime.now(this.clock).toLocalDate()));
    }

}
