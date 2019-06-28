<#import "parts/common.ftl" as c>
<@c.page>
<div>
    <h5>${task.deadline?date}</h5>
</div>
<div>
    <h5>${task.topicTask}</h5>
</div>
<div>
    <h5>${task.textTask}</h5>
</div>
<#--<div>-->
    <#--<h5>${task.filename}</h5>-->
<#--</div>-->
<div>
    <h5>${task.tag}</h5>
</div>


</@c.page>
