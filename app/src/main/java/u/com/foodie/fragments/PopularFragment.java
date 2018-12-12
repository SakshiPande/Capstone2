package u.com.foodie.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import u.com.foodie.R;
import u.com.foodie.adapter.EstablishmentAdapter;
import u.com.foodie.adapter.PopularRestaurantAdapter;
import u.com.foodie.model.PopularBaseResponse;
import u.com.foodie.model.Restaurant;
import u.com.foodie.networking.FoodieAPI;
import u.com.foodie.networking.RestClient;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PopularFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PopularFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRvPopular;
    private Double mLat,mLng;
    private String sort,order;
    private  int   count;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PopularFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularFragment newInstance(String param1, String param2) {
        PopularFragment fragment = new PopularFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLat=getArguments().getDouble("lat");
            mLng=getArguments().getDouble("lng");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_popular, container, false);
        mRvPopular=rootView.findViewById(R.id.rv_popular);

        sort="rating";
        count=10;
        order="asc";

        getPopularRestaurants();
        return rootView;
    }

    private void getPopularRestaurants() {
        Retrofit retrofit=RestClient.retrofitService();
        FoodieAPI foodieAPI=retrofit.create(FoodieAPI.class);
        Call<PopularBaseResponse> popularBaseResponseCall=foodieAPI.getPopularRestaurants(mLat,mLng,count,sort,order);
        popularBaseResponseCall.enqueue(new Callback<PopularBaseResponse>() {
            @Override
            public void onResponse(Call<PopularBaseResponse> call, Response<PopularBaseResponse> response) {
                if(response.isSuccessful()){
                    PopularBaseResponse popularBaseResponse=response.body();
                    List<Restaurant> restaurantList=popularBaseResponse.getRestaurants();
                    if (mRvPopular != null) {
                        mRvPopular.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        mRvPopular.setLayoutManager(linearLayoutManager);
                        mRvPopular.setAdapter(new PopularRestaurantAdapter(restaurantList, getContext()));

                    }

                }

            }

            @Override
            public void onFailure(Call<PopularBaseResponse> call, Throwable t) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
