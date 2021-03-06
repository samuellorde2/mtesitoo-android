package com.mtesitoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mtesitoo.ProductActivity;
import com.mtesitoo.R;
import com.mtesitoo.backend.model.Product;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
/**
 * Created by jackwu on 2015-07-11.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {

    private Context mContext;
    private float deviceWidth;
    private static ArrayList<Product> mProducts;
    private String uri;


    public ProductListAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        mContext = context;
        mProducts = products;

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
    }

    public void refresh(ArrayList<Product> products) {
        mProducts.clear();
        mProducts.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_item, parent, false);
        }

        int padding = Integer.parseInt(mContext.getString(R.string.padding));
        if (position == 0) {
            convertView.setPadding(padding, padding, padding, padding / 2);
        } else {
            convertView.setPadding(padding, padding / 2, padding, padding / 2);
        }

        ViewHolder holder = new ViewHolder(convertView);
        holder.context = mContext;
        holder.product = product;

        ViewGroup.LayoutParams params = holder.itemLayout.getLayoutParams();
        params.width = (int) deviceWidth;

        params = holder.layoutDivider.getLayoutParams();
        params.width = (int) (0.85 * deviceWidth);

        params = holder.productThumbnail.getLayoutParams();
        params.height = (int) (0.4 * deviceWidth);
        params.width = (int) (0.4 * deviceWidth);

        holder.productName.setText(product.getName());
        holder.productCategory.setText(product.getCategory());
        holder.productPrice.setText(product.getPricePerUnit());
        uri = product.getmThumbnail().toString();
        if(uri.contains(" ")){
            uri = uri.replace(" ","%20");
            Picasso.with(holder.context).load(uri).into(holder.productThumbnail);
        }
        else{
            Picasso.with(holder.context).load(uri).into(holder.productThumbnail);
        }

        return convertView;
    }

    static class ViewHolder {

        Product product;
        Context context;

        @Bind(R.id.product_list_item)
        LinearLayout itemLayout;
        @Bind(R.id.product_name)
        TextView productName;
        @Bind(R.id.product_thumbnail)
        ImageView productThumbnail;
        @Bind(R.id.product_category)
        TextView productCategory;
        @Bind(R.id.product_price)
        TextView productPrice;
        @Bind(R.id.product_layout_divider)
        View layoutDivider;

        @OnClick(R.id.product_see_details_link)
        public void onClick(View view) {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra(context.getString(R.string.bundle_product_key), product);
            context.startActivity(intent);
        }

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}