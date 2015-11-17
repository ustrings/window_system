<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!--
		Charisma v1.0.0

		Copyright 2012 Muhammad Usman
		Licensed under the Apache License v2.0
		http://www.apache.org/licenses/LICENSE-2.0

		http://usman.it
		http://twitter.com/halalit_usman
	-->
	<meta charset="utf-8">
	<title>Free HTML5 Bootstrap Admin Template</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
	<meta name="author" content="Muhammad Usman">

	<!-- The styles -->
	<link id="bs-css" href="/resources/css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	<link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="/resources/css/charisma-app.css" rel="stylesheet">
	<link href="/resources/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
	<link href='/resources/css/fullcalendar.css' rel='stylesheet'>
	<link href='/resources/css/fullcalendar.print.css' rel='stylesheet'  media='print'>
	<link href='/resources/css/chosen.css' rel='stylesheet'>
	<link href='/resources/css/uniform.default.css' rel='stylesheet'>
	<link href='/resources/css/colorbox.css' rel='stylesheet'>
	<link href='/resources/css/jquery.cleditor.css' rel='stylesheet'>
	<link href='/resources/css/jquery.noty.css' rel='stylesheet'>
	<link href='/resources/css/noty_theme_default.css' rel='stylesheet'>
	<link href='/resources/css/elfinder.min.css' rel='stylesheet'>
	<link href='/resources/css/elfinder.theme.css' rel='stylesheet'>
	<link href='/resources/css/jquery.iphone.toggle.css' rel='stylesheet'>
	<link href='/resources/css/opa-icons.css' rel='stylesheet'>
	<link href='/resources/css/uploadify.css' rel='stylesheet'>

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<!-- The fav icon -->
	<link rel="shortcut icon" href="img/favicon.ico">
		
</head>

<body>
		<%@include file="../inc/header.jsp"%>
		<div class="container-fluid">
		<div class="row-fluid">
				
			<%@include file="../inc/menu.jsp"%>
			
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			<div id="content" class="span10">
			<!-- content starts -->
			

			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#">Home</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">Grid</a>
					</li>
				</ul>
			</div>

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 12</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid sortable">
				<div class="box span3">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 3</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
				<div class="box span3">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 3</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
				<div class="box span3">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 3</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
				<div class="box span3">
					<div class="box-header well" data-original-title>
						<h2>Plain</h2>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid sortable">
				<div class="box span6">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 6</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
				
				<div class="box span6">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 6</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
			</div><!--/row-->
			<div class="row-fluid sortable">
				<div class="box span4">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 4</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
				
				<div class="box span4">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 4</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
				
				<div class="box span4">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-th"></i> Grid 4</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
                  	<div class="row-fluid">
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                        <div class="span4"><h6>span 4</h6></div>
                    </div>                   
                  </div>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid">
				<div class="span12 well">
					<div>
						<h1>Box less area</h1>
						<p>The flat boxes can be created using grids. But you can also use grids inside grids, which makes the layout 100% flexible!</p>
					</div>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid show-grid">
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
				<div class="span1">1</div>
			</div>
			
			<div class="row-fluid show-grid">
				<div class="span4">4</div>
				<div class="span4">4</div>
				<div class="span4">4</div>
			</div>
			
			<div class="row-fluid show-grid">
				<div class="span3">3</div>
				<div class="span3">3</div>
				<div class="span3">3</div>
				<div class="span3">3</div>
			</div>
			
			<div class="row-fluid show-grid">
				<div class="span4">4</div>
				<div class="span8">8</div>
			</div>
			
			<div class="row-fluid show-grid">
				<div class="span6">6</div>
				<div class="span6">6</div>
			</div>
			
			<div class="row-fluid show-grid">
				<div class="span12">12</div>
			</div>

    
					<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
				
		<hr>

		<div class="modal hide fade" id="myModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>Settings</h3>
			</div>
			<div class="modal-body">
				<p>Here settings can be configured...</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Close</a>
				<a href="#" class="btn btn-primary">Save changes</a>
			</div>
		</div>

		<footer>
			<p class="pull-left">&copy; <a href="http://usman.it" target="_blank">Muhammad Usman</a> 2012</p>
			<p class="pull-right">Powered by: <a href="http://usman.it/free-responsive-admin-template">Charisma</a></p>
		</footer>
		
	</div><!--/.fluid-container-->

<%@include file="../inc/footer.jsp"%>
</body>
</html>
