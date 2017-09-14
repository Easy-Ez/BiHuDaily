package cn.ml.saddhu.bihudaily.engine.domain;

/**
 * Created by sadhu on 2017/3/26.
 * Email static.sadhu@gmail.com
 * Describe: 评论bean
 */

public class CommentBean {

    /**
     * own : false
     * author : 王歆玮2011
     * content : 不清楚您们争论的是什么，只想对您的这番话说一下我的见解。所谓科学不是万能的观点，是把科学当成了某种东西或者某个理论了，而我的观点是，科学其实是一种方法论，他对事物现象的解释是应该是逻辑自洽的，是来源于观察所得的事实和严密推理的，他的理论也应该是是可证明的或者可证伪的，这才是科学有别于各种玄学理论民科理论的原因
     * avatar : http://pic3.zhimg.com/119c2d9e8f3fdeffeef5175522f85eae_im.jpg
     * time : 1490538434
     * reply_to : {"content":"仔细想想是不是瞎扯？你能用进化论解释局部，解释不了全局。你用科学可以解释阶段，无法解释始终。你用科学解释得了班级考试规则，解释不了班级是怎么来的。所以不要无限把科学推广到所有方面和时空，不要把科学当真理。因为他不是。","status":0,"id":28512840,"author":"范子计然"}
     * voted : false
     * id : 28508281
     * likes : 20
     */

    public boolean own;
    public String author;
    public String content;
    public String avatar;
    public int time;
    public ReplyToBean reply_to;
    public boolean voted;
    public int id;
    public int likes;
    public  boolean viewHasExpand =true;


    public static class ReplyToBean {
        /**
         * content : 仔细想想是不是瞎扯？你能用进化论解释局部，解释不了全局。你用科学可以解释阶段，无法解释始终。你用科学解释得了班级考试规则，解释不了班级是怎么来的。所以不要无限把科学推广到所有方面和时空，不要把科学当真理。因为他不是。
         * status : 0
         * id : 28512840
         * author : 范子计然
         */

        public String content;
        public int status;
        public int id;
        public String author;
        public String error_msg;
    }
}
