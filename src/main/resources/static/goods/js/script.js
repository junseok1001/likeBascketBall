$(document).ready(function() {
    // 1. 이미지 미리보기 로직
    $('#imageInput').on('change', function(e) {
        const files = e.target.files;
        $('#imagePreview').empty();
        
        for(let i=0; i<files.length; i++) {
            const reader = new FileReader();
            reader.onload = function(event) {
                // market.css의 preview-img 클래스 사용
                const html = `
                    <div class="preview-img-container">
                        <img src="${event.target.result}" class="preview-img">
                    </div>`;
                $('#imagePreview').append(html);
            }
            reader.readAsDataURL(files[i]);
        }
    });

    // 2. 폼 제출 로직
    $('#marketPostForm').on('submit', function(e) {
        e.preventDefault();
        alert('중고 상품 등록이 완료되었습니다!');
        location.href = 'market.html'; // 등록 후 리스트로 이동
    });
});