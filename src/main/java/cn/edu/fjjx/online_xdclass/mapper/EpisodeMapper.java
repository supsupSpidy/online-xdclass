package cn.edu.fjjx.online_xdclass.mapper;

import cn.edu.fjjx.online_xdclass.model.entity.Episode;
import org.apache.ibatis.annotations.Param;

public interface EpisodeMapper {

    Episode findFirstEpisodeByVideoId(@Param("video_id") int videoId);

}
