package com.example.raphifou.find.FireBaseClasses;

/**
 * Created by oliviermedec on 09/05/2017.
 */
import android.content.SharedPreferences;
import android.util.Log;

import com.example.raphifou.find.R;
import com.example.raphifou.find.Retrofit.ApiBackFireBase;
import com.example.raphifou.find.Retrofit.ApiBackend;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.FireBaseObject;
import com.example.raphifou.find.Retrofit.FireBaseResponse;
import com.example.raphifou.find.Retrofit.mainResponseObject;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.idFcm), token);
        editor.commit();

        String tokenUser = sharedPref.getString(getString(R.string.token), null);
        String idUser = sharedPref.getString(getString(R.string.id), null);

        BackEndApiService service = ApiBackend.getClient().create(BackEndApiService.class);
        Call<mainResponseObject> call = service.addIdFcm(tokenUser, idUser, token);
        call.enqueue(new Callback<mainResponseObject>() {
            @Override
            public void onResponse(Call<mainResponseObject> call, Response<mainResponseObject> response) {
                Log.w(getPackageName(), response.toString());
            }

            @Override
            public void onFailure(Call<mainResponseObject> call, Throwable t) {
                Log.e(getPackageName(), t.toString());
            }
        });
    }
}
