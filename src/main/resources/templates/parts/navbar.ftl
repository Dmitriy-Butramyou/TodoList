<#include "security.ftl">
<#import "login.ftl" as l>

 <nav class="navbar navbar-expand-lg navbar-light " style="background-color: #73fdb9;">
     <a class="navbar-brand" href="/">ToDoList</a>
     <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
             aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
         <span class="navbar-toggler-icon"></span>
     </button>

     <div class="collapse navbar-collapse" id="navbarSupportedContent">
         <ul class="navbar-nav mr-auto">
             <li class="nav-item">
                 <a class="nav-link" href="/">Home</a>
             </li>
             <#if user??>
                   <li class="nav-item">
                       <a class="nav-link" href="/index">Tasks</a>
                   </li>

                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Profile</a>
                </li>

             </#if>

             <#if isAdmin>
                  <li class="nav-item">
                      <a class="nav-link" href="/user">User List</a>
                  </li>
             </#if>

             <#if user??>
                 <li class="nav-item">
                     <a class="nav-link" href="/task/add">New Task</a>
                 </li>
             </#if>
              <#if user??>
         <a href="/task/performed">
             <button class="btn btn-outline-success my-2 my-sm-0 ml-3" name="day" value="Performed" type="submit">
                 Performed
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

         <div class="navbar-text mr-3">${name}</div>
            <@l.logout />
     </div>
 </nav>