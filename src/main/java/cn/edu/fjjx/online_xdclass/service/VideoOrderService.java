package cn.edu.fjjx.online_xdclass.service;

import cn.edu.fjjx.online_xdclass.model.entity.VideoOrder;

import java.util.List;

public interface VideoOrderService {
    int save(int userId,int videoId);

    List<VideoOrder> listOrderByUserId(Integer userId);
}
