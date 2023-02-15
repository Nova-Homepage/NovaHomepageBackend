<h1>20230215 현재까지 구현된 NovaBackEnd 기능 및 설명</h1>  

demo 의 서버 포트 8081

<h3> 1. OAuth2를 통한 소셜 로그인 기능 제공</h3>
OAuth2를 이용한 로그인은 해당 로그인 인증을 지원하는 링크를 통해 로그인 한 후 
access token을 발급받아야합니다.

각 로그인을 위한 링크는 아래와 같습니다
> localhost:8081/oauth2/authorize/{ 'ProviderId' }

여기서 ProviderId 란 OAuth2 로그인이 가능한 서비스 제공자를 의미합니다.  
현재 NovaBackEnd 에서 구현된 로그인 서비스의 제공자는 아래와 같습니다. 

>- github -> localhost:8081/oauth2/authorize/github</br>

>- google -> localhost:8081/oauth2/authorize/google</br>

<b><p style="font-size: 12px">** 위 주소는 서버가 열려있을때 동작합니다. 서버가 열려있지 않다면 동작하지 않습니다.</p></b>  

위 주소에서 로그인 할 경우 access token 이 발급됩니다.
해당 access token을 이용하여 서비스에 접근할 수 있습니다.
>ex)
> 
> <img src="/pic/token.PNG">

토큰은 user_id에 해당하는 정보 (sub), 권한정보, 토큰만료 정보를 담고 있습니다.
>ex)
> 
> <img src="/pic/token_info.PNG">

<h3>2. 권한 별 접근 가능한 리소스</h3>
>- GUEST -> localhost:8081/auth/guest/**
>- USER -> localhost:8081/auth/user/**
>- ADMIN -> localhost:8081/auth/admin/**

권한이 없는 리소스에 접근하였을 경우 </br>
권한없음 에러.</br>
Full authentication is required to access this resource 에러가 발생합니다.

<b><p style="font-size: 12px">** guest 가 접근 가능한 리소스는 permitAll 로써 모든 사람이 접근 가능합니다.


<h3>3. 처음 로그인 할 경우 저장되는 내용 설명</h3>

처음 로그인 한 경우 회원의 이름과 이메일, 등록일, 권한, 프로필 이미지 등의  데이터가 데이터베이스에 저장됩니다.</BR></BR>
처음 로그인 한 경우 모든 권한은 GUEST 권한을 갖습니다.</BR></BR>
GUEST 권한을 가진 회원은 USER, 및 ADMIN 권한을 가진 리소스에 접근할 수 없습니다.</p></b>


<h2> 추후 추가될 기능은 아래와 같습니다. </h2>
- 회원 탈퇴기능 2023 02 19 예정</BR></BR>
- 회원 권한 변경기능 (관리자 페이지) 2023 02 19 예정</BR></BR>
- <b>(임시)</b> 게시판 글 작성 및 댓글 작성기능  2023 02 23 예정</BR></BR>

