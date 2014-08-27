package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
public class XmlUtils {

    public static String getText(XmlPullParser parser) {
        if (parser == null) {
            return null;
        }

        try {
            if (parser.next() == XmlPullParser.TEXT) {
                return parser.getText();
            } else {
                return null;
            }
        } catch (XmlPullParserException ignored) {
            ignored.printStackTrace();
            return null;
        } catch (IOException ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    public static boolean isCorrect(XmlPullParser parser, int type, String namespace, String name) {
        if (parser == null) {
            return false;
        }

        try {
            parser.require(type, namespace, name);
            return true;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() == XmlPullParser.START_TAG) {
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }
    }
}
