<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<div id="artcle">
	<div id="header2">
		<h1> �н���ǥ : UI Design : Apache Tiles�����ϱ� </h1>
	</div>
	<div id="content">
		<p>����� ���� ������ �Դϴ�.</p>
		<form method="post" action="upsave2" enctype="multipart/form-data">
			<input type="hidden" name="reip"
				value="<%=request.getRemoteAddr()%>">
			<table>
				<tr>
					<th colspan="2">���� ���ε� ��������</th>
				</tr>
				<tr>
					<td>�ۼ���</td>
					<td><input name="writer"></td>
				</tr>
				<tr>
					<td>�з�</td>
					<td><select name="grpcode">
							<option value="">�� ��</option>
							<option value="A001">����</option>
							<option value="A002">������</option>
							<option value="A003">��ī����</option>
					</select></td>
				</tr>
				<tr>
					<td>�ǸŰ�</td>
					<td><input name="price"></td>
				</tr>
				<tr>
					<td>��й�ȣ</td>
					<td><input name="pwd" type="password" maxlength="10">
					</td>
				</tr>
				<tr>
					<td>������ ����</td>
					<td><input name="comm"></td>
				</tr>
				<tr>
					<td>��ǰ�̹���</td>
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