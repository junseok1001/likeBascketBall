$(document).ready(function() {
    // 1. 스크롤 시 네비게이션 스타일 변경
    $(window).scroll(function() {
        if ($(this).scrollTop() > 100) {
            $('.main-nav').addClass('scrolled');
        } else {
            $('.main-nav').removeClass('scrolled');
        }
    });

    // 2. 스무스 스크롤 (Anchor 링크 클릭 시)
    $('a.nav-link').on('click', function(event) {
        if (this.hash !== "") {
            event.preventDefault();
            var hash = this.hash;
            $('html, body').animate({
                scrollTop: $(hash).offset().top - 80
            }, 800);
        }
    });

    // 3. 버튼 클릭 시 피드백 (간단 로직)
    $('.btn-orange, .btn-dark').on('click', function() {
        console.log("Button Clicked: " + $(this).text().trim());
    });
});