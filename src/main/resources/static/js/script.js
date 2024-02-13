// basic-N42 [TPLrQm20HZ]
(function() {
  $(function() {
    $(".basic-N42").each(function() {
      const $block = $(this);
      // Swiper
      const swiper = new Swiper(".basic-N42 .contents-swiper", {
        slidesPerView: 'auto',
        spaceBetween: 0,
        allowTouchMove: false,
        loop: true,
        autoplay: {
          delay: 5000,
        },
        navigation: {
          nextEl: ".basic-N42 .swiper-button-next",
          prevEl: ".basic-N42 .swiper-button-prev",
        },
        pagination: {
          type: "progressbar",
          el: ".basic-N42 .swiper-pagination",
          clickable: true,
        },
      });
    });
  });
})();
// basic-N50 [HBlrPvJp28]
(function() {
  $(function() {
    $(".basic-N50").each(function() {
      const $block = $(this);
      const $dim = $block.find('.contents-dim');
      // Mobile Filter Open
      $block.find('.btn-filter').on('click', function() {
        $block.addClass('filter-active');
        $dim.fadeIn();
      });
      // Mobile Filter Close
      $block.find('.btn-close, .contents-dim').on('click', function() {
        $block.removeClass('filter-active');
        $dim.fadeOut();
      });
    });
  });
})();
// basic-N51 [BJlRpvYY5G]
(function() {
  $(function() {
    $(".basic-N51").each(function() {
      const $block = $(this);
      const $thumbnail = $block.find('.contents-thumbnail .contents-thumbimg');
      const $thumbitem = $block.find('.contents-thumbitem .contents-thumbimg');
      // Thumbnail Click Event
      $thumbitem.on("click", changePic);

      function changePic() {
        const newPic = $(this).attr("src");
        $thumbnail.attr("src", newPic);
      }
      // Like Button Click Event
      $block.find('.btn-like-line').on('click', function() {
        $block.find('.contents-brand-group').addClass('like-on');
      });
      $block.find('.btn-like-fill').on('click', function() {
        $block.find('.contents-brand-group').removeClass('like-on');
      });
      // Amount Count Button Click Event
      $block.find(".contents-amount").each(function() {
        const $this = $(this);
        const $amountNumElement = $this.find(".contents-amount-num");
        $this.on("click", ".btn-minus", function() {
          let amountNum = parseInt($amountNumElement.text());
          if (amountNum > 1) {
            amountNum--;
          }
          $amountNumElement.text(amountNum);
        });
        $this.on("click", ".btn-plus", function() {
          let amountNum = parseInt($amountNumElement.text());
          amountNum++;
          $amountNumElement.text(amountNum);
        });
      });
    });
  });
})();

window.onload = function() {
  // 수정 버튼을 가져오고 클릭 이벤트에 대한 핸들러를 추가합니다.
  var editButton = document.querySelector('.btn.btn-outline-dark');
  editButton.addEventListener('click', function() {
    var reviewContent = this.parentNode.parentNode.previousSibling.previousSibling.firstChild;
    var newContent = prompt("후기를 수정해주세요:", reviewContent.textContent);
    if (newContent !== null) {
      reviewContent.textContent = newContent;
    }
  });

  // 삭제 버튼을 가져오고 클릭 이벤트에 대한 핸들러를 추가합니다.
  var deleteButton = document.querySelector('.btn.btn-dark');
  deleteButton.addEventListener('click', function() {
    if (confirm("이 후기를 정말로 삭제하시겠습니까?")) {
      this.parentNode.parentNode.parentNode.remove();
    }
  });
}
