package dev.panda.lib.utilities;

import dev.panda.lib.chat.ChatUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public class Server {

    public final String SERVER_VERSION =
            Bukkit.getServer()
                    .getClass().getPackage()
                    .getName().split("\\.")[3]
                    .substring(1);

    public final int SERVER_VERSION_INT = Integer.parseInt(
            SERVER_VERSION
                    .replace("1_", "")
                    .replaceAll("_R\\d", ""));

    public String getIP() {
        URL url;
        BufferedReader in;
        String ipAddress = "";

        try {
            url = new URL("http://bot.whatismyipaddress.com");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            ipAddress = in.readLine().trim();

            if (ipAddress.length() <= 0)
                try {
                    InetAddress ip = InetAddress.getLocalHost();
                    ipAddress = ip.getHostAddress().trim();
                } catch (Exception exp) {
                    ipAddress = "ERROR";
                }
        }
        catch (Exception ex) {
            ChatUtil.log("[PandaLicense] Error in check your host IP!");
        }

        return ipAddress;
    }

    public String getDate(String dateFormat, String timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(new Date());
    }

    public String getHour(String hourFormat, String timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(hourFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(new Date());
    }
}
