package sig.android.simpleoauth;

import java.net.URLDecoder;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

public class GetAccessTokenTask extends AsyncTask<Object, Object, AccessToken> {

    private Context context;
    private OAuthConsumer consumer;
    private OAuthProvider provider;
    private String aouthVerifier;

    public GetAccessTokenTask(Context context, String aouthVerifier, OAuthConsumer consumer, OAuthProvider provider) {

        this.context = context;
        this.aouthVerifier = aouthVerifier;
        this.provider = provider;
        this.consumer = consumer;
    }

    @Override
    protected AccessToken doInBackground(Object... params) {

        AccessToken accessToken = null;
        try {
            provider.retrieveAccessToken(consumer, URLDecoder.decode(aouthVerifier,"UTF-8"));
            accessToken = storeAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    private AccessToken storeAccessToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        Editor editor = sharedPreferences.edit();
        
        editor.putString(Preferences.ACCESS_TOKEN, consumer.getToken());
        editor.putString(Preferences.ACCESS_SECRET, consumer.getTokenSecret());
        
        return new AccessToken(consumer.getToken(), consumer.getTokenSecret());
    }

    @Override
    protected void onPostExecute(AccessToken accessToken) {
        Intent intent = new Intent(context, TweetActivity.class);
        intent.putExtra(Preferences.ACCESS_TOKEN, accessToken.getToken());
        intent.putExtra(Preferences.ACCESS_SECRET, accessToken.getTokenSecret());
        context.startActivity(intent);
    }

}
