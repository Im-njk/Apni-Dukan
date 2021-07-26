package com.example.productpage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.productpage.activities.HomeActivity;
import com.example.productpage.activities.MainActivity;
import com.example.productpage.model.newuserinfo;
import com.example.productpage.model.product;

import java.util.ArrayList;
import java.util.List;

public class Dbhandler extends SQLiteOpenHelper {
    public Dbhandler( Context context) {
        super(context, "Ecommerce",null,params.DB_VERSION);
    }
    SQLiteDatabase db;



    @Override
     public void onCreate(SQLiteDatabase db) {
     String Usertable= "CREATE TABLE USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NAME NVARCHAR(100),EMAIL NVARCHAR(50),PHONE NVARCHAR(15),PASSWORD NVARCHAR(16),ADDRESS NVARCHAR(200))";

     String producttable="CREATE TABLE PRODUCTS(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NAME NVARCHAR(100), IMGURL NVARCHAR(500),DESCRIPTION NVARCHAR(500),PRICE INT ,CATEGORY NVARCHAR(50), RATING DECIMAL(20,1))";

     String Carttable="CREATE TABLE CART (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, PRODUCTID INT,USERID INT,PRICE INT,QUANTITY INT)";

     String wishlisttable="CREATE TABLE WISHLIST (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, PRODUCTID INT,USERID INT,PRICE INT,QUANTITY INT)";


     db.execSQL(Usertable);
     db.execSQL(producttable);
     db.execSQL(Carttable);
     db.execSQL(wishlisttable);

    }

    public List<product> fetchall_wishlist(){
        SQLiteDatabase db=this.getReadableDatabase();
        List<product> l1 =new ArrayList<>();
        product p1=new product();
        String select="SELECT PRODUCTID FROM WISHLIST WHERE USERID="+MainActivity.id;
        Cursor cursor =db.rawQuery(select,null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            int id= cursor.getInt(0);
            p1= getproductbyid(id);
            l1.add(p1);
            cursor.moveToNext();
        }
        return l1;
    }

    public boolean checkitemin_wishlist(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String select="SELECT USERID ,PRODUCTID ,QUANTITY FROM WISHLIST WHERE USERID="+ MainActivity.id +" AND PRODUCTID="+id;
        Cursor cursor=db.rawQuery(select,null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            return false;
        }else
            return true;
    }

    public void additemto_wishlist(int id){
        product p1=new product();
        p1=getproductbyid(id);
        SQLiteDatabase db=this.getWritableDatabase();
        boolean bool=checkitemin_wishlist(id);
        if (!bool) {
            ContentValues values = new ContentValues();
            values.put("USERID",MainActivity.id);
            values.put("PRODUCTID",id);
            values.put("PRICE",p1.getPrice());
            values.put("QUANTITY",1);
            db.insert("WISHLIST",null,values);
        }
    }

