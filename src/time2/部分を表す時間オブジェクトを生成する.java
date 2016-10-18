package time2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class 部分を表す時間オブジェクトを生成する {

    private Year year;
    private YearMonth yearMonth;
    private Month month;
    private MonthDay monthDay;
    private DayOfWeek dayOfWeek;

    @Before
    public void 部分を表す時間時間オブジェクトを用意する() {
        this.year = Year.of(2016);
        this.yearMonth = YearMonth.of(2016, 10);
        this.month = Month.of(10);
        this.monthDay = MonthDay.of(10, 16);
        this.dayOfWeek = DayOfWeek.of(7);
    }

    @Test
    public void 列挙型を使う() {
        assertThat(this.yearMonth, is(YearMonth.of(2016, Month.OCTOBER)));
        assertThat(this.month, is(Month.OCTOBER));
        assertThat(this.monthDay, is(MonthDay.of(Month.OCTOBER, 16)));
        assertThat(this.dayOfWeek, is(DayOfWeek.SUNDAY));
    }

    @Test
    public void 曜日を計算する() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E");
        System.out.println(dateTimeFormatter);
        dateTimeFormatter.parse("Tuesday");
    }

}
