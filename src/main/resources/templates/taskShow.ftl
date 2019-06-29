<#import "parts/common.ftl" as c>
<@c.page>
<div class="card text-center">
    <div class="card-header">
        Deadline: ${task.deadline?date}
    </div>
    <div class="card-body">
        <h5 class="card-title">${task.topicTask}</h5>
        <p class="card-text">${task.textTask}</p>
        <a href="/task/change/${task.id}" class="btn btn-outline-primary">Change</a>
    </div>
    <div class="card-footer text-muted">
        <#if attachment?has_content>
            ${attachment.originalName}
        </#if>

      <div>Have a nice day :)</div>

    </div>
</div>

</@c.page>
