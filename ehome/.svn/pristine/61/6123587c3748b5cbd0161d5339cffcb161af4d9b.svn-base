package com.ehome.cloud.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.service.IAppMemberService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.MapUtils;
import com.ehome.core.util.StringUtils;

/**
 * app 会员
 * 
 * @Title:AppMemberServiceImpl
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月22日
 * @version 1.0,2017年2月22日
 * @{tags
 */
@Service("appMemberService")
public class AppMemberServiceImpl extends BaseService<Member> implements
		IAppMemberService {

	// @Resource
	// private AppMemberMapper appMemberMapper;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBaseUnion(Map<String, Object> map) {
		return sqlSessionTemplate.selectList(
				"com.ehome.cloud.member.mapper.AppMemberMapper.queryBaseUnion",
				map);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int insertApply(Map<String, Object> map) {
		int flag = 0;
		flag = (Integer) sqlSessionTemplate.insert(
				"com.ehome.cloud.member.mapper.AppMemberMapper.insertApply",
				map);
		// 保存操作记录表
		if (flag > 0) {
			map.put("member_id", map.get("id"));
			flag = (Integer) sqlSessionTemplate
					.insert("com.ehome.cloud.member.mapper.AppMemberMapper.insertRecord",
							map);// 操作记录表
		}
		// 保存家庭成员
		if (flag > 0) {
			Map<String, Object> familyMap = new HashMap<>();
			List<Map<String, Object>> list = new ArrayList<>();
			if (StringUtils.isNotBlank(MapUtils.getString(map,
					"family_relationships", null))) {
				// String tempName[] = MapUtils.getString(map,
				// "family_member_names", null).split(",");
				// String tempPhone[] = MapUtils.getString(map,
				// "family_member_phones", null).split(",");
				String family_relationship[] = MapUtils.getString(map,
						"family_relationships", null).split(",");
				if (family_relationship.length > 0) {
					for (int j = 0; j < family_relationship.length; j++) {
						// list.add(Integer.parseInt(tempStr[j].toString()));
						Map<String, Object> tempMap = new HashMap<>();
						tempMap.put("member_id", Integer.parseInt(map.get(
								"member_id").toString()));
						tempMap.put("family_relationship",
								family_relationship[j].toString());
						list.add(tempMap);
					}
					familyMap.put("list", list);
					flag = (Integer) sqlSessionTemplate
							.insert("com.ehome.cloud.member.mapper.AppMemberMapper.insertFamilyMember",
									familyMap);
				}
			}
		}
		// 保存简历
		if (flag > 0) {
			Map<String, Object> jianLiMap = new HashMap<>();// 简历
			List<Map<String, Object>> list = new ArrayList<>();
			if (StringUtils.isNotBlank(MapUtils.getString(map, "company_names",
					null))
					&& StringUtils.isNotBlank(MapUtils.getString(map, "jobs",
							null))
					&& StringUtils.isNotBlank(MapUtils.getString(map,
							"joined_dates", null))
					&& StringUtils.isNotBlank(MapUtils.getString(map,
							"release_dates", null))
					&& StringUtils.isNotBlank(MapUtils.getString(map,
							"job_contents", null))) {
				String[] company_name = MapUtils.getString(map,
						"company_names", null).split(",");
				String[] job = MapUtils.getString(map, "jobs", null).split(",");
				String[] joined_date = MapUtils.getString(map, "joined_dates",
						null).split(",");
				String[] release_date = MapUtils.getString(map,
						"release_dates", null).split(",");
				String[] job_content = null;
				if (MapUtils.getString(map, "job_contents", null) != null) {
					if (MapUtils.getString(map, "job_contents", null).contains(
							"&")) {
						job_content = MapUtils.getString(map, "job_contents",
								null).split("&");
					} else if (MapUtils.getString(map, "job_contents", null)
							.contains("%26")) {
						job_content = MapUtils.getString(map, "job_contents",
								null).split("%26");
					} else {
						job_content = MapUtils.getString(map, "job_contents",
								null).split(",");
					}
				}
				if ((company_name.length == job.length)
						&& (company_name.length == joined_date.length)
						&& (company_name.length == release_date.length)
						&& (company_name.length == job_content.length)) {
					for (int j = 0; j < company_name.length; j++) {
						Map<String, Object> tempMap = new HashMap<>();
						tempMap.put("member_id", Integer.parseInt(map.get(
								"member_id").toString()));
						tempMap.put("job", job[j]);
						tempMap.put("company_name", company_name[j]);
						tempMap.put("release_date",
								DateUtils.getDate(release_date[j]));
						tempMap.put("joined_date",
								DateUtils.getDate(joined_date[j]));
						tempMap.put("job_content", job_content[j]);
						list.add(tempMap);
					}
					jianLiMap.put("list", list);
					flag = (Integer) sqlSessionTemplate
							.insert("com.ehome.cloud.member.mapper.AppMemberMapper.insertMemberJianLi",
									jianLiMap);
				} else {

				}

			}
		}
		return flag;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int updateApply(Map<String, Object> map) {
		int flag = 0;
		flag = (Integer) sqlSessionTemplate.update(
				"com.ehome.cloud.member.mapper.AppMemberMapper.updateApply",
				map);
		if (flag > 0) {
			flag = (Integer) sqlSessionTemplate
					.insert("com.ehome.cloud.member.mapper.AppMemberMapper.insertRecord",
							map);// 操作记录表
		}
		// 保存家庭成员
		if (flag > 0) {
			sqlSessionTemplate
					.delete("com.ehome.cloud.member.mapper.AppMemberMapper.deleteRecord",
							MapUtils.getInteger(map, "member_id", -1));// 删除之前的家庭成员记录

			Map<String, Object> familyMap = new HashMap<>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (StringUtils.isNotBlank(MapUtils.getString(map,
					"family_relationships", null))) {
				// String tempName[] = MapUtils.getString(map,
				// "family_member_names", null).split(",");
				// String tempPhone[] = MapUtils.getString(map,
				// "family_member_phones", null).split(",");
				String family_relationship[] = MapUtils.getString(map,
						"family_relationships", null).split(",");
				if (family_relationship.length > 0) {
					for (int j = 0; j < family_relationship.length; j++) {
						// list.add(Integer.parseInt(tempStr[j].toString()));
						Map<String, Object> tempMap = new HashMap<>();
						tempMap.put("member_id", Integer.parseInt(map.get(
								"member_id").toString()));
						tempMap.put("family_relationship",
								family_relationship[j].toString());
						list.add(tempMap);
					}
					familyMap.put("list", list);
					flag = (Integer) sqlSessionTemplate
							.insert("com.ehome.cloud.member.mapper.AppMemberMapper.insertFamilyMember",
									familyMap);
				} else {

				}
			}

		}
		// 保存简历
		if (flag > 0) {
			sqlSessionTemplate
					.delete("com.ehome.cloud.member.mapper.AppMemberMapper.deleteJinaLi",
							MapUtils.getInteger(map, "member_id", -1));// 删除之前的家庭成员记录

			Map<String, Object> jianLiMap = new HashMap<>();// 简历
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (StringUtils.isNotBlank(MapUtils.getString(map, "company_names",
					null))
					&& StringUtils.isNotBlank(MapUtils.getString(map, "jobs",
							null))
					&& StringUtils.isNotBlank(MapUtils.getString(map,
							"joined_dates", null))
					&& StringUtils.isNotBlank(MapUtils.getString(map,
							"release_dates", null))
					&& StringUtils.isNotBlank(MapUtils.getString(map,
							"job_contents", null))) {
				String company_name[] = MapUtils.getString(map,
						"company_names", null).split(",");
				String job[] = MapUtils.getString(map, "jobs", null).split(",");
				String joined_date[] = MapUtils.getString(map, "joined_dates",
						null).split(",");
				String release_date[] = MapUtils.getString(map,
						"release_dates", null).split(",");
				// String job_content[] = MapUtils.getString(map,
				// "job_contents", null).split("%26");
				String job_content[] = null;
				if (MapUtils.getString(map, "job_contents", null) != null) {
					if (MapUtils.getString(map, "job_contents", null).contains(
							"&")) {
						job_content = MapUtils.getString(map, "job_contents",
								null).split("&");
					} else if (MapUtils.getString(map, "job_contents", null)
							.contains("%26")) {
						job_content = MapUtils.getString(map, "job_contents",
								null).split("%26");
					} else {
						job_content = MapUtils.getString(map, "job_contents",
								null).split(",");
					}
				}
				if ((company_name.length == job.length)
						&& (company_name.length == joined_date.length)
						&& (company_name.length == release_date.length)
						&& (company_name.length == job_content.length)) {
					for (int j = 0; j < company_name.length; j++) {
						Map<String, Object> tempMap = new HashMap<>();
						tempMap.put("member_id", Integer.parseInt(map.get(
								"member_id").toString()));
						tempMap.put("job", job[j]);
						tempMap.put("company_name", company_name[j]);
						tempMap.put("release_date",
								DateUtils.getDate(release_date[j]));
						tempMap.put("joined_date",
								DateUtils.getDate(joined_date[j]));
						tempMap.put("job_content", job_content[j]);
						list.add(tempMap);
					}
					jianLiMap.put("list", list);
					flag = (Integer) sqlSessionTemplate
							.insert("com.ehome.cloud.member.mapper.AppMemberMapper.insertMemberJianLi",
									jianLiMap);
				} else {

				}

			}

		}

		return flag;
	}

	/**
	 * 
	 * @param integer
	 * @return
	 */
	@Override
	public Map<String, Object> queryApplyInfoById(Integer app_user_id) {
		return sqlSessionTemplate
				.selectOne(
						"com.ehome.cloud.member.mapper.AppMemberMapper.queryApplyInfoById",
						app_user_id);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryProvinceCityByname(
			Map<String, Object> map) {
		return sqlSessionTemplate
				.selectList(
						"com.ehome.cloud.member.mapper.AppMemberMapper.queryProvinceCityByname",
						map);
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryCityByname(
			Map<String, Object> map) {
		return sqlSessionTemplate
				.selectList(
						"com.ehome.cloud.member.mapper.AppMemberMapper.queryCityByname",
						map);
	}

	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@Override
	public Map<String, Object> queryOrgainPId(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("com.ehome.cloud.member.mapper.AppMemberMapper.queryOrgainPId",map);
	}

	
	 /**
	  * 
	  * @param pidInfo
	  * @return 
	  */
	@Override
	public Map<String, Object> queryOrgian(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("com.ehome.cloud.member.mapper.AppMemberMapper.queryOrgian",map);
	}

	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@Override
	public String queryCityNameByCityId(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("com.ehome.cloud.member.mapper.AppMemberMapper.queryCityNameByCityId",map);

	}

	// /**
	// *
	// * @param map
	// * @return
	// */
	// @Override
	// public List<Map<String, Object>> queryBaseUnion(Map<String, Object> map)
	// {
	// return appMemberMapper.queryBaseUnion(map);
	// }

}
