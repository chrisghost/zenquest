$(function() {
  $(".clickable").click(function() { window.location.pathname = $(this).data('url') });
})
