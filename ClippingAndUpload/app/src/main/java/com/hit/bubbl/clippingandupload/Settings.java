package com.hit.bubbl.clippingandupload;

/**
 * @author Bubbles
 * @create 2018/7/24
 * @Describe
 */
public class Settings {

    private static String ip = "192.168.1.0", port = "8080", debugMsg = "";

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Settings.ip = ip;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Settings.port = port;
    }

    public static String getUploadUrl() {
        return "http://" + ip + ":" + port + "/imageUpload_war/UploadServlet?";
    }

    public static String getDebugMsg() {
        return debugMsg;
    }

    public static void setDebugMsg(String debugMsg) {
        Settings.debugMsg = debugMsg;
    }
}
