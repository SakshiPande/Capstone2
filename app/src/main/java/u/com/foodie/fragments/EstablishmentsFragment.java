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
import u.com.foodie.model.CityIdBase;
import u.com.foodie.model.Establishments;
import u.com.foodie.model.EstablishmentsBaseResponse;
import u.com.foodie.networking.FoodieAPI;
import u.com.foodie.networking.RestClient;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstablishmentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EstablishmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstablishmentsFragment extends Fragment {
    private RecyclerView mRvRestaurants;
    private OnFragmentInteractionListener mListener;
    private Double mLat,mLng;
    private String cityId;

    public EstablishmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstablishmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstablishmentsFragment newInstance(String param1, String param2) {
        EstablishmentsFragment fragment = new EstablishmentsFragment();

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

        View rootView= inflater.inflate(R.layout.fragment_establishment, container, false);

        mRvRestaurants=(RecyclerView)rootView.findViewById(R.id.rv_categories);



        getCityID();

        return rootView;
    }

    private void getCityID() {
        try {


            Retrofit retrofit = RestClient.retrofitService();
            FoodieAPI foodieAPI = retrofit.create(FoodieAPI.class);
            // TODO : change the lat lng values
            //19.0760
            //72.8777
            Call<CityIdBase> cityIdBaseCall = foodieAPI.getCityID(mLat, mLng);
            cityIdBaseCall.enqueue(new Callback<CityIdBase>() {
                @Override
                public void onResponse(Call<CityIdBase> call, Response<CityIdBase> response) {
                    if (response.isSuccessful()) {
                        CityIdBase cityIdBase = response.body();
                        cityId = cityIdBase.getLocality().getCityId();
                        getEstablishments(cityId);
                    }
                    if (response.code() == 404) {
                        Toast.makeText(getActivity().getApplicationContext(), "We are sorry Zomato is unavailable in your city", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<CityIdBase> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getEstablishments(String cityId) {
        Retrofit retrofit=RestClient.retrofitService();
        FoodieAPI foodieAPI=retrofit.create(FoodieAPI.class);
        Call<Establishments> establishmentsCall=foodieAPI.getEstablishment(cityId);
        establishmentsCall.enqueue(new Callback<Establishments>() {
            @Override
            public void onResponse(Call<Establishments> call, Response<Establishments> response) {

                if(response.isSuccessful()){
                    Establishments establishments=response.body();
                    List<EstablishmentsBaseResponse>  establishmentList=establishments.getEstablishments();
                    if (mRvRestaurants != null) {
                        mRvRestaurants.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        mRvRestaurants.setLayoutManager(linearLayoutManager);
                        mRvRestaurants.setAdapter(new EstablishmentAdapter(establishmentList, getContext()));

                    }
                }
            }

            @Override
            public void onFailure(Call<Establishments> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();

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
