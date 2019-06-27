<#import "parts/common.ftl" as c>

<@c.page>
<h4>User editor</h4>
<form action="/user" method="post">

    <table class="table col-md-6 mt-3">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">User</th>
            <th scope="col">Admin</th>
        </tr>
        </thead>
        <tbody>
        <tr>

      <td>
          <input type="text" name="username" value="${user.username}">
      </td>
 <#list roles as role>
       <td> <div>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
       </td>
 </#list>
    </table>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit" class="btn btn-success">Save</button>
        </tbody>
        </thead>


</form>
</@c.page>