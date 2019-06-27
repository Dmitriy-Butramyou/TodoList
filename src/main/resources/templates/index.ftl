<#import "parts/common.ftl" as c>
<@c.page>
<h3>Find by tag</h3>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/index" class="form-inline">
            <select class="form-control form-control-sm" name="filter">
                <option value="New">New</option>
                <option value="In the process">In the process</option>
                <option value="Complete">Complete</option>
            </select>
            <button type="submit" class="btn btn-primary ml-2">Find</button>

        </form>
    </div>
</div>
<div>
    <a class="btn btn-primary mb-3" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new Task
    </a>
</div>
<div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" class="form-inline" enctype="multipart/form-data">
            <div class="form-group mb-2 mr-2">
                <input type="text" name="topicTask" placeholder="Введите тему" />
            </div>
            <div class="form-group mb-2">
                <input type="text" name="textTask" placeholder="Введите задание" />
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <input type="date" id="deadline" name="deadline" class="form-control form-control-sm"
                       placeholder="YYYY-MM-DD">
            </div>
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary mb-2">Send</button>
            </div>
        </form>
    </div>
</div>
<form method="get" action="/index"">
    <div class="btn-group mb-3" role="group" aria-label="Basic example">
        <button type="submit" class="btn btn-outline-primary" name="day" value="All">All</button>
        <button type="submit" class="btn btn-outline-primary" name="day" value="Today">Today</button>
        <button type="submit" class="btn btn-outline-primary" name="day" value="Tomorrow">Tomorrow</button>
        <button type="submit" class="btn btn-outline-primary" name="day" value="Someday">Someday</button>
        <button type="submit" class="btn btn-outline-danger" name="day" value="Deadline Missing">Deadline Missing</button>
    </div>
</form>





<div class="list-group col-md-6">
<#list tasks as task>

  <a href="#" class="list-group-item list-group-item-action mb-2">
      <div class="d-flex w-100 justify-content-between">
          <h5 class="mb-1">${task.topicTask}</h5>
          <small>${task.deadline?date}</small>
      </div>
      <p class="mb-1">${task.textTask}</p>
      <small>${task.authorName}</small>
  </a>
<#else >
        No task
</#list>
</div>
</@c.page>
