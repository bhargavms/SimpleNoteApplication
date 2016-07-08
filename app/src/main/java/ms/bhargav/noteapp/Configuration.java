package ms.bhargav.noteapp;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Locale;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class Configuration {
    private static PrettyTime prettyTime = null;
    public static PrettyTime getPrettyTime() {
        if(prettyTime == null) {
            prettyTime = new PrettyTime(Locale.getDefault());
        }
        return prettyTime;
    }
}
