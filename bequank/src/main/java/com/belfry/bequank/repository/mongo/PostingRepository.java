package com.belfry.bequank.repository.mongo;

import com.belfry.bequank.entity.mongo.Posting;

import javax.transaction.Transactional;
import java.util.ArrayList;

//author:andi

@Transactional
public interface PostingRepository {
    /**
     * @param page  第几页
     * @param count 每页有几项
     *              按时间顺序从最近到较远排列
     **/
    ArrayList<Posting> getHotSpots(int page, int count);
}

