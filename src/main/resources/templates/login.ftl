<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div class="col-sm-10 mx-auto">
<h4>Login page</h4>
    ${message!}
<@l.login "/login" false/>
</div>
</@c.page> 