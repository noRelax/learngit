package com.ehome.cloud.marry.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.ehome.cloud.app.marry.dto.AppMarryLoveDto;
import com.ehome.cloud.marry.mapper.AppMarryLoveMapper;
import com.ehome.cloud.marry.model.MarryLoveModel;
import com.ehome.cloud.marry.service.IAppMarryLoveService;
import com.ehome.cloud.marry.service.IGoldCoinService;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.redis.JedisUtils;
import com.github.pagehelper.PageHelper;

/**
 * @Title: AppMarryLoveServiceImpl.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月20日 下午2:23:17
 * @version
 */
@Service("appMarryLoveService")
public class AppMarryLoveServiceImpl extends BaseService<MarryLoveModel> implements IAppMarryLoveService {

    @Resource
    private AppMarryLoveMapper appMarryLoveMapper;

    @Resource
    private IAppUserService appUserService;
    
    @Resource
    private IGoldCoinService goldCoinService;

    @Override
    public List<AppMarryLoveDto> recommend(Integer appUserId, Integer rows) throws BusinessException {
        AppUserModel appUserModel = appUserService.selectByKey(appUserId);
        List<Integer> loved = this.queryMyLove(appUserId); // 过滤已喜欢过的人;
        loved.add(appUserId); // 过滤掉自己
        List<AppMarryLoveDto> list = appMarryLoveMapper.randomSelectOppositeSex(loved, appUserModel.getSex(), rows);
        return list;
    }

    @Override
    public List<AppMarryLoveDto> getMyLoveList(List<Integer> myLove, Integer page, Integer rows) throws BusinessException {
        PageHelper.startPage(page, rows, false);
        List<AppMarryLoveDto> list = appMarryLoveMapper.selectDtoByAppUserIdList(myLove);
        return this.customSort(myLove, list);
    }

    @Override
    public List<AppMarryLoveDto> getLoveMeList(List<Integer> loveMe, Integer page, Integer rows) throws BusinessException {
        PageHelper.startPage(page, rows, false);
        List<AppMarryLoveDto> list = appMarryLoveMapper.selectDtoByAppUserIdList(loveMe);
        return this.customSort(loveMe, list);
    }

    @Override
    public List<AppMarryLoveDto> getLoveTogetherList(List<Integer> togetherList, Integer page, Integer rows) {
        PageHelper.startPage(page, rows, false);
        List<AppMarryLoveDto> list = appMarryLoveMapper.selectDtoByAppUserIdList(togetherList);
        return this.customSort(togetherList, list);
    }

    @Override
    public List<Integer> queryMyLove(Integer appUserId) {
        List<Integer> list = null;
        Jedis jedis = JedisUtils.getJedis();

        Condition condition = new Condition(MarryLoveModel.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("appUserId", appUserId);
        criteria.andEqualTo("statu", 1);
        list = appMarryLoveMapper.queryMyLove(appUserId);
        
        List<MarryLoveModel> modelList = this.selectByCondition(condition);
        for (MarryLoveModel marryLoveModel : modelList) {
            JedisUtils.zAdd("marry:MyLove:" + appUserId, marryLoveModel.getCreateTime().getTime(), marryLoveModel.getLoveAppUserId().toString());
        }
        jedis.expire(("marry:MyLove:" + appUserId).getBytes(), 604800);// 过期时间一周
        jedis.close();
        return list;

    }

    @Override
    public List<Integer> queryLoveMe(Integer appUserId) {
        List<Integer> list = null;
        Jedis jedis = JedisUtils.getJedis();

        Condition condition = new Condition(MarryLoveModel.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("loveAppUserId", appUserId);
        criteria.andEqualTo("statu", 1);
        list = appMarryLoveMapper.queryLoveMe(appUserId);

        List<MarryLoveModel> modelList = this.selectByCondition(condition);
        for (MarryLoveModel marryLoveModel : modelList) {
            JedisUtils.zAdd("marry:LoveMe:" + appUserId, marryLoveModel.getCreateTime().getTime(), marryLoveModel.getAppUserId().toString());
        }
        jedis.expire(("marry:LoveMe:" + appUserId).getBytes(), 604800);// 过期时间一周
        jedis.close();
        return list;
    }

    /**
     * //TODO 添加喜欢的人
     * @see com.ehome.cloud.marry.service.IAppMarryLoveService#addLove(java.lang.Integer, java.lang.Integer)
     **/
    @Override
    public void addLove(Integer appUserId, Integer loveAppUserId) throws Exception{
        Integer addGoldCoinNum = 0;
        MarryLoveModel queryModel = new MarryLoveModel();
        queryModel.setAppUserId(appUserId);
        queryModel.setLoveAppUserId(loveAppUserId);
        MarryLoveModel model = this.selectOne(queryModel);
        //存在记录则把状态更新为1，不存在则插入
        if (model == null) {
            addGoldCoinNum = goldCoinService.loveAtherAddGoldCoins(appUserId, loveAppUserId);
            MarryLoveModel insertModel = new MarryLoveModel();
            insertModel.setAppUserId(appUserId);
            insertModel.setLoveAppUserId(loveAppUserId);
            insertModel.setCreateTime(new Date());
            insertModel.setStatu(1);
            insertModel.setGoldCoin(addGoldCoinNum);
            this.saveNotNull(insertModel);
        } else {
            model.setStatu(1);
            model.setCreateTime(new Date());
            this.updateNotNull(model);
        }
    }

    /**
     * //TODO 取消喜欢
     * @see com.ehome.cloud.marry.service.IAppMarryLoveService#canceLove(java.lang.Integer, java.lang.Integer)
     **/
    @Override
    public void canceLove(Integer appUserId, Integer loveAppUserId) {
        
        MarryLoveModel marryLoveModel = new MarryLoveModel();
        marryLoveModel.setAppUserId(appUserId);
        marryLoveModel.setLoveAppUserId(loveAppUserId);
        MarryLoveModel model = this.selectOne(marryLoveModel);
        model.setStatu(2);
        model.setCreateTime(new Date());
        this.updateByKey(model);
        
    }
    
    private List<AppMarryLoveDto> customSort(List<Integer> list, List<AppMarryLoveDto> dotList){
        List<AppMarryLoveDto> resultList = new ArrayList<AppMarryLoveDto>(dotList.size());
        for (Integer integer : list) {
            for (AppMarryLoveDto appMarryLoveDto : dotList) {
                if(appMarryLoveDto.getAppUserId().intValue() == integer.intValue()){
                    resultList.add(appMarryLoveDto);
                    break;
                }
            }
        }
        return resultList; 
    }
}
