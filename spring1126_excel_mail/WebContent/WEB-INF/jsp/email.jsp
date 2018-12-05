<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<form method="post" action="sendEmail" enctype="multipart/form-data">
	<table width="80%">
		<tr>
			<td>Email TO : </td>
			<td><input type="email" name="mailTo" required="required"></td>
		</tr>
		<tr>
			<td>Subject : </td>
			<td><input type="text" name="subject"></td>
		</tr>
		<tr>
			<td>Message : </td>
			<td><textarea cols="50" rows="10" name="text"></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="Send E-mail">
			</td>
		</tr>
	</table>
</form>