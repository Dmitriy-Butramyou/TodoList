<#import "parts/common.ftl" as c>
<@c.page>
<div>Список заданий</div>
 <form method="get" action="/index">
     <select name="filter" id="">
         <option value="New">New</option>
         <option value="In the process">In the process</option>
         <option value="Complete">Complete</option>
     </select>
     <#--<input type="text" name="filter" value="${filter?ifExists}">-->
     <button type="submit">Найти</button>
 </form>
    <#list tasks as task>
        <div>
            <strong>${task.authorName}</strong>
            <span>${task.textTask}</span>
            <span>${task.tag}</span>
            <i>${task.deadline}</i>
        </div>
    <#else >
        No task
    </#list>
<p>Get your greeting <a href="/">here</a></p>
</@c.page>
