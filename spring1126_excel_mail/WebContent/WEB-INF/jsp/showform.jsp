<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<div id="artcle">
	<div id="header2">
		<h1> 학습목표 : UI Design : Apache Tiles적용하기 </h1>
	</div>
	<div id="content">
		<p>여기는 메인 컨텐츠 입니다.</p>
		<form method="post" action="upsave2" enctype="multipart/form-data">
			<input type="hidden" name="reip"
				value="<%=request.getRemoteAddr()%>">
			<table>
				<tr>
					<th colspan="2">파일 업로드 연습문제</th>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input name="writer"></td>
				</tr>
				<tr>
					<td>분류</td>
					<td><select name="grpcode">
							<option value="">선 택</option>
							<option value="A001">음식</option>
							<option value="A002">연애인</option>
							<option value="A003">셀카찍찍</option>
					</select></td>
				</tr>
				<tr>
					<td>판매가</td>
					<td><input name="price"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input name="pwd" type="password" maxlength="10">
					</td>
				</tr>
				<tr>
					<td>간단한 설명</td>
					<td><input name="comm"></td>
				</tr>
				<tr>
					<td>상품이미지</td>
					<td><input type="file" name="multipartFile"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Send"></td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script>
	
</script>
<style>
	#cont{width:500px; margin:auto;}
	#cont table{width:100%; border: 1px dotted #000; text-align: center;
	height: 150px;}
	#cont table th{font-size: 20px; color: gray;}
</style>