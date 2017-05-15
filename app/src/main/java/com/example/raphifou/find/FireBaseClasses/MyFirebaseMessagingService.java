package com.example.raphifou.find.FireBaseClasses;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.raphifou.find.CheckAuth;
import com.example.raphifou.find.MainActivity;
import com.example.raphifou.find.MapsActivity;
import com.example.raphifou.find.MyUtilities.MyUtilities;
import com.example.raphifou.find.R;
import com.example.raphifou.find.Retrofit.FireBaseObject;
import com.example.raphifou.find.ShareAskCache.AskCache;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oliviermedec on 09/05/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    public String latitude = null;
    public String longitude = null;
    public String flag = null;
    public String sendingFcm = null;
    public String loginUser = null;
    public String idUser = null;
    String title = null;
    String body = null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token), null);
        if (token == null) {
            return;
        }

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            latitude = remoteMessage.getData().get("latitude");
            longitude = remoteMessage.getData().get("longitude");
            flag = remoteMessage.getData().get("flag");
            sendingFcm = remoteMessage.getData().get("idFcm");
            idUser = remoteMessage.getData().get("idUser");
            loginUser = remoteMessage.getData().get("loginUser");
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");

            List<Object> list = new ArrayList<>();
            list.add(latitude);
            list.add(longitude);
            list.add(flag);
            list.add(sendingFcm);
            list.add(idUser);
            list.add(loginUser);
            list.add(title);
            list.add(body);

            if (!new MyUtilities().checkIfObjectsNull(list)) { // One of the element of the list is null
                return;
            }

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
                Log.w(TAG, "Shedule thing to do");
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        }

        if (title != null && body != null) {
            sendNotification(title, body);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String title) {
        PendingIntent pendingIntent = null;
        if (Integer.parseInt(flag) == 0) {

            Log.w(getPackageName(), "Received location :: " + latitude + " || " + longitude);

            Intent mapIntent = new Intent(getApplication(), MapsActivity.class);
            mapIntent.putExtra(getString(R.string.latitude), latitude);
            mapIntent.putExtra(getString(R.string.longitude), longitude);
            pendingIntent = PendingIntent.getActivity(this, 0, mapIntent,
                    PendingIntent.FLAG_ONE_SHOT);
            final FireBaseObject fireBaseObject = new FireBaseObject("", this.title, "", 0, "",idUser, this.title, latitude, longitude);
            new AskCache(getApplicationContext()).addFireBaseObject(fireBaseObject);

        } else if (Integer.parseInt(flag) == 1) {
            Intent intent = new Intent(getApplication(), CheckAuth.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(getString(R.string.sendingFcm), sendingFcm);
            intent.putExtra(getString(R.string.loginUser), loginUser);
            intent.putExtra(getString(R.string.idUser), idUser);
            pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