    public int get_cartcount(){
        SQLiteDatabase db=this.getWritableDatabase();
        String select="SELECT USERID ,PRODUCTID ,QUANTITY FROM CART WHERE USERID="+ MainActivity.id;
        Cursor cursor=db.rawQuery(select,null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

     public List<product> fetchallcart(){
        SQLiteDatabase db=this.getReadableDatabase();
        List<product> l1 =new ArrayList<>();
        product p1=new product();
        String select="SELECT PRODUCTID FROM CART WHERE USERID="+MainActivity.id;
        Cursor cursor =db.rawQuery(select,null);
        cursor.moveToFirst();
         for (int i = 0; i < cursor.getCount(); i++) {
             int id= cursor.getInt(0);
            p1= getproductbyid(id);
            l1.add(p1);
            cursor.moveToNext();
         }
         return l1;
     }

     public int getintitem(String item,String Table,int id){
        SQLiteDatabase db=this.getReadableDatabase();
         String Querty;
        if(Table=="CART")
         Querty="SELECT "+item+" FROM "+Table+" WHERE PRODUCTID="+id+" AND USERID="+MainActivity.id;
        else
            Querty="SELECT "+item+" FROM "+Table+" WHERE ID="+id+" AND USERID="+MainActivity.id;
        Cursor cursor =db.rawQuery(Querty,null);
        cursor.moveToFirst();
        if(cursor.getCount()!=0)
        return cursor.getInt(0);
        else
            return 0;
    }

    public int getintitem(String item,String Table){
        SQLiteDatabase db=this.getReadableDatabase();
        String Querty;
        if(Table=="CART")
            Querty="SELECT "+item+" FROM "+Table+" WHERE  USERID="+MainActivity.id;
        else
            Querty="SELECT "+item+" FROM "+Table+" WHERE  USERID="+MainActivity.id;
        Cursor cursor =db.rawQuery(Querty,null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public String getStringitem(String item,String Table,int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String Querty="SELECT "+item+" FROM "+Table+" WHERE ID="+id;
        Cursor cursor =db.rawQuery(Querty,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

     public boolean checkitemincart(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String select="SELECT USERID ,PRODUCTID ,QUANTITY FROM CART WHERE USERID="+ MainActivity.id +" AND PRODUCTID="+id;
        Cursor cursor=db.rawQuery(select,null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            return false;
        }else
            return true;
    }

     public int changequantity(String action,int pid,int actprice){
        SQLiteDatabase db=this.getWritableDatabase();
        String quan= "SELECT QUANTITY , PRICE FROM CART WHERE USERID="+MainActivity.id+" AND PRODUCTID="+pid;
        Cursor cursor=db.rawQuery(quan,null);
        cursor.moveToFirst();
        int Quan=cursor.getInt(0);
        if(action=="+")
        Quan++;
        else
            Quan--;
        int price;
        price=actprice*Quan;
        String update="UPDATE CART SET QUANTITY="+Quan+" WHERE USERID="+MainActivity.id+" AND PRODUCTID="+pid;
        String update1="UPDATE CART SET PRICE="+price+" WHERE USERID="+MainActivity.id+" AND PRODUCTID="+pid;
        db.execSQL(update);
        db.execSQL(update1);
       return Quan;
     }

     public void removeitem(int id,String table){
        SQLiteDatabase db=this.getWritableDatabase();
        String del="DELETE FROM "+table+" WHERE PRODUCTID="+id+" AND USERID="+MainActivity.id;
        db.execSQL(del);

     }

     public void additemtocart(int id){
        product p1=new product();
        p1=getproductbyid(id);
        SQLiteDatabase db=this.getWritableDatabase();
        boolean bool=checkitemincart(id);
        if (!bool) {
            ContentValues values = new ContentValues();
            values.put("USERID",MainActivity.id);
            values.put("PRODUCTID",id);
            values.put("PRICE",p1.getPrice());
            values.put("QUANTITY",1);
            db.insert("CART",null,values);

        }
    }

     public List<product> fetchproductbycat(){
        List<product> productList=new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        String products="SELECT NAME,IMGURL,DESCRIPTION,PRICE,CATEGORY,ID FROM PRODUCTS";
         product p1=new product();
        Cursor cursor =db.rawQuery(products,null);
        cursor.moveToFirst();
         for (int i = 0; i <cursor.getCount(); i++) {
             p1.setPrice(String.valueOf(cursor.getInt(3)));
             p1.setTitle(cursor.getString(0));
             p1.setDescription(cursor.getString(2));
             p1.setImage(cursor.getString(1));
             p1.setCategory(cursor.getString(4));
             p1.setId(cursor.getInt(5));
             cursor.moveToNext();
             productList.add(p1);
         }

        return productList;
     }

     public product getproductbyid(int id){
        product p1=new product();
         SQLiteDatabase db =this.getReadableDatabase();
         String products="SELECT NAME,IMGURL,DESCRIPTION,PRICE,CATEGORY,ID FROM PRODUCTS WHERE ID="+id;
         Cursor cursor =db.rawQuery(products,null);
         cursor.moveToFirst();
         p1.setPrice(String.valueOf(cursor.getInt(3)));
         p1.setTitle(cursor.getString(0));
         p1.setDescription(cursor.getString(2));
         p1.setImage(cursor.getString(1));
         p1.setCategory(cursor.getString(4));
         p1.setId(cursor.getInt(5));
        return p1;
     }

     public newuserinfo userdatabyid(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String select ="SELECT  NAME ,EMAIL ,PHONE ,PASSWORD ,ADDRESS FROM USERS WHERE ID= "+id;
        Cursor cursor=db.rawQuery(select,null);
        cursor.moveToFirst();
        newuserinfo info=new newuserinfo(cursor.getString(0),cursor.getString(2),cursor.getString(3));
        cursor.moveToFirst();
        return info;
    }

     public boolean adduser(newuserinfo info){
           SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", info.getName());
        values.put("PHONE", info.getnumber());
        values.put("PASSWORD", info.getPassword());
        String s;
        s="SELECT PASSWORD FROM USERS WHERE PHONE="+info.getnumber();
        Cursor cursor=db.rawQuery(s,null);
        if(cursor.getCount()==0){
            db.insert("USERS",null,values);
            return true;
        }else
            return false;
    }

     public void addproduct(){
       SQLiteDatabase db=this.getWritableDatabase();
       ContentValues values = new ContentValues();
       ContentValues values2 = new ContentValues();

       String select = "SELECT * FROM PRODUCTS" ;
       Cursor cursor = db.rawQuery(select, null);
       if(cursor.getCount()<2) {

           values.put("NAME", "Sandisk Pendrive 16GB 3.0");
           values.put("DESCRIPTION", "This is a very Useful device with storage of 16GB");
           values.put("PRICE", 2000);
           values.put("RATING", 5.2);
           values.put("IMGURL", "https://5.imimg.com/data5/AS/YS/MY-7949025/64gb-usb-3-0-sandisk-pen-drive-500x500.jpg");
           values2.put("CATEGORY", "Electronics");
           db.insert("PRODUCTS",null,values);
           values2.put("NAME", "Round Neck Tshirt Black");
           values2.put("DESCRIPTION", "This is a Cotton T-shirt with round neck and black in color");
           values2.put("PRICE", 450);
           values2.put("RATING", 4.5);
           values2.put("IMGURL", "https://4.imimg.com/data4/TG/IS/MY-25346845/men-s-round-neck-t-shirt-500x500.jpg");
           values2.put("CATEGORY", "Clothing");
           db.insert("PRODUCTS",null,values2);
       }
   }

     public int checklogin(String PHONE, String password){
       SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT PASSWORD FROM USERS WHERE PHONE="+PHONE ;
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();
        String d_password=cursor.getString(0);
        if(d_password.equals(password)){
            String getid="SELECT ID FROM USERS WHERE PHONE="+PHONE;
            Cursor cursor1 = db.rawQuery(getid, null);
            cursor1.moveToFirst();
            int id=cursor1.getInt(0);
            return id;}
        else
            return -1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
