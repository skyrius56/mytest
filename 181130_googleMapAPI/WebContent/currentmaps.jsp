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
	
	/* 현재 위치(위도/경도)를 받아오기 위한 부분 */
	$(function() {
		
		if (nav == null) {
			nav = window.navigator;
		}
		if (nav != null) {
			var geoloc = nav.geolocation;
			if (geoloc != null) {
				/* Callback 성공 시, successCallback() 호출 */
				geoloc.getCurrentPosition(successCallback);
			} else {
				alert("geolocation not supported");
			}
		} else {
			alert("Navigator not found");
		}
		
		
	});

	function successCallback(position) {
		///* 위도 */ var latitude = position.coords.latitude;
		///* 경도 */ var longitude = position.coords.longitude; 
		//alert("경도 : " + latitude + ", 위도 : " + longitude);

		/* 위도 */ var latitude = 37.618684;
		/* 경도 */ var longitude = 126.920906; 
		
		/* 위도 */ clatitude = position.coords.latitude;
		/* 경도 */ clongitude = position.coords.longitude; 
		
		//var dist = distance(latitude, longitude, clatitude, clongitude, "kilometer");
		//alert(dist);
	
		/* Google Map으로 위도와 경도 초기화 */
		initialize(latitude, longitude);
		
	}

	function initialize(latitude, longitude) {
		
		// 기준점이 되는 위도, 경도
		var baseLocation = new google.maps.LatLng(latitude, longitude);
		
		let bgeocoder = new google.maps.Geocoder();
		let cgeocoder = new google.maps.Geocoder();
		// 기준점 위치를 주소로
		bgeocoder.geocode({'latLng' : baseLocation}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK)  {
				if (results[1]) {
					$("#baseLocation").val(results[1].formatted_address);
				}
			}
		});
		
		// 현재 위치를 주소로
		cgeocoder.geocode({'latLng' : new google.maps.LatLng(clatitude, clongitude)}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK)  {
				if (results[1]) {
					$("#currentLocation").val(results[1].formatted_address);
				}
			}
		});
	
		
		var dist = distance(latitude, longitude, clatitude, clongitude, "kilometer");
		$("#distance").val(dist + " km");
		
		/* 현재 위치의 위도와 경도 정보를 currentLocatioon 에 초기화 */
		var currentLocation = new google.maps.LatLng(clatitude, clongitude);

		var mapOptions = {
			center : baseLocation, /* 지도에 보여질 위치 */ 				
			zoom : 10, /* 지도 줌 (0축소 ~ 18확대),  */ 	
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		
		var mapOptions2 = {
				center : currentLocation, /* 지도에 보여질 위치 */ 				
				zoom : 10, /* 지도 줌 (0축소 ~ 18확대),  */ 	
				mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		
		/* DIV에 지도 달아주기 */
		map = new google.maps.Map(document.getElementById("map_canvas"),
				mapOptions);
		//map2 = new google.maps.Map(document.getElementById("map_canvas"),
		//		mapOptions2);
		
		/* 지도 위에 마커 달아주기 */
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
		/* 지도에서 마우스 클릭시 마커 생성 */
		google.maps.event.addListener(map, 'click', function(event) {
			///* 위도 */ clatitude = position.coords.latitude;
			///* 경도 */ clongitude = position.coords.longitude; 
			
			// 마우스 누를때마다 경도, 위도 얻기
			// 원래는 현재위치마다 마커가 생성되고, 그 위치와 base되는 위치의 거리를 구할것!
			var lat = event.latLng.lat(); 
			var lng = event.latLng.lng(); 
			
			var dist = distance(latitude, longitude, lat, lng, "kilometer");
			
			$("#distance").val(dist + " km");
			//alert(dist)
			
			// 좌표 -> 주소
			codeCoordinates(event);
			addMarker(event.latLng);
			
	        var clocation = new GLatLng(lat, lng);
	        var polyOptions = {geodesic:true};
	        var polyline = new GPolyline([baseLocation, clocation], "#ffff00", 5, 1, polyOptions);
	        map.setMap(polyline);
			//delete base;
		});
	}
	
	//클릭 이벤트 발생 시 그 좌표를 주소로 변환하는 함수입니다.
	function codeCoordinates(event) {
		// 좌표를 받아 reverse geocoding(좌표를 주소로 바꾸기)를 실행합니다.
		let geocoder = new google.maps.Geocoder();
		geocoder.geocode({'latLng' : event.latLng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK)  {
			if (results[1]) {
				$("#currentLocation").val(results[1].formatted_address);
			}
		}
		});
	}
	
	 // 주소를 좌표로 변환
	  function showAddress(address){

	   if(geocoder){
	    geocoder.getLatLng (address,
	     function(point){
	      if(!point){
	       alert("'"+address + "' 를 찾지 못하였습니다.");
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
	 * 이 소스는 마커를 하나만 추가할 수 있도록 구현해놓습니다.
	 * 개발자분들 재량에 따라 코드를 응용하도록 하세요.  
	 */
	function addMarker(location) {
		/* 기존에 있던 마커 삭제 후 */
		/*새 마커 추가하기. */ 
		marker2.setMap(null);
		marker2 = new google.maps.Marker({
			position : location,
			map : map
		});
		/* 마커 토글바운스 이벤트 걸어주기(마커가 통통 튀도록 애니메이션을 걸어줌) */
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
	<div>지정 위치 : <input type="text" id="baseLocation" ></div>
	<div>현재 위치 : <input type="text" id="currentLocation" ></div>
	<div>두 지점 사이의 거리 : <input type="text" id="distance" ></div>
	<div>위치 검색 : <input type="text" id="locSearch" ><input type="button" value="검색"></div>
</body>

</html>