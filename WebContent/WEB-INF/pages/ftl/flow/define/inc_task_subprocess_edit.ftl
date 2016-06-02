<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
 	 <tr class="title">
 		 <td colspan="2">子流程任务选项</td>
 	 </tr>
 	 <tr>
  		<td class="showTitle">(<font color="red">*</font>)选择子流程：</td>
  		<td>
  			<select id="subProcessId" name="subProcessId" style="width:200px;"
  				title="分拆环节允许下一步任务存在多个选择，选择方式不同。">
	 				<option value="1" selected="selected">1</option>
	 				<option value="2">2</option>
	 				<option value="3">3</option>
	 			</select>
  		</td>
  	</tr>
   <tr id="conditionOption">
  		<td class="showTitle" width="150px">说明：</td>
  		<td width="300px">
  			子流程任务可以指定一个现有的流程作为一个任务，相当于嵌套流程。<br>
  			嵌套流程执行完成后，可以接着执行后续节点任务。<br>
  		</td>
  </tr>		
 </table>