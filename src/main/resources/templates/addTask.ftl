<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>
    <form method="post">
        <input type="text" name="textTask" placeholder="Введите задание" />
        <input type="date" id="deadline" name="deadline" class="form-control form-control-sm"
               placeholder="YYYY-MM-DD">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>

    </form>
</div>

<div>
    <#list tasks as task>
        <div>
            <strong>${task.authorName}</strong>
            <span>${task.textTask}</span>
            <i>${task.deadline}</i>
        </div>
        <#else >
        No task
    </#list>
</div>
<p>Get your greeting <a href="/">here</a></p>
</@c.page>
