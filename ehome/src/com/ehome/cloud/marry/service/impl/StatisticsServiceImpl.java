package com.ehome.cloud.marry.service.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.ehome.cloud.marry.dto.MarryMemberDto;
import com.ehome.cloud.marry.dto.StatisticsDto;
import com.ehome.cloud.marry.mapper.MarryMemberMapper;
import com.ehome.cloud.marry.model.GoldCoinModel;
import com.ehome.cloud.marry.model.MarryCommentModel;
import com.ehome.cloud.marry.model.MarryPhoto;
import com.ehome.cloud.marry.model.MarryThumpUpModel;
import com.ehome.cloud.marry.model.MarryUserInterest;
import com.ehome.cloud.marry.service.IAppMarryThumpUpService;
import com.ehome.cloud.marry.service.IGoldCoinService;
import com.ehome.cloud.marry.service.IMarryCommentService;
import com.ehome.cloud.marry.service.IMarryMemberService;
import com.ehome.cloud.marry.service.IMarryPhotoService;
import com.ehome.cloud.marry.service.IMarryUserInterestService;
import com.ehome.cloud.marry.service.IStatisticsService;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.MapUtils;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.StringUtils;
import com.ehome.core.util.redis.JedisUtils;

/**
 * 
 * @Title:StatisticsServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月19日 下午4:25:09
 * @version:
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements IStatisticsService {

	@Resource
	private MarryMemberMapper marryMemberMapper;

	@Resource
	private IAppUserService appUserService;

	@Resource
	private IMarryMemberService marryMemberService;

	@Resource
	private IGoldCoinService goldCoinService;

	@Resource
	private IMarryPhotoService marryPhotoService;

	@Resource
	private IAppMarryThumpUpService appMarryThumpUpService;

	@Resource
	private IMarryCommentService marryCommentService;

	@Resource
	private IMarryUserInterestService marryUserInterestService;

	@Resource
	private IDictionaryService dictionaryService;

	/**
	 * 数据统计 统计年收入明细和年龄明细
	 * 
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public Map<String, List<StatisticsDto>> queryStatistics(Integer deviceType,
			String startDate, String endDate) throws BusinessException {
		Map<String, List<StatisticsDto>> statisticsMap = new HashMap<>();
		List<StatisticsDto> ageDtoList = new ArrayList<>(), incomeDtoList = new ArrayList<>();
		List<Dictionary> incomeList = dictionaryService
				.queryByCode("CODE_ANNUAL_INCOME"), ageList = dictionaryService
				.queryByCode("CODE_AGE");
		Set<Integer> incomeSet = incomeList.stream().map(o -> {
			return NumberUtils.toInt(o.getDictKey());
		}).collect(Collectors.toSet());
		Set<Integer> ageSet = ageList.stream().map(o -> {
			return NumberUtils.toInt(o.getDictKey());
		}).collect(Collectors.toSet());
		// 统计数据源
		List<MarryMemberDto> list = marryMemberMapper.queryStatistics(
				deviceType, startDate, endDate);
		DictoryCodeUtils.renderList(list);
		if (CollectionUtils.isNotEmpty(list)) {
			DecimalFormat df = new DecimalFormat("0%");// 格式化
			//可以设置精确几位小数  
			df.setMaximumFractionDigits(2);
			//模式 例如四舍五入  
			df.setRoundingMode(RoundingMode.HALF_UP);
			int userTotal = list.size();
			Map<Integer, List<MarryMemberDto>> ageMap = list.stream()
					.filter(o -> !NumberUtils.isNull(o.getDictAge()))
					.collect(Collectors.groupingBy(MarryMemberDto::getDictAge));
			Map<Integer, List<MarryMemberDto>> comeMap = list
					.stream()
					.filter(o -> !NumberUtils.isNull(o.getAnnualIncome()))
					.collect(
							Collectors
									.groupingBy(MarryMemberDto::getAnnualIncome));
			// 统计年龄
			if (MapUtils.isNotEmpty(ageMap)) {
				ageSet.forEach(o -> {
					if (!ageMap.containsKey(o)) {
						ageMap.put(o, Collections.emptyList());
					}
				});
				ageMap.entrySet()
						.stream()
						.sorted(Map.Entry
								.<Integer, List<MarryMemberDto>> comparingByKey())
						.forEach(o -> {
							int userNum = o.getValue().size();
							float userPercent = userNum / (float) userTotal;
							StatisticsDto ageDto = new StatisticsDto();
							ageDto.setDictAge(o.getKey());
							ageDto.setUserNum(userNum);
							ageDto.setUserPercent(df.format(userPercent));
							DictoryCodeUtils.renderCode(ageDto);
							ageDtoList.add(ageDto);
						});
			}
			// 统计收入
			if (MapUtils.isNotEmpty(comeMap)) {
				incomeSet.forEach(o -> {
					if (!comeMap.containsKey(o)) {
						comeMap.put(o, Collections.emptyList());
					}
				});
				comeMap.entrySet()
						.stream()
						.sorted(Map.Entry
								.<Integer, List<MarryMemberDto>> comparingByKey())
						.forEach(o -> {
							int userNum = o.getValue().size();
							float userPercent = userNum / (float) userTotal;
							StatisticsDto comeDto = new StatisticsDto();
							comeDto.setAnnualIncome(o.getKey());
							comeDto.setUserNum(userNum);
							comeDto.setUserPercent(df.format(userPercent));
							DictoryCodeUtils.renderCode(comeDto);
							incomeDtoList.add(comeDto);
						});
			}
		}
		statisticsMap.put("income", incomeDtoList);
		statisticsMap.put("age", ageDtoList);
		return statisticsMap;
	}

	/**
	 * 用户转化
	 */
	@Override
	public Map<String, String> queryUserConvert(Integer deviceType,
			String startDate, String endDate) throws BusinessException {
		Map<String, String> resultMap = new HashMap<>();
		DecimalFormat df = new DecimalFormat("0%");// 格式化
		//可以设置精确几位小数  
		df.setMaximumFractionDigits(2);
		//模式 例如四舍五入  
		df.setRoundingMode(RoundingMode.HALF_UP);
		// 统计APP总用户
		Condition condition = new Condition(AppUserModel.class);
		if (!NumberUtils.isNull(deviceType) && !NumberUtils.eqZero(deviceType))
			condition.createCriteria().andEqualTo("sourceDevice", deviceType);
		//APP总用户：APP总的注册用户数，包含第三方和手机注册用户
		List<AppUserModel> userList = appUserService
				.selectByCondition(condition);
		if (CollectionUtils.isNotEmpty(userList)) {
			resultMap.put("userCount", userList.size() + "");
			//婚恋总用户：APP总用户中，用户属性身高字段非空的所有用户
			List<AppUserModel> marryList = userList.stream()
					.filter(p -> StringUtils.isNotBlank(p.getHeight()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(marryList)) {
				float userPercent = marryList.size() / (float) userList.size();
				resultMap.put("marryCount",
						marryList.size() + "(" + df.format(userPercent) + ")");
				//认证用户：婚恋总用户中，通过认证的用户，判断依据：身份证号码非空
				List<AppUserModel> authList = marryList.stream()
						.filter(p -> StringUtils.isNotBlank(p.getIdCard()))
						.collect(Collectors.toList());
				// 统计婚恋总用户中会员认证的用户
				if (CollectionUtils.isNotEmpty(authList)) {
					float authPercent = authList.size()
							/ (float) marryList.size();
					resultMap.put("authUserCount",
							authList.size() + "(" + df.format(authPercent)
									+ ")");
				} else {
					resultMap.put("authUserCount", "0%");
				}
				// 统计婚恋用户中配对成功用户
				if (JedisUtils.exist("marry:mutual:privateChat")) {
					long size = JedisUtils.zCard("marry:mutual:privateChat");
					if (size != 0) {
						float mPercent = size / (float) marryList.size();
						resultMap.put("mutual",
								size + "(" + df.format(mPercent) + ")");
					}
				} else {
					resultMap.put("mutual", "0%");
				}
				// 统计婚恋用户中男女比例
				Set<Integer> appUserIdList = marryList.stream().map(o -> {
					return o.getId();
				}).collect(Collectors.toSet());
				int sexNanNum = 0, sexNvNum = 0;
				String sexPer = "0:0";
				if (CollectionUtils.isNotEmpty(appUserIdList)) {
					Map<Integer, List<AppUserModel>> sexUserMap = userList
							.stream()
							.filter(o -> {
								return appUserIdList.contains(o.getId())
										&& !NumberUtils.isNull(o.getSex());
							})
							.collect(
									Collectors.groupingBy(AppUserModel::getSex));
					if (MapUtils.isNotEmpty(sexUserMap)) {
						if (sexUserMap.containsKey(1)) {
							sexNanNum = sexUserMap.get(1).size();
						}
						if (sexUserMap.containsKey(2)) {
							sexNvNum = sexUserMap.get(2).size();
						}
						if (sexNvNum != 0) {
							int n = NumberUtils.getMaxGY(sexNanNum, sexNvNum);
							sexPer = sexNanNum / n + ":" + sexNvNum / n;
						}
					}
				}
				resultMap.put("sexPer", sexPer);
			} else {
				resultMap.put("marryCount", "0%");
			}
		} else {
			resultMap.put("userCount", "0");
			resultMap.put("marryCount", "0%");
			resultMap.put("authUserCount", "0%");
			resultMap.put("mutual", "0%");
			resultMap.put("sexPer", "0%");
		}
		return resultMap;
	}

	/**
	 * 金币转化
	 */
	@Override
	public Map<String, Integer> queryGoldCoinsConvert(Integer deviceType,
			String startDate, String endDate) throws BusinessException {
		Map<String, Integer> resultMap = new HashMap<>();
		//查找总婚恋会员用户
		List<MarryMemberDto> marryList = marryMemberMapper.queryStatistics(
				deviceType, startDate, endDate);
		//统计金币流水
		Condition condition = new Condition(GoldCoinModel.class);
		Criteria criteria = condition.createCriteria();
		if (!NumberUtils.isNull(deviceType) && !NumberUtils.eqZero(deviceType)) {
			criteria.andEqualTo("sourceDevice", deviceType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			// criteria.andGreaterThanOrEqualTo("changeTime", startDate);
			criteria.andCondition("DATE_FORMAT(change_time,'%Y-%m-%d') >= ",
					startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			// criteria.andLessThanOrEqualTo("changeTime", endDate);
			criteria.andCondition("DATE_FORMAT(change_time,'%Y-%m-%d') <= ",
					endDate);
		}
		List<GoldCoinModel> goldList = goldCoinService
				.selectByCondition(condition);
		int totalGolds = 0, remainGolds = 0, addGolds = 0, expendGolds = 0;
		if (CollectionUtils.isNotEmpty(goldList)) {
			//新增金币
			addGolds = goldList.stream()
					.filter(o -> o.getGoldCoinsChangeDerection().equals(1))
					.mapToInt(GoldCoinModel::getGoldCoinsChangeNum).sum();
			//消耗金币
			expendGolds = goldList.stream()
					.filter(o -> o.getGoldCoinsChangeDerection().equals(2))
					.mapToInt(GoldCoinModel::getGoldCoinsChangeNum).sum();
			//金币流水总额
			totalGolds = addGolds + expendGolds;
		}
		//统计剩余金币数
		if (CollectionUtils.isNotEmpty(marryList)) {
			remainGolds = marryList.stream()
					.filter(o -> !NumberUtils.isNull(o.getGoldCoins()))
					.mapToInt(MarryMemberDto::getGoldCoins).sum();
		}
		resultMap.put("totalGolds", totalGolds);// 金币流水总额
		resultMap.put("remainGolds", remainGolds);// 剩余金币
		resultMap.put("addGolds", addGolds);// 新增金币
		resultMap.put("expendGolds", expendGolds);// 消耗金币
		return resultMap;
	}

	/**
	 * 数据统计
	 */
	@Override
	public Map<String, Integer> queryDataStatis(Integer deviceType,
			String startDate, String endDate) throws BusinessException {
		Map<String, Integer> resultMap = new HashMap<>();
		int photoNum = 0, thumbNum = 0, commentNum = 0;
		// 照片总数
		Condition photoCon = new Condition(MarryPhoto.class);
		Criteria photoCriteria = photoCon.createCriteria();
		if (!NumberUtils.isNull(deviceType) && !NumberUtils.eqZero(deviceType)) {
			photoCriteria.andEqualTo("sourceDevice", deviceType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			// photoCriteria.andGreaterThanOrEqualTo("publicTime", startDate);
			photoCriteria.andCondition(
					"DATE_FORMAT(public_time,'%Y-%m-%d') >= ", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			// photoCriteria.andLessThanOrEqualTo("publicTime", endDate);
			photoCriteria.andCondition(
					"DATE_FORMAT(public_time,'%Y-%m-%d') <= ", endDate);
		}
		List<MarryPhoto> photoList = marryPhotoService
				.selectByCondition(photoCon);
		if (CollectionUtils.isNotEmpty(photoList)) {
			photoNum = photoList.size();
		}
		// 点赞总数
		Condition thumpCon = new Condition(MarryThumpUpModel.class);
		Criteria thumpCriteria = thumpCon.createCriteria();
		if (!NumberUtils.isNull(deviceType) && !NumberUtils.eqZero(deviceType)) {
			thumpCriteria.andEqualTo("sourceDevice", deviceType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			// thumpCriteria.andGreaterThanOrEqualTo("createTime", startDate);
			thumpCriteria.andCondition(
					"DATE_FORMAT(create_time,'%Y-%m-%d') >= ", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			// thumpCriteria.andLessThanOrEqualTo("createTime", endDate);
			thumpCriteria.andCondition(
					"DATE_FORMAT(create_time,'%Y-%m-%d') <= ", endDate);
		}
		List<MarryThumpUpModel> thumpList = appMarryThumpUpService
				.selectByCondition(thumpCon);
		if (CollectionUtils.isNotEmpty(photoList)) {
			thumbNum = thumpList.size();
		}
		// 评论总数
		Condition commentCon = new Condition(MarryCommentModel.class);
		Criteria commentCriteria = commentCon.createCriteria();
		if (!NumberUtils.isNull(deviceType) && !NumberUtils.eqZero(deviceType)) {
			commentCriteria.andEqualTo("sourceDevice", deviceType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			// commentCriteria.andGreaterThanOrEqualTo("publicTime", startDate);
			commentCriteria.andCondition(
					"DATE_FORMAT(public_time,'%Y-%m-%d') >= ", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			// commentCriteria.andLessThanOrEqualTo("publicTime", endDate);
			commentCriteria.andCondition(
					"DATE_FORMAT(public_time,'%Y-%m-%d') <= ", endDate);
		}
		List<MarryCommentModel> commentList = marryCommentService
				.selectByCondition(commentCon);
		if (CollectionUtils.isNotEmpty(photoList)) {
			commentNum = commentList.size();
		}
		Integer msgCount = marryMemberMapper.queryMessageCount();
		if (!NumberUtils.isNull(msgCount))
			resultMap.put("msgCount", msgCount);
		else
			resultMap.put("msgCount", 0);
		resultMap.put("photoNum", photoNum);
		resultMap.put("thumbNum", thumbNum);
		resultMap.put("commentNum", commentNum);
		return resultMap;
	}

	/**
	 * 用户意向
	 */
	@Override
	public Map<String, List<StatisticsDto>> queryUserInterest(
			Integer deviceType, String startDate, String endDate)
			throws BusinessException {
		Map<String, List<StatisticsDto>> resultMap = new HashMap<>();
		List<Dictionary> require = dictionaryService
				.queryByCode("CODE_SELECT_REQUIRE");
		Set<Integer> requireSet = require.stream().map(o -> {
			return NumberUtils.toInt(o.getDictKey());
		}).collect(Collectors.toSet());
		Condition condition = new Condition(MarryUserInterest.class);
		Criteria criteria = condition.createCriteria();
		if (!NumberUtils.isNull(deviceType) && !NumberUtils.eqZero(deviceType)) {
			criteria.andEqualTo("sourceDevice", deviceType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			criteria.andGreaterThanOrEqualTo("operatorDate", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			criteria.andLessThanOrEqualTo("operatorDate", endDate);
		}
		List<MarryUserInterest> list = marryUserInterestService
				.selectByCondition(condition);
		if (CollectionUtils.isNotEmpty(list)) {
			DecimalFormat df = new DecimalFormat("0%");// 格式化
			//可以设置精确几位小数  
			df.setMaximumFractionDigits(2);
			//模式 例如四舍五入  
			df.setRoundingMode(RoundingMode.HALF_UP);
			List<StatisticsDto> dtoList = new ArrayList<>();
			int total = list.size();// 总数
			//System.out.println(total);
			Map<Integer, List<MarryUserInterest>> map = list.stream().collect(
					Collectors.groupingBy(MarryUserInterest::getMarryDemand));
			// 统计用户意向
			if (MapUtils.isNotEmpty(map)) {
				requireSet.forEach(o -> {
					if (!map.containsKey(o)) {
						map.put(o, Collections.emptyList());
					}
				});
				map.entrySet()
						.stream()
						.sorted(Map.Entry
								.<Integer, List<MarryUserInterest>> comparingByKey())
						.forEach(o -> {
							int userNum = o.getValue().size();
							double userPercent = userNum * 1.0 / total * 1.0;
							StatisticsDto ageDto = new StatisticsDto();
							ageDto.setInterestType(o.getKey());
							ageDto.setUserNum(userNum);
							ageDto.setUserPercent(df.format(userPercent));
							DictoryCodeUtils.renderCode(ageDto);
							dtoList.add(ageDto);
						});
			}
			resultMap.put("data", dtoList);
		} else {
			List<StatisticsDto> dtoList = require.stream().map(o -> {
				StatisticsDto dto = new StatisticsDto();
				dto.setInterestType(NumberUtils.toInt(o.getDictKey()));
				dto.setUserNum(0);
				dto.setUserPercent("");
				return dto;
			}).collect(Collectors.toList());
			resultMap.put("data", dtoList);
		}
		return resultMap;
	}

}
