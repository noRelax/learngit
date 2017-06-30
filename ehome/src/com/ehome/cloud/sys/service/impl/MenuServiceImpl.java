package com.ehome.cloud.sys.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.sys.mapper.MenuMapper;
import com.ehome.cloud.sys.model.MenuModel;
import com.ehome.cloud.sys.service.IMenuService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.CollectionUtils;

/**
 * 菜单资源管理业务Service
 * 
 * @Title:MenuServiceImpl
 * @Description:
 * @author:张钟武
 * @date:2017年1月21日 上午11:37:28
 * @version:
 */
@Service("menuService")
public class MenuServiceImpl extends BaseService<MenuModel> implements
		IMenuService {

	@Resource
	private MenuMapper menuMapper;

	/**
	 * 
	 * @param menuModel
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public int insertMenu(MenuModel menuModel) throws BusinessException {
		return menuMapper.insert(menuModel);
	}

	/**
	 * 
	 * @param menuModel
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public int updateMenu(MenuModel menuModel) throws BusinessException {
		return menuMapper.updateMenu(menuModel);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public int deleteMenuById(Integer id) throws BusinessException {
		return menuMapper.deleteMenuById(id);
	}

	/**
	 * 
	 * @param keyword
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MenuModel> queryForList(String keyword, Integer page,
			Integer rows) throws Exception {
		return menuMapper.queryForList(keyword);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuModel> queryAuthPermissByRoleId(List<Integer> rolesId)
			throws BusinessException {
		if (CollectionUtils.isNotEmpty(rolesId)) {
			return menuMapper.queryAuthPermissByRoleId(rolesId);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public List<Map<String, Object>> queryListAllParentMenu() throws Exception {
		return menuMapper.queryListAllParentMenu();
	}

	@Override
	public List<Map<String, Object>> queryListSubMenuByParentId(Integer parentId)
			throws Exception {
		return menuMapper.queryListSubMenuByParentId(parentId);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> queryListAllMenu() throws Exception {
		// 暂时支持三层
		List<Map<String, Object>> rl = this.queryListAllParentMenu();
		for (Map<String, Object> map : rl) {
			List<Map<String, Object>> subList = this
					.queryListSubMenuByParentId(map.get("id") == null ? -1
							: Integer.parseInt(map.get("id").toString()));
			if (CollectionUtils.isNotEmpty(subList)) {
				for (Map<String, Object> tempMap : subList) {
					List<Map<String, Object>> tempSubList = queryListSubMenuByParentId(tempMap
							.get("id") == null ? -1 : Integer.parseInt(tempMap
							.get("id").toString()));
					tempMap.put("nodes", tempSubList);
				}
			}
			map.put("nodes", subList);
		}
		return rl;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int saveMenus(Map<String, Object> map) {
		return menuMapper.saveMenus(map);
	}

	/**
	 * 
	 * @param parseInt
	 * @return
	 */
	@Override
	public int deleteMenusByRoleId(int roleId) {
		return menuMapper.deleteMenusByRoleId(roleId);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryTreelist(Map<String, Object> map) {
		return menuMapper.queryTreelist(map);
	}

	/**
	 * 
	 * @param parseInt
	 * @return
	 */
	@Override
	public Map<String, Object> queryByIdReturnMap(int id) {
		return menuMapper.queryByIdReturnMap(id);
	}

	/**
	 * 
	 * @param map
	 */
	@Override
	public int insertMenuByMap(Map<String, Object> map) {
		return menuMapper.insertMenuByMap(map);
	}

	/**
	 * 
	 * @param map
	 */
	@Override
	public int updateMenuByMap(Map<String, Object> map) {
		return menuMapper.updateMenuByMap(map);
	}

	/**
	 * 
	 * @param pid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getChilds(String pid) {
		return menuMapper.getChilds(pid);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String, Object>> queryListAllMenuByRole(Map map)
			throws Exception {
		List<Map<String, Object>> rl = this.queryListAllParentMenuByRole(map);
		if (CollectionUtils.isNotEmpty(rl)) {
			for (Map<String, Object> mapTemp : rl) {
				List<Map<String, Object>> subList = this
						.queryListSubMenuByParentIdByRole(
								mapTemp.get("id") == null ? -1 : Integer
										.parseInt(mapTemp.get("id").toString()),
								map);
				if (CollectionUtils.isNotEmpty(subList)) {
					for (Map<String, Object> tempMap : subList) {
						List<Map<String, Object>> tempSubList = queryListSubMenuByParentIdByRole(
								tempMap.get("id") == null ? -1
										: Integer.parseInt(tempMap.get("id")
												.toString()), map);
						tempMap.put("nodes", tempSubList);
					}
				}
				mapTemp.put("nodes", subList);
			}
		}
		return rl;
	}

	/**
	 * //TODO 添加方法功能描述
	 * @param i
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> queryListSubMenuByParentIdByRole(
			int parentId, Map map) {
		map.put("parentId", parentId);
		return menuMapper.queryListSubMenuByParentIdByRole(map);
	}

	/**
	 * //TODO 添加方法功能描述
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Map<String, Object>> queryListAllParentMenuByRole(Map map) {
		return menuMapper.queryListAllParentMenuByRole(map);
	}
}
