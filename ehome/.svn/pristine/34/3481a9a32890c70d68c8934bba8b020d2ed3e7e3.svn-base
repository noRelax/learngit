package com.ehome.activiti.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ehome.activiti.general.Code;
import com.ehome.activiti.services.WorkFlowService;
import com.ehome.activiti.services.WorkFlowUtilService;
import com.ehome.cloud.common.controller.CommonController;
import com.ehome.cloud.sys.service.IOrgainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 工作流模型控制器
 * 
 * @author kokJuis
 * @version 1.0
 * @date 2017-2-6
 */
@Controller
@RequestMapping("/model/*")
public class ModuleController extends CommonController {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private WorkFlowUtilService workFlowUtilService;

	// 引用水洪的
	@Resource
	private IOrgainService orgainService;

	/**
	 * 跳转到模型列表
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-6
	 * @return
	 */
	@RequestMapping(value = "/goModelList")
	public ModelAndView goModelList(HttpServletRequest request) {

		String category = request.getParameter("category");

		ModelAndView mav = new ModelAndView("activiti/modelList.html");
		mav.addObject("category", category);
		return mav;
	}

	/**
	 * 跳转到新增模型
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-12
	 * @return
	 */
	@RequestMapping(value = "/goAddModel")
	public ModelAndView goAddModel() {
		ModelAndView mav = new ModelAndView("process/addModel");
		return mav;

	}

	// **************************获取组织结构列表***************************

	/**
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-29
	 * @return
	 */
	@RequestMapping(value = "/goSelectCity")
	public ModelAndView goSelectCity() {
		ModelAndView mav = new ModelAndView("activiti/selectOrgList.html");
		return mav;

	}

	/**
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-29
	 * @return
	 */
	@RequestMapping(value = "/goSelectOrg")
	public ModelAndView goSelectOrg() {
		ModelAndView mav = new ModelAndView("activiti/selectOrgList.html");
		return mav;

	}

