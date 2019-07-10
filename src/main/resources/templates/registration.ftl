<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div class="col-sm-10 mx-auto">
<h4>Add new User</h4>
    ${message!}
    <@l.login "/registration" true/>
</div>
</@c.page> 