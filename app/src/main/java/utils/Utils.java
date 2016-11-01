package utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Utils {

    // POPULATING HTML TEMPLATES
    public static String getAndroidAssetFile(Context context, String filename) {
        AssetManager am = context.getAssets();
        InputStream assetStream;
        StringBuilder result = new StringBuilder();
        try {
            assetStream = am.open(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(assetStream));
            String line;
            while ((line = r.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static Pattern getOembedSpikeUrlCheckRegex() {
        return Pattern.compile("^.*?www.usatoday.com/.*?/(\\d+)/(\\?.*)?$");
    }

    public static String replaceInTemplate(String orig, String key, String value) {
        if(orig == null) return "";

        String result = orig;
        if (value != null) {
            result = orig.replace("<!-- {{" + key + "}} -->", value);
        }
        return result;
    }
}
