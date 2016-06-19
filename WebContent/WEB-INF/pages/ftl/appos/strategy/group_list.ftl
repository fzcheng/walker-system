<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="180px">策略组id</td>
	    <td width="180px">策略组名</td>
	    <td width="180px">备注</td>
	  </tr>
 
	<#list pagerView.datas as app>
	 	<tr>
		    <td>
		    	<input name="ids" type="checkbox" style="width: 50px;" value="${app.group_id}" class="check_box" />
		    </td>
		    <td>${app.group_id}</td>
		    <td>${app.group_name}</td>
		    <td>${app.remark}</td>
	  	</tr>
	  
	  	<tr style="display: none" class="newlist_tr_detail">
            <td width="833px" style="line-height: 1;" colspan="6">
                <div class="newlist_detail">
                    <div class="clearfix">
                        <ul>
                            <span>详情</span><li class="newlist_deatil_last"> 岗位职责：  1、进行文本数据的结构化处理，完成文本抽取算法的设计与实现；</li>
                        </ul>
                    </div>
                </div>
             </td>
        </tr>
	</#list>
</table>
	<#include "/common/page_view.ftl">