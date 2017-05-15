package com.example.raphifou.find.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.raphifou.find.Cache.Cache;
import com.example.raphifou.find.GPS.GPSTracker;
import com.example.raphifou.find.R;
import com.example.raphifou.find.Retrofit.ApiBackFireBase;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.FireBaseObject;
import com.example.raphifou.find.Retrofit.FireBaseResponse;
import com.example.raphifou.find.ShareAskCache.AskCache;
import com.example.raphifou.find.ShareAskCache.ShareCache;
import com.example.raphifou.find.User;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by oliviermedec on 11/05/2017.
 */

public class ContactList extends RecyclerView.Adapter<ContactList.ViewHolder> implements SectionTitleProvider {
    List<User> users = null;
    Context context = null;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    int type = 0;
    public String longitude = null;
    public String latitude = null;
    ShareCache shareCache = null;
    AskCache askCache = null;

    public ContactList(List<User> users, Context context, int type) {
        this.users =  users;
        this.context = context;
        this.type = type;
        shareCache = new ShareCache(context);
        askCache = new AskCache(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bubble_text, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(users.get(position).Login);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(users.get(position).Login.toUpperCase().charAt(0)),
                        generator.getColor(users.get(position).Login));
        holder.imgBubble.setImageDrawable(drawable);

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), MODE_PRIVATE);
        final String login = sharedPref.getString(context.getString(R.string.login), null);
        final String idFcm = sharedPref.getString(context.getString(R.string.idFcm), null);

        holder.txtUserName.setText(users.get(position).FirstName + " " + users.get(position).LastName);

        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()){
            latitude = ""+gps.getLatitude(); // returns latitude
            longitude = ""+gps.getLongitude();
            Log.w(context.getPackageName(), "Send location :: " + latitude + " || " + longitude);
        }
        gps.stopUsingGPS();

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    new MaterialDialog.Builder(context)
                            .title(users.get(position).Login)
                            .content("Do you want to share or ask location with " + users.get(position).Login + " ?")
                            .positiveText("Share")
                            .negativeText("Ask")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //get my position and send it
                                    BackEndApiService service = ApiBackFireBase.getClientFireBase().create(BackEndApiService.class);
                                    Call<FireBaseResponse> call = service.sendMsgtToUser("application/json",
                                            "key=AAAAYeZt82k:APA91bGQwNoUkZybkScveS_-koc2I6ySW9_9BXJBAKEN6t43Xs8S2diVxXp-5ERdYYSuj17QpUMc5rwINFDbjIyidzLYuw-2uNl5Qx1CSjrPqxFrDCPIzxCkxYSCBLg_5S5X6P4nCuXS",
                                            new FireBaseObject(users.get(position).idFcm, login, "Watch the location of " + login, 0, idFcm, users.get(position)._id, users.get(position).Login, latitude, longitude));
                                    call.enqueue(new Callback<FireBaseResponse>() {
                                        @Override
                                        public void onResponse(Call<FireBaseResponse> call, Response<FireBaseResponse> response) {
                                            Log.w(context.getPackageName(), response.toString());
                                        }

                                        @Override
                                        public void onFailure(Call<FireBaseResponse> call, Throwable t) {
                                            Log.e(context.getPackageName(), t.toString());
                                        }
                                    });
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    BackEndApiService service = ApiBackFireBase.getClientFireBase().create(BackEndApiService.class);
                                    Call<FireBaseResponse> call = service.sendMsgtToUser("application/json",
                                            "key=AAAAYeZt82k:APA91bGQwNoUkZybkScveS_-koc2I6ySW9_9BXJBAKEN6t43Xs8S2diVxXp-5ERdYYSuj17QpUMc5rwINFDbjIyidzLYuw-2uNl5Qx1CSjrPqxFrDCPIzxCkxYSCBLg_5S5X6P4nCuXS",
                                            new FireBaseObject(users.get(position).idFcm, login, "Share your location with " + login, 1, idFcm, users.get(position)._id, users.get(position).Login, latitude, longitude));
                                    call.enqueue(new Callback<FireBaseResponse>() {
                                        @Override
                                        public void onResponse(Call<FireBaseResponse> call, Response<FireBaseResponse> response) {
                                            Log.w(context.getPackageName(), response.toString());
                                        }

                                        @Override
                                        public void onFailure(Call<FireBaseResponse> call, Throwable t) {
                                            Log.e(context.getPackageName(), t.toString());
                                        }
                                    });
                                }
                            })
                            .show();
                } else if (type == 1) { // Share
                    new MaterialDialog.Builder(context)
                            .title(users.get(position).Login)
                            .content("Do you want to share with " + users.get(position).Login + " ?")
                            .positiveText("Yes")
                            .negativeText("No")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    shareToUser(position, login, idFcm);
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                } else if (type == 2) { // Ask
                    new MaterialDialog.Builder(context)
                            .title(users.get(position).Login)
                            .content("Do you want to ask " + users.get(position).Login + "'s location ?")
                            .positiveText("Yes")
                            .negativeText("No")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    askToUser(position, login, idFcm);
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                } else if (type == 3) { // Add Friend
                    new MaterialDialog.Builder(context)
                            .title("Friend request")
                            .content("Do you want to send to " + users.get(position).Login + " a friend request ?")
                            .positiveText("Yes")
                            .negativeText("No")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                }
            }
        });
    }

    public void shareToUser(final int position, String login, String idFcm) {
        final FireBaseObject fireBaseObject = new FireBaseObject(users.get(position).idFcm, login, "Watch the location of " + login, 0, idFcm, users.get(position)._id, users.get(position).Login, latitude, longitude);
        BackEndApiService service = ApiBackFireBase.getClientFireBase().create(BackEndApiService.class);
        Call<FireBaseResponse> call = service.sendMsgtToUser("application/json",
                "key=AAAAYeZt82k:APA91bGQwNoUkZybkScveS_-koc2I6ySW9_9BXJBAKEN6t43Xs8S2diVxXp-5ERdYYSuj17QpUMc5rwINFDbjIyidzLYuw-2uNl5Qx1CSjrPqxFrDCPIzxCkxYSCBLg_5S5X6P4nCuXS",
                fireBaseObject);
        call.enqueue(new Callback<FireBaseResponse>() {
            @Override
            public void onResponse(Call<FireBaseResponse> call, Response<FireBaseResponse> response) {
                Log.w(context.getPackageName(), response.toString());
                shareCache.addFireBaseObject(fireBaseObject);
                new MaterialDialog.Builder(context)
                        .title(users.get(position).Login)
                        .content("Your location have been shared with " + users.get(position).Login)
                        .positiveText("Ok")
                        .show();
            }

            @Override
            public void onFailure(Call<FireBaseResponse> call, Throwable t) {
                Log.e(context.getPackageName(), t.toString());
            }
        });
    }

    public void askToUser(final int position, String login, String idFcm) {
        final FireBaseObject fireBaseObject = new FireBaseObject(users.get(position).idFcm, login, "Share your location with " + login, 1, idFcm, users.get(position)._id, users.get(position).Login, latitude, longitude);
        BackEndApiService service = ApiBackFireBase.getClientFireBase().create(BackEndApiService.class);
        Call<FireBaseResponse> call = service.sendMsgtToUser("application/json",
                "key=AAAAYeZt82k:APA91bGQwNoUkZybkScveS_-koc2I6ySW9_9BXJBAKEN6t43Xs8S2diVxXp-5ERdYYSuj17QpUMc5rwINFDbjIyidzLYuw-2uNl5Qx1CSjrPqxFrDCPIzxCkxYSCBLg_5S5X6P4nCuXS",
                fireBaseObject);
        call.enqueue(new Callback<FireBaseResponse>() {
            @Override
            public void onResponse(Call<FireBaseResponse> call, Response<FireBaseResponse> response) {
                Log.w(context.getPackageName(), response.toString());
                //askCache.addFireBaseObject(fireBaseObject);
                new MaterialDialog.Builder(context)
                        .title(users.get(position).Login)
                        .content("You ask the location to " + users.get(position).Login)
                        .positiveText("Ok")
                        .show();
            }

            @Override
            public void onFailure(Call<FireBaseResponse> call, Throwable t) {
                Log.e(context.getPackageName(), t.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public String getSectionTitle(int position) {
        return users.get(position).LastName.substring(0, 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView txtUserName;
        public ImageView imgBubble;
        public RelativeLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.txtLogin);
            txtUserName = (TextView)itemView.findViewById(R.id.txtUserName);
            imgBubble = (ImageView)itemView.findViewById(R.id.image_view);
            container = (RelativeLayout)itemView.findViewById(R.id.container);
        }
    }
}
