package com.example.raphifou.find.Fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.example.raphifou.find.R;
import com.example.raphifou.find.Retrofit.ApiBackend;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.FireBaseResponse;
import com.example.raphifou.find.Retrofit.UsersResponse;
import com.example.raphifou.find.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oliviermedec on 11/05/2017.
 */

public class ContactList extends RecyclerView.Adapter<ContactList.ViewHolder>  {
    List<User> users = null;
    Context context = null;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    int type = 0;

    public ContactList(List<User> users, Context context, int type) {
        this.users =  users;
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bubble_text, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(users.get(position).Login);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(users.get(position).Login.charAt(0)),
                        generator.getColor(users.get(position).Login));
        holder.imgBubble.setImageDrawable(drawable);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    new MaterialDialog.Builder(context)
                            .title(users.get(position).Login)
                            .content("Do you want to share or ask location with " + users.get(position).Login)
                            .positiveText("Share")
                            .negativeText("Ask")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    final BackEndApiService service = ApiBackend.getClientFireBase().create(BackEndApiService.class);
                                    Call<FireBaseResponse> call = service.sendMsgtToUser("application/json", "key=AAAAYeZt82k:APA91bGQwNoUkZybkScveS_-koc2I6ySW9_9BXJBAKEN6t43Xs8S2diVxXp-5ERdYYSuj17QpUMc5rwINFDbjIyidzLYuw-2uNl5Qx1CSjrPqxFrDCPIzxCkxYSCBLg_5S5X6P4nCuXS");
                                    call.enqueue(new Callback<FireBaseResponse>() {
                                        @Override
                                        public void onResponse(Call<FireBaseResponse> call, Response<FireBaseResponse> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<FireBaseResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    // TODO
                                }
                            })
                            .show();
                } else if (type == 1) {

                } else if (type == 2) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView imgBubble;
        public RelativeLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.txtLogin);
            imgBubble = (ImageView)itemView.findViewById(R.id.image_view);
            container = (RelativeLayout)itemView.findViewById(R.id.container);
        }
    }
}
