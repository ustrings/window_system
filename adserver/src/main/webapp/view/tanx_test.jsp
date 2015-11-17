<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
     <head> 
       <meta http-equiv="Content-Type" content="text/html; charset= tf-8" /> 
       <meta name="viewport" content=" ser-scalable=no, width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/> 
       <script type="text/javascript" src="http://cdn.tanx.com/t/tanxmobile/mraid.js"></script> 
     </head>
     <body>
       <img id="demo" style="width:100%; height:100%" src="" alt=""> 
       <script>   
           if ((mraid.getState() == "loading")) {
               mraid.addEventListener('ready', loadAndDisplayAd) 
           } else {
               loadAndDisplayAd()
           } 

           function loadAndDisplayAd() { 
                 //预加载图片 
                 var img = document.getElementById("demo"); 
                 img.onload = function(){ 
                     //在图片加载完成后，用户看到的不再是天窗，这时调用mraid.show()，表示 创意渲染完毕，可以进行展示 
                     mraid.show(); 
                 } 
                 img.src = 'http://creative.vaolan.com/img/623f34ee90414be9893d2dc7b28e8c02.jpg'; 
                 img.onclick = function() {
                 mraid.open("http://a.m.tmall.com/i36667276677.html"); 
                   return false; 
                 } 
           } 
       </script> 
   </body> 
   </html>