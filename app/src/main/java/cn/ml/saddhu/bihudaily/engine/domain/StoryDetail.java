package cn.ml.saddhu.bihudaily.engine.domain;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.List;

/**
 * Created by sadhu on 2017/2/22.
 * Email static.sadhu@gmail.com
 * Describe:文章详情
 */
public class StoryDetail {

    /**
     * body : <div class="main-wrap content-wrap">
     * <div class="headline">
     * <p>
     * <div class="img-place-holder"></div>
     * <p>
     * <p>
     * <p>
     * </div>
     * <p>
     * <div class="content-inner">
     * <p>
     * <p>
     * <p>
     * <div class="question">
     * <h2 class="question-title"></h2>
     * <div class="answer">
     * <p>
     * <div class="meta">
     * <img class="avatar" src="http://pic3.zhimg.com/67dc209d4e00b276437d1e2816b4786a_is.jpg">
     * <span class="author">Lens，</span><span class="bio">&quot;即使是一道最微弱的光，我们也要把它洒向需要温暖的生活……&quot;</span>
     * </div>
     * <p>
     * <div class="content">
     * <p>文章来自<a href="https://www.zhihu.com/org-intro">机构帐号</a>：<a href="https://www.zhihu.com/org/lens-27">Lens</a></p>
     * <hr />
     * <p>这个冬日，《四月物语》里的暗恋少女、《告白》中的复仇教师&mdash;&mdash;松隆子，时隔五年主演了一部日剧，名叫<strong>《四重奏》</strong>。</p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-ad8a956d0ecb42b1d03424199f6a6a52_b.jpg" alt="" /></p>
     * <p><em>除去松隆子，其他主演还包括满岛光、高桥一生以及松田龙平</em></p>
     * <p>各怀心思的两男两女，&ldquo;偶然&rdquo;相遇，随即组成了四重奏，在轻井泽开始了合租生活。&ldquo;全员单恋，全员说谎&rdquo;的宣传语，似乎泄露了这个故事的少许酸涩，和难言之隐。</p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-7157efa41f4f6db95553bbf68bf86fda_b.jpg" alt="" /></p>
     * <p><em>上图松隆子所说的这句话，或许是这四个人能够聚在一起的原因之一</em></p>
     * <p>不少人对这部《四重奏》怀有很高的期待，卡司的强大是一个原因。同时还有另一个质量保障：<strong>它的编剧是曾写出《东京爱情故事》剧本的坂元裕二。</strong></p>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><img class="content-image" src="https://pic4.zhimg.com/v2-264c7bb8e4137506f591703669823727_b.jpg" alt="" /></p>
     * <p>&ldquo;丸～子～&rdquo;，名叫赤名莉香的女孩，眼睛笑得像月牙一样，叫着自己心爱男子的名字。</p>
     * <p>她是上世纪 90 年代，一部名叫《东京爱情故事》（以下简称《东爱》）的日剧女主角。</p>
     * <p>在播放最后一集时，日本 OL 纷纷消失在夜晚的街头，蹲在家里收看。1995 年它被上海电视台引进到中国，更是影响了一批人的恋爱观。</p>
     * <p><img class="content-image" src="https://pic1.zhimg.com/v2-c5e2f24ea8f6e01c2471594765c2d0cc_b.jpg" alt="" /></p>
     * <p>现在想来，《东爱》的剧情也没有什么大不了。就是一段老套的多角恋爱：从小城市到东京打拼的永尾完治，与活泼的海归白领赤名莉香相恋。但在完治心中，却藏着自己年少时的初恋关口里美。而里美又倾心于完治的好友&mdash;&mdash;&ldquo;浪子&rdquo;三上健一。</p>
     * <p>但看过《东爱》的人仍然对它念念不忘。有一位网友这样总结：<strong>想来想去，我看过唯一一部把重心真正落在&ldquo;爱情&rdquo;上的偶像剧，就是这一部。</strong></p>
     * <p>是呀，特别是那个敢爱敢恨、为爱付出一切的莉香，仿佛就是爱情最真切的模样。</p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-aba5540e0104168c7f252df8dc5a4d62_b.jpg" alt="" /></p>
     * <p>在被告知完治早已心有所属时，她会倔强地反问：&ldquo;<strong>那又怎样？</strong>&rdquo;</p>
     * <p>当完治被梦中情人拒绝时，她会深夜在电话中安慰：&ldquo;<strong>恋爱啊，只要参与了就有意义。即便没有结局，喜欢上另一个人的瞬间，是会永远留下来的。</strong>&rdquo;</p>
     * <p>完治的摇摆不定令她气愤，她会说：&ldquo;<strong>你的心只有一个，并不是有两个啊，你到底把心放在哪里呢？你要好好抓住我，你的眼中只能有我，不然，我会跑掉的。</strong>&rdquo;</p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-3125d3f13974060a2efdead3ce9df21e_b.jpg" alt="" /></p>
     * <p>最后完治还是选择了和自己更为相似的里美。观者为莉香流泪，为她打抱不平，却也黯然明白了一个道理：爱情并不是谁付出的多，谁就会得到胜利。</p>
     * <p>赤名莉香的成功塑造，离不开写出剧本的编剧坂元裕二。<strong>而这一年，他只有 23 岁。</strong></p>
     * <p>在原著漫画作者柴文门的笔下，莉香并不像电视剧中的这般纯粹，她会背叛男友和别的男人相爱，对待爱情时常暧昧。</p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-81d987f0d4fed62101492a8ef2857832_b.jpg" alt="" /></p>
     * <p>改变这一切的就是坂元裕二，他参考了当时扮演者铃木保奈美的形象，重构了赤名莉香这一角色。</p>
     * <p>那个时候的坂元，还不懂什么是高超的技法、深远的立意。助他完成这一经典纯爱故事的原因，或许正如他自己所说：<strong>&ldquo;那时的我对爱情还有憧憬。&rdquo;</strong></p>
     * <p><img class="content-image" src="https://pic4.zhimg.com/v2-f24acb76e75c450781a79cb412dcd353_b.jpg" alt="" /></p>
     * <p>现在的坂元裕二已经 50 岁了，对于爱情也许并不像以前那样炙热，但他的创作激情一直还在。</p>
     * <p>特别是在 2010 年以后，他写下了不少经典的剧本。风格虽然不一，但内核都是动人的情感。剧中的台词，也时常戳进人们的心坎里。</p>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><strong>《Mother》，2010</strong></p>
     * <p><img class="content-image" src="https://pic1.zhimg.com/v2-788ee8f12a64fce4b4004b7f9df754a0_b.jpg" alt="" /></p>
     * <p><em>主演：松雪泰子 / 芦田爱菜 </em></p>
     * <p>有网友这样描述自己对《Mother》的喜爱：&ldquo;<strong>如果这是篇作文，一定会出现在满分作文选。</strong><strong>&rdquo;</strong>它是一篇关于母爱的命题作文，更妙的是它发生在毫无血缘关系的女人和女孩身上。</p>
     * <p><img class="content-image" src="https://pic1.zhimg.com/v2-5f28746da37ea0734d756ee25230a560_b.jpg" alt="" /></p>
     * <p>在小学教员奈绪眼里，七岁的怜南是会在大人面前露出灿烂笑容的早熟女孩。直到一个冬夜，奈绪在路边发现了被母亲丢弃在垃圾袋中的怜南。</p>
     * <p>这个连恋爱都没有谈过，眉宇间总是藏着哀愁的女人奈绪，此刻却愿意为这个可怜的小女孩，做一次母亲。<strong>于是她&ldquo;诱拐&rdquo;了她。</strong></p>
     * <p><img class="content-image" src="https://pic1.zhimg.com/v2-9f9092fb33ed84dfe1d1f7f5da86b574_b.jpg" alt="" /></p>
     * <p>奈绪带怜南离开了寒冷的北国，给她取了一个新名字：继美，音同候鸟。其实奈绪在五岁时，同样被生母狠心抛弃，一直由养母抚养长大。或许她自己也是一只渴望回归母爱的候鸟。</p>
     * <p><img class="content-image" src="https://pic2.zhimg.com/v2-0539b70ca11423ab6926062f68b1fedd_b.jpg" alt="" /></p>
     * <p><em>这场逃亡，让奈绪和自己的生母及养母重逢</em></p>
     * <p>最后，命运还是拆散了这对&ldquo;母女&rdquo;。离别前，奈绪写了一封信给二十岁的怜南：</p>
     * <blockquote>20 岁的继美，你现在是什么样的女孩呢？你成为什么样的大人了呢？相遇时才 104cm 的你，现在穿着流行的服装，穿着小小的 16.5cm 的鞋子的你，现在穿着高跟鞋，走到我面前擦肩而过的瞬间，我要怎么称呼你呢？<br /><br />&hellip;&hellip;<br /><br />遇见你真好，能成为你妈妈真好。和你度过的季节，成为你妈妈的季节，对我来说，是现在的全部。然后，和你再次重逢的季节，对我来说，将会是今后要打开的宝箱。<br /><br />我爱你！</blockquote>
     * <p>这封信奔溃了很多人的泪腺，编剧坂元裕二的&ldquo;目的&rdquo;达到了。</p>
     * <p>&ldquo;并不是因为喜欢写独白。但是，<strong>平常不太写的东西，偶尔做一次，就会变成必杀技一样的存在</strong>。&rdquo;</p>
     * <p>这俨然成为了坂元裕二式日剧的必杀技。那掩不住的真情藏在琐碎的日常中，通过信件娓娓道来，一不小心就温暖了你我他。</p>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><strong>《最完美的离婚》，2013</strong></p>
     * <p><img class="content-image" src="https://pic2.zhimg.com/v2-fb183aefa463e9a624553175a3ece009_b.jpg" alt="" /></p>
     * <p><em>主演：瑛太 / 尾野真千子 / 真木阳子 / 绫野刚 /</em></p>
     * <p>不再是《东爱》里爱与不爱的选择题，《最完美的离婚》呈现的，是在鸡毛蒜皮的生活中，爱情是如何被渐渐消磨的。</p>
     * <p>这是坂元的&ldquo;与时俱进&rdquo;之作，一不留神还窥见了婚姻的实质。</p>
     * <p><img class="content-image" src="https://pic2.zhimg.com/v2-ef6310454df792f03fc1dfae82a819e9_b.jpg" alt="" /></p>
     * <p>光生是地震后会第一时间询问&ldquo;我的盆栽没事吧&rdquo;、带点强迫症的自我男人；而他的妻子结夏，是看着富士山长大、大大咧咧的真性情女人。</p>
     * <p>性格和价值观南辕北辙的两个人，竟然结成了夫妻，&ldquo;离婚&rdquo;似乎变成了生活的主旋律。一些&ldquo;金句&rdquo;也随之蹦了出来。</p>
     * <p><img class="content-image" src="https://pic4.zhimg.com/v2-1df233e79af0faf95db674e0f2dd505b_b.jpg" alt="" /></p>
     * <p>比如，维修大叔这样吐槽单身生活：<strong>&ldquo;两个人一起吃的是饭，一个人吃的叫饲料。&rdquo;</strong></p>
     * <p><img class="content-image" src="https://pic2.zhimg.com/v2-5b25a7adab44c3f0d965c2bce4fd459d_b.jpg" alt="" /></p>
     * <p>奶奶这样讲述着恋爱中男女的不同：&ldquo;<strong>女人只要喜欢上对方，就全部都能原谅。但男人却相反，喜欢上了就会逐渐去挑剔女人的缺点。</strong> &rdquo;</p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-a25152cba447d287755cf0f27c66593a_b.jpg" alt="" /></p>
     * <p>男二这样理解婚姻：&ldquo; <strong>我不认为离婚是最坏的结局。最坏的结局不是离婚，而是成为面具夫妇。对对方没有爱，也没有任何期待，却在一起生活，这才是最大的不幸。</strong>&rdquo;</p>
     * <p>故事的最后，结夏还是离开了光生。不是因为不爱，恰恰是因为喜欢光生的那些有趣特质，不想强行改变他，而选择了分手。</p>
     * <p><img class="content-image" src="https://pic1.zhimg.com/v2-a7c5ddaf390e935275c79000bb22363c_b.jpg" alt="" /></p>
     * <p>在 14 年的特别篇中，光生开始了一个人的生活。当中了二等奖却无人分享喜悦，走到阳台冰冷的雪花碰触到手尖时，他终于意识到生活中少了什么。</p>
     * <p><img class="content-image" src="https://pic1.zhimg.com/v2-8508ebe2c2a0b3444a83aaa808ce9e04_b.jpg" alt="" /></p>
     * <p>于是他拿出信纸，带着一点羞涩，和几分笨拙，给远方的结夏写信。</p>
     * <p>信是这么开头的：</p>
     * <blockquote>春寒料峭，你是否安好？没有感冒吧？没有长冻疮吧？对不起，突然给你写信。如果能承蒙你在寒夜中雅鉴，不胜荣幸。<br /><br /><br />首先，向你报告，在我们家即将迎来第三个年头的两只猫的情况&hellip;&hellip;</blockquote>
     * <p><img class="content-image" src="https://pic2.zhimg.com/v2-b3fbbc469fc27c7993149b990db32859_b.jpg" alt="" /></p>
     * <p>末了，信是如此结尾的：</p>
     * <blockquote>总有一天会觉得这样的想法太过愚蠢，却还是这样想着。在夜色中散步，猜拳决定，吃着烤红薯，笑着，牵着手，含着满口的烤红薯，再说起同样的话。我们在一起的话会很开心吧？一起慢慢变老吧。 <br />可以嫁给我吗？<br /><br />2014 年 2 月 8 日 <br />我在目黑川岸边的旧公寓，和两只猫一起，等待着春天的来临。</blockquote>
     * <p><img class="content-image" src="https://pic1.zhimg.com/v2-4080d2ce58f16bfa1e1c93378d76ff40_b.jpg" alt="" /></p>
     * <p>最后的光生，终于完成了迟到的告白。而结局呢，就留给观众去猜了。</p>
     * <p>回到剧名，为什么离婚还有完美可言？编剧坂元裕二是这样回答的：&ldquo;<strong>主要是两个人有沟通，这个结果是最重要的。</strong>&rdquo;</p>
     * <p><strong><strong>▽</strong></strong></p>
     * <p>除了以上这两部剧外，坂元近年来还创作了不同题材的日剧：刻画凶手和被害人家庭的《尽管如此 也要活下去》、描写单亲妈妈不易的《Woman》、关注女性性别歧视的《问题餐厅》。</p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-efda8e30e26dbba7401b58adf6244e5e_b.jpg" alt="" /></p>
     * <p><em>去年，坂元裕二回归月九，创作出《追忆潸然》，呈现了东京打工族的群像爱情</em></p>
     * <p>日本导演是枝裕和，认为坂元裕二和自己在某方面像得出奇：他们都是被时代牵引着一路走来。</p>
     * <p>是枝还称赞坂元是个细腻的人，&ldquo;<strong>1991 年的《东爱》到现在，已经经过了四分之一个世纪，坂元尚且还保存着这份细腻，这一点或许就是他的强硬之处。</strong>&rdquo;</p>
     * <p>这么多年来，坂元很少参加采访，而是把那些想说的话藏进剧本里。</p>
     * <p>最后，在这个寒冷的冬夜，让我们重温一下那部经典的《东爱》。听听坂元那时眼里的爱情❤️</p>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><img class="content-image" src="https://pic4.zhimg.com/v2-9c011fea5f49cea24d0de1c5044eb06f_b.jpg" alt="" /></p>
     * <blockquote>我爱你并不是因为你是谁，而是我在你身边的时候我是谁。</blockquote>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><img class="content-image" src="https://pic2.zhimg.com/v2-daa85394427a9c13a41e8236fd8c79a1_b.jpg" alt="" /></p>
     * <blockquote>现代人不缺爱情，或者说不缺貌似爱情的东西，但是寂寞的感觉依然挥之不去。我们可以找个人来谈情说爱，但是，却始终无法缓解一股股涌上心头的落寞荒芜。爱情不是便当，它们依然需要你的郑重其事。</blockquote>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><img class="content-image" src="https://pic4.zhimg.com/v2-23c3e6602f26182b5ce5c36ddf63e3a7_b.jpg" alt="" /></p>
     * <blockquote>不是想着明天爱情会变得怎样而谈着恋爱的。就是有那时候的我，才有现在的自己。我真的能够对自己说：你做得真好。</blockquote>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-70d4ba57c262443519653fdc6c6039d2_b.jpg" alt="" /></p>
     * <blockquote>莉香：当你爱上一个人的时候，哪有什么对与错呢？<br /><br />三上：这倒没有，只不过你的爱会让人喘不过气来。<br /><br />莉香：我知道，可是没办法啊，我只会这一种爱人的方式啊。</blockquote>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><img class="content-image" src="https://pic3.zhimg.com/v2-3a4683690b80726c7eb33fea849f3a46_b.jpg" alt="" /></p>
     * <blockquote>在我还很年轻的时候，根本不知何为恐惧，但是只有你的温柔，会让我感到害怕。</blockquote>
     * <p><strong><strong>▽</strong></strong></p>
     * <p><img class="content-image" src="https://pic4.zhimg.com/v2-0c3dbabe07fb5bfd27fe6c6f174e0867_b.jpg" alt="" /></p>
     * <blockquote>完治：我从以前就想知道，你的大包里都装了些什么呀？<br /><br />莉香：爱和希望！</blockquote>
     * <p><strong>在所有看过的日剧中，最&ldquo;戳&rdquo;到你的台词是哪句？为什么？</strong></p>
     * <hr />
     * <p>关注微信公众号：Lens 杂志（Lensmagazine）</p>
     * <hr />
     * <p>「知乎<span class="lG">机构</span><span class="lG">帐号</span>」是<span class="lG">机构</span>用户专用的知乎<span class="lG">帐号</span>，与知乎社区内原有的个人<span class="lG">帐号</span>独立并行，其使用者为有正规资质的组织<span class="lG">机构</span>，包括但不限于科研院所、公益组织、政府机关、媒体、企业等。这不仅是知乎对<span class="lG">机构</span>的「身份认证」，更是涵盖了内容流通机制、<span class="lG">帐号</span>规范等全套<span class="lG">帐号</span>体系。和个人<span class="lG">帐号</span>一样，<span class="lG">机构</span><span class="lG">帐号</span>开通不需要任何费用，同时也受社区规范的监督管理，并要遵守相关协议。目前<span class="lG">机构</span><span class="lG">帐号</span>入驻采用邀请制。您可以通过 &nbsp;<a href="http://zhihu.com/org-intro" target="_blank">什么是「知乎机构帐号」</a>&nbsp;来了解更多<span class="lG">机构</span><span class="lG">帐号</span>信息。</p>
     * <p>
     * <div class="view-more"><a href="http://zhuanlan.zhihu.com/p/25020752">查看知乎讨论</a></div>
     * <p>
     * </div>
     * </div>
     * </div>
     * <p>
     * <p>
     * </div>
     * </div>
     * image_source : 《四重奏》
     * title : 《四重奏》的编剧 23 岁写出了《东京爱情故事》剧本
     * image : http://pic3.zhimg.com/68c31d907a76dcf27c48117487a7e51e.jpg
     * share_url : http://daily.zhihu.com/story/9230029
     * js : []
     * ga_prefix : 021821
     * section : {"thumbnail":"http://pic4.zhimg.com/d26fbaaa7cafa663006a875f08583533.jpg","id":28,"name":"放映机"}
     * images : ["http://pic1.zhimg.com/4d5c9084e7b309be9e27c532f18f8cfc.jpg"]
     * type : 0
     * id : 9230029
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    public String body;
    public String image_source;
    public String title;
    public String image;
    public String share_url;
    public String ga_prefix;
    public Section section;
    public Theme theme;
    public int type;
    public int id;
    public List<String> css;


    public String sectionOrThemeInfo() {
        GenericJson v1 = new GenericJson();
        v1.setFactory(JacksonFactory.getDefaultInstance());
        if (this.theme != null) {
            v1.put("theme_id", this.theme.getId());
            v1.put("theme_name", this.theme.getName());
            v1.put("theme_image", this.theme.getThumbnail());
        }

        if (this.section != null) {
            v1.put("section_id", Integer.valueOf(this.section.getId()));
            v1.put("section_name", this.section.getName());
            v1.put("section_thumbnail", this.section.getThumbnailUrl());
        }

        Boolean v0 = this.isThemeSubscribed();
        if (v0 == null) {
            v0 = false;
        }

        v1.put("theme_subscribed", v0);
        return v1.toString().replaceAll("\"", "\\\\\"");
    }

    public Boolean isThemeSubscribed() {
        // TODO: 2017/2/24 是否订阅 
//        Boolean v0;
//        if(this.theme == null) {
//            v0 = null;
//        }
//        else {
//            Model v0_1 = new Select().from(ThemeLog.class).where("theme_id = ?", new Object[]{Integer.valueOf(this.theme.getId())}).executeSingle();
//            v0 = v0_1 == null ? Boolean.valueOf(false) : ((ThemeLog)v0_1).isSubscribed();
//        }
        return false;
    }
}
