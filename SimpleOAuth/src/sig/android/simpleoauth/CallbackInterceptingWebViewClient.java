package sig.android.simpleoauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

// See http://developer.android.com/guide/webapps/webview.html for more info.

public class CallbackInterceptingWebViewClient extends WebViewClient {

    private OAuthProvider provider;
    private OAuthConsumer consumer;
    
    public CallbackInterceptingWebViewClient(OAuthConsumer consumer, OAuthProvider provider) {
        this.provider = provider;
        this.consumer = consumer;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.contains(Preferences.CALLBACK_URL)) {
            
            Uri uri = Uri.parse(url);
            String aouthVerifier = uri.getQueryParameter("oauth_verifier");
            
            Context context = view.getContext();
            new GetAccessTokenTask(context, aouthVerifier, consumer, provider).execute();
            
            return false;
        }
        
        return true;
    }
}
