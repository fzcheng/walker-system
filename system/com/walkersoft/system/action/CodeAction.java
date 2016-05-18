package com.walkersoft.system.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.Assert;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.application.util.CodeUtils;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.CodeEntity;
import com.walkersoft.system.manager.CodeManagerImpl;

@Controller
public class CodeAction extends SystemAction {

	@Autowired
	private CodeManagerImpl codeManager;
	
	private static final String LOG_MSG_CODEDEL = "删除代码";
	private static final String LOG_MSG_CODEADD = "添加代码";
	private static final String LOG_MSG_CODEEDIT = "编辑代码";
	
	@RequestMapping("admin/code/index")
	public String index(Model model){
		setUserPointers(model);
		this.loadList(model, codeManager.queryPageCodeTables());
		return BASE_URL + "code";
	}
	
	@RequestMapping(value = "permit/admin/code/reload")
	public String reloadPage(Model model){
		this.loadList(model, codeManager.queryPageCodeTables());
		return BASE_URL + "code_list";
	}
	
//	/**
//	 * 加载代码表列表数据
//	 */
//	@RequestMapping("permit/admin/code/loadSplitCodeTable")
//	public void loadSplitCodeTable(HttpServletResponse response){
//		int iposStart = setAjaxListPage();
//		
//		GenericPager<CodeEntity> codeTableList;
//		ListPageContext.setCurrentPageSize(16);
//		try {
//			codeTableList = codeManager.queryPageCodeTables();
//			
//			Document document = CodeLoadUtil.getRootDoc();
//			Element rootElt = CodeLoadUtil.createRootElm("rows", document);
//			CodeLoadUtil.setElmAttr(rootElt, "total_count", String.valueOf(codeTableList.getTotalRows()));
//			CodeLoadUtil.setElmAttr(rootElt, "pos", String.valueOf(iposStart));
//			
//			for(CodeEntity codePo : codeTableList.getDatas()){
//				//数据类型
//				String codeId = codePo.getId();
//				Element row = CodeLoadUtil.createNewElm("row", rootElt);
//				CodeLoadUtil.setElmAttr(row, "id", codeId);
//				
//				Element noCell = CodeLoadUtil.createNewElm("cell", row);
//				CodeLoadUtil.setElmText(noCell, String.valueOf(codeId));
//				
//				Element nameCell = CodeLoadUtil.createNewElm("cell", row);
//				CodeLoadUtil.setElmText(nameCell, codePo.getName());
//				
//				//数据类型
//				String showDataType = "";
//				int codeSec = codePo.getCodeSec();
//				if(codeSec == 0){
//					showDataType = "系统代码";
//				} else if(codeSec == 1){
//					showDataType = "用户代码";
//				}
//				Element dtCell = CodeLoadUtil.createNewElm("cell", row);
//				CodeLoadUtil.setElmText(dtCell, showDataType);
//				
//				//Element editCell = CodeLoadUtil.createNewElm("cell", row);
//				//CodeLoadUtil.setElmText(editCell, "编辑");
//			}
//			
//			this.ajaxOutPutXml(document.asXML());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
	
	/**
	 * 根据代码表，加载其下面第一级代码项
	 * @throws IOException
	 */
	@RequestMapping("permit/admin/code/loadOneLvlItem")
	public void loadOneLvlItem(HttpServletResponse response) throws IOException{
		String parentCodeId = this.getParameter("codeTableId");
		if(parentCodeId == null || parentCodeId.equals("")){
			this.ajaxOutPutText("加载代码项失败！因为传递的代码表ID不存在。");
			return;
		}
		
//		JSONObject treeRoot = new JSONObject();
//		treeRoot.put("id", "0");
//		
//		JSONArray rootItem = new JSONArray();
//		
//		try {
//			List<CodeEntity> itemList = codeManager.queryOneLvlItem(parentCodeId);
//			this.addTreeNodes(itemList, rootItem);
//			treeRoot.put("item", rootItem);
//			this.ajaxOutPutJson(treeRoot);
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		this.ajaxOutPutJson(CodeUtils.getCodeTreeForJson(parentCodeId));
	}
	
	private void addTreeNodes(List<CodeEntity> itemList, JSONArray item){
		for(CodeEntity codePo : itemList){
			JSONObject jo = new JSONObject();
			jo.put("id", codePo.getId());
			String nodeName = codePo.getName();
			jo.put("text", nodeName);
			jo.put("userdata","[{name:'parentId',content:'"+codePo.getParentId()+"'}]");
			int childSum = codePo.getChildSum();
			if(childSum > 0){
				jo.put("child", "1");
			}
			item.add(jo);
		}
	}
	
