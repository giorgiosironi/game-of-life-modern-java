<link rel="StyleSheet" href="/style.css" media="screen" />

<h1>Game Of Life</h1>
<table>

<#list 0..(generation.rows-1) as x>
  <tr>
  <#list 0..(generation.columns-1) as y>
  	<td class="state_${generation.state(x, y)?lower_case}"></td>
  </#list>
  </tr>
</#list>
</table>

<div class="navigation">
	
	<div class="generation_link">
		<#if (links.prev??)>
		    <a href="${links.prev}" rel="prev">Previous generation</a>
		<#else>
		    Previous generation
		</#if>
	</div>
	<div class="generation_link">
		<a href="${links.next}" rel="next">Next generation</a>
	</div>
</div>