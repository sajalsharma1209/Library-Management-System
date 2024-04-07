package com.shashank.platform.classroomappui.ui.Books.Fragments.Add_Book;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shashank.platform.classroomappui.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBookFragment newInstance(String param1, String param2) {
        AddBookFragment fragment = new AddBookFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}
//
//    @SuppressLint("CheckResult")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        EditText id, name, publication, author, pages, price;
//        Button add;
//
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_add_student, container, false);
////        id = v.findViewById(R.id.bookid);
////        name = v.findViewById(R.id.bookname);
////        author = v.findViewById(R.id.bookauthor);
////        publication = v.findViewById(R.id.bookpublication);
////        price = v.findViewById(R.id.bookprice);
//        add = v.findViewById(R.id.addbutton);
////        pages = v.findViewById(R.id.bookpages);
//        add.setOnClickListener(view -> {
//            String bookid = id.getText().toString().trim();
//            String bookname = name.getText().toString().trim().toUpperCase();
//            String bookpublication = publication.getText().toString().trim().toUpperCase();
//            String bookauthor = author.getText().toString().trim().toUpperCase();
//            String bookpages = pages.getText().toString().trim().toUpperCase();
//            String bookprice = price.getText().toString().trim().toUpperCase();
//
//            if (bookid.isEmpty() && bookname.isEmpty() && bookpublication.isEmpty() && bookauthor.isEmpty() && bookprice.isEmpty()) {
//                id.setError("This field is required");
//                name.setError("This field is required");
//                publication.setError("This field is required");
//                author.setError("This field is required");
//                pages.setError("This field is required");
//                price.setError("This field is required");
//            } else if (bookid.isEmpty()) {
//                id.setError("This field is required");
//            } else if (bookname.isEmpty()) {
//                name.setError("This field is required");
//
//            } else if (bookpublication.isEmpty()) {
//                publication.setError("This field is required");
//
//            } else if (bookauthor.isEmpty()) {
//                author.setError("This field is required");
//
//            } else if (bookprice.isEmpty()) {
//                price.setError("This field is required");
//
//            }
//            else if (bookpages.isEmpty()) {
//                price.setError("This field is required");
//
//            }else {
//
//
//
//
//            }
//
//
//        });
//
//        return v;
//
//    }
//}