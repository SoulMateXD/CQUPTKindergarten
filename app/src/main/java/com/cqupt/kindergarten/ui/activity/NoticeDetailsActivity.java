package com.cqupt.kindergarten.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.util.ScreenUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.activity_news_toolbar)
    Toolbar activityNewsToolbar;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(activityNewsToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String str = "<h2 style=\"text-align: center;\"><b id=\"linkMyTitle\">于简单处安放自己</b></h2><p>简单之美是透着一种禅意，如天上轻盈的白云，随缘自在；如花中纯白，清新淡雅；如冬<a href=\"http://www.jj59.com/zti/xue/\">雪</a>初落，纯洁通透；如画中留白，给人以想象。简单，纯粹而通透，天然而不加雕琢。<br>　　<img src=\"http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/bc/otm_thumb.gif\"><br>　　用简单的心看世界，世界是澄澈的，明朗而美好；用简单的心去<a href=\"http://www.jj59.com/zti/shenghuo/\">生活</a>，生活是的诗意的；用简单的心，看待<a href=\"http://www.jj59.com/zheliwenzhang/\">人生</a>，人生是向上的。简单的人，不悲不喜，在清浅的岁月中，拥有一段平凡的人生，将旅途中的经历，安放在心中，用清淡的笔墨抒写自己的流年，看着走远的繁花似锦，不言执着，坐拥一季纯白，在岁月的素笺上轻轻描摹，<a href=\"http://www.jj59.com/zti/weixiao/\">微笑</a>着收藏，简单且<a href=\"http://www.jj59.com/zti/kuaile/\">快乐</a>着。<br>　　<br>　　人生的画卷铺开，简单便是生命的本<a href=\"http://www.jj59.com/zti/se/\">色</a>，自然更能贴近生活。世间路有千万条，每个人都有不同的活法，你有清风，我有俗世，我在凡尘中看烟火，你在尘世外养静心，或浓或淡，冷暖薄凉，能够活出自我便是灿烂，孤独是灵魂的盛放，喧嚣也是生命的累积。<br>　　<img src=\"http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/0b/tootha_thumb.gif\"><br>　　将世间<a href=\"http://www.jj59.com/zti/fanhua/\">繁华</a>，安放在安静处，写上平安与喜乐，慈悲与智慧，与光阴对话，寻找生活真意，心怀明亮和温暖，<a href=\"http://www.jj59.com/zti/yigeren/\">一个人</a>是诗，两个人是画，经历的所有都会成为沿途的风景，生命中的一切都无需拒绝，微笑着面对。<br>　　<br>　　一个简单的人，一定是心地纯净的，如冬雪，如春花，他们不奢望不强求，用最疏朗的线条，刻画自己的人生轨迹，懂得心安，便是活着最美好的状态，读懂了生命，也就是读懂了自己的本心，接近心中那个最好的自己。<br>　　<br>　　因为简单，去除了许多尘烟烦忧，回归朴实天然，不饰雕琢，日子，心安身暖便可，赏心也只需三两枝，拥有太多的东西便会负累。尘世繁华，有多少人，能将日子过到恬淡清宁？简单，是尝过百味过后的淡然，和年龄无关，与心境有关。<br>　　<br>　　做一个简单的人，把一颗凡俗的心安放在最合适的地方，不惧怕光阴，春来看花，夏来赏荷，秋来观月，冬来听雪，无论何时，都要拥抱着阳光，踏实地走好每一步，努力的适应身边的环境，认真细致的过好每一天。<br>　　<br>　　冷暖交织的岁月，总是让我们无从适应，如若，你总是在追逐季节的脚步，就会因一直在奔跑而负累，不如换个活法，偶尔慢下来，等一等灵魂，倘若简单了，你就会发现，每个季节里都有美好，每一个日子也都藏有诗意。<br>　　<br>　　简单的人能静处于日月，也能热闹于市井，因为有一份安宁的心境，最好的世界不在别人那里，心在清幽处，再大的喧嚣不过是沿途的风景。花开花落间，最美不过秋月，最暖不过人心，来去皆是真，不沉陷过去，不幻想于未来，修得淡泊平静，就不会有纠缠与烦忧，不苛求生活的人，生活也不会为难你。<br>　　<br>　　一个简单的人，不会因为曾经的挫折坎坷而愤愤不平，也不会因为未来道路上的起起伏伏而畏首畏脚，更不会因为偶尔命运的不公而<a href=\"http://www.jj59.com/liuxingfeizhuliu/\">颓废</a>低迷，他会把这一切当成一种考验，用最温柔的力量，战胜一切不可跨越的困难，懂得用一颗感恩的心，来对待生活的给予，生命的路上也许偶尔会遇到阴云雾霾，但你要学会依着风雨前行，只要中有阳光，定会绽放最美的花朵。<br>　　<br>　　喜欢这样一种生活态度，不争辩，不解释，与时光相安，于岁月中与人温暖，淡淡的微笑生活，淡然中，有原谅，也有宽容，有安然，也会有期待，因而便会有美。喜欢一种简单，只浅浅的喜欢，淡淡的爱，依着阳光而行，如山谷里开着的花朵，无人知晓，却自带芬芳，对与错，荣与枯都做自然，珍惜会意的美，安住<a href=\"http://www.jj59.com/zti/renjian/\">人间</a>，看花开花落，春来秋往。<br>　　<br>　　简单，就是用一杯水的纯净，来面对一辈子的繁杂，其实人生最重要的是，让自己<a href=\"http://www.jj59.com/zti/xingfu/\">幸福</a>，很多时候，我们违背了初衷，在年月深长的路上，走的行色匆匆，被生活的荆棘磨砺着，靠不了岸。倘若一个人，能读懂简单的真正含义，他一定是经历了一个复杂的过程，才懂得删繁就简，一切从容。<br>　　<br>　　因为简单，去除了许多尘烟烦忧，回归朴实天然，不饰雕琢，日子，心安身暖便可，赏心也只需三两枝，拥有太多的东西便会负累。尘世繁华，有多少人，能将日子过到恬淡清宁？简单，是百味过后的放下和懂得，这份淡然，和年龄无关，与心境有关。<br>　　<br>　　简单的人，一杯茶也会品出云淡风轻，一朵花也能写意一处风景，不取悦，不疏离，心甘情愿的接受眼前的一切，懂得欣赏，于尘烟中见月朗，在百花中寻芬芳，活得清澈，走的宽阔疏朗，它们不会把太多的人请进生命里，也不会负累前行，在内心修篱种菊，知足而平和，于简单中书写生命的最美！<img src=\"http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/0b/tootha_thumb.gif\" style=\"color: inherit;\"></p>\n" +
                "                    <p><br></p>\n";
        RichText.initCacheDir(this);
        RichText.fromHtml(str)
                .scaleType(ImageHolder.ScaleType.FIT_CENTER)
                .fix(new ImageFixCallback() {
                    @Override
                    public void onInit(ImageHolder holder) {

                    }

                    @Override
                    public void onLoading(ImageHolder holder) {

                    }

                    @Override
                    public void onSizeReady(ImageHolder holder, int imageWidth, int imageHeight, ImageHolder.SizeHolder sizeHolder) {

                    }


                    @Override
                    public void onImageReady(ImageHolder holder, int width, int height) {
                        int ScreenWidth = ScreenUtils.getScreenWidth(getApplicationContext());
                        if (ScreenWidth > width)
                            holder.setSize(width, height);
                    }

                    @Override
                    public void onFailure(ImageHolder holder, Exception e) {

                    }
                })//设置图片最大宽度
                .into(tvContent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
