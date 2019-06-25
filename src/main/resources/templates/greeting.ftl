<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>Hello, user</div>
<div>
    <@l.logout/>
</div>
<p><a href="/addTask">Add new task</a></p>
<p><a href="/index">Index Page</a></p>

</@c.page>
