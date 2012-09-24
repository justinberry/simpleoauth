package sig.android.simpleoauth;

public class AccessToken {

    private String token;
    private String tokenSecret;

    public AccessToken(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }
    
    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }
}
