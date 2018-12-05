<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAvq5g1nETuTPLFE_o_bjZkjNWR-e3NDRk"></script>

<script>
function initialize()
{
  var mapProp = {
    center: new google.maps.LatLng(37.250943, 127.028344),
    zoom:9,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  var mapProp2 = {
    center: new google.maps.LatLng(37.250943, 127.028344),
    zoom:9,
    mapTypeId: google.maps.MapTypeId.SATELLITE
  };
  var mapProp3 = {
    center: new google.maps.LatLng(37.250943, 127.028344),
    zoom:9,
    mapTypeId: google.maps.MapTypeId.HYBRID
  };
  var mapProp4 = {
    center: new google.maps.LatLng(37.250943, 127.028344),
    zoom:9,
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };
  var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
  var map2 = new google.maps.Map(document.getElementById("googleMap2"),mapProp2);
  var map3 = new google.maps.Map(document.getElementById("googleMap3"),mapProp3);
  var map4 = new google.maps.Map(document.getElementById("googleMap4"),mapProp4);
}
 
google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
 
<body>
<div id="googleMap" style="width:400px;height:300px;"></div>
<br>
<div id="googleMap2" style="width:400px;height:300px;"></div>
<br>
<div id="googleMap3" style="width:400px;height:300px;"></div>
<br>
<div id="googleMap4" style="width:400px;height:300px;"></div>
 
</body>
</html>
