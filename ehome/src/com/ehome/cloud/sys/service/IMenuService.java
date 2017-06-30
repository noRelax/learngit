package com.ehome.cloud.sys.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.sys.model.MenuModel;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 菜单资源接口
 * 
 * @Title:IMenuService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 上午11:38:13
 * @version:
 */
public interface IMenuService extends IService<MenuModel> {

	int insertMenu(MenuModel menuModel) throws BusinessException;

	int updateMenu(MenuModel menuModel) throws BusinessException;

	int deleteMenuById(Integer id) throws BusinessException;

	List<MenuModel> queryForList(String keyword, Integer page, Integer rows)
			throws Exception;

	List<MenuModel> queryAuthPermissByRoleId(List<Integer> rolesId)
			throws BusinessException;

	public List<Map<String, Object>> queryListAllParentMenu() throws Exception;

	public List<Map<String, Object>> queryListAllMenu() throws Exception;

	public List<Map<String, Object>> queryListSubMenuByParentId(Integer parentId)
			throws Exception;

	/**
	 * 
	 * @param map
	 * @return 
	 */
	int saveMenus(Map<String, Object> map);

	/**
	 * 
	 * @param parseInt
	 * @return 
	 */
	int deleteMenusByRoleId(int roleId);

	/**
	 * 
	 * @param map
	 * @return 
	 */
	public List<Map<String, Object>> queryTreelist(Map<String, Object> map);

	/**
	 * 
	 * @param parseInt
	 * @return 
	 */
	Map<String, Object> queryByIdReturnMap(int parseInt);

	/**
	 * 
	 * @param map 
	 */
	int insertMenuByMap(Map<String, Object> map);

	/**
	 * 
	 * @param map 
	 */
	int updateMenuByMap(Map<String, Object> map);

	/**
	 * 
	 * @param pid
	 * @return 
	 */
	List<Map<String, Object>> getChilds(String pid);

	/**
	 * //TODO 添加方法功能描述
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> queryListAllMenuByRole(Map map) throws Exception;
}
