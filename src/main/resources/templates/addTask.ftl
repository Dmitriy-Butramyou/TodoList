<#import "parts/common.ftl" as c>
<@c.page>

<div>
    <form method="post" enctype="multipart/form-data">

        <div class="form-group row">
            <label class="col-sm-3 col-form-label">Subject:</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" name="topicTask" placeholder="Enter subject..." />
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-3 col-form-label">Deadline:</label>
            <div class="col-sm-5">
                <input type="date" id="deadline" name="deadline" class="form-control form-control-sm">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-3 col-form-label">Enter a task:</label>
            <div class="col-sm-5">
                <textarea class="form-control"  name="textTask" placeholder="Enter a task..." id="exampleFormControlTextarea1" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-3 col-form-label">Upload file:</label>
            <div class="col-sm-8">
                <input type="file"  name="file" class="form-control-file" id="exampleFormControlFile1">                    </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="form-group">
            <button type="submit" class="btn btn-primary mb-2">Send</button>
        </div>
    </form>
</div>

</@c.page>