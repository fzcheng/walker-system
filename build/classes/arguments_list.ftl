<table border="0" cellpadding="0" cellspacing="0" width="560px" class="formtable">
	<tr>
 		<td class="showTitle" colspan="4">分组名称</td>
 	</tr>

	<tr>
 		<td class="title" width="160px">[id]</td>
 		<td width="80px">类型</td>
 		<td width="200px" align="left">
 			<input type="text" id="var" class="textbox_req" maxlength="200"/>
 		</td>
 		<td width="120px">默认[]</td>
 	</tr>
 	
</table>
<br>

<table id="mytable" cellspacing="0" summary="The technical specifications of the Apple PowerMac G5 series">  
<caption> </caption>  
  <tr>  
    <th scope="col" abbr="Configurations" width="220px">Configurations</th>  
    <th scope="col" abbr="Dual 1.8">Dual 1.8GHz</th>  
    <th scope="col" abbr="Dual 2">Dual 2GHz</th>  
 		<th scope="col" abbr="Dual 2.5">Dual 2.5GHz</th>  
  </tr>
  <#assign _index = 0>
  <#list varList as var> 
  <#if (_index % 2 == 0)>
  	<#assign _color = "spec">
  	<#assign _color2 = "">
  <#else>
  	<#assign _color = "specalt">
  	<#assign _color2 = "alt">
  </#if>
  <tr>  
    <th scope="row" abbr="Model" class="${_color}">[id]${var.description}</th>  
    <td class="${_color2}">M9454LL/A</td>
    <td class="${_color2}">M9455LL/A</td>  
    <td class="${_color2}">M9457LL/A</td>
  </tr>
  <#assign _index = _index+1>
  </#list> 
  <tr>  
    <th scope="row" abbr="G5 Processor" class="specalt">mapabc</th>  
    <td class="alt">Dual 1.8GHz PowerPC G5</td>  
    <td class="alt">Dual 2GHz PowerPC G5</td>  
    <td class="alt">Dual 2.5GHz PowerPC G5</td>  
  </tr>  
  <tr>  
    <th scope="row" abbr="Frontside bus" class="spec">Lennvo</th>  
    <td>900MHz per processor</td>  
    <td>1GHz per processor</td>  
    <td>1.25GHz per processor</td>  
  </tr>  
  <tr>  
    <th scope="row" abbr="L2 Cache" class="specalt">Black</th>  
    <td class="alt">512K per processor</td>  
    <td class="alt">512K per processor</td>  
    <td class="alt">512K per processor</td>  
  </tr>  
</table> 