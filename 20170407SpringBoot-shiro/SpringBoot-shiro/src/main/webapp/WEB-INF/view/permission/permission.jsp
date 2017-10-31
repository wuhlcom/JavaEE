<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<% String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FISH-YU权限管理-首页</title>
<link href="<%=contextPath%>/static/css/content-base.css" rel="stylesheet" />
<link href="<%=contextPath%>/static/css/jqgrid/ui.jqgrid.css" rel="stylesheet" />
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>权限管理模块</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<div class="form-group">
					<button id="btnAdd" type="button" class="btn btn-primary "
						onclick="addModel()">
						<i class="fa fa-plus"></i>&nbsp;添加
					</button>
					<button id="btnEdit" type="button" class="btn btn-info "
						onclick="editModel()">
						<i class="fa fa-pencil"></i> 编辑
					</button>
					<button id="btnDel" type="button" class="btn btn-danger "
						onclick="delData()">
						<i class="fa fa-remove"></i>&nbsp;&nbsp;<span class="bold">删除</span>
					</button>
				</div>

				<div class="form-group">
					<div class="input-group">
						<input id="txtSearchKey" type="text" class="input form-control"
							placeholder="搜索关键字" /> <span class="input-group-btn">
							<button id="btnSearch" class="btn btn btn-primary" type="button">
								<i class="fa fa-search"></i> 搜索
							</button>
						</span>
					</div>
				</div>

				<div class="jqGrid_wrapper">
					<table id="table_list"></table>
					<div id="pager_list"></div>
				</div>
			</div>
		</div>
	</div>



	<script src="<%=contextPath%>/static/js/content/base.js"></script>


	<script src="<%=contextPath%>/static/js/content/list.js"></script>

	<script>
		/* function addModel() {
			$("#btnAdd").button("loading");
			window.location.href = "/Role/Add";
		}

		function editModel() {//编辑
			var row = JucheapGrid.GetData();
			if (row != null) {
				$("#btnEdit").button("loading");
				window.location.href = "/Role/Edit/" + row.Id;
			} else {
				parent.layer.alert("请选择要编辑的数据");
			}
		}

		function delData() {//删除
			XPage.DelData("/Role/Delete");
		} */
		
		function addModel() {
			//$("#btnAdd").button("loading");
		    jQuery("#table_list").jqGrid('editGridRow', "new", {
		      height : 300,
		      reloadAfterSubmit : false
		    });
		
		    
		}

		function editModel() {//编辑
	        var gr = jQuery("#table_list").jqGrid('getGridParam', 'selrow');
		     console.log(gr)
	        if (gr != null){
	          jQuery("#table_list").jqGrid('editGridRow', gr, {
	            height : 300,
	            reloadAfterSubmit : true
	          });
	        }else{
	          alert("请选择至少一条记录！");
	        }
		      
		}

		function delData() {//删除
			var gr = jQuery("#table_list").getGridParam('selrow');
			if( gr != null ) jQuery("#table_list").delGridRow(gr,{reloadAfterSubmit:false});
			else alert("Please Select Row to delete!");
		}

		
		function searchData() {//搜索
			var json = {
				keywords : $("#txtSearchKey").val()
			};
			XPage.Search(json);
		}
		$(document).ready(function() {
			var config = {
				title : '权限列表',
				url : '/permission/getPermissionListWithPager',
				colNames : [ '主键', '权限名称', '权限URL','URL转换的资源标识' ],
				colModel : [ {
					name : 'id',
					index : 'id',
					width : 60,
					key : true,
					hidden : true
				}, {
					name : 'name',
					index : 'name',
					width : 60,
					editable:true
				}, {
					name : 'url',
					index : 'url',
					width : 100,
					editable:true
				} , {
					name : 'permissionIdentify',
					index : 'permissionIdentify',
					width : 100
				}],
				viewrecords : true,
				rowNum : 10,
			    rowList : [ 10, 20, 30 ],
		        caption : "Editing PermissionInfo Table",
		        editurl : "/permission/edit"
			};
			JucheapGrid.Load(config);
			$("#btnSearch").bind("click", searchData);
		});
	</script>

</body>
</html>

