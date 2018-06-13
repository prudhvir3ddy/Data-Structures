package in.learncodeonline.datastrucutures;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout mDemoSlider;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<QuestionsModel> list;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview);
        collapsingToolbarLayout = findViewById(R.id.ctl);
        mDemoSlider = findViewById(R.id.place_image);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();

        Slider();

        collapsingToolbarLayout.setTitle("Data Structures");
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);

        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList("list");
        } else {
            getJsonData(getResources().getString(R.string.json_url));
        }

    }

    private void Slider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Learn Code online ", "https://learncodeonline.in/assets/img/slider1.png");
        url_maps.put("Tutor", "https://learncodeonline.in/assets/img/slider2.png");
        url_maps.put("Youtuber", "https://learncodeonline.in/assets/img/slider3.png");
        url_maps.put("hitesh choudhary", "https://www.hiteshchoudhary.com/imgs/Home.jpg");

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", list);
    }

    private void getJsonData(String jsonUrl) {
        AndroidNetworking.post(jsonUrl)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray questions = response.optJSONArray("questions");
                        for (int i = 0; i < questions.length(); i++) {
                            JSONObject questionAndAnswer = questions.optJSONObject(i);
                            QuestionsModel questionsModel = new QuestionsModel();
                            questionsModel.setQuestion(questionAndAnswer.optString("question"));
                            questionsModel.setAnswer(questionAndAnswer.optString("Answer"));
                            list.add(i, questionsModel);
                        }
                        adapter = new RecyclerViewAdapter(list, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "" + anError);
                    }
                });

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
