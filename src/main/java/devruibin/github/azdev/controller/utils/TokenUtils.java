package devruibin.github.azdev.controller.utils;

public class TokenUtils {
    public static String  getToken(String header){
        return header.substring(7);
    }
}
