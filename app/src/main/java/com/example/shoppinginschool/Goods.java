package com.example.shoppinginschool;

public class Goods {
    private int GoodsID;
    private String GoodsImg;
    private String GoodsName;
    private String GoodsPrice;
    private String GoodsDes;

    public int getGoodsID() {
        return GoodsID;
    }
    public String getGoodsImg() {return GoodsImg;}

    public String getGoodsName() {
        return GoodsName;
    }
    public String getGoodsDes() {
        return GoodsDes;
    }
    public String getGoodsPrice() {
        return GoodsPrice;
    }

    public Goods(String  goodsImg, String goodsName, String goodsPrice,String goodsDes) {
        GoodsName = goodsName;
        GoodsPrice = goodsPrice;
        GoodsImg = goodsImg;
        GoodsDes = goodsDes;
    }
}
