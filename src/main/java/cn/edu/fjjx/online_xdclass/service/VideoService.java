package cn.edu.fjjx.online_xdclass.service;

import cn.edu.fjjx.online_xdclass.model.entity.Video;
import cn.edu.fjjx.online_xdclass.model.entity.VideoBanner;

import java.util.List;

public interface VideoService {
    List<Video> listVideo();

    List<VideoBanner> listBanner();

    Video findDetailById(int videoId);
}
