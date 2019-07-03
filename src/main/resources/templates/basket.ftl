<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div class="list-group col-md-5 mx-auto">
<a href="/basket/deleteAll">
    <button type="button" class="btn btn-danger btn-lg btn-block">Delete everything</button>
</a>

</div>
<div class="list-group col-md-5 mx-auto">
<#list tasks as task>
    <a href="/task/${task.id}" class="list-group-item list-group-item-action mb-2 ">
        <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">${task.topicTask} <span class="badge badge-success">${task.tag}</span></h5>
            <small>${task.deadline?date}</small>
        </div>
        <p class="mb-1">${task.textTask} </p>
        <small>${task.authorName} </small>
    </a>

    <div class="d-flex bd-highlight mb-3">
            <a href="/basket/restore/${task.id}">
            <button type="button" class="ml-auto btn btn-outline-success mb-3">Restore</button>
            </a>

        <a href="/basket/delete/${task.id}" class="ml-auto">
            <button type="button" class="ml-auto btn btn-outline-danger mb-3">Delete</button>
        </a>

    </div>

<#else >
        No task
</#list>
</div>

</@c.page>
