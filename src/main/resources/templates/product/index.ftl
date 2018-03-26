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
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label >名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}" />
                        <#--前面我们写过对空值的处理，直接在可能为的字段后面加上感叹号和双引号即可，如果处理的字段是
                        非对象的话，可以这样处理，但是如果是对象的话，我们需要再在对象上添加一个括号即可-->
                        </div>

                        <div class="form-group">
                            <label >价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}" />
                        </div>
                        <div class="form-group">
                            <label >库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}" />
                        </div>
                        <div class="form-group">
                            <label >描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}" />
                        </div>

                        <div class="form-group">
                            <label >图片</label>
                            <img width="100px" height="100px" src="${(productInfo.productIcon)!''}" alt="">
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}" />
                        </div>

                        <div class="form-group">
                            <label >类目</label>
                        <select name="categoryType" class="form-control">
                            <#list categoryList as category>
                                <option value="${category.categoryType}"
                                    <#--存在在freemark中的表示：(对象)?? -->
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType ==category.categoryType>
                                            selected
                                        </#if>
                                >${category.categoryName}
                                </option>
                            </#list>
                        </select>
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}" />
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
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

