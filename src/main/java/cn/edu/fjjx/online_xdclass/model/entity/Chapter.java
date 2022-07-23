package cn.edu.fjjx.online_xdclass.model.entity;

import cn.edu.fjjx.online_xdclass.mapper.UserMapper;
import cn.edu.fjjx.online_xdclass.service.UserService;
import cn.edu.fjjx.online_xdclass.utils.CommonUtil;
import cn.edu.fjjx.online_xdclass.utils.JWTUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chapter {
    private Integer id;
    @JsonProperty("video_id")
    private Integer videoId;
    private String title;
    private Integer ordered;
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonProperty("episode_list")
    private List<Episode> episodeList;

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", videoId=" + videoId +
                ", title='" + title + '\'' +
                ", ordered=" + ordered +
                ", createTime=" + createTime +
                '}';
    }

    @Service
    public static class UserServiceImpl implements UserService {

        @Autowired
        private UserMapper userMapper;

        private static final String [] headImg = {
                "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
                "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
                "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
                "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
                "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
        };


        @Override
        public int save(Map<String, String> userInfo) {
            User user = parseToUser(userInfo);
            if(user!=null) {
                return userMapper.save(user);
            }else {
                return -1;
            }
        }

        @Override
        public String findByPhoneAndPwd(String phone, String pwd) {
            User user = userMapper.findByPhoneAndPwd(phone, CommonUtil.MD5(pwd));
            if (user == null){
                return null;
            }else {
                String token = JWTUtil.geneJsonWebToken(user);
                return token;
            }
        }

        @Override
        public User findByUserId(Integer userId) {
            User user = userMapper.findByUserId(userId);
            return user;
        }

        private User parseToUser(Map<String, String> userInfo) {
            if(userInfo.containsKey("phone")&&userInfo.containsKey("pwd")&&userInfo.containsKey("name")){
                User user = new User();
                user.setName(userInfo.get("name"));
                user.setHeadImg(getHeadImg());
                user.setCreateTime(new Date());
                user.setPhone(userInfo.get("phone"));
                String pwd = userInfo.get("pwd");//MD5加密
                user.setPwd(CommonUtil.MD5(pwd));
                return user;
            }else {
                return null;
            }
        }

        private String getHeadImg(){
            int size = headImg.length;
            Random random = new Random();
            int index = random.nextInt(size);
            return headImg[index];
        }
    }
}
