$(document).ready(function () {
    var date = new Date();
    $('#monthSelect').val(date.getMonth()+1);
    $('#yearSelect').val(date.getFullYear());
    /*$('#linechartForm').submit(function (event) {
        event.preventDefault();
        refreshLineChart();
    });*/
    $('#accountSelect, #monthSelect, #yearSelect').on('change', function () {
        refreshLineChart();
    });
    $('.checkboxType').on('click', function () {
        refreshLineChart();
    });

});



function LoadLineChart(month, year, accountName, types) {
    $.ajax({
        type: 'POST',
        beforeSend: function (request) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(header, token);
        },
        contentType: 'application/json; charset=UTF-8',
        url: '/statistics',
        data: JSON.stringify({
            "month": month,
            "year": year,
            "accountName": accountName,
            "types": types
        })
    }).done(function (data) {
        drawLineChart(data);
    }).fail(function (error) {
        alert('FAIL ' + error);
    });
}
