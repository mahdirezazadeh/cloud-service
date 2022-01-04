package ir.urmia.cloudservice.config.security;

public class SecurityConstant {

    private SecurityConstant() {

    }

    public static String[] getPermitAllUrls() {
        return new String[]{
                "/login", "/sign-up"
        };
    }

}
