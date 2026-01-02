$(document).ready(function() {
        // 회원 유형 카드 선택 효과
        $('.status-card').click(function() {
            $('.status-card').removeClass('active');
            $(this).addClass('active');
            $(this).find('input[type="radio"]').prop('checked', true);
        });

        // 아이디 중복 확인 버튼 클릭
        $('#btnCheckId').click(function() {
            const loginId = $('#login_id').val();
            if(loginId.length < 4) {
                alert('아이디는 4자 이상 입력해주세요.');
                return;
            }
            // AJAX 중복 체크 로직 (예시)
            alert('사용 가능한 아이디입니다.');
        });

        // 폼 제출 (회원가입 버튼)
        $('#signupForm').on('submit', function(e) {
            e.preventDefault();

            // 유효성 검사 예시
            const password = $('#password').val();
            if(password.length < 8) {
                alert('비밀번호는 8자 이상이어야 합니다.');
                return;
            }

            // 전체 데이터 직렬화
            const formData = $(this).serialize();
            console.log("제출 데이터:", formData);

            alert('회원가입이 완료되었습니다!');
            // location.href = 'login.html';
        });

        // 휴대폰 번호 자동 하이픈 (선택 사항)
        $('#phone_number').on('keyup', function() {
            $(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-"));
        });
    });