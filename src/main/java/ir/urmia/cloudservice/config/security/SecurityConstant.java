package ir.urmia.cloudservice.config.security;

public class SecurityConstant {

    private SecurityConstant() {

    }

    public static String[] getPermitAllUrls() {
        return new String[]{
                "/sign-up", "/login-page", "/css/**", "/js/**"
        };
    }

}
