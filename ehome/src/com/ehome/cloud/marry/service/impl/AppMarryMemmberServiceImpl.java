package com.ehome.cloud.marry.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.app.marry.dto.AppMarryCommentDto;
import com.ehome.cloud.app.marry.dto.AppMarryPhotoDto;
import com.ehome.cloud.app.marry.service.IAppMarryService;
import com.ehome.cloud.marry.mapper.MarryMemberMapper;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.cloud.marry.service.IAppMarryMemmberService;
import com.ehome.cloud.marry.service.IAppMarryPhotoService;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.NumberUtils;

/**
 * @Title: AppMarryMemmberServiceImpl
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月18日 下午5:41:17
 * @version
 */
@Service("appMarryMemmberService")
public class AppMarryMemmberServiceImpl extends BaseService<MarryMemberModel> implements IAppMarryMemmberService {

    @Resource
    private MarryMemberMapper marryMemberMapper;

    @Resource
    private IAppMarryPhotoService appMarryPhotoService;

    @Resource
    private IAppUserService appUserService;

    @Resource
    private IAppMarryService appMarryService;

    @Resource
    private IMemberService memberService;

    @Override
    public Map<String, Object> queryIndexPhoto(Integer appUserId, Integer marryDemand, Integer page, Integer sourceDevice) {
        AppUserModel userModel = appUserService.selectByKey(appUserId);
        MarryMemberModel entity = new MarryMemberModel();
        entity.setAppUserId(appUserId);
        MarryMemberModel memberModel = this.selectOne(entity);

        Member model = new Member();
        model.setAppUserId(appUserId);
        Member member = memberService.selectOne(model);

        Map<String, Object> resultMap = new HashMap<>();
        List<Integer> photoIdList = null;
        if (marryDemand.intValue() == 1) {
            String birthday = userModel.getBirthday(); // 年龄相仿
            String[] strings = birthday.split("-");
            Integer topLimit = NumberUtils.toInteger(strings[0]) + 3;
            Integer lowerLimit = NumberUtils.toInteger(strings[0]) - 3;
            photoIdList = marryMemberMapper.queryNearAge(appUserId, topLimit, lowerLimit, userModel.getSex()); // 年龄相仿
        } else if (marryDemand.intValue() == 2) {
            photoIdList = marryMemberMapper.querySamePlace(appUserId, memberModel.getWorkPlace(), memberModel.getHometown(),userModel.getSex()); // 同城老乡
        } else if (marryDemand.intValue() == 3) { // 兴趣相同
            List<Map<String, Object>> interets = appMarryService.queryInterets(appUserId);
            List<Integer> interetIds = new ArrayList<>();
            for (Map<String, Object> map : interets) {
                interetIds.add((Integer) map.get("interestId"));
            }
            if (CollectionUtils.isEmpty(interetIds)) {
                return null;
            }
            photoIdList = marryMemberMapper.querySameInterests(appUserId, interetIds,userModel.getSex());
        } else if (marryDemand.intValue() == 4) { // 门当户对
            if (member == null) {
                photoIdList = marryMemberMapper.queryByAnuualIncome(appUserId, userModel.getAnnualIncome(),userModel.getSex());
            } else {
                List<Integer> list = marryMemberMapper.queryByAnuualIncome(appUserId, userModel.getAnnualIncome(),userModel.getSex());
                photoIdList = marryMemberMapper.queryByEductionAndDegree(appUserId, member.getEducation(), member.getDegree(),userModel.getSex());
                photoIdList.removeAll(list);
                photoIdList.addAll(list);
            }
        } else if (marryDemand.intValue() == 5) {// 管理高层
            photoIdList = marryMemberMapper.queryManage(appUserId,userModel.getSex());
        } else if (marryDemand.intValue() == 6) { // 职业稳定
            photoIdList = marryMemberMapper.queryCarrerStabilization(appUserId,userModel.getSex());
        } else if (marryDemand.intValue() == 7) {// 认证会员
            photoIdList = marryMemberMapper.queryIdentificationMember(appUserId,userModel.getSex());
        } else if (marryDemand.intValue() == 8) {// 学识相近
            if (member != null) {
                photoIdList = marryMemberMapper.queryByEductionAndDegree(appUserId, member.getEducation(), member.getDegree(),userModel.getSex());
            } else {
                return null;
            }
        } else if (marryDemand.intValue() == 9) { // 星座属性
            List<Integer> stars = new ArrayList<>(3);
            if(memberModel.getStar()==null){
                return null;
            }
            if (memberModel.getStar().intValue() == 1) {
                stars.add(9);
                stars.add(5);
                stars.add(7);
            } else if (memberModel.getStar().intValue() == 2) {
                stars.add(10);
                stars.add(6);
                stars.add(12);
            } else if (memberModel.getStar().intValue() == 3) {
                stars.add(3);
                stars.add(7);
                stars.add(4);
            } else if (memberModel.getStar().intValue() == 4) {
                stars.add(8);
                stars.add(6);
                stars.add(2);
            } else if (memberModel.getStar().intValue() == 5) {
                stars.add(1);
                stars.add(9);
                stars.add(11);
            } else if (memberModel.getStar().intValue() == 6) {
                stars.add(2);
                stars.add(10);
                stars.add(12);
            } else if (memberModel.getStar().intValue() == 7) {
                stars.add(11);
                stars.add(3);
                stars.add(1);
            } else if (memberModel.getStar().intValue() == 8) {
                stars.add(12);
                stars.add(4);
                stars.add(2);
            } else if (memberModel.getStar().intValue() == 9) {
                stars.add(5);
                stars.add(1);
                stars.add(7);
            } else if (memberModel.getStar().intValue() == 10) {
                stars.add(2);
                stars.add(8);
                stars.add(11);
            } else if (memberModel.getStar().intValue() == 11) {
                stars.add(3);
                stars.add(7);
                stars.add(5);
            } else if (memberModel.getStar().intValue() == 12) {
                stars.add(4);
                stars.add(8);
                stars.add(2);
            }
            photoIdList = marryMemberMapper.queryMatchByStars(appUserId, stars,userModel.getSex());
        } else if (marryDemand.intValue() == 10) {
            photoIdList = marryMemberMapper.querySuiYuan(appUserId,userModel.getSex());
        } else if (marryDemand.intValue() == 11) {
            photoIdList = marryMemberMapper.querySelectedQuality(appUserId,userModel.getSex());
        }

        if(null != photoIdList){
            CollectionUtils.removeNull(photoIdList);
        }

        if (CollectionUtils.isNotEmpty(photoIdList)) {
            resultMap.put("photoNumber", photoIdList.size());
            try {
                if (page <= photoIdList.size()) {
                    AppMarryPhotoDto appMarryPhotoDto = appMarryPhotoService.getPhotoDetail(appUserId, photoIdList.get(page - 1));
                    List<AppMarryCommentDto> list = appMarryPhotoService.getComment(photoIdList.get(page - 1), 1, 2);
                    appMarryPhotoDto.setAppMarryCommentDtoList(list);
                    resultMap.put("appMarryPhotoDto", appMarryPhotoDto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }
}
