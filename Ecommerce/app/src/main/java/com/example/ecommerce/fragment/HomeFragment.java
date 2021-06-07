package com.example.ecommerce.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.activity.ShowAllActivity;
import com.example.ecommerce.adapter.AllProductAdapter;
import com.example.ecommerce.adapter.CategoryAdapter;
import com.example.ecommerce.adapter.NewProductAdapter;
import com.example.ecommerce.model.AllProduct;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.NewProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public
class HomeFragment extends Fragment {

    RecyclerView recyclerView, recNewProduct, recAll;
    TextView cateShow;
    //category
    CategoryAdapter adapter;
    NewProductAdapter adapterNewPr;
    AllProductAdapter adapterAll;

    List<Category> listCate;
    List<NewProduct> listNewP;
    List<AllProduct> listAll;

    //FireStore
    FirebaseFirestore db;
    public
    HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public
    View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView =  view.findViewById(R.id.rec_category);
        recNewProduct = view.findViewById(R.id.new_product_rec);
        recAll = view.findViewById(R.id.popular_rec);

        cateShow = view.findViewById(R.id.category_see_all);

        db = FirebaseFirestore.getInstance();
        //image slide
        ImageSlider slider = view.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1, "Giảm giá lên đến 30%", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Trả góp lãi suất 0%", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "Sản phẩm bán chạy nhất tháng", ScaleTypes.CENTER_CROP));

        slider.setImageList(slideModels);

        //category
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        listCate = new ArrayList<>();
        adapter = new CategoryAdapter(getContext(), listCate);
        recyclerView.setAdapter(adapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                listCate.add(category);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                        }
                    }
                });

        //newproduct
        recNewProduct.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        listNewP = new ArrayList<>();
        adapterNewPr = new NewProductAdapter(getContext(), listNewP);
        recNewProduct.setAdapter(adapterNewPr);

        db.collection("NewProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProduct newProduct = document.toObject(NewProduct.class);
                                listNewP.add(newProduct);
                                adapterNewPr.notifyDataSetChanged();
                            }
                        } else {
                        }
                    }
                });

        //all product
        recAll.setLayoutManager(new GridLayoutManager(getActivity(),2));
        listAll = new ArrayList<>();
        adapterAll = new AllProductAdapter(getContext(), listAll);
        recAll.setAdapter(adapterAll);

        db.collection("AllProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AllProduct product = document.toObject(AllProduct.class);
                                listAll.add(product);
                                adapterAll.notifyDataSetChanged();
                            }
                        } else {
                        }
                    }
                });

        //action textview see all
        cateShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                startActivity(new Intent(getContext(), ShowAllActivity.class));
            }
        });
        return view;
    }
}