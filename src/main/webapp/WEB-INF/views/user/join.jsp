<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var='root' value="${pageContext.request.contextPath }/" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>미니 프로젝트</title>
    <!-- Bootstrap CDN -->
    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
    />
    <style type="text/css">
    	form span { color : coral;}
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
  </head>
  <body>
    <!-- 상단 메뉴 부분 -->
    <c:import url="/WEB-INF/views/include/menu.jsp"/>

    <div class="container" style="margin-top: 100px">
      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">
          <div class="card shadow">
            <div class="card-body">
            <!--  컨트롤러에 보내짐 joinUserBean 처음에는 빈객체, 그후 class의 내용담겨 보내짐 form태그 쓸때 사용 -->
              <form:form action="${root }user/join_pro" modelAttribute="joinUserBean">
                <div class="form-group">
                  <form:label path="user_name">이름</form:label>
                  <form:input path="user_name" class="form-control" />
                  <form:errors path="user_name"/>
                </div>
                <div class="form-group">
                  <form:label path="user_id">아이디</form:label>
                  <div class="input-group">
                    <form:input path="user_id" class="form-control" />
                    <div class="input-group-append">
                      <button onclick="checkUserId()" type="button" class="btn btn-primary">중복확인</button>
                    </div>
                  </div>
                  <form:errors path="user_id" />
                </div>
                <div class="form-group">
                <!-- 보이지 않는 체크(중복확인이 되었을 때 true가 됨) -->
                	<form:hidden path="userIdChecked"/>
                    <form:errors class="idCheck" path="userIdChecked" />
                </div>
                <div class="form-group">
                  <form:label path="user_pw">비밀번호</form:label>
                  <form:input path="user_pw" class="form-control" />
                  <form:errors path="user_pw" />
                </div>
                <div class="form-group">
                  <form:label path="user_pw2">비밀번호 확인</form:label>
                  <form:input path="user_pw2" class="form-control" />
                  <form:errors path="user_pw2" />
                </div>
                <span>${msg}</span>
                <div class="form-group">
                  <div class="text-right">
                    <button type="submit" class="btn btn-primary">회원가입</button>
                  </div>
                </div>
              </form:form>
            </div>
          </div>
        </div>
        <div class="col-sm-3"></div>
      </div>
    </div>

    <!-- 하단 푸터 부분 -->
   <c:import url="/WEB-INF/views/include/footer.jsp"/>
   <script>
   		function checkUserId(){
   			//입력한 id값을 가져오기
   			const user_id = $("#user_id").val();
   			//id가 없을경우
   			if(user_id.length == 0){
   				alert('아이디를 입력해주세요')
   				return
   			}
   			//id중복체크를 위해 서버에 요청함(ajax사용)
   			$.ajax({
   				url : '${root}user/check/' + user_id,
   				type : 'get',
   				dataType : 'text',
   				success : function(result){
   					if(result.trim() == 'true'){
   						alert('사용할 수 있는 아이디입니다')
   						//중복확인됨 체크, 히든입력에 true를 넣는다.
   						$("#userIdChecked").val('true')
   						$(".idCheck").text('')
   					} else {
   						alert('사용할 수 없는 아이디 입니다')
   						//중복확인 안됨
   						$("#userIdChecked").val('false')
   					}
   				}
   			})
   		}
   		function resetUserIdExist(){
   			$("#userIdChecked").val('false')
   		}
   </script>
  </body>
</html>
