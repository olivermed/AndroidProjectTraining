package com.example.raphifou.find.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.raphifou.find.Home;
import com.example.raphifou.find.MainActivity;
import com.example.raphifou.find.R;
import com.example.raphifou.find.Resquest.MyResquestor;
import com.example.raphifou.find.Retrofit.ApiBackend;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.LoginResponse;
import com.example.raphifou.find.Retrofit.UsersResponse;
import com.example.raphifou.find.User;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String Tag = ContactFragment.class.toString();
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String type;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FastScroller fastScroller;
    private ProgressBar progressBarFriends;

    private OnFragmentInteractionListener mListener;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String type, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        progressBarFriends = (ProgressBar) view.findViewById(R.id.progressBar3);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getContacts();

        List<User> usersList = new ArrayList<>();               /* Solution temporaire pour la scroll view */
        usersList.add(new User("", "", "", "", ""));
        mAdapter = new ContactList(usersList, getContext(), 0);
        mRecyclerView.setAdapter(mAdapter);
        fastScroller.setRecyclerView(mRecyclerView);

        return view;
    }

    private void getContacts() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token), null);

        final BackEndApiService service = ApiBackend.getClient().create(BackEndApiService.class);
        Call<UsersResponse> call = service.getUsers(token);
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                Log.w(Tag, response.body().getResults().toString());
                List<User> usersList = response.body().getResults();
                if (type != null) {
                    mAdapter = new ContactList(usersList, getContext(), Integer.parseInt(type));
                } else {
                    mAdapter = new ContactList(usersList, getContext(), 0);
                }
                mRecyclerView.setAdapter(mAdapter);
                fastScroller.setRecyclerView(mRecyclerView);
                progressBarFriends.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarMenu();
        ((MainActivity)getContext()).showFab(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment searchFriend = new SearchFriendFragment();
                ((MainActivity)getContext()).setFragment(searchFriend, SearchFriendFragment.Tag);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getContext()).hideFab();
    }

    public void setAppBarMenu() {
        ((MainActivity)getActivity()).setAppBarMenu(R.id.nav_contact);
        getActivity().setTitle(getString(R.string.TitleContact));
        if (Objects.equals(type, "1")) {
            getActivity().setTitle("Share");
        } else if (Objects.equals(type, "2")) {
            getActivity().setTitle("Ask");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
