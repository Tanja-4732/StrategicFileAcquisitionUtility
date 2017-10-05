package logic;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class BL {
    public static final boolean BEFORE = false;
    public static final boolean AFTER = true;

    public String getHTML(String URL) {

        java.net.URL url;
        StringBuffer sb = new StringBuffer();

        try {
            // get URL content

            url = new URL(URL);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public ArrayList<String> parseURL(String URL) {
        int firstBracket = -1, secondBracket = -1, colon = -1;
        char[] temp = URL.toCharArray();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == '[') {
                firstBracket = i;
                break;
            }
        }
        for (int i = firstBracket + 1; i < temp.length; i++) {
            if (temp[i] == ']') {
                secondBracket = i;
                break;
            }
        }
        for (int i = firstBracket + 1; i < secondBracket; i++) {
            if (temp[i] == ':') {
                colon = i;
                break;
            }
        }

        int start = Integer.parseInt(URL.substring(firstBracket + 1, colon)),
                end = Integer.parseInt(URL.substring(colon + 1, secondBracket));
        ArrayList<String> list = new ArrayList<>();
        String newURL = URL.substring(0, firstBracket);
        for (int i = start; i < end + 1; i++) {
            list.add(newURL + i);
        }
        return list;
    }

    public int getLenght(String URL) {
        int firstBracket = -1, secondBracket = -1, colon = -1;
        char[] temp = URL.toCharArray();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == '[') {
                firstBracket = i;
                break;
            }
        }
        for (int i = firstBracket + 1; i < temp.length; i++) {
            if (temp[i] == ']') {
                secondBracket = i;
                break;
            }
        }
        for (int i = firstBracket + 1; i < secondBracket; i++) {
            if (temp[i] == ':') {
                colon = i;
                break;
            }
        }

        int start = Integer.parseInt(URL.substring(firstBracket + 1, colon)),
                end = Integer.parseInt(URL.substring(colon + 1, secondBracket));
        return end - start;
    }

    public int getPercent(int y, int x) {
        return (y / x) * 100;
    }

    public String getLink(String HTML, String searchFor, boolean position) {
        if (!HTML.contains(searchFor)) {
//            throw new Error();
        }
        int foundAt = HTML.indexOf(searchFor);

        int firstQuotMk = -1;
        int secondQuotMk = -1;
        if (position) {
            secondQuotMk = foundAt - 1;
            for (int i = foundAt - 2; i >= 0; i--) {
                if (HTML.charAt(i) == '"') {
                    firstQuotMk = i;
                    break;
                }
            }
        } else {
            firstQuotMk = foundAt + searchFor.length() + 1;
            for (int i = foundAt + searchFor.length() + 1; i < HTML.length(); i++) {
                if (HTML.charAt(i) == '"') {
                    secondQuotMk = i;
                    break;
                }
            }
        }

        return HTML.substring(firstQuotMk + 1, secondQuotMk);
    }

    public String buildLink(String partialImageURL) {
        return "http://random.cat/" + partialImageURL;
    }

    public void downloadAndWrite(String URL, String absolutePath) throws IOException {
        URL urlObject = new URL(URL);
        File file = new File(absolutePath + "\\" + urlObject.getFile());
        FileUtils.copyURLToFile(urlObject, file);
    }
}
