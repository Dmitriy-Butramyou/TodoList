<#import "parts/common.ftl" as c>
<@c.page>
<div class="list-group col-md-5 mx-auto">
<form method="get" action="/index">
    <div class="btn-group mb-3" role="group" aria-label="Basic example">
        <button type="submit" class="btn btn-outline-primary" name="day" value="All">All</button>
        <button type="submit" class="btn btn-outline-primary" name="day" value="Today">Today</button>
        <button type="submit" class="btn btn-outline-primary" name="day" value="Tomorrow">Tomorrow</button>
        <button type="submit" class="btn btn-outline-primary" name="day" value="Someday">Someday</button>
        <button type="submit" class="btn btn-outline-danger" name="day" value="Deadline Missing">Deadline Missing</button>
    </div>
</form>
</div>

<div class="list-group col-md-5 mx-auto">
<#list tasks as task>
    <a href="/task/${task.id}" class="list-group-item list-group-item-action mb-2 ">
        <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">${task.topicTask}
                <span class="badge badge-success"><#if task.tag>New<#else></#if></span></h5>
            <small>${task.deadline?date}</small>
        </div>
        <p class="mb-1">${task.textTask} </p>
        <small>${task.authorName} </small>
    </a>

    <div class="d-flex bd-highlight mb-3">

        <a href="/index/complete/${task.id}">
        <button type="button" class="btn btn-outline-success mb-3">Performed</button>
    </a>
        <a href="/index/delete/${task.id}" class="ml-auto">
            <button type="button" class="btn btn-outline-danger mb-3">Delete</button>
        </a>

    </div>

<#else >
        No task
</#list>
</div>
</@c.page>
