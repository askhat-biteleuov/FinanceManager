$(document).ready(function () {
    $('#rangeForm').submit(function (event) {
        event.preventDefault();
        refreshChart();
    });
});



function LoadChart(start, end, account) {
    $.ajax({
        type: 'POST',
        beforeSend: function (request) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(header, token);
        },
        contentType: 'application/json; charset=UTF-8',
        url: '/account/page',
        data: JSON.stringify({
            "start": start,
            "end": end,
            "accountName": account
        })
    }).done(function (data) {
        drawChart(data);
    }).fail(function (error) {
        alert('FAIL ' + error);
    });
}