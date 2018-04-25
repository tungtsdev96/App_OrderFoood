package com.ptpmcn.orderfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.activity.DetailFoodActivity;
import com.ptpmcn.orderfood.adapter.food.ClickDeteteLike;
import com.ptpmcn.orderfood.adapter.food.FoodLinearAdapter;
import com.ptpmcn.orderfood.api.ApiFactory;
import com.ptpmcn.orderfood.api.BaseCallBack;
import com.ptpmcn.orderfood.interfaces.AlertListener;
import com.ptpmcn.orderfood.interfaces.LoadMoreRcvListener;
import com.ptpmcn.orderfood.interfaces.RecycleViewItemClick;
import com.ptpmcn.orderfood.model.Food;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.util.ArrayList;

import okhttp3.ResponseBody;

/**
 * Created by tungts on 12/11/2017.
 */

public class ListFoodLikedFragment extends BaseFragment implements RecycleViewItemClick, LoadMoreRcvListener, ClickDeteteLike {

    RecyclerView rcv_food_liked;
    FoodLinearAdapter foodLinearAdapter;
    ArrayList<Food> arrFoodLiked = new ArrayList<>();
    int page = 1;

    public static ListFoodLikedFragment newInstance(){
        ListFoodLikedFragment listFoodLikedFragment = new ListFoodLikedFragment();
        return listFoodLikedFragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_rcv;
    }

    @Override
    protected void addControls() {
        innitRecyvleView();
    }

    private void innitRecyvleView() {
        rcv_food_liked = root.findViewById(R.id.rcv_);
        foodLinearAdapter = new FoodLinearAdapter(getContext(),arrFoodLiked,rcv_food_liked, Constant.Key.KEY_LIKE);
        rcv_food_liked.setAdapter(foodLinearAdapter);
        rcv_food_liked.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_food_liked.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        foodLinearAdapter.setOnRecycleViewItemClick(this);
        foodLinearAdapter.setOnLoadMoreRcvListener(this);
        foodLinearAdapter.setOnClickDeteteLike(this);
    }

    @Override
    protected void innitData() {
        loadFood(page);
    }

    private void loadFood(int page) {
        ApiFactory.getApiFoods().getListFoodLikedByIdCustomer(AccountUtil.fakeCustomer().getCustomer_id(),page).enqueue(new BaseCallBack<ArrayList<Food>>(getContext()) {
            @Override
            public void onSuccess(ArrayList<Food> result) {
                if (arrFoodLiked.size()>0 && arrFoodLiked.get(arrFoodLiked.size()-1) == null){
                    arrFoodLiked.remove(arrFoodLiked.size()-1);
                }
                arrFoodLiked.addAll(result);
                foodLinearAdapter.notifyDataSetChanged();
                foodLinearAdapter.setLoading();
            }
        });
    }

    @Override
    public void onItemClickRecycleView(int postion) {
        Intent intent = new Intent(getActivity(),DetailFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Food",arrFoodLiked.get(postion));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemLongClickRecycleView(int postion) {

    }

    @Override
    public void onLoadMore() {
        arrFoodLiked.add(null);
        foodLinearAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFood(page++);
            }
        },2000);
    }

    @Override
    public void deleteLike(final int postiton) {
        showAlert("Thông báo", "Bạn có chắc chắn muốn xóa", "Xóa", "Quay lại", new AlertListener() {
            @Override
            public void onYesClicked() {
                ApiFactory.getApiCustomer().unLike(arrFoodLiked.get(postiton).getProduct_id(),AccountUtil.fakeCustomer().getCustomer_id()).enqueue(new BaseCallBack<ResponseBody>(getContext()) {
                    @Override
                    public void onSuccess(ResponseBody result) {
                        Toast.makeText(getContext(), "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        foodLinearAdapter.notifyItemRemoved(postiton);
                    }
                });
            }

            @Override
            public void onNoClicked() {
            }
        });
    }
}
