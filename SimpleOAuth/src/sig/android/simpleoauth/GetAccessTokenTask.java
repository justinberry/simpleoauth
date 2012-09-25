package sig.android.simpleoauth;

import java.net.URLDecoder;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;

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
            String token = consumer.getToken();
            String secret = consumer.getTokenSecret();
            accessToken = storeAccessToken(token, secret);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    private AccessToken storeAccessToken(String token, String secret) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        Editor editor = sharedPreferences.edit();
        
        editor.putString(Constants.ACCESS_TOKEN, token);
        editor.putString(Constants.ACCESS_SECRET, secret);
        
        return new AccessToken(token, secret);
    }

    @Override
    protected void onPostExecute(AccessToken accessToken) {
        Intent intent = new Intent(context, TweetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CONSUMER, consumer);
        intent.putExtras(bundle);
        intent.putExtra(Constants.ACCESS_TOKEN, accessToken.getToken());
        intent.putExtra(Constants.ACCESS_SECRET, accessToken.getTokenSecret());
        context.startActivity(intent);
    }

}
