<#import "parts/common.ftl" as c>
<@c.page>
<div class="card text-center">


    <div class="card-header">
        <#if task.complete>
            <a href="/index/uncomplete/${task.id}">
               <button type="button" class="btn btn-outline-danger">Click on me if the task is not completed. ((</button>
            </a>
        <#else >
            <a href="/index/complete/${task.id}">
               <button type="button" class="btn btn-outline-success">Click on me if the task is done.</button>
            </a>
        </#if>


        <h5>Deadline: ${task.deadline?date}</h5>
    </div>
    <div class="card-body">
        <h5 class="card-title">${task.topicTask}</h5>
        <p class="card-text">${task.textTask}</p>
        <a href="/task/change/${task.id}" class="btn btn-outline-primary">Change</a>
    </div>
    <div class="card-footer text-muted">
        <#if attachment?has_content>
            ${attachment.originalName}
            <div>
                <form method="post">
                    <button type="submit" class="btn btn-outline-danger">Delete file</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        <#else >
            <div>Have a nice day :)</div>
        </#if>

    </div>
</div>

</@c.page>
