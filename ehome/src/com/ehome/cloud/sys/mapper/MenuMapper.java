package com.ehome.cloud.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.model.MenuModel;
import com.ehome.core.frame.MyMapper;

/**
 * 菜单资源Mapper
 * 
 * @Title:MenuMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 上午10:14:46
 * @version:
 */
public interface MenuMapper extends MyMapper<MenuModel> {

	int insertMenu(MenuModel menuModel);

	int updateMenu(MenuModel menuModel);

	int deleteMenuById(@Param("id") Integer id);

	List<MenuModel> queryForList(@Param("keyword") String keyword);

	List<MenuModel> queryAuthPermissByRoleId(
			@Param("rolesId") List<Integer> rolesId);

	List<Map<String, Object>> queryListAllParentMenu();

	List<Map<String, Object>> queryListSubMenuByParentId(Integer parentId);
	
	 /**
	  * 
	  * @return 
	  */
	List<Map<String, Object>> queryListAllMenu();
	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	int saveMenus(Map<String, Object> map);
	
	 /**
	  * 
	  * @param roleId
	  * @return 
	  */
	int deleteMenusByRoleId(int roleId);
	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> queryTreelist(Map map);
	
	 /**
	  * 
	  * @param id
	  * @return 
	  */
	Map<String, Object> queryByIdReturnMap(int id);
	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	int insertMenuByMap(Map<String, Object> map);
	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	int updateMenuByMap(Map<String, Object> map);
	
	 /**
	  * 
	  * @param pid
	  * @return 
	  */
	List<Map<String, Object>> getChilds(String pid);

    @SuppressWarnings("rawtypes")
	List<Map<String, Object>> queryListAllParentMenuByRole(Map map);

    /**
     * //TODO 添加方法功能描述
     * @param roleId
     * @return
     */
    @SuppressWarnings("rawtypes")
	List<Map<String, Object>> queryListSubMenuByParentIdByRole(Map map);
}
