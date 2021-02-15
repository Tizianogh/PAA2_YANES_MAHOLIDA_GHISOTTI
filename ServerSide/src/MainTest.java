import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainTest {

    public static void main(String[] args) {
        Date aujourdhui = new Date();

        DateFormat shortDateFormatEN = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT, new Locale("FR","fr"));

        System.out.println(shortDateFormatEN.format(aujourdhui));
    }
}
