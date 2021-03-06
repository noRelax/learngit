package com.ehome.core.util;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class PageData extends HashMap<String, Object> implements
		Map<String, Object> {

	private static final long serialVersionUID = 1L;

	Map<String, Object> map = null;
	HttpServletRequest request;

	public PageData(HttpServletRequest request) {
		this.request = request;
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, String[]> properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<>();
		Iterator<Entry<String, String[]>> entries = properties.entrySet()
				.iterator();
		String name = "", value = "";
		while (entries.hasNext()) {
			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) entries
					.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		int currentPage = StringUtils.String2Int(
				returnMap.get("page") == null ? "1" : returnMap.get("page")
						.toString(), 1);
		int showCount = StringUtils.String2Int(
				returnMap.get("rows") == null ? "10" : returnMap.get("rows")
						.toString(), 10); // 每页显示条数
		int start = (currentPage - 1) * showCount;
		returnMap.put("page", start);
		returnMap.put("rows", showCount);
		map = returnMap;
	}

	public PageData() {
		map = new HashMap<>();
	}

	@Override
	public Object get(Object key) {
		Object obj = null;
		if (map.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) map.get(key);
			obj = request == null ? arr
					: (request.getParameter((String) key) == null ? arr
							: arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public Object getInt(Object key) {
		Object objvalu = map.get(key);
		Integer valu = null;
		if (objvalu instanceof Integer) {
			if (objvalu != null) {
				valu = ((Integer) objvalu).intValue();
			}
		}
		return valu;
	}

	public String getString(Object key) {
		return (String) get(key);
	}

	public String getString(Object key, String defaultValue) {
		return get(key) == null ? defaultValue : (String) get(key);
	}

	public String getStringIgnoreNull(Object key) {
		if (null == (String) get(key)) {
			return "";
		}
		return (String) get(key);
	}

	public Integer getInteger(Object key) {
		return (Integer) getInt(key);
	}

	public Integer getInteger(Object key, Integer defaultValue) {
		return NumberUtils.toInt(get(key).toString(), defaultValue);
	}

	@Override
	public Object put(String key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set<Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return map.values();
	}
}