	/**
	 * 查询组织结构树
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-9
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryTreelist")
	@ResponseBody
	public List<Map<String, Object>> queryTreelist(HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("baseOrgId", 0);
		List<Map<String, Object>> varList = orgainService.queryTreelist(map);

		List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> map2 : varList) {
			if (((int) map2.get("type")) != 1) {
				tmp.add(map2);
			}
		}

		return tmp;

	}

	/**
	 * 查询全部模型
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectAllModel")
	public Map<String, Object> selectAllModel(HttpServletRequest request) {

		// 获取前端过来的参数
		Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数
																		// 每次加1
		Integer iDisplayStart = Integer.valueOf(request
				.getParameter("iDisplayStart"));// 起始
		Integer iDisplayLength = Integer.valueOf(request
				.getParameter("iDisplayLength"));// 每页显示的size

		String category = request.getParameter("category");// modelKey

		Map<String, Object> map = new HashMap<String, Object>();

		List<Model> resultList = workFlowService.selectAllModel(category,
				iDisplayStart, iDisplayLength);

		// 为操作次数加1
		int initEcho = sEcho + 1;

		map.put("sEcho", initEcho);
		map.put("iTotalRecords", workFlowService.selectModelCount(category));
		map.put("iTotalDisplayRecords",
				workFlowService.selectModelCount(category));
		map.put("aData", resultList);

		return map;
	}

	/**
	 * 添加模型
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-5
	 * @param name
	 *            流程名称
	 * @param key
	 *            流程key
	 * @param description
	 *            备注
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "create")
	public void create(@RequestParam("name") String name,
			@RequestParam("key") String key,
			@RequestParam("category") String category,
			@RequestParam("description") String description,
			HttpServletRequest request, HttpServletResponse response) {
		try {

			// 创建模型
			Model modelData = workFlowService.createModel(name, key, category,
					description);

			// 返回到可视编辑页面
			response.sendRedirect(request.getContextPath()
					+ "/static/activitiViewer/modeler.html?modelId="
					+ modelData.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 动态添加模型,这个方法只能用在简单的流程，不推荐使用
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-12
	 * @param orgId
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "dynamicAddModel")
	public Map<String, String> dynamicAddModel(String processName,
			String orgId, String taskName) {

		Map<String, String> result = new HashMap<String, String>();

		try {

			// 创建模型资源
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace",
					"http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.set("stencilset", stencilSetNode);

			// 创建模型
			Model modelData = repositoryService.newModel();
			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			String description = StringUtils.defaultString("备注");
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(processName);
			modelData.setKey(StringUtils.defaultString("process"));

			// 保存模型
			repositoryService.saveModel(modelData);

			// 创建bpmn模型
			BpmnModel model = new BpmnModel();
			// 新建流程
			Process process = new Process();
			// 设置流程ID
			process.setId("process");
			// 设置流程名称
			process.setName(processName);

			// 创建bpmn元素
			// 添加开始元素
			process.addFlowElement(workFlowUtilService.createStartEvent());

			// 获取节点机构
			String[] orgIds = orgId.split(",");
			String[] taskNames = taskName.split(",");

			for (int i = 0; i < orgIds.length; i++) {
				// 添加任务节点
				process.addFlowElement(workFlowUtilService.createUserTask("t_"
						+ orgIds[i], taskNames[i]));
			}

			// 添加结束流程
			process.addFlowElement(workFlowUtilService.createEndEvent());

			// 添加箭头,任务首尾相连
			for (int i = 0; i <= orgIds.length; i++) {

				if (i == 0) {
					process.addFlowElement(workFlowUtilService
							.createSequenceFlow("start", "t_" + orgIds[i], null));
				} else if (i == orgIds.length) {
					process.addFlowElement(workFlowUtilService
							.createSequenceFlow("t_" + orgIds[i - 1], "end",
									"${direction=='go'}"));
				} else {
					process.addFlowElement(workFlowUtilService
							.createSequenceFlow("t_" + orgIds[i - 1], "t_"
									+ orgIds[i], "${direction=='go'}"));

					process.addFlowElement(workFlowUtilService
							.createSequenceFlow("t_" + orgIds[i], "t_"
									+ orgIds[0], "${direction=='back'}"));

				}

			}

			// 把流程添加到bpmn模型
			model.addProcess(process);

			// 生成BPMN自动布局
			new BpmnAutoLayout(model).execute();

			// 将模型JSON数据的Java对象树转换成BpmnModel实例
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			ObjectNode tmp = jsonConverter.convertToJson(model);
			tmp.put("resourceId", modelData.getId());

			// 添加流程资源
			repositoryService.addModelEditorSource(modelData.getId(), tmp
					.toString().getBytes("utf-8"));

			// 部署这个BPMN模型
			// 创建DeploymentBuilder实例
			DeploymentBuilder builder = repositoryService.createDeployment();
			// 过滤相同的流程，防止重复部署，关键是为流程部署起一个名字，没有名字不会过滤
			builder.enableDuplicateFiltering().name(processName);
			// 部署流程模型
			builder.name(processName);

			// 将BpmnModel实例转成BPMN XML数据
			byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model,
					"UTF-8");
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);

			String bpmnName = processName + ".bpmn20.xml";
			builder.addInputStream(bpmnName, in).deploy();

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "添加成功");

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "服务器异常");
		}
		return result;

	}

	/**
	 * 删除模型
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-6
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delModel", method = RequestMethod.POST)
	public Map<String, String> delModel(String ids) {

		Map<String, String> result = new HashMap<String, String>();

		try {
			workFlowService.delModel(ids);
			result.put("code", Code.SUCCESS + "");
			result.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "服务器异常");
		}

		return result;
	}

	/**
	 * 编辑模型
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-6
	 * @param modelId
	 */
	@RequestMapping(value = "goEdit")
	public void goEdit(String modelId, HttpServletRequest request,
			HttpServletResponse response) {

		// 返回到可视编辑页面
		try {
			response.sendRedirect(request.getContextPath()
					+ "/static/activitiViewer/modeler.html?modelId=" + modelId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 部署模型
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-6
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deploymentModel")
	public Map<String, String> deploymentModel(String modelId,
			String processName, String tenantId, HttpServletRequest request) {

		Map<String, String> result = new HashMap<String, String>();

		try {

			workFlowService.deploymentModel(modelId, tenantId);

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "部署成功");

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SUCCESS + "");
			result.put("msg", "部署失败,已有相同流程部署或者模型有误");
		}
		return result;
	}

}
