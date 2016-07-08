package ms.bhargav.noteapp;

import android.content.Context;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class ColorGenerator {
    private static String[] colors = new String[]{
            "red", "deep_purple", "light_blue", "green", "yellow", "deep_orange", "pink", "indigo",
            "cyan", "amber", "brown", "purple", "blue", "teal", "lime", "orange"
    };

    /**
     * Method for generating a random material color of the given intensity, from the google material
     * palette.
     *
     * @return a random color of the given intensity.
     */
    public static int getRandomMaterialColor(Context c) {
        int randomIndex = (int) (Math.random() * colors.length);
        String colorString = String.format("%s_%s", colors[randomIndex], "700");
        int identifier = c.getResources()
                .getIdentifier(colorString, "color", c.getPackageName());
        return c.getResources().getColor(identifier);
    }
}
