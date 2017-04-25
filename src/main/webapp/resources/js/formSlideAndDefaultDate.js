$(document).ready(function () {
    $('#adding').on('click', 'button', function () {
        $(this).next('form').slideToggle();
    });

    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
    $('#incomeForm').find('[name=date]').on('focus', function () {
      $(this).val(today);
    });
    $('#outcomeForm').find('[name=date]').on('focus', function () {
        $(this).val(today);
    });
});