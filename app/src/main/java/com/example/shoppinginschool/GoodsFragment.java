package com.example.shoppinginschool;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsFragment extends Fragment {
    private MyDBopenHelper myDBopenHelper;
    private SQLiteDatabase db;
    RecyclerView recy_view1,recy_view_news;
    private List<JsonToBean.ShowapiResBodyDTO.ListDTO> recipeList =new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "GoodsFragment";
    public GoodsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoodsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoodsFragment newInstance(String param1, String param2) {
        GoodsFragment fragment = new GoodsFragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_goods,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recy_view1 = view.findViewById(R.id.recy_view1);
        recy_view_news = view.findViewById(R.id.recy_view_news);
        myDBopenHelper = new MyDBopenHelper(getContext(),"GOODS_Database.db",null,1);
        db = myDBopenHelper.getReadableDatabase();
        /*for (int i = 0;i < 10; i++){
            Goods coke = new Goods(R.drawable.coke,"可乐","￥16.9元/kg",1);
            goodsList.add(coke);}
        GoodsAdapter adapter = new GoodsAdapter(goodsList);*/
        GoodsAdapter adapter = new GoodsAdapter(myDBopenHelper.getAllGoodsData(),getContext());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recy_view1.setLayoutManager(layoutManager);
        recy_view1.setAdapter(adapter);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        String address = String
                .format("https://route.showapi.com/119-42?showapi_appid=1401213&needContent=0&showapi_sign=269c2f14cb77427da2844a2bccb20508");
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonStr = response.body().string();
                JsonToBean jsonToBean = new Gson().fromJson(jsonStr, new TypeToken<JsonToBean>() {}.getType());
                JsonToBean.ShowapiResBodyDTO showapiResBodyDTO = jsonToBean.getShowapi_res_body();
                List<JsonToBean.ShowapiResBodyDTO.ListDTO> listDTOS = showapiResBodyDTO.getList();

                    if (getActivity() == null) return;
                    recipeList.clear();
                    recipeList.addAll(listDTOS);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NewsAdapter adapter = new NewsAdapter(getActivity(), recipeList);
                            StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            recy_view_news.setLayoutManager(st);
                            recy_view_news.setAdapter(adapter);
                        }
                    });
            }
        });
    }
}