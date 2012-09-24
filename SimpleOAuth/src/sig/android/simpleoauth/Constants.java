package sig.android.simpleoauth;


public class Constants {
    // The filename of the preferences file
    public static final String SHARED_PREFERENCES_NAME = "simple_oauth";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String ACCESS_SECRET = "access_secret";

    public static final String REQUEST_URI_KEY = "request_uri";
    public static final String CONSUMER = "oauth_consumer";
    
    public static final String CONSUMER_TOKEN = "aOrl7qeLFLwzezhemjl3Q";
    public static final String CONSUMER_SECRET = "4gghGs5eEVDrl5mCLo5CEPSEVgVxihadiGkmfADyNvw";
    public static final String TWITTER_REQUEST_TOKEN_URI = "https://api.twitter.com/oauth/request_token";
    public static final String TWITTER_ACCESS_TOKEN_URI = "https://api.twitter.com/oauth/access_token";
    public static final String TWITTER_AUTH_URI = "https://api.twitter.com/oauth/authorize";
    
    public static final String  CALLBACK_SCHEME = "x-simple-oauth";
    public static final String  CALLBACK_URL = CALLBACK_SCHEME + "://callback";
}