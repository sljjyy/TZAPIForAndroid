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
import com.tianzunchina.sample.event.EventActivity;
import com.tianzunchina.sample.widget.ADImageBanner;
import com.tianzunchina.sample.widget.ADItem;
import com.tianzunchina.sample.widget.AppIco;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by yqq on 2017/4/6.
 */

public class HomeFragment extends TZFragment implements AdapterView.OnItemClickListener {

    private ADImageBanner adIBanner;
    private List<ADItem> adItems = new ArrayList<>();
    View view;
    private ArrayList<AppIco> appIcos = new ArrayList<>();
    ListView lvNewsList ;
    private TZCommonAdapter<News> nAdapter;
    private List<News> recommends = new ArrayList<>();
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
        lvNewsList = (ListView) view.findViewById(R.id.lvRecommendList);
        nAdapter = new TZCommonAdapter<News>(this.getActivity(), recommends, R.layout.item_news) {

            @Override
            public int getItemViewType(int position) {
                return recommends.get(position).getType() == 0 ? WITHOUT_PHOTO : WITH_PHOTO;
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public void convert(TZViewHolder holder, News news, int position) {
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
        lvNewsList.setOnItemClickListener(this);
        initGridView();

        adIBanner = (ADImageBanner) view.findViewById(R.id.adibanner);
        adItems.add(new ADItem("风景1", "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=风景&step_word=&hs=0&pn=0&spn=0&di=1&pi=0&rn=1&tn=baiduimagedetail&is=&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2406026261%2C2423921907&os=2470393457%2C4049286471&simid=&adpicid=0&lpn=0&ln=1990&fr=&fmq=1459502303089_R&fm=&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2F201304%2F10%2F104028xfxsklfilosaa1jh.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fkkf_z%26e3B2uwg_z%26e3Bv54AzdH3Fwg165t1-macbclb-8-8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0"));
        adItems.add(new ADItem("风景2", "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=风景&step_word=&hs=0&pn=0&spn=0&di=1&pi=0&rn=1&tn=baiduimagedetail&is=&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2406026261%2C2423921907&os=2470393457%2C4049286471&simid=&adpicid=0&lpn=0&ln=1990&fr=&fmq=1459502303089_R&fm=&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2F201304%2F10%2F104028xfxsklfilosaa1jh.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fkkf_z%26e3B2uwg_z%26e3Bv54AzdH3Fwg165t1-macbclb-8-8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0"));
        adItems.add(new ADItem("风景3","https://chowsangsang.tmall.com/?spm=a1z10.1-b-s.w5001-14824279996.9.HrMaJh&scene=taobao_shop&mm_gxbid=1_1309191_7ba63d076ba2b19767f08fece0312d28"));
        setAdData();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppIco appIco = (AppIco) parent.getAdapter().getItem(position);
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
        appIcos.add(new AppIco(EventActivity.class,R.string.submit_event, R.mipmap.img_my_task_event));
        appIcos.add(new AppIco(R.string.xzsp, R.mipmap.ico_xzsp));
        appIcos.add(new AppIco(R.string.office, R.mipmap.ico_chaxun));
        appIcos.add(new AppIco(R.string.law, R.mipmap.ico_wenshu));
       /* appIcos.add(new AppIco(R.string.move_car, R.mipmap.ico_move_car));
        appIcos.add(new AppIco(R.string.comm, R.mipmap.ico_send_out));
        appIcos.add(new AppIco(R.string.folder, R.mipmap.img_folder));*/

    }

    private void getList(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/HomePageService.asmx","GetTodayCustomNewsByRegion");
        tzRequest.addParam("UserID",10);
        tzRequest.addParam("SkipNumber",0);
        tzRequest.addParam("TakeNumber",5);
        tzRequest.addParam("RegionID",33);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("News");
                    int size = jsonList.length();
                    List<News> newses = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        News news = new News(jsonList.getJSONObject(i));
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

    protected void setNewsList(int skip, List<News> list) {
        if (skip == 0) {
            recommends.clear();
            recommends.addAll(list);
        } else {
            for (News t : list) {
                if (!recommends.contains(t)) {
                    recommends.add(t);
                }
            }
        }
        nAdapter.notifyDataSetChanged();
    }
}
