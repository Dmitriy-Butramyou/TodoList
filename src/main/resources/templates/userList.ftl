<#import "parts/common.ftl" as c>

<@c.page>
<div class="col-sm-7 mx-auto">
<h4>List of users</h4>
<table class="table mt-3">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Name</th>
        <th scope="col">Role</th>
        <th scope="col">Edit</th>
    </tr>
    </thead>
    <tbody>
<#list users as user>
<tr>
    <td>${user.username}</td>
    <td><#list user.roles as role>${role}<#sep>, </#list></td>
    <td><a href="/user/${user.id}" class="badge badge-info">Edit</a></td>
</tr>
</#list>
    </tbody>
    </thead>
</table>
</div>
</@c.page>