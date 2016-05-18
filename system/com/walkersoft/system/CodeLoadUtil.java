package com.walkersoft.system;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

//import cn.com.xinyuan.modp.core.security.ModpSecurityContext;
//
//import com.core.cache.CodeCacheUtil;
//import com.core.cache.DepartCacheUtil;
//import com.core.cache.ProjectSecCacheUtil;
//import com.core.cache.SaleUserCacheUtil;
//import com.core.cache.UserCacheUtil;
//import com.core.common.PreferenceUtil;
//import com.core.common.SystemConstant;
//import com.core.pojo.DepartmentObj;
//import com.core.security.pojo.User;
//import com.rights.sort.DynamicPoBySxhCompare;
//import com.zzxy.framework.common.LongCalendar;
//import com.zzxy.framework.common.LongDateHelper;
//import com.zzxy.framework.common.vo.TreeObject;
//import com.zzxy.framework.po.DynamicPO;

/**
 * 移植的老代码，已被废弃
 * @author shikeying
 *
 */
@Deprecated
public class CodeLoadUtil {


//	/**
//	 * �õ����µ�һ���ַ���ʽ�磺2011/05/01
//	 * @return
//	 */
//	public static String getFirstDayMonthForShow(){
//		long _date = LongDateHelper.getMonthFirstDay(LongCalendar.getLongCalendar(), 1);
//		return LongCalendar.toString(_date);
//	}
//	
//	/**
//	 * �õ��������һ���ַ���ʽ�磺2011/05/31
//	 * @return
//	 */
//	public static String getLatsDayMonthForShow(){
//		return LongCalendar.toString(LongDateHelper.getLastDayOfMonth());
//	}
//	
//	/**
//	 * ����û�ID����������
//	 */
//	public static String getUserNameById(String userId){
//		return UserCacheUtil.getUserNameById(userId);
//	}
//	
//	/**
//	 * ��ݲ��ű�ţ����ز�������
//	 * @param departNum
//	 * @return
//	 */
//	public static String getDepartNameById(String departNum){
//		return DepartCacheUtil.getDepartNameById(departNum);
//	}
//	/**
//	 * �Էֺŷָ����ַ����ݴ���
//	 * */
//	@SuppressWarnings("unchecked")
//	public static List getDataWithSemi(String semiData){
//		
//		//ȥ���ո񲢰����ķֺ�תΪӢ�ķֺ�
//		String list1 = semiData.replaceAll(" ","").replaceAll("��",";");
//		List _sLst =  new ArrayList();
//		if(semiData.indexOf(";")!=-1){
//				String _lst[] = list1.split(";");
//				for (int j = 0; j < _lst.length; j++) {
//					_sLst.add(_lst[j]);
//				}
//				return _sLst;
//			}else{
//				if(semiData !=null &&!semiData.equals(""))
//						_sLst.add(semiData);
//				else
//					_sLst = null;
//			}
//		return _sLst;
//	}
//	/**
//	 * �Զ��ŷָ����ַ����ݴ���
//	 * */
//public static String getDataWithComma(String commaData){
//		
//		//ȥ���ո񲢰����Ķ��ź�תΪӢ�Ķ���
//		String list1 = commaData.replaceAll(" ","").replaceAll("��",",");
//		String comdata="";
//		if(commaData !=null &&!commaData.equals("")){
//			if(commaData.indexOf(",")!=-1){
//					String _lst[] = list1.split(",");
//					for (int j = 0; j < _lst.length; j++) {
//						if(j < _lst.length-1){
//							if(_lst[j] == null || _lst[j].equals("") || "null".equals(_lst[j]))
//								comdata += "? ~";
//							else
//								comdata += LongCalendar.toString(Long.parseLong(_lst[j]))+" ~ ";
//						}
//						if(j == _lst.length-1){
//							if(_lst[j] != null && !_lst[j].equals(""))
//							    comdata += LongCalendar.toString(Long.parseLong(_lst[j]));
//							else
//								comdata += "?";
//						}
//					}
//			}else{
//				    comdata = LongCalendar.toString(Long.parseLong(list1)) +"~ ?";
//		
//			}
//		}
//		return comdata;
//	}
//	
//	/**
//	 * �������л�����б�
//	 */
//	public static List<DepartmentObj> getDepartmentObjList(){
//		return DepartCacheUtil.departAllList;
//	}
//	
//	/**
//	 *��������б� 
//	 * */
//	@SuppressWarnings("unchecked")
//	public static List getYearList(){
//		Calendar calendar = Calendar.getInstance();    
//		//ȡ����ǰ�����
//        int currentYear = calendar.get(Calendar.YEAR); 
//		int minyear = PreferenceUtil.getMinYear();
//		
//		List yearList = new ArrayList();
//		for(int i=currentYear;i>=minyear;i--)
//		    yearList.add(i);
//		return yearList; 
//	}
//	
//	
//	/**
//	 * �������л�XML�ı�����
//	 */
//	public static String getDepartmentXML(String selectedId){
//		List<DepartmentObj> departments = getDepartmentObjList();
//		Document rootDoc = CodeLoadUtil.getRootDoc();
//		Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//		Element areaCell = CodeLoadUtil.createNewElm("option", rootElt);
//		CodeLoadUtil.setElmAttr(areaCell, "value", "");
//		CodeLoadUtil.setElmText(areaCell, "ȫ��");
//		//CodeLoadUtil.setElmAttr(areaCell, "selected", "selected");
//		for(DepartmentObj department :departments){
//			String departName = department.getDepartName();
//			String departId = department.getDepartId();
//			areaCell = CodeLoadUtil.createNewElm("option", rootElt);
//			CodeLoadUtil.setElmAttr(areaCell, "value", departId);
//			CodeLoadUtil.setElmText(areaCell, departName);
//			if(selectedId != null 
//					&& !selectedId.equals("")
//					&& selectedId.equals(departId)){
//				CodeLoadUtil.setElmAttr(areaCell, "selected", "selected");
//			}
//		}
//		return rootDoc.asXML().replace("\n", "");
//	}
//	
//	/**
//     * @���ܣ���ݲ���Id���ظò����Լ��Ӳ���XML���ݣ�combo���ʹ��
//     * @return ������Ϣxml
//     * @���ڣ�2012-06-12
//     */
//    public static String getDeptComboXMLById(String deptId){
//        DepartmentObj deptObj = DepartCacheUtil.getDepartById(deptId);
//        List<DepartmentObj> deptSubList = deptObj.getChildDepartList();
//        
//        Document rootDoc = CodeLoadUtil.getRootDoc();
//        Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//        
//        if(deptSubList != null){
//            Element allCell = CodeLoadUtil.createNewElm("option", rootElt);
//            CodeLoadUtil.setElmAttr(allCell, "value", "");
//            CodeLoadUtil.setElmText(allCell, "ȫ��");
//            CodeLoadUtil.setElmAttr(allCell, "selected", "selected");
//        }
//        
//        Element deptCell = CodeLoadUtil.createNewElm("option", rootElt);
//        CodeLoadUtil.setElmAttr(deptCell, "value", deptId);
//        CodeLoadUtil.setElmText(deptCell, deptObj.getDepartName().replace("[+]", ""));
//        if(deptSubList == null){
//            CodeLoadUtil.setElmAttr(deptCell, "selected", "selected");
//        }
//        
//        getDeptSubXML(deptSubList, rootElt, deptCell,"&nbsp;&nbsp;- ");
//        
//        
//        return rootDoc.asXML().replace("\n", "");
//    }
//    
//    /**
//     * @���ܣ������Ӳ�����Ϣ
//     * @param deptSubList �Ӳ�����Ϣ�б�
//     * @param rootElt select���Ԫ�ظ�Ԫ��<complete>
//     * @param deptCell select�����Ԫ��<option>
//     * @param tip �Ӳ��������֮��Ĵ�λ���
//     * @���ڣ�2012-06-12
//     */
//    private static void getDeptSubXML(List<DepartmentObj> deptSubList, Element rootElt, Element deptCell, String tip){
//        if (deptSubList != null) {
//            for (DepartmentObj obj : deptSubList) {
//                deptCell = CodeLoadUtil.createNewElm("option", rootElt);
//                CodeLoadUtil.setElmAttr(deptCell, "value", obj.getDepartId());
//                CodeLoadUtil.setElmText(deptCell, tip + obj.getDepartName().replace("--", ""));
//                List<DepartmentObj> subList = obj.getChildDepartList();
//                getDeptSubXML(subList, rootElt, deptCell, "&nbsp;&nbsp;&nbsp;&nbsp;" + tip);
//            }
//        }
//    }
//    
//    
//    
//	/**
//	 * �������л�XML�ı�����(��������ȫ����)
//	 */
//	public static String getAllDepartmentXML(String selectedId){
//		List<DepartmentObj> departments = getDepartmentObjList();
//		Document rootDoc = CodeLoadUtil.getRootDoc();
//		Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//		//Element areaCell = CodeLoadUtil.createNewElm("option", rootElt);
//		//CodeLoadUtil.setElmAttr(areaCell, "selected", "selected");
//		for(DepartmentObj department :departments){
//			String departName = department.getDepartName();
//			String departId = department.getDepartId();
//			Element areaCell = CodeLoadUtil.createNewElm("option", rootElt);
//			CodeLoadUtil.setElmAttr(areaCell, "value", departId);
//			CodeLoadUtil.setElmText(areaCell, departName);
//			if(selectedId != null 
//					&& !selectedId.equals("")
//					&& selectedId.equals(departId)){
//				CodeLoadUtil.setElmAttr(areaCell, "selected", "selected");
//			}
//		}
//		return rootDoc.asXML().replace("\n", "");
//	}
//	
//	/**
//	 * ��ݴ���ID�����ش�������
//	 * @param codeId
//	 * @return
//	 */
//	public static String getCodeNameById(String codeId){
//		return CodeCacheUtil.getCodeNameById(codeId);
//	}
//	
//	/**
//	 * ��ݴ����ID��������һ������������
//	 */
//	public static List<DynamicPO> getSingleCodeDataById(String codeTableId){
//		List<DynamicPO> childCodeLst = new ArrayList<DynamicPO>();
//		TreeObject codeTreeObj = CodeCacheUtil.getCodeTree(codeTableId);
//		if(codeTreeObj != null){
//			for(Map.Entry<String, TreeObject> entry : codeTreeObj.getChildrenData().entrySet()){
//				DynamicPO _codePo = (DynamicPO)entry.getValue().getData();
//				childCodeLst.add(_codePo);
//			}
//			Collections.sort(childCodeLst, new DynamicPoBySxhCompare());
//		}
//		return childCodeLst;
//	}
//	
//
//	/**
//	 * @���ܣ���ݴ��������idȡ�øø�����µĴ������.
//	 * @param codeRootId ������ID
//	 * @return List<String> �����������
//	 */
//    public static List<String> getCodeNameListByRootId(String codeRootId) {
//        List<String> childCodeNameLst = new ArrayList<String>();
//        TreeObject codeTreeObj = CodeCacheUtil.getCodeTree(codeRootId);
//        if (codeTreeObj != null) {
//            for (Map.Entry<String, TreeObject> entry : codeTreeObj.getChildrenData().entrySet()) {
//                DynamicPO codePo = (DynamicPO) entry.getValue().getData();
//                childCodeNameLst.add(codePo.get("NAME").toString());
//            }
//        }
//        return childCodeNameLst;
//    }
//	
//	/**
//	 * ��ݴ����ID��������һ������XML����
//	 * @param codeTableId
//	 * @param selectedId:�û�ѡ��Ĵ���ID
//	 * @return
//	 */
//	public static String getSingleCodeXMLText(String codeTableId, String selectedId){
//		List<DynamicPO> childCodeLst = getSingleCodeDataById(codeTableId);
//		Document rootDoc = CodeLoadUtil.getRootDoc();
//		Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//		
//		if(childCodeLst != null && childCodeLst.size() > 0){
//			for(DynamicPO _codePo : childCodeLst){
//				String codeId = _codePo.get("ID").toString();
//				Element areaCell = CodeLoadUtil.createNewElm("option", rootElt);
//				CodeLoadUtil.setElmAttr(areaCell, "value", codeId);
//				CodeLoadUtil.setElmText(areaCell, _codePo.get("NAME").toString());
//				
//				if(selectedId != null && !selectedId.equals("")){
//					if(selectedId.equals(codeId)){
//						System.out.println("selected");
//						CodeLoadUtil.setElmAttr(areaCell, "selected", "selected");
//					}
//				}else{
//					if(_codePo.size()==1){
//						CodeLoadUtil.setElmAttr(areaCell, "selected", "1");
//					}
//				}
//			}
//		}
//		return rootDoc.asXML().replace("\n", "");
//	}
//	
//	/**
//	 * ��ݴ����ID��������һ������XML����
//	 * @param codeTableId
//	 * @param selectedId
//	 * @param exclude:Ҫ�ų�Ĵ���ID�������Ӣ�Ķ��ŷָ�
//	 * @return
//	 */
//	public static String getSingleCodeXMLText(String codeTableId, String selectedId, String exclude){
//		List<DynamicPO> childCodeLst = getSingleCodeDataById(codeTableId);
//		Document rootDoc = CodeLoadUtil.getRootDoc();
//		Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//		
//		if(childCodeLst != null && childCodeLst.size() > 0){
//			//�����ų�Ĵ��룬���й���
//			List<String> excludeLst = new ArrayList<String>();
//			if(exclude != null && !exclude.equals("")){
//				String[] excludeVals = exclude.split(",");
//				if(excludeVals != null && excludeVals.length > 0){
//					for(String _e : excludeVals){
//						if(!excludeLst.contains(_e)){
//							excludeLst.add(_e);
//						}
//					}
//				}
//			}
//			//
//			for(DynamicPO _codePo : childCodeLst){
//				String codeId = _codePo.get("ID").toString();
//				//ȥ���ų��
//				if(excludeLst.contains(codeId)){
//					continue;
//				}
//				Element areaCell = CodeLoadUtil.createNewElm("option", rootElt);
//				CodeLoadUtil.setElmAttr(areaCell, "value", codeId);
//				CodeLoadUtil.setElmText(areaCell, _codePo.get("NAME").toString());
//				
//				if(selectedId != null && !selectedId.equals("")){
//					if(selectedId.equals(codeId)){
//						System.out.println("selected");
//						CodeLoadUtil.setElmAttr(areaCell, "selected", "selected");
//					}
//				}else{
//					if(_codePo.size()==1){
//						CodeLoadUtil.setElmAttr(areaCell, "selected", "1");
//					}
//				}
//			}
//		}
//		return rootDoc.asXML().replace("\n", "");
//	}
//	
//	/**
//	 * �����û����õ�����Ȩ��XML���ݣ���combo���ʹ��
//	 * @return
//	 */
//	
//	public static String getUserOfAreaXML(){
////		List<DynamicPO> result = new ArrayList<DynamicPO>();
//		List<String> _userSecAreaLst = ModpSecurityContext.getCurrentOperator().getUserMgtArea();
//		List<DynamicPO> allAreaList = CodeLoadUtil.getSingleCodeDataById(SystemConstant.CODE_ID_AREA);
//		
//		Document rootDoc = CodeLoadUtil.getRootDoc();
//		Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//		CodeLoadUtil.setElmAttr(rootElt, "id", "area");
//		
//		if(_userSecAreaLst != null && _userSecAreaLst.size() > 0){
//			for(String _cid : _userSecAreaLst){
//				Element areaCell =CodeLoadUtil.createNewElm("option", rootElt);
//				CodeLoadUtil.setElmAttr(areaCell, "value", _cid);
//				CodeLoadUtil.setElmText(areaCell, CodeCacheUtil.getCodeNameById(_cid));
//				if(_cid.length() == 1){
//					CodeLoadUtil.setElmAttr(areaCell, "selected", "1");
//				}
//			}
//		} else {
//			for(DynamicPO _codePo : allAreaList){
//				Element areaCell =CodeLoadUtil.createNewElm("option", rootElt);
//				CodeLoadUtil.setElmAttr(areaCell, "value", _codePo.get("ID").toString());
//				CodeLoadUtil.setElmText(areaCell, _codePo.get("NAME").toString());
//				//CodeLoadUtil.setElmAttr(areaCell, "selected", "1");
//			}
//		}
//		return rootDoc.asXML().replace("\n", "");
//	}
//	
//	/**
//	 * ���ؿյ�XML���ݣ�combo���ʹ��
//	 * @return
//	 */
//	public static String getEmptyComboXML(){
//		Document rootDoc = CodeLoadUtil.getRootDoc();
//		CodeLoadUtil.createRootElm("complete", rootDoc);
//		return rootDoc.asXML().replace("\n", "");
//	}
//	
//	/**
//	 * @���ܣ�����Ӫ����Ա�б��XML����,combo���ʹ��.
//	 * @param flag:Ӫ����Ա��ְ��ְ״̬��true:����ʾ��ְ״̬��false����ʾ��ְ״̬
//	 * @return string Ӫ����Աxml�ַ�
//	 * @���ڣ�2012-06-10
//	 */
//    public static String getSaleUserXML(String departId, boolean flag) {
//        Document rootDoc = CodeLoadUtil.getRootDoc();
//        Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//        List<User> saleUserList = null;
//        if (departId != null && !"".equals(departId)) {
//            saleUserList = SaleUserCacheUtil.getDepartSaleUsrLstAble(departId, flag);
//            // saleUserList = SaleUserCacheUtil.getDepartSaleUsrLst(departId);
//        } else {
//            saleUserList = SaleUserCacheUtil.getAllSaleUserListAble(flag);
//            // saleUserList = SaleUserCacheUtil.getAllSaleUserList();
//        }
//        if (saleUserList != null && saleUserList.size() > 0) {
//            for (User user : saleUserList) {
//                Element sUserCell = CodeLoadUtil.createNewElm("option", rootElt);
//                CodeLoadUtil.setElmAttr(sUserCell, "value", user.getId());
//                CodeLoadUtil.setElmText(sUserCell, user.getUsername());
//            }
//        }
//        return rootDoc.asXML().replace("\n", "");
//    }
//	
//	/**
//	 * @���ܣ���������Ӫ����Ա�б��XML����,combo���ʹ��.
//	 * @param flag:Ӫ����Ա��ְ��ְ״̬��true:��ְ״̬��false����ְ״̬
//	 * @return string Ӫ����Աxml�ַ�
//     * @���ڣ�2012-06-10
//	 */
//    public static String getSaleAreaUserXML(boolean flag) {
//        List<String> _userSecAreaLst = ModpSecurityContext.getCurrentOperator().getUserMgtArea();
//        String departId = ModpSecurityContext.getCurrentOperator().getDepartId();
//        Document rootDoc = CodeLoadUtil.getRootDoc();
//        Element rootElt = CodeLoadUtil.createRootElm("complete", rootDoc);
//        System.out.println("_userSecAreaLst" + _userSecAreaLst);
//
//        // ���յ������û�
//        List<User> resultLst = new ArrayList<User>();
//
//        if (_userSecAreaLst != null && _userSecAreaLst.size() > 0) {
//
//            // List<User> departUserList =
//            // SaleUserCacheUtil.getDepartSaleUsrLst(departId);
//            List<User> departUserList = SaleUserCacheUtil.getDepartSaleUsrLstAble(departId, flag);
//            List<String> areaUsrLst = ProjectSecCacheUtil.getAreaSecUsrLst(_userSecAreaLst);
//            for (User _usr : departUserList) {
//                String _usrId = _usr.getId();
//                if (areaUsrLst.contains(_usrId)) {
//                    resultLst.add(_usr);
//                }
//            }
//            if (resultLst != null && resultLst.size() > 0) {
//                for (User user : resultLst) {
//                    Element sUserCell = CodeLoadUtil.createNewElm("option", rootElt);
//                    CodeLoadUtil.setElmAttr(sUserCell, "value", user.getId());
//                    CodeLoadUtil.setElmText(sUserCell, user.getUsername());
//                }
//            }
//        }
//        return rootDoc.asXML().replace("\n", "");
//    }
//	
//	/**
//	 * �õ���ǰ�û����ڵĲ���(��ҳ��ֱ��ʹ�ã�ֻ����⹫����һ����)
//	 * @return
//	 */ 
//	public static String getCurrentUserDepartId(){
//		return ModpSecurityContext.getCurrentOperator().getDepartId();
//	}
	
	
	public static Document getRootDoc(){
		return DocumentHelper.createDocument();
	}
	
	public static Element createRootElm(String _name, Document _doc){
		return _doc.addElement(_name);
	}
	
	public static Element createNewElm(String _name, Element parent){
		return parent.addElement(_name);
	}
	
	public static void setElmText(Element _elm, String _value){
		_elm.setText(_value);
	}
	
	public static void setElmAttr(Element _elm, String _attr, String _value){
//		_elm.attributeValue(_attr, _value);
		_elm.setAttributeValue(_attr, _value);
	}
}
