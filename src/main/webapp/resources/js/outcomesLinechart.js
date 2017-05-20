$(document).ready(function () {
    $('#linechartForm').submit(function (event) {
        event.preventDefault();
        refreshLineChart();
    });
});



function LoadLineChart(month, year, accountName) {
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
            "accountName": accountName
        })
    }).done(function (data) {
        drawLineChart(data);
    }).fail(function (error) {
        alert('FAIL ' + error);
    });
}
