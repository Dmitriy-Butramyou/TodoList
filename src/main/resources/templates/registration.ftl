<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<h4>Add new User</h4>
    ${message!}
    <@l.login "/registration" true/>
</@c.page> 