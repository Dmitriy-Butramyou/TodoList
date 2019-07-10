<#--#0b245d-->
<#include "security.ftl">
<#import "login.ftl" as l>
<div style="background-color: #0b245d;">
 <nav class="navbar navbar-expand-lg navbar-dark col-sm-10 mx-auto" style="background-color: #0b245d;">
     <a class="navbar-brand" href="/">
         <img src="/img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
         ToDoList</a>
     <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
             aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
         <span class="navbar-toggler-icon"></span>
     </button>

     <div class="collapse navbar-collapse" id="navbarSupportedContent">
         <ul class="navbar-nav mr-auto">
             <li class="nav-item">
                 <a class="nav-link " href="/">Home</a>
             </li>
             <#if user??>
                   <li class="nav-item">
                       <a class="nav-link" href="/index">Tasks</a>
                   </li>

                 <li class="nav-item">
                     <a class="nav-link" href="/task/add">New Task</a>
                 </li>
             </#if>

              <#if user??>
         <a href="/task/performed">
             <button class="btn btn-outline-success my-2 my-sm-0 ml-3" type="submit">
                 Performed
             </button>
         </a>
         </form>
              </#if>

             <#if user??>
         <a href="/basket">
             <button class="btn btn-outline-danger my-2 my-sm-0 ml-3" type="button">
                 Basket
             </button>
         </a>
             </#if>

         </ul>
         </a>
<#if user??>
    <div class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${name}
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
            <#if user??>
                    <a class="dropdown-item" href="/user/profile">Profile</a>
            </#if>
            <#if isAdmin>
                      <a class="dropdown-item" href="/user">User List</a>
            </#if>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Something else here</a>
        </div>
    </div>
<#else >
         <div class="navbar-text mr-3">${name}</div>
</#if>
            <@l.logout />
     </div>
 </nav>
</div>