package com.mtesitoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mtesitoo.backend.model.Seller;
import com.mtesitoo.fragment.HelpFragment;
import com.mtesitoo.fragment.InfoFragment;
import com.mtesitoo.fragment.OrderFragment;
import com.mtesitoo.fragment.ProductFragment;
import com.mtesitoo.fragment.ProfileFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    private Context mContext;
    private Seller mSeller;

    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mContext = this;
        mSeller = getIntent().getExtras().getParcelable(getString(R.string.bundle_seller_key));

        setSupportActionBar(toolbar);
        buildNavigationDrawer();

        ProductFragment f = ProductFragment.newInstance(this, mSeller);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_product) {
            Intent intent = new Intent(this, AddProductActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buildNavigationDrawer() {
        final IProfile profile = new ProfileDrawerItem()
                .withName(mSeller.getUsername())
                .withEmail(getString(R.string.role_seller));

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .withCompactStyle(true)
                .addProfiles(profile)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)

                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_home)
                                .withIcon(GoogleMaterial.Icon.gmd_home)
                                .withIdentifier(Integer.parseInt(mContext.getString(R.string.menu_item_home_index)))
                                .withSelectable(false),
                        new SectionDrawerItem()
                                .withName(R.string.drawer_item_section_header_account),
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_profile)
                                .withIcon(GoogleMaterial.Icon.gmd_face)
                                .withIdentifier(Integer.parseInt(mContext.getString(R.string.menu_item_profile_index)))
                                .withSelectable(false),
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_orders)
                                .withIcon(GoogleMaterial.Icon.gmd_history)
                                .withIdentifier(Integer.parseInt(mContext.getString(R.string.menu_item_order_index)))
                                .withBadgeStyle(new BadgeStyle()
                                        .withTextColor(Color.WHITE)
                                        .withColorRes(R.color.md_red_700)),
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_new)
                                .withIcon(GoogleMaterial.Icon.gmd_add)
                                .withIdentifier(Integer.parseInt(mContext.getString(R.string.menu_item_add_product_index))),
                        new SectionDrawerItem()
                                .withName(R.string.drawer_item_section_header_app),
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_info)
                                .withIcon(GoogleMaterial.Icon.gmd_info_outline)
                                .withIdentifier(Integer.parseInt(mContext.getString(R.string.menu_item_info_index)))
                                .withSelectable(false),
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_help)
                                .withIcon(GoogleMaterial.Icon.gmd_help_outline)
                                .withIdentifier(Integer.parseInt(mContext.getString(R.string.menu_item_help_index)))
                                .withSelectable(false),
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_exit)
                                .withIcon(GoogleMaterial.Icon.gmd_exit_to_app)
                                .withIdentifier(Integer.parseInt(mContext.getString(R.string.menu_item_exit_index)))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Fragment f = null;

                            if (drawerItem.getIdentifier() == Integer.parseInt(mContext.getString(R.string.menu_item_home_index))) {
                                f = ProductFragment.newInstance(mContext, mSeller);
                            } else if (drawerItem.getIdentifier() == Integer.parseInt(mContext.getString(R.string.menu_item_profile_index))) {
                                f = ProfileFragment.newInstance();
                            } else if (drawerItem.getIdentifier() == Integer.parseInt(mContext.getString(R.string.menu_item_order_index))) {
                                f = OrderFragment.newInstance(mContext, mSeller);
                            } else if (drawerItem.getIdentifier() == Integer.parseInt(mContext.getString(R.string.menu_item_add_product_index))) {
                                Intent intent = new Intent(mContext, AddProductActivity.class);
                                mContext.startActivity(intent);
                            } else if (drawerItem.getIdentifier() == Integer.parseInt(mContext.getString(R.string.menu_item_info_index))) {
                                f = InfoFragment.newInstance();
                            } else if (drawerItem.getIdentifier() == Integer.parseInt(mContext.getString(R.string.menu_item_help_index))) {
                                f = HelpFragment.newInstance();
                            } else if (drawerItem.getIdentifier() == Integer.parseInt(mContext.getString(R.string.menu_item_exit_index))) {
                                finish();
                            }

                            if (f != null) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                            }
                        }

                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();

        result.updateBadge(3, new StringHolder(10 + ""));
    }
}