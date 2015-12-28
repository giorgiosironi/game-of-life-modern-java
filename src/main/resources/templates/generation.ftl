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

	<a href="/plane?generation=${generation_index - 1}" rel="prev">Previous generation</a>

<a href="/plane?generation=${generation_index + 1}" rel="next">Next generation</a>