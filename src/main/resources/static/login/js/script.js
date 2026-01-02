/**
 * Basketball Hub - 메인 스크립트
 * (회원가입 유형 선택, 로그인/회원가입 처리, 소셜 로그인 연동 포함)
 */

$(document).ready(function() {
    
    /* ==========================================================================
       1. 네비게이션 및 스크롤 이벤트 (공통)
       ========================================================================== */
    $(window).scroll(function() {
        if ($(this).scrollTop() > 50) {
            $('.main-nav').addClass('scrolled');
        } else {
            $('.main-nav').removeClass('scrolled');
        }
    });


    /* ==========================================================================
       2. 회원가입 유형 선택 인터랙션 (Signup)
       ========================================================================== */
    $('.user-type-card').on('click', function() {
        // 모든 카드의 활성 상태 해제 후 클릭한 카드만 활성화
        $('.user-type-card').removeClass('active'); 
        $(this).addClass('active'); 
        
        // 데이터 속성에서 유형 추출 (예: RENTER, GENERAL 등)
        const selectedType = $(this).data('type');
        console.log("선택된 회원 유형:", selectedType);

        // 라디오 버튼이 있는 경우 체크 처리
        $(this).find('input[type="radio"]').prop('checked', true);
    });


    /* ==========================================================================
       3. 로그인 처리 및 소셜 로그인 (Login)
       ========================================================================== */
    
    // 일반 로그인 폼 제출
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        
        const loginId = $("#loginInput").val();
        const password = $("#passwordInput").val();

        // [TODO] 서버 API 연동 (AJAX)
        console.log("로그인 시도 ID:", loginId);
        alert("로그인을 시도합니다.\n아이디: " + loginId);
        
        /* $.ajax({
            url: '/api/login',
            method: 'POST',
            data: { login_id: loginId, password: password },
            success: function(res) { ... },
            error: function(err) { ... }
        });
        */
    });

    // 카카오 로그인 버튼 클릭
    $('.btn-kakao').on('click', function(e) {
        e.preventDefault();
        console.log("카카오 로그인 API 호출");
        
        // 카카오 SDK 초기화 및 로그인 로직이 들어갈 자리입니다.
        alert("카카오톡 간편 로그인을 시작합니다.");
        // window.location.href = 'YOUR_KAKAO_AUTH_URL';
    });

    // 구글 로그인 버튼 클릭
    $('.btn-google').on('click', function(e) {
        e.preventDefault();
        console.log("구글 로그인 API 호출");
        
        // 구글 OAuth2 로직이 들어갈 자리입니다.
        alert("구글 계정으로 로그인을 시작합니다.");
        // window.location.href = 'YOUR_GOOGLE_AUTH_URL';
    });


    /* ==========================================================================
       4. 회원가입 처리 (Signup Form)
       ========================================================================== */
    $('#signupForm').on('submit', function(e) {
        e.preventDefault();

        // 폼 데이터를 객체화
        const formData = $(this).serialize();
        console.log("회원가입 제출 데이터:", formData);

        // [TODO] 서버 API 연동 (AJAX)
        alert('회원가입 신청이 완료되었습니다!');
    });


    /* ==========================================================================
       5. 기타 유틸리티 (휴대폰 번호 자동 하이픈 등)
       ========================================================================== */
    $('#phone_number').on('keyup', function() {
        $(this).val($(this).val()
            .replace(/[^0-9]/g, "")
            .replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3")
            .replace("--", "-"));
    });

});