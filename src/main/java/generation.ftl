<h1>Game Of Life</h1>

<#-- TODO: extract style.css -->
<style type="text/css">
td { border: 1px solid black; width: 30px; height: 30px; }
td.state_alive {
	background-color: black;
}
</style>
<table>

<#list 0..(generation.rows-1) as x>
  <tr>
  <#list 0..(generation.columns-1) as y>
  	<td class="state_${generation.state(x, y)?lower_case}"></td>
  </#list>
  </tr>
</#list>
</table>