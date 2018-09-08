package cz.tyckouni.poopio.ui.fragments.poopCreation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import cz.tyckouni.poopio.R;
import cz.tyckouni.poopio.base.entities.PoopType;
import cz.tyckouni.poopio.ui.activities.PoopTypesAdapter;

/**
 * A simple {@link Fragment} subclass implementing the poop type selection.
 *
 * Activities that contain this fragment must implement the
 * {@link OnPoopTypeSelectedListener} interface
 * to handle interaction events.
 * Use the {@link PoopTypeSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoopTypeSelectionFragment extends Fragment {

    private OnPoopTypeSelectedListener mListener;

    private RecyclerView mRecyclerView;
    private ArrayList<PoopType> mPoopTypes;
    private PoopTypesAdapter mPoopTypesAdapter;

    public PoopTypeSelectionFragment() {
        // Required empty public constructor
    }

    public static PoopTypeSelectionFragment newInstance() {
        PoopTypeSelectionFragment fragment = new PoopTypeSelectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_poop_type_selection, container, false);

        mRecyclerView = rootView.findViewById(R.id.poop_type_recycler_view);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));

        mPoopTypes = new ArrayList<>(Arrays.asList(PoopType.values()));

        mPoopTypesAdapter = new PoopTypesAdapter(getContext(), mPoopTypes);
        mRecyclerView.setAdapter(mPoopTypesAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPoopTypeSelectedListener) {
            mListener = (OnPoopTypeSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPoopTypeSelectedListener");
        }
    }

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
     */
    public interface OnPoopTypeSelectedListener {

        /**
         * Called when user selects the poop type
         *
         * @param poopType selected poop type
         */
        void onPoopTypeSelected(PoopType poopType);
    }
}