	/**
	 * 根据上级代码ID，加载下级代码项
	 * @throws IOException
	 */
	@RequestMapping("permit/admin/code/loadChildrenByParent")
	public void loadChildrenByParent(HttpServletResponse response) throws IOException{
		String cid = this.getParameter("id");
		
		try {
			List<CodeEntity> itemList = codeManager.queryOneLvlItem(cid);
			
			JSONObject selectNode = new JSONObject();
			selectNode.put("id", cid);
			
			JSONArray childItems = new JSONArray();
			this.addTreeNodes(itemList, childItems);
			
			selectNode.put("item", childItems);
			this.ajaxOutPutJson(selectNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("permit/admin/code/showAddCodeItem")
	public String showAddCodeItem(Model model, String parentId){
		Assert.hasText(parentId);
		model.addAttribute("parentCodeId", parentId);
		return BASE_URL + "code_add";
	}
	
	@RequestMapping("permit/admin/code/showEditCodeItem")
	public String showEditCodeItem(Model model, String id){
		Assert.hasText(id);
		CodeEntity code = CodeUtils.getCodeObject(id);
		model.addAttribute("id", code.getId());
		model.addAttribute("codeName", code.getName());
		model.addAttribute("codeStand", code.getCodeId());
		return BASE_URL + "code_edit";
	}
	
	@RequestMapping("admin/code/saveCode")
	public void saveCode(HttpServletResponse response) throws IOException{
		String parentId = this.getParameter("parentId");
		String codeName = this.getParameter("codeName");
		String codeStand = this.getParameter("codeStand");
		
		CodeEntity entity = new CodeEntity();
		entity.setName(codeName);
		entity.setParentId(parentId);
		entity.setCodeId(codeStand);
		entity.setCodeType(1);
		entity.setCodeSec(1);
		try {
			codeManager.execInsertCodeItem(entity);
			systemLog(LOG_MSG_CODEADD + codeName, LogType.Add);
			
		} catch (Exception e) {
			this.ajaxOutPutText("保存代码项失败！操作数据库错误：" + e.getMessage());
			logger.error("", e);
			return;
		}
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	//编辑代码项
	@RequestMapping("admin/code/updateCode")
	public void updateCode(HttpServletResponse response) throws Exception{
		String codeId = this.getParameter("parentId");
		String codeName = this.getParameter("codeName");
		String codeStand = this.getParameter("codeStand");
		
		if(codeId == null || "".equals(codeId) 
				|| codeName == null || "".equals(codeName)){
			this.ajaxOutPutText("更新代码项失败！因为传递的ID或内容不存在。");
			return;
		}
		CodeEntity codePo = new CodeEntity();
		codePo.setId(codeId);
		codePo.setName(codeName);
		if(codeStand != null && !"".equals(codeStand)){
			codePo.setCodeId(codeStand);
		}
		try {
			codeManager.execUpdateCodeItem(codePo);
			systemLog(LOG_MSG_CODEEDIT + codeId, LogType.Edit);
		}catch (Exception e) {
			this.ajaxOutPutText("更新代码项失败！操作数据库错误。");
			return;
		}
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	//删除代码项
	@RequestMapping("admin/code/deleteCodeInfo")
	public void deleteCodeInfo(HttpServletResponse response) throws IOException{
		String codeId = this.getParameter("codeId");
		String parentId = this.getParameter("parentId");
		try {
			if(codeId != null && !codeId.equals("") && !codeId.equals("null")){
				CodeEntity code = codeManager.queryExist(codeId);
				//System.out.println("@##$"+codeLst);
				if(code != null && code.getChildSum() > 0){
					this.ajaxOutPutText("该节点包含子节点，请先删除子节点！");
					return;
				}else{
					codeManager.execDeleteCodeInfo(codeId, parentId);
					systemLog(LOG_MSG_CODEDEL + codeId, LogType.Delete);
					this.ajaxOutPutText(MESSAGE_SUCCESS);
				}
			}else{
				this.ajaxOutPutText("删除数据失败,缺少参数：codeId = " + codeId);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.ajaxOutPutText("删除数据失败！");
			return;
		}
	}
}
