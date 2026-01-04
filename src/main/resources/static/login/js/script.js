/**
 * Basketball Hub - 메인 스크립트
 * (회원가입 유형 선택, 로그인/회원가입 처리, 소셜 로그인 연동 포함)
 */

$(document).ready(function() {
    
    /* 1. 네비게이션 및 스크롤 이벤트 (공통) */
    $(window).scroll(function() {
        if ($(this).scrollTop() > 50) {
            $('.main-nav').addClass('scrolled');
        } else {
            $('.main-nav').removeClass('scrolled');
        }
    });


    /* 로그인 처리 및 소셜 로그인 (Login) */
    
    // 일반 로그인 폼 제출
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        
        let loginId = $("#loginInput").val();
        let password = $("#passwordInput").val();

        
        $.ajax({
            type:"post"
            , url:"/user/login"
            , data:{"loginId":loginId, "password": password}
            , success: function(response){

                if(response.result === "success"){
                    location.href="/main";
                }else{
                    location.reload();
                }

            }
            , error: function(){
                alert("로그인 오류!!!");
            }
        });

    });

    // 카카오 로그인 버튼 클릭
    $('.btn-kakao').on('click', function(e) {
        e.preventDefault();
        console.log("카카오 로그인 API 호출");
        
        // 카카오 SDK 초기화 및 로그인 로직이 들어갈 자리입니다.
        alert("카카오톡 간편 로그인을 시작합니다.");
        // window.location.href = 'YOUR_KAKAO_AUTH_URL';
    });



    /* 기타 유틸리티 (휴대폰 번호 자동 하이픈 등) */
    $('#phone_number').on('keyup', function() {
        $(this).val($(this).val()
            .replace(/[^0-9]/g, "")
            .replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3")
            .replace("--", "-"));
    });

});