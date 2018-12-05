<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<div id="article">
 <div id="header2">
  <h1>  �н���ǥ : UI Design : Apache Tiles �����ϱ�</h1>
  <p id="time">2018-11-22</p>
 </div>
 <div id="content">
  <p>����� ���� ������</p>
  <div id="cont">
  <table>
   <thead>
    <tr>
     <th colspan="7">����Ʈ</th>
    </tr>
    <tr>
     <td>��ȣ</td>
     <td>�ڵ�</td>
     <td>�̹���</td>
     <td>�ۼ���</td>
     <td>����</td>
     <td>��¥</td>
     <td>����</td>
    </tr>
   </thead>
   <tbody>
    <c:forEach var="listv" items="${list}">
     <tr>
      <td>${listv.num}</td>
      <td>${listv.grpcode}</td>
      <td><a href="">
      <img src="resources/imgfile/${listv.path}">
      </a></td>
      <td>${listv.writer}</td>
      <td>${listv.price}</td>
      <td>${listv.regdate}</td>
      <td><a href="fileDown?fileName=${listv.path}" style="color: black">Download</a></td>
     </tr>
    </c:forEach>
   </tbody>
   <tfoot>
    <tr>
     <td colspan="7" id="pageTd">
      <%--Page ���� ������ ���� --%> 
      <c:choose>
       <c:when test="${searchType == null}">
        <c:choose>
         <c:when test="${pageInfo.currentBlock eq 1}">
          <img src="resources/img/prev.png">
         </c:when>
         <c:otherwise>
          <a
           href="showlist.kosta?page=
         ${(pageInfo.currentBlock-1)*pageInfo.pagesPerBlock }">
           <img src="resources/img/prev.png">
          </a>
         </c:otherwise>
        </c:choose>

        <%--Page  ������ ���� --%>
        <c:choose>
         <c:when test="${pageInfo.currentBlock ne pageInfo.totalBlocks}">
          <c:forEach begin="1" end="${pageInfo.pagesPerBlock}"
           varStatus="num">
                        [<a
            href="showlist?page=
                        ${(pageInfo.currentBlock - 1) * pageInfo.pagesPerBlock + num.count }">
            ${(pageInfo.currentBlock- 1) * pageInfo.pagesPerBlock + num.count }</a>]
                       </c:forEach>
         </c:when>
         <c:otherwise>
          <c:forEach
           begin="${(pageInfo.currentBlock-1)*pageInfo.pagesPerBlock + 1}"
           end="${pageInfo.totalPages}" varStatus="num">
                        [<a
            href="showlist?page=
          ${(pageInfo.currentBlock - 1) * pageInfo.pagesPerBlock + num.count }">
            ${(pageInfo.currentBlock - 1) * pageInfo.pagesPerBlock + num.count }</a>]
                    </c:forEach>
         </c:otherwise>
        </c:choose>


        <%--Page ���� ������ ���� --%>
        <c:choose>
         <c:when test="${pageInfo.currentBlock eq pageInfo.totalBlocks}">
          <img src="resources/img/next.png">
         </c:when>
         <c:otherwise>
          <a
           href="showlist?page=
         ${pageInfo.currentBlock * pageInfo.pagesPerBlock + 1 }">
           <img src="resources/img/next.png">
          </a>
         </c:otherwise>
        </c:choose>
       </c:when>
       <c:otherwise>

        <c:choose>
         <c:when test="${pageInfo.currentBlock eq 1}">
          <img src="resources/img/prev.png">
         </c:when>
         <c:otherwise>
          <a
           href="showlist.kosta?searchType=${searchType}&searchValue=${searchValue}&page=
         ${(pageInfo.currentBlock-1)*pageInfo.pagesPerBlock }">
           <img src="resources/img/prev.png">
          </a>
         </c:otherwise>
        </c:choose>

        <%--Page  ������ ���� --%>
        <c:choose>
         <c:when test="${pageInfo.currentBlock ne pageInfo.totalBlocks}">
          <c:forEach begin="1" end="${pageInfo.pagesPerBlock}"
           varStatus="num">
                        [<a href="showlist?searchType=${searchType}&searchValue=${searchValue}&page=
                        ${(pageInfo.currentBlock - 1) * pageInfo.pagesPerBlock + num.count }">
            ${(pageInfo.currentBlock- 1) * pageInfo.pagesPerBlock + num.count }</a>]
                       </c:forEach>
         </c:when>
         <c:otherwise>
          <c:forEach
           begin="${(pageInfo.currentBlock-1)*pageInfo.pagesPerBlock + 1}"
           end="${pageInfo.totalPages}" varStatus="num">
                        [<a href="showlist?searchType=${searchType}&searchValue=${searchValue}&page=
          ${(pageInfo.currentBlock - 1) * pageInfo.pagesPerBlock + num.count }">
            ${(pageInfo.currentBlock - 1) * pageInfo.pagesPerBlock + num.count }</a>]
                    </c:forEach>
         </c:otherwise>
        </c:choose>


        <%--Page ���� ������ ���� --%>
        <c:choose>
         <c:when test="${pageInfo.currentBlock eq pageInfo.totalBlocks}">
          <img src="resources/img/next.png">
         </c:when>
         <c:otherwise>
          <a
           href="showlist?searchType=${searchType}&searchValue=${searchValue}&page=
         ${pageInfo.currentBlock * pageInfo.pagesPerBlock + 1 }">
           <img src="resources/img/next.png">
          </a>
         </c:otherwise>
        </c:choose>
       </c:otherwise>
      </c:choose>
     </td>
    </tr>
    <tr>
     <th colspan="7">
      <form method="post" action="search.kosta">
       <input type="hidden" name="page" value="${param.page}"> <select
        name="searchType">
        <option value="1">�ۼ���</option>
        <option value="2">Code</option>
        <option value="3">����</option>
       </select>&nbsp; <input name="searchValue"> <input type="submit"
        value="Search">
      </form>
     </th>
    </tr>
    <tr>
     <th colspan="7"><input type="button" value="writer"
      onclick="location='showform.kosta'"></th>
    </tr>
    <tr>
     <th colspan="7"><a href="downloadExcel" style="color: black">Download Excel</a></th>
    </tr>
   </tfoot>
  </table>
 </div>
 </div>
</div>
<script>
 $(function(){

 });
</script> 


<style type="text/css">
#cont {
 width: 500px;
 margin: auto;
}

#cont table {
 width: 100%;
 border: 1px dotted #000
}

#cont table thead th {
 font-size: 30px;
 color: orange
}

#cont table thead td {
 text-align: center;
 background-color: skyblue
}

#cont tbody img {
 width: 80px;
 border: 0px
}
#pageTd img {
 width: 15px;
 
}
#pageTd a{
color: #000000;
}
</style>

