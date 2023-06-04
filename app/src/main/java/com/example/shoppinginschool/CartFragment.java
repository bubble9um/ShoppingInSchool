package com.example.shoppinginschool;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartAdapter.OnCheckedChangeListener {
    private RecyclerView recy_view_cart;
    private MyDBopenHelper myDBopenHelper;
    TextView goods_sumprice;
    Button pay;
    private SQLiteDatabase db;
    private final int FIRST_NOTFICATION_ID = 1600;
    private final String CHANNEL_ID = "xmh";
    NotificationCompat.Builder builder1;
    NotificationManagerCompat notificationManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragmnet.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_fragment, container, false);
        initView(view);
        loadData();
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    private void initView(View view) {
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(CartFragment.this.getContext());
        myDBopenHelper = new MyDBopenHelper(getContext(), "GOODS_Database.db", null, 1);
        db = myDBopenHelper.getReadableDatabase();
        recy_view_cart = view.findViewById(R.id.recy_view_cart);
        goods_sumprice = view.findViewById(R.id.goods_sumprice);
        CheckBox choose_all = view.findViewById(R.id.choose_all);
        pay = view.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("是否确认支付？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDBopenHelper.deleteAllCartData();
                        loadData();
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                        builder1 = new NotificationCompat.Builder(CartFragment.this.getContext(), CHANNEL_ID)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setSmallIcon(R.mipmap.icon_48)
                                .setContentTitle("成功付款！")
                                .setContentText("请你耐心等候，货物马上送到");
                        Notification notification = builder1.build();
                        if (ActivityCompat.checkSelfPermission(CartFragment.this.getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        notificationManager.notify(FIRST_NOTFICATION_ID, notification);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
    }

    public void loadData(){
        //不能实现实时更新总价，需要重新启动activity才变
        goods_sumprice.setText(myDBopenHelper.getSumPrice()+"元");
        CartAdapter adapter = new CartAdapter(myDBopenHelper.getAllCartData(),getContext());
        adapter.setOnCheckedChangeListener(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recy_view_cart.setLayoutManager(layoutManager);
        recy_view_cart.setAdapter(adapter);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){             //O=26
            CharSequence name = "我的通知渠道";
            String description = "这些是我的通知";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = ContextCompat.getSystemService(CartFragment.this.getContext(), NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCheckedChange(boolean isChecked) {
        loadData();
    }

}