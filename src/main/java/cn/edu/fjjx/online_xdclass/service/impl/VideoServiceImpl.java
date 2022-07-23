package cn.edu.fjjx.online_xdclass.service.impl;

import cn.edu.fjjx.online_xdclass.config.CacheKeyManager;
import cn.edu.fjjx.online_xdclass.model.entity.Video;
import cn.edu.fjjx.online_xdclass.model.entity.VideoBanner;
import cn.edu.fjjx.online_xdclass.mapper.VideoMapper;
import cn.edu.fjjx.online_xdclass.service.VideoService;
import cn.edu.fjjx.online_xdclass.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    @Override
    public List<Video> listVideo() {
        try{
            Object cacheObj =
                    baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEL_LIST,()->{
                        List<Video> videoList = videoMapper.listVideo();
                        return videoList;
                    });
            if(cacheObj instanceof List){
                List<Video> videoList = (List<Video>)cacheObj;
                return videoList;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//可以返回兜底数据，业务系统降级-》SpringCloud专题课程
        return null;
    }

    @Override
    public List<VideoBanner> listBanner() {
        try {
            Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY,()->
            {
                List<VideoBanner> bannerList = videoMapper.listVideoBanner();
                return bannerList;
            });
            if (cacheObj instanceof List){
                List<VideoBanner> bannerList = (List<VideoBanner>)cacheObj;
                return bannerList;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Video findDetailById(int videoId) {

        String videoCacheKey =
                String.format(CacheKeyManager.VIDEO_DETAIL,videoId);
        try{
            Object cacheObject = baseCache.getOneHourCache().get(
                    videoCacheKey, ()->{
            // 需要使⽤用mybaits关联复杂查询
                        Video video = videoMapper.findDetailById(videoId);
                        return video;
                    });
            if(cacheObject instanceof Video){
                Video video = (Video)cacheObject;
                return video;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
