package cn.edu.fjjx.online_xdclass.mapper;

import cn.edu.fjjx.online_xdclass.model.entity.Video;
import cn.edu.fjjx.online_xdclass.model.entity.VideoBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    List<Video> listVideo();

    List<VideoBanner> listVideoBanner();

    Video findDetailById(@Param("video_id")int videoId);

    Video findById(@Param("video_id") int id);
}
