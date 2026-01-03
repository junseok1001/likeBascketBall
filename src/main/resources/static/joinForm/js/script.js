$(document).ready(function() {
    var isDuplicateCheck = false;
    // 회원 유형 카드 선택 효과
    $('.status-card').click(function() {
        $('.status-card').removeClass('active');
        $(this).addClass('active');
        $(this).find('input[type="radio"]').prop('checked', true);
    });

    // 아이디 중복 확인 버튼 클릭
    $('#btnCheckId').on("click",function(){
        let loginId = $("#loginIdInput").val();

        if(loginId.length < 4) {
            alert('아이디는 4자 이상 입력해주세요.');
            return;
        }

        $.ajax({
            type:"post"
            , url:"/user/isDuplicate"
            , data:{"loginId":loginId}
            , success:function(response){
                isDuplicateCheck = true;
                if(response.result === "success"){
                    $("#loginIdInput").data("duplicate", true);
                    $("#isAvailable").removeClass("d-none");
                }else{
                    $("#isDuplicate").removeClass("d-none");
                }
            }
            , error: function(){
                alert("중복확인 실패!");
            }
        });

    });

    //아이디 input에 값 바뀔시 바로 초기화
    $("#loginIdInput").on("input", function(){
        isDuplicateCheck = false;
        $("#loginIdInput").data("duplicate", false);

        $("#isAvailable").addClass("d-none");
        $("#isDuplicate").addClass("d-none");
    });

    // 폼 제출 (회원가입 버튼)
    $('#signupForm').on('submit', function(e) {
        e.preventDefault();

        let userStatus = $("input[name= 'user_status']:checked").val();
        let loginId = $("#loginIdInput").val();
        let password = $("#passwordInput").val();
        let passwordConfirm = $("#passwordConfirmInput").val();
        let nickName = $("#nickNameInput").val();
        let email = $("#emailInput").val();
        let birthday = $("#birthdayInput").val();
        let phoneNumber = $("#phoneNumberInput").val();


        // 유효성 검사 예시
        if(password.length < 8) {
            alert('비밀번호는 8자 이상이어야 합니다.');
            return;
        }

        if(password != passwordConfirm){
            alert("비밀번호가 맞는지 다시 확인하세요");
            return;
        }

        // 아이디 중복 체크 확인
        // 로직 구현
        if(!isDuplicateCheck){
            alert("아이디 중복을 확인하셔야 합니다");
            return;
        }


        $.ajax({
            type:"post"
            , url:"/user/join"
            , data:{"loginId":loginId, "password":password,
                    "nickName":nickName, "userStatus":userStatus,
                    "birthday":birthday, "email":email, "phoneNumber":phoneNumber}
            , success: function(response){
                if(response.result === "success"){
                    location.href="/main";
                }else{
                    alert("회원가입 실패!!");
                }
            }
            , error: function(){
                alert("회원가입 에러!!");
            }
        });
    });

    // 휴대폰 번호 자동 하이픈 (선택 사항)
    $('#phone_number').on('keyup', function() {
        $(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-"));
    });
});