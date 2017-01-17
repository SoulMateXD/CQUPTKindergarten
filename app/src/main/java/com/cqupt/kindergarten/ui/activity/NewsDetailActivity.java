package com.cqupt.kindergarten.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.injection.component.DaggerNewsDetailActivityComponent;
import com.cqupt.kindergarten.injection.component.NewsDetailActivityComponent;
import com.cqupt.kindergarten.injection.module.NewsDetailActivityModule;
import com.cqupt.kindergarten.presenter.NewsDetailActivityPresenter;
import com.cqupt.kindergarten.ui.ui_interface.INewsDetailActivityInterface;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity implements INewsDetailActivityInterface {


    @Inject
    NewsDetailActivityPresenter mNewsDetailActivityPresenterm;
    private NewsDetailActivityComponent mNewsDetailActivityComponent;
    @BindView(R.id.img_news)
    ImageView mImgNews;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_news_title)
    TextView mTvNewsTitle;
    @BindView(R.id.tv_news_content)
    TextView mTvNewsContent;


    @Override
    public void setUpComponent() {
        if (mNewsDetailActivityComponent == null){
            mNewsDetailActivityComponent = DaggerNewsDetailActivityComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .newsDetailActivityModule(new NewsDetailActivityModule(this))
                    .build();
            mNewsDetailActivityComponent.inject(this);
        }
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("详情");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this)
                .load("http://pic.service.yaolan.com/32/20160906/98/1473134188002_1_w600_h400_o.jpg")
                .into(mImgNews);
        mTvNewsContent.setMovementMethod(LinkMovementMethod.getInstance());
        String str = "<div id=\"content_p\" class=\"cont_font114\"><div><img src=\"http://pic.service.yaolan.com/32/20160906/98/1473134188002_1_w600_h400_o.jpg\" alt=\"\" /></div><div>&nbsp;</div><div>&nbsp;</div><br />班主任吐槽<div>&nbsp;</div><div>老师与家长交流越来越离不开&ldquo;班级微信群&rdquo;，但不知从什么时候起，&ldquo;班级微信群&rdquo;开始变了味儿，炫富的、晒娃的、拉投票的、代购的、做<a href=/zhishi/gupiao/ target=\"_blank\" style=\"border-bottom-width:1px;border-bottom-style:dashed;\" title=\"股票\">股票</a>的&hellip;&hellip;</div><div>&nbsp;</div><div>有时我会很气愤，也很迷茫，家长都如此不遵<a href=/zhishi/shouguize/ target=\"_blank\" style=\"border-bottom-width:1px;border-bottom-style:dashed;\" title=\"守规则\">守规则</a>，又如何来教育孩子遵守规则呢？！</div><div>&nbsp;</div><div>随着微信越来越广泛的使用，&ldquo;家长微信群&rdquo;逐渐成了每个班级不可缺少的交流平台。老师们会通报孩子们的在校情况、发布重要通知；家长们有疑问也会在微信群上与老师沟通。但伴随着便利的同时，很多家长的行为让微信群&ldquo;变了味&rdquo;，给老师和家长们增添了许多烦恼。</div><div>&nbsp;</div><div>曾经有一段时间，老葛班主任一度想关闭班级微信群，问其原因，其皱着眉头说：&ldquo;是家长们的&lsquo;热情&rsquo;太过了！&rdquo;事情是这样的：</div><div>&nbsp;</div><div>老葛每次在微信群里说一句话，哪怕只是最正常的布置作业，就会有大波儿的家长回应：老师您辛苦了！老师太棒了！谢谢老师！&hellip;&hellip;</div><div>&nbsp;</div><div>有家长甚至还沿着这个话题闲聊起来。</div><div>&nbsp;</div><div>由此造成的后果是</div><div>&nbsp;</div><div>手机&ldquo;滴滴&rdquo;地响个没完，更重要的是，家长们如此一刷屏，很多没有第一时间看手机的家长，很有可能错过老师公布的重要信息。</div><div>&nbsp;</div><div>多多本身也是小学生妈妈，这个问题多多遇到过很多次，也听周围的妈妈们抱怨过很多次。甚至还有妈妈会指名道姓地说：&ldquo;那个XX的妈妈太烦人了，刷屏大王！&rdquo;&ldquo;XX孩子的爷爷、姥爷、爸爸、妈妈都在群里，一家子有时还在微信群里聊天，真是太过分了！&rdquo;&hellip;&hellip;</div><div>&nbsp;</div><div>这是个问题吗？绝对是问题，而且是个大问题！</div><div>&nbsp;</div><div>有办法解决吗？</div><div>&nbsp;</div><div>其实，方法很简单，只需老师逐条列出一些规则，在微信群里公布一下就可以了。</div><div>&nbsp;</div><div>当然，如果你所在的&ldquo;家长群&rdquo;暂时还没有规则，你可以把这篇文章分享给孩子的老师，或是直接将文章分享在&ldquo;家长群&rdquo;里。相信家长们看了之后，&ldquo;家长群&rdquo;会越来越有规则。</div><div>&nbsp;</div><div>&ldquo;经过多方采访及与众班主任商讨，建议家长们千万不要做以下类型的家长</div><div>&nbsp;</div><div>1讨好刷屏型</div><div>&nbsp;</div><div>1）老师一发话，马上回应：&ldquo;您辛苦了，保重身体&rdquo;。</div><div>&nbsp;</div><div>老师布置的作业、公布的消息，看到即可，不需要回复，为了防止没必要刷屏，&ldquo;收到&rdquo;两字都无需回复。</div><div>&nbsp;</div><div>2）你说好我跟一句，你说不好我也踩一下，一条信息几十人回复同样内容，很快一条重要的信息就这样被&ldquo;淹没&rdquo;了。</div><div>&nbsp;</div><div>群外私聊，切不可肆意刷屏。</div><div>&nbsp;</div><div>3）晚上10点之后还在家长群里发消息。</div><div>&nbsp;</div><div>晚上尽量不要在群里聊得太晚，以免影响他人休息，有重要事情可以跟老师直接联系。</div><div>&nbsp;</div><div>2炫富显摆型</div><div>&nbsp;</div><div>1）炫耀孩子的成绩。</div><div>&nbsp;</div><div>这种行为其实就是在默默伤害那些成绩差的孩子及家长的心。</div><div>&nbsp;</div><div>2）晒各种旅游的照片。</div><div>&nbsp;</div><div>类似这种私人的照片，在朋友圈晒晒也就可以了，发班级群只会引起大家反感。</div><div>&nbsp;</div><div>3）时不时把小孩得到的小贴画、完成的作业、写得工工整整的字贴上来，骄傲地请大家点赞，其实是希望老师给予关注。</div><div>&nbsp;</div><div>即使家长不到处晒，一个孩子的优秀锋芒也是遮挡不住的。低调的&ldquo;才子&rdquo;&ldquo;才女&rdquo;才是最令人佩服的，也最令老师喜爱。</div><div>&nbsp;</div><div>3过度关注型</div><div>&nbsp;</div><div>1）每日不停的在班群中询问老师孩子的情况，生怕孩子在学校出现新&ldquo;状况&rdquo;。</div><div>&nbsp;</div><div>家长如此不相信孩子，孩子又如何树立信息？又如何能长大？</div><div>&nbsp;</div><div>2）一个孩子，三代人都要加入班级群。</div><div>&nbsp;</div><div>孩子成长的压力就是这样形成的！家长越是这样，孩子成长的就会越累。</div><div>&nbsp;</div><div>4发布无关信息型</div><div>&nbsp;</div><div>1）集赞、投票、卖东西&hellip;&hellip;统统发进班级群。</div><div>&nbsp;</div><div>你希望别人指着你的孩子说：&ldquo;就是他妈妈，经常在班级群发卖东西的信息！&rdquo;所以，请给孩子留点面子！</div><div>&nbsp;</div><div>5群里争吵型</div><div>&nbsp;</div><div>1）孩子在学校发生了小争执，家长护子心切，在群里开战。</div><div>&nbsp;</div><div>你都如此冲动，又如何教育孩子不争吵、不打架、冷静地对待一切呢？请记住，你的行为在潜移默化地塑造着孩子的性格。</div><div>&nbsp;</div><div>2）与老师开战（这种情况很少，但确实也存在，例如，责备老师偏心）</div><div>&nbsp;</div><div>越是这种时候越应该与老师私下沟通。另外，家长一定要调整好心态，选择信任老师，这点很重要。</div><div>&nbsp;</div><div>&ldquo;当然，如果有老师看到这篇文章，那真是太好了！为了&ldquo;家长微信群&rdquo;更规范，也为了老师和家长的沟通更高效，在此，也为老师准备了几点小小的建议：</div><div>&nbsp;</div><div>1）在班级群中不点名<a href=/zhishi/pipinghaizi/ target=\"_blank\" style=\"border-bottom-width:1px;border-bottom-style:dashed;\" title=\"批评孩子\">批评孩子</a>、公布成绩、排名等信息，这只会伤害孩子自尊心，同时也会让家长感到不舒服。</div><div>&nbsp;</div><div>2）不要每次只发布优等生或表现优异的学生的照片，尽量让所有家长都能看到自己孩子的照片出现在班级群中。</div><div>&nbsp;</div><div>3）不管是成绩好的孩子还是成绩差的，都多表扬、少批评，尽可能多地去发现孩子的优点。</div><div>&nbsp;</div><div>4）绝不转发不经考证的信息，千万不要造成家长不必要的担心。</div><div>&nbsp;</div><div>5）如果个别学生有问题可单独与家长沟通，普遍问题可以在班群中与家长交流。</div><div>&nbsp;</div><div>6）试着在每一条通知后加上&ldquo;不用回复&rdquo;几个字或类似的话语，可以避免大量不必要信息的骚扰。</div><div>&nbsp;</div><div>7）做班级群中的引导者，对于一些不适合发在班级群里的内容，要学会婉言提醒。</div><div>&nbsp;</div></div>\n";
        mTvNewsContent.setClickable(true);
        mTvNewsContent.setText(Html.fromHtml(str));
        mTvNewsContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void getNewsDetail() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
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
