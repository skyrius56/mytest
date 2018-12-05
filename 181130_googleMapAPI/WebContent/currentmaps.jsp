<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
	#baseLocation, #currentLocation{
		width: 510px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAvq5g1nETuTPLFE_o_bjZkjNWR-e3NDRk&sensor=true"></script>
<script type="text/javascript">
	var nav = null;
	var map;
	var marker;
	let clatitude;
	let clongitude;
	let baseLocation, currentLocation;
	
	/* ���� ��ġ(����/�浵)�� �޾ƿ��� ���� �κ� */
	$(function() {
		
		if (nav == null) {
			nav = window.navigator;
		}
		if (nav != null) {
			var geoloc = nav.geolocation;
			if (geoloc != null) {
				/* Callback ���� ��, successCallback() ȣ�� */
				geoloc.getCurrentPosition(successCallback);
			} else {
				alert("geolocation not supported");
			}
		} else {
			alert("Navigator not found");
		}
		
		
	});

	function successCallback(position) {
		///* ���� */ var latitude = position.coords.latitude;
		///* �浵 */ var longitude = position.coords.longitude; 
		//alert("�浵 : " + latitude + ", ���� : " + longitude);

		/* ���� */ var latitude = 37.618684;
		/* �浵 */ var longitude = 126.920906; 
		
		/* ���� */ clatitude = position.coords.latitude;
		/* �浵 */ clongitude = position.coords.longitude; 
		
		//var dist = distance(latitude, longitude, clatitude, clongitude, "kilometer");
		//alert(dist);
	
		/* Google Map���� ������ �浵 �ʱ�ȭ */
		initialize(latitude, longitude);
		
	}

	function initialize(latitude, longitude) {
		
		// �������� �Ǵ� ����, �浵
		var baseLocation = new google.maps.LatLng(latitude, longitude);
		
		let bgeocoder = new google.maps.Geocoder();
		let cgeocoder = new google.maps.Geocoder();
		// ������ ��ġ�� �ּҷ�
		bgeocoder.geocode({'latLng' : baseLocation}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK)  {
				if (results[1]) {
					$("#baseLocation").val(results[1].formatted_address);
				}
			}
		});
		
		// ���� ��ġ�� �ּҷ�
		cgeocoder.geocode({'latLng' : new google.maps.LatLng(clatitude, clongitude)}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK)  {
				if (results[1]) {
					$("#currentLocation").val(results[1].formatted_address);
				}
			}
		});
	
		
		var dist = distance(latitude, longitude, clatitude, clongitude, "kilometer");
		$("#distance").val(dist + " km");
		
		/* ���� ��ġ�� ������ �浵 ������ currentLocatioon �� �ʱ�ȭ */
		var currentLocation = new google.maps.LatLng(clatitude, clongitude);

		var mapOptions = {
			center : baseLocation, /* ������ ������ ��ġ */ 				
			zoom : 10, /* ���� �� (0��� ~ 18Ȯ��),  */ 	
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		
		var mapOptions2 = {
				center : currentLocation, /* ������ ������ ��ġ */ 				
				zoom : 10, /* ���� �� (0��� ~ 18Ȯ��),  */ 	
				mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		
		/* DIV�� ���� �޾��ֱ� */
		map = new google.maps.Map(document.getElementById("map_canvas"),
				mapOptions);
		//map2 = new google.maps.Map(document.getElementById("map_canvas"),
		//		mapOptions2);
		
		/* ���� ���� ��Ŀ �޾��ֱ� */
		marker = new google.maps.Marker({
			position : baseLocation,
			map : map
		});
		marker2 = new google.maps.Marker({
			position : currentLocation,
			map : map
		});
		
		google.maps.event.addListener(marker, 'click', toggleBounce(marker));
		google.maps.event.addListener(marker2, 'click', toggleBounce(marker2));

		// This event listener will call addMarker() when the map is clicked.
		/* �������� ���콺 Ŭ���� ��Ŀ ���� */
		google.maps.event.addListener(map, 'click', function(event) {
			///* ���� */ clatitude = position.coords.latitude;
			///* �浵 */ clongitude = position.coords.longitude; 
			
			// ���콺 ���������� �浵, ���� ���
			// ������ ������ġ���� ��Ŀ�� �����ǰ�, �� ��ġ�� base�Ǵ� ��ġ�� �Ÿ��� ���Ұ�!
			var lat = event.latLng.lat(); 
			var lng = event.latLng.lng(); 
			
			var dist = distance(latitude, longitude, lat, lng, "kilometer");
			
			$("#distance").val(dist + " km");
			//alert(dist)
			
			// ��ǥ -> �ּ�
			codeCoordinates(event);
			addMarker(event.latLng);
			
	        var clocation = new GLatLng(lat, lng);
	        var polyOptions = {geodesic:true};
	        var polyline = new GPolyline([baseLocation, clocation], "#ffff00", 5, 1, polyOptions);
	        map.setMap(polyline);
			//delete base;
		});
	}
	
	//Ŭ�� �̺�Ʈ �߻� �� �� ��ǥ�� �ּҷ� ��ȯ�ϴ� �Լ��Դϴ�.
	function codeCoordinates(event) {
		// ��ǥ�� �޾� reverse geocoding(��ǥ�� �ּҷ� �ٲٱ�)�� �����մϴ�.
		let geocoder = new google.maps.Geocoder();
		geocoder.geocode({'latLng' : event.latLng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK)  {
			if (results[1]) {
				$("#currentLocation").val(results[1].formatted_address);
			}
		}
		});
	}
	
	 // �ּҸ� ��ǥ�� ��ȯ
	  function showAddress(address){

	   if(geocoder){
	    geocoder.getLatLng (address,
	     function(point){
	      if(!point){
	       alert("'"+address + "' �� ã�� ���Ͽ����ϴ�.");
	      }else {
	     
	    	  
	      }
	     }
	    );
	   }
	 }


	function distance(lat1, lon1, lat2, lon2, unit) {
        
		var theta = lon1 - lon2;
		var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + 
		Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
         
        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }
 
        return (dist);
    }
     
 
    // This function converts decimal degrees to radians
    function deg2rad(deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    function rad2deg(rad) {
        return (rad * 180 / Math.PI);
    }

	
	// Add a marker to the map and push to the array.
	/*
	 * �� �ҽ��� ��Ŀ�� �ϳ��� �߰��� �� �ֵ��� �����س����ϴ�.
	 * �����ںе� �緮�� ���� �ڵ带 �����ϵ��� �ϼ���.  
	 */
	function addMarker(location) {
		/* ������ �ִ� ��Ŀ ���� �� */
		/*�� ��Ŀ �߰��ϱ�. */ 
		marker2.setMap(null);
		marker2 = new google.maps.Marker({
			position : location,
			map : map
		});
		/* ��Ŀ ��۹ٿ �̺�Ʈ �ɾ��ֱ�(��Ŀ�� ���� Ƣ���� �ִϸ��̼��� �ɾ���) */
		google.maps.event.addListener(marker2, 'click', toggleBounce(marker2));
	}

	function toggleBounce(marker) {
		if (marker.getAnimation() != null) {
			marker.setAnimation(null);
		} else {
			marker.setAnimation(google.maps.Animation.BOUNCE);
		}
	}
	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
<body>
	<div id="map_canvas" style="width: 600px; height: 600px"></div>
	<div>���� ��ġ : <input type="text" id="baseLocation" ></div>
	<div>���� ��ġ : <input type="text" id="currentLocation" ></div>
	<div>�� ���� ������ �Ÿ� : <input type="text" id="distance" ></div>
	<div>��ġ �˻� : <input type="text" id="locSearch" ><input type="button" value="�˻�"></div>
</body>

</html>