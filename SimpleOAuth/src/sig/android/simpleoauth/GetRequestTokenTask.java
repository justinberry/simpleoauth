package sig.android.simpleoauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class GetRequestTokenTask extends AsyncTask<Object, Object, String> {

    private OAuthConsumer consumer;
    private OAuthProvider provider;
    private Context context;
    
    public GetRequestTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider) {
        this.context = context;
        this.consumer = consumer;
        this.provider = provider;
    }
    
    @Override
    protected String doInBackground(Object... params) {

        String requestURL = null;
        try {
            requestURL = provider.retrieveRequestToken(consumer, Preferences.CALLBACK_URL);
        } catch (OAuthException e) {
            System.err.println("Failed to contact provider");
            e.printStackTrace();
        }
        
        return requestURL;
    }
    
    @Override
    protected void onPostExecute(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(Preferences.REQUEST_URI_KEY, url);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
