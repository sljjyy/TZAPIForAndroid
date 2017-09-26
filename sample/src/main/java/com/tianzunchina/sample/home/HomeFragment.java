package com.tianzunchina.sample.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.flyco.banner.anim.select.RotateEnter;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.anim.unselect.NoAnimExist;
import com.flyco.banner.transform.ZoomOutSlideTransformer;
import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.view.list.TZCommonAdapter;
import com.tianzunchina.android.api.view.list.TZViewHolder;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.event.EventActivity1;
import com.tianzunchina.sample.model.Advertisement1;
import com.tianzunchina.sample.model.News1;
import com.tianzunchina.sample.util.ocr.platerecognizer.ui.CameraActivity;
import com.tianzunchina.sample.view.WebViewActivity1;
import com.tianzunchina.sample.widget.ADImageBanner1;
import com.tianzunchina.sample.widget.ADItem1;
import com.tianzunchina.sample.widget.AppIco1;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by yqq on 2017/4/6.
 */

public class HomeFragment extends TZFragment  {

    private ADImageBanner1 adIBanner;
    private List<ADItem1> adItems = new ArrayList<>();
    View view;
    private ArrayList<AppIco1> appIcos = new ArrayList<>();
    ListView lvNewsList ;
    private TZCommonAdapter<News1> nAdapter;
    private List<News1> adNews = new ArrayList<>();
    private final int WITHOUT_PHOTO = 0, WITH_PHOTO = 1;
    SOAPWebAPI webAPI = new SOAPWebAPI();
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        getList();

        return view;
    }

    private void init(){
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("身边");
        lvNewsList = (ListView) view.findViewById(R.id.lvRecommendList);
        nAdapter = new TZCommonAdapter<News1>(this.getActivity(), adNews, R.layout.item_news) {

            @Override
            public int getItemViewType(int position) {
                return adNews.get(position).getType() == 0 ? WITHOUT_PHOTO : WITH_PHOTO;
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public void convert(TZViewHolder holder, News1 news, int position) {
                switch (news.getType()) {
                    case WITHOUT_PHOTO:
                        holder.setText(R.id.tvTitle, news.getTitle());
                        holder.setText(R.id.tvTime, news.getUpdateTimeStr());
                        holder.setText(R.id.tvContent, news.getContent());
                        holder.setVisible(R.id.ivPic, false);
                        break;
                    case WITH_PHOTO:
                        holder.setText(R.id.tvTitle, news.getTitle());
                        holder.setText(R.id.tvTime, news.getUpdateTimeStr());
                        holder.setImage(R.id.ivPic, "http://218.108.93.154:8090/ImgHandler.ashx?Path=" + news.getPaths().get(0), R.drawable.pic_loading);
                        holder.setVisible(R.id.tvContent, false);
                        break;
                    default:
                        break;
                }
            }
        };
        lvNewsList.setAdapter(nAdapter);
        lvNewsList.setOnItemClickListener((parent, view1, position, id) -> {

        });
        initGridView();

        getHomeAdvertisements();
        adIBanner = (ADImageBanner1) view.findViewById(R.id.adibanner);
    }
    //TODO 广告栏配置
    private void setAdData() {
        adIBanner.setSelectAnimClass(ZoomInEnter.class)
                .setSource(adItems)
                .setSelectAnimClass(RotateEnter.class)
                .setUnselectAnimClass(NoAnimExist.class)
                .setTransformerClass(ZoomOutSlideTransformer.class)
                .startScroll();
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppIco1 appIco = (AppIco1) parent.getAdapter().getItem(position);
            Class<?> toClass = appIco.getToClass();
            if (toClass != null) {
                intent = new Intent(getActivity(), toClass);
                startActivity(intent);

              /*  switch (appIco.getNameID()) {
                    case R.string.submit_event:
                        intent.putExtra("title", appIco.getTitle());
                        break;
                }*/
            }
        }
    }

    /**
     * 初始化首页图标
     */
    private void initGridView(){
        GridView gridview = (GridView) view.findViewById(R.id.app_gridView);
        initAppList();

        HomePageButtonAdapter adapter = new HomePageButtonAdapter(this.getActivity(), appIcos);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new ItemClickListener());

        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.llAppColumns);
        params.height = layout.getHeight();
        gridview.setLayoutParams(params);
    }
    //初始化图标
    synchronized private void initAppList() {
        appIcos.add(new AppIco1(EventActivity1.class,R.string.submit_event, R.mipmap.img_my_task_event));
        appIcos.add(new AppIco1(WebViewActivity1.class,R.string.web, R.mipmap.ico_xzsp));
      /*  appIcos.add(new AppIco1(R.string.office, R.mipmap.ico_chaxun));
        appIcos.add(new AppIco1(R.string.law, R.mipmap.ico_wenshu));
        appIcos.add(new AppIco1(R.string.move_car, R.mipmap.ico_move_car));
        appIcos.add(new AppIco1(R.string.comm, R.mipmap.ico_send_out));
        appIcos.add(new AppIco1(R.string.folder, R.mipmap.img_folder));*/

    }

    private void getList(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/HomePageService.asmx","GetTodayCustomNewsByRegion");
        tzRequest.addParam("UserID",60);
        tzRequest.addParam("SkipNumber",0);
        tzRequest.addParam("TakeNumber",5);
        tzRequest.addParam("RegionID",1);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("News");
                    int size = jsonList.length();
                    List<News1> newses = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        News1 news = new News1(jsonList.getJSONObject(i));
                        if (!newses.contains(news)) {
                            newses.add(news);
                        }
                    }
                    setNewsList(0, newses);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });

    }

    protected void setNewsList(int skip, List<News1> list) {
        if (skip == 0) {
            adNews.clear();
            adNews.addAll(list);
        } else {
            for (News1 t : list) {
                if (!adNews.contains(t)) {
                    adNews.add(t);
                }
            }
        }
        nAdapter.notifyDataSetChanged();
    }

    private void getHomeAdvertisements(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/HomePageService.asmx","GetAdvertisementsOfNewsColumnByRegion");
        tzRequest.addParam("ADTypeID", 1);
        tzRequest.addParam("RegionID", 1);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("Advertisements");
                    int adSize = jsonList.length();

                    adItems.clear();
                    for (int i = 0; i < adSize; i++) {
                        Advertisement1 advertisement = new Advertisement1(jsonList.getJSONObject(i));
                        adNews.add(new News1(advertisement.getNewsTitle(), advertisement.getNewsURL()));
                        String imgePath = advertisement.getAdPicURL();
                        adItems.add(new ADItem1(advertisement.getNewsTitle(), "http://218.108.93.154:8090/" + imgePath));
                    }
                    setAdData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });
    }
}
