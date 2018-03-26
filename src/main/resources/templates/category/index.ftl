<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
            <#include "../common/nav.ftl">
<#--主要内容content-->

    <div class="page-content=wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label >名字</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}" />
                        <#--前面我们写过对空值的处理，直接在可能为的字段后面加上感叹号和双引号即可，如果处理的字段是
                        非对象的话，可以这样处理，但是如果是对象的话，我们需要再在对象上添加一个括号即可-->
                        </div>

                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}" />
                        </div>
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<#--<h1>${orderDTOPage.totalPages}</h1>-->

