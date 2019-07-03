<#import "parts/common.ftl" as c>
<@c.page>
    ${message!}
<div class="list-group col-md-5 mx-auto">
<h5>${username}</h5>
<form method="post">
    <div class="form-group row">
        <label class=" col-form-label">Username:</label>
        <div class="ml-3">
            <input type="text" name="username"  class="form-control"value="${user.username}">
        </div>
    </div>
    <div class="form-group row">
        <label class=" col-form-label">Password:</label>
        <div class="ml-3">
            <input type="password" name="password" class="form-control" placeholder="Change Password"/>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Save</button>
</form>
</div>
</@c.page>