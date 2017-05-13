$(document).ready(function () {
    $('#adding').on('click', 'button', function () {
        $(this).next('form').slideToggle();
    });

    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var firstDay = now.getFullYear()+"-"+(month)+"-"+("01");
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
    $('#start').val(firstDay);
    $('#end').val(today);
    $('#incomeDate').val(today);
    $('#outcomeDate').val(today);
    $('#transferDate').val(today);
});