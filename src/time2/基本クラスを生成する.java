package time2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    @Test
    public void Instantを生成する() {
        LocalDateTime localDateTime = LocalDateTime.of(2016, 10, 16, 15, 23);
        // Instant instant = localDateTime.atOffset(null).toInstant();
    }

    @Test
    public void DayOfWeekを試す() {

        DayOfWeek dow = DayOfWeek.MONDAY;
        Locale locale = Locale.getDefault();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddE");
        System.out.println(dateTimeFormatter.parse("20161020Thursday"));
        // System.out.println(dow.getDisplayName(TextStyle.FULL, locale));
        // System.out.println(dow.getDisplayName(TextStyle.NARROW, locale));
        // System.out.println(dow.getDisplayName(TextStyle.SHORT, locale));
    }

}
