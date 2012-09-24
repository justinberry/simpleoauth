package sig.android.simpleoauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity {

    private static OAuthConsumer consumer;
    private static OAuthProvider provider;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConsumerAndProviderSingletons();
        
        AccessToken accessToken = loadAccessTokenFromCache();
        if (accessToken == null) {
            String requestUri = retrieveLoadedRequestUri();
            if (requestUri == null) {
                new GetRequestTokenTask(this, consumer, provider).execute();
            }
            else {
                Button getAccessTokenBtn = (Button) findViewById(R.id.getAccessTokenBtn);
                getAccessTokenBtn.setEnabled(false);
                getAccessTokenBtn.setText(R.string.complete);
                WebView webView = (WebView) findViewById(R.id.webView);
                webView.setWebViewClient(new CallbackInterceptingWebViewClient(consumer, provider));
                webView.loadUrl(requestUri);
            }
        }
        else {
            Intent intent = new Intent(getApplicationContext(), TweetActivity.class);
            
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.CONSUMER, consumer);
            intent.putExtras(bundle);
            
            intent.putExtra(Constants.ACCESS_TOKEN, accessToken.getToken());
            intent.putExtra(Constants.ACCESS_SECRET, accessToken.getTokenSecret());
            getApplicationContext().startActivity(intent);
        }
    }

    private void initConsumerAndProviderSingletons() {
        if (consumer == null || provider == null) {
            consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_TOKEN, Constants.CONSUMER_SECRET);
            provider = new CommonsHttpOAuthProvider(Constants.TWITTER_REQUEST_TOKEN_URI
                    ,Constants.TWITTER_ACCESS_TOKEN_URI
                    ,Constants.TWITTER_AUTH_URI);
        }
    }

    private String retrieveLoadedRequestUri() {
        Bundle extras = getIntent().getExtras();
        
        if (extras == null) {
            return null;
        }
        
        return extras.getString(Constants.REQUEST_URI_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Loads saved access tokens from preferences if available. Side note: If
     * you just want to pass stuff between Activities, use Bundles instead.
     * SharedPreferences = persistent
     * Bundles = non-persistent.
     */
    private AccessToken loadAccessTokenFromCache() {
        // MODE_PRIVATE = only this application can access these preferences
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE);

        String accessToken = sharedPreferences.getString(
                Constants.ACCESS_TOKEN, null);
        String accessSecret = sharedPreferences.getString(
                Constants.ACCESS_SECRET, null);

        if (accessToken != null && accessSecret != null) {
            return new AccessToken(accessToken, accessSecret);
        }
        
        return null;
    }

    public void onGetAccessTokenClick(View button) {

    }
}
